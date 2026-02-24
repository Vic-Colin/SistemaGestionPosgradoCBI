// ================== INIT GENERAL ==================
document.addEventListener("DOMContentLoaded", init);

function init() {
    configurarCamposDinamicos();
    initAcordeon();
    initModal();
    initTabla();
    initFiltros();
    cargarCatalogos();
    cargarAlumnos(); 
}

// ================== CONFIGURACIÓN DINÁMICA ==================
function configurarCamposDinamicos() {
    const fecha = new Date();
    const anioActualCorto = fecha.getFullYear() % 100;

    const regIngreso = document.getElementById("regIngreso");
    const regPierde = document.getElementById("regPierdeCalidad");
    const pad = (n) => n < 10 ? '0' + n : n;
    if (regIngreso) {
        regIngreso.innerHTML = '<option value="" disabled selected>Trimestre Ingreso...</option>';
        for (let y = anioActualCorto; y >= 16; y--) {
            ["O", "P", "I"].forEach(t => {
                let valor = `${pad(y)}-${t}`;
                regIngreso.add(new Option(valor, valor));
            });
        }
    }

    if (regPierde) {
        regPierde.innerHTML = '<option value="" disabled selected>Trimestre pierde calidad...</option>';
        for (let y = anioActualCorto - 5; y <= anioActualCorto + 8; y++) {
            ["O", "P", "I"].forEach(t => {
                let valor = `${pad(y)}-${t}`;
                regPierde.add(new Option(valor, valor));
            });
        }
    }
}

function cargarCatalogos() {
    fetch('../AlumnadoServlet?accion=catalogos')
        .then(response => {
            if (!response.ok) throw new Error("Error obteniendo catálogos");
            return response.json();
        })
        .then(data => {
            const selectArea = document.getElementById("alumArea");
            const selectDirector = document.getElementById("alumDirector");
            const selectCodirector = document.getElementById("alumCodirector");

            // 1. Limpiar y establecer opción por defecto
            selectArea.innerHTML = '<option value="" disabled selected>Seleccione un área...</option>';
            selectDirector.innerHTML = '<option value="" disabled selected>Seleccione director...</option>';
            selectCodirector.innerHTML = '<option value="" selected>Sin Codirector</option>'; // Este puede quedar vacío

            // 2. Llenar áreas de concentración
            data.areas.forEach(area => {
                selectArea.add(new Option(area.nombre, area.idArea));
            });

            // 3. Llenar directores y codirectores
            data.profesores.forEach(prof => {
                selectDirector.add(new Option(prof.nombreCompleto, prof.numeroEconomico));
                selectCodirector.add(new Option(prof.nombreCompleto, prof.numeroEconomico));
            });
        })
        .catch(err => console.error("Error al cargar catálogos:", err));
}

// ================== ACORDEÓN ====================
function initAcordeon() {
    const headers = document.querySelectorAll(".accordion-header");

    headers.forEach(header => {
        const body = document.getElementById(header.dataset.target);
        const icono = header.querySelector(".icono");

        header.addEventListener("click", () => {
            const abierto = body.style.maxHeight && body.style.maxHeight !== "0px";

            if (abierto) {
                body.style.maxHeight = "0px";
                icono.style.transform = "rotate(0deg)";
            } else {
                body.style.maxHeight = body.scrollHeight + "px";
                icono.style.transform = "rotate(90deg)";
            }
        });
    });
}

// ================== MODAL ==================
let modal, modalEliminar, form, tablaBody;

function initModal() {
    modal = document.getElementById("modalRegistro");
    modalEliminar = document.getElementById("modalEliminar");
    form = document.getElementById("formNuevoAlumno");
    tablaBody = document.querySelector("#tablaAlumnos tbody");

    const btnNuevo = document.getElementById("btnAbrirModal");
    const btnCerrarX = document.getElementById("btnCerrarX");
    const btnCancelar = document.getElementById("btnCancelarModal");
    const btnCancelarDel = document.getElementById("btnCancelarEliminar");

    if (btnNuevo) {
        btnNuevo.addEventListener("click", abrirModalNuevo);
    }

    btnCerrarX?.addEventListener("click", cerrarModal);
    btnCancelar?.addEventListener("click", cerrarModal);
    btnCancelarDel?.addEventListener("click", () => modalEliminar.style.display = "none");

    form.addEventListener("submit", guardarAlumno);
}

function abrirModalNuevo() {
    document.getElementById("modalTitulo").innerText = "Registrar Nuevo Alumno";
    document.getElementById("editRowIndex").value = "-1";
    form.reset();
    modal.style.display = "block";
}

function cerrarModal() {
    modal.style.display = "none";
}

// ================== TABLA Y CARGA AJAX ==================
function initTabla() {
    window.prepararEdicion = prepararEdicion;
    window.confirmarEliminar = confirmarEliminar;
}
window.alumnosData = [];
// 🟢 NUEVO: Función para pedir datos al servidor
function cargarAlumnos() {
    const params = new URLSearchParams({
        matricula: document.getElementById("filtroMatricula")?.value.trim() || "",
        trimIngreso: document.getElementById("filtroTrimestre")?.value || "",
        trimPierdeCalidad: document.getElementById("filtroPierdeCalidad")?.value || "",
        anioTitulacion: document.getElementById("filtroAnioTit")?.value || "",
        estatusUam: document.getElementById("filtroEstatus")?.value || ""
    });

    fetch(`../AlumnadoServlet?${params.toString()}`)
        .then(response => {
            if (!response.ok) throw new Error("Error en red");
            return response.json();
        })
        .then(data => {
            window.alumnosData = data;
            tablaBody.innerHTML = "";
            if(data.length === 0){
                 tablaBody.innerHTML = `<tr><td colspan="17" style="text-align:center;">No se encontraron registros</td></tr>`;
                 return;
            }
            data.forEach(alumno => {
                tablaBody.innerHTML += renderRow(alumno);
            });
        })
        .catch(err => console.error("Error al cargar alumnos:", err));
}

function renderRow(d) {
    const val = (v) => v ? v : '-'; // Función para manejar nulls
    return `
        <tr>
            <td>${d.matricula}</td>
            <td>${val(d.curp)}</td>
            <td>${d.nombreCompleto}</td>
            <td>${val(d.correoInstitucional)}</td>
            <td>${val(d.correoAlternativo)}</td>
            <td>${val(d.telefono)}</td>
            <td>${val(d.trimestreIngreso)}</td>
            <td>${val(d.fechaInicio)}</td>
            <td>${val(d.trimestrePierdeCalidad)}</td>
            <td><span class="status-badge">${val(d.estatusUam)}</span></td>
            <td>${val(d.estatusBeca)}</td>
            <td>${val(d.numeroActa)}</td>
            <td>${val(d.fechaTitulacion)}</td>
            <td>${val(d.tituloTesis)}</td>
            <td>${val(d.areaConcentracion)}</td>
            <td>${val(d.director)}</td>
            <td>${val(d.codirector)}</td>
            <td>
                <div style="display:flex; gap:8px;">
                    <button type="button" class="btn-action-icon edit" onclick="prepararEdicion(this)">✏️</button>
                    <button type="button" class="btn-action-icon delete" onclick="confirmarEliminar(this)">🗑️</button>
                </div>
            </td>
        </tr>
    `;
}

function seleccionarOpcionRobusta(selectId, valorBuscado) {
    if (!valorBuscado) return;
    const select = document.getElementById(selectId);
    if (!select) return;

    // Limpiamos el valor buscado: quitamos espacios, guiones y pasamos a Mayúsculas
    const limpiar = (t) => String(t).replace(/-/g, '').trim().toUpperCase();
    const buscadoLimpio = limpiar(valorBuscado);

    for (let option of select.options) {
        if (limpiar(option.value) === buscadoLimpio || 
            limpiar(option.text) === buscadoLimpio) {
            select.value = option.value;
            return;
        }
    }
}

function prepararEdicion(btn) {
    const fila = btn.closest("tr");
    const matricula = fila.cells[0].innerText.trim();

    // Consultar al servidor los datos completos del alumno
    fetch(`../AlumnadoServlet?accion=buscar&matricula=${matricula}`)
        .then(response => {
            if(!response.ok) throw new Error("Error en la red");
            return response.text(); 
        })
        .then(text => {
            // Limpieza de seguridad del JSON por si el servidor envía algún salto de línea
            const cleanJson = text.substring(text.indexOf('{'), text.lastIndexOf('}') + 1);
            const alumno = JSON.parse(cleanJson);

            if (!alumno) return;

            document.getElementById("modalTitulo").innerText = "Editar Información del Alumno";
            document.getElementById("editRowIndex").value = "1"; // Indica Edición

            // --- 1. Datos Generales ---
            document.getElementById("regMatricula").value = alumno.matricula || "";
            document.getElementById("regMatricula").readOnly = true; 
            document.getElementById("regCurp").value = alumno.curp || "";
            document.getElementById("regNombre").value = alumno.nombreCompleto || "";
            document.getElementById("regCorreoInst").value = alumno.correoInstitucional || "";
            document.getElementById("regCorreoAlt").value = alumno.correoAlternativo || "";
            document.getElementById("regTel").value = alumno.telefono || "";
            //document.getElementById("regEstatus").value = alumno.estatusUam || "Vigente";
            seleccionarOpcionRobusta("regEstatus", alumno.estatusUam);

            // --- 2. Datos Académicos ---
            document.getElementById("regIngreso").value = alumno.trimestreIngreso || "";
            //document.getElementById("regPierdeCalidad").value = alumno.trimestrePierdeCalidad || "";
            seleccionarOpcionRobusta("regPierdeCalidad", alumno.trimestrePierdeCalidad);
            document.getElementById("regFechaIngUam").value = alumno.fechaInicio || "";

            // --- 3. Beca SECIHTI ---
            document.getElementById("regCVU").value = alumno.cvu || "";
            document.getElementById("regEstatusBeca").value = alumno.estatusBeca || "";
            document.getElementById("regFechaFinBeca").value = alumno.regFechaFinBeca || "";
            document.getElementById("regFechaMax").value = alumno.regFechaMax || "";

            // --- 4. Titulación ---
            document.getElementById("regActa").value = alumno.numeroActa || "";
            document.getElementById("regFechaTit").value = alumno.fechaTitulacion || "";

            // --- 5. Proyecto de Tesis ---
            document.getElementById("alumTituloTesis").value = alumno.tituloTesis || "";
            // Asignación de llaves foráneas a los selects dinámicos
            document.getElementById("alumArea").value = alumno.idAreaConcentracion || "";
            document.getElementById("alumDirector").value = alumno.numEcoDirector || "";
            document.getElementById("alumCodirector").value = alumno.numEcoCodirector || "";

            // Mostrar modal
            modal.style.display = "block";

            // Forzar actualización visual de acordeones si es necesario
            const bodies = document.querySelectorAll(".accordion-body");
            bodies.forEach(body => {
                if (body.style.maxHeight && body.style.maxHeight !== "0px") {
                    body.style.maxHeight = body.scrollHeight + "px";
                }
            });
        })
        .catch(err => console.error("Error al obtener detalle del alumno:", err));
}

let matriculaAEliminar = null;
function confirmarEliminar(btn) {
    const fila = btn.closest("tr");
    matriculaAEliminar = fila.cells[0].innerText.trim();
    modalEliminar.style.display = "block";
    modalEliminar.style.zIndex = "10000";
    
    const modalContent = modalEliminar.querySelector('.modal-content');
    if(modalContent) {
        modalContent.style.margin = "15% auto";
    }
    
    
    document.getElementById("btnConfirmarBorrado").onclick = () => {
        // Enviar petición POST al servidor
        fetch('../AlumnadoServlet', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: `accion=eliminar&matricula=${encodeURIComponent(matriculaAEliminar)}`
        })
        .then(res => res.text())
        .then(res => {
            if (res === "OK") {
                modalEliminar.style.display = "none";
                cargarAlumnos(); // Recargar tabla
            } else {
                alert("Error al eliminar. Puede que el alumno tenga registros vinculados en otras tablas.");
            }
        });
    };
}

// ================== FORMULARIO AJAX ==================
function getAlumnoFromForm() {
    const val = id => document.getElementById(id)?.value || "";
    return {
        // Datos Personales
        matricula: val("regMatricula"),
        curp: val("regCurp"),
        nombre: val("regNombre"),
        correoInst: val("regCorreoInst"),
        correoAlt: val("regCorreoAlt"),
        telefono: val("regTel"),
        trimIngreso: val("regIngreso"),
        trimPierde: val("regPierdeCalidad"),
        estatusUam: val("regEstatus"),
        
        // Beca
        cvu: val("regCVU"),
        estatusBeca: val("regEstatusBeca"),
        fechaFinBeca: val("regFechaFinBeca"),
        fechaMaxBeca: val("regFechaMax"),
        
        // Titulación
        acta: val("regActa"),
        fechaTit: val("regFechaTit"),
        
        // Tesis
        tituloTesis: val("alumTituloTesis"),
        areaTesis: val("alumArea"),
        directorTesis: val("alumDirector"),
        codirectorTesis: val("alumCodirector")
    };
}
function setFormValues(d) {
    const map = {
        matricula: "regMatricula",
        nombre: "regNombre",
        correoInst: "regCorreoInst",
        correoAlt: "regCorreoAlt",
        tel: "regTel",
        ingreso: "regIngreso",
        pierdeCalidad: "regPierdeCalidad"
    };
    Object.entries(d).forEach(([k, v]) => {
        const el = document.getElementById(map[k]);
        if (el && v !== "-") el.value = v;
    });
}

function guardarAlumno(e) {
    e.preventDefault();
    const dataObj = getAlumnoFromForm();
    const isEdit = document.getElementById("editRowIndex").value !== "-1";
    
    // Preparar datos para enviar
    const params = new URLSearchParams(dataObj);
    params.append("accion", isEdit ? "editar" : "crear");

    fetch('../AlumnadoServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params.toString()
    })
    .then(res => res.text())
    .then(res => {
        if (res === "OK") {
            cerrarModal();
            cargarAlumnos(); // Refrescar la tabla
        } else {
            alert("Ocurrió un error al guardar. Verifica la matrícula (podría estar duplicada).");
        }
    })
    .catch(err => console.error("Error guardando:", err));
}

// ================== FILTROS AJAX ==================
function initFiltros() {
    // 🟢 MODIFICADO: Ahora los filtros llaman al servidor (Delay)
    let timeoutFiltros;
    const triggerFiltro = () => {
        clearTimeout(timeoutFiltros);
        timeoutFiltros = setTimeout(cargarAlumnos, 300);
    };

    document.getElementById("filtroMatricula")?.addEventListener("input", triggerFiltro);
    document.getElementById("filtroAnioTit")?.addEventListener("change", cargarAlumnos);
    document.getElementById("filtroTrimestre")?.addEventListener("change", cargarAlumnos);
    document.getElementById("filtroPierdeCalidad")?.addEventListener("change", cargarAlumnos);
    document.getElementById("filtroEstatus")?.addEventListener("change", cargarAlumnos);
}
