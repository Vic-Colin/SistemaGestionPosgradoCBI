// ================== INIT GENERAL ==================
document.addEventListener("DOMContentLoaded", init);

function init() {
    configurarCamposDinamicos();
    initAcordeon();
    initModal();
    initTabla();
    initFiltros();
    cargarAlumnos(); 
}

// ================== CONFIGURACIÓN DINÁMICA ==================
function configurarCamposDinamicos() {
    const fecha = new Date();
    const anioActualCorto = fecha.getFullYear() % 100;

    const regIngreso = document.getElementById("regIngreso");
    const regPierde = document.getElementById("regPierdeCalidad");

    if (regIngreso) {
        regIngreso.innerHTML = '<option value="" disabled selected>Trimestre Ingreso...</option>';
        for (let y = anioActualCorto; y >= 16; y--) {
            ["O", "P", "I"].forEach(t => {
                regIngreso.add(new Option(`${y}-${t}`, `${y}-${t}`));
            });
        }
    }

    if (regPierde) {
        regPierde.innerHTML = '<option value="" disabled selected>Trimestre pierde calidad...</option>';
        for (let y = anioActualCorto; y <= anioActualCorto + 6; y++) {
            ["O", "P", "I"].forEach(t => {
                regPierde.add(new Option(`${y}-${t}`, `${y}-${t}`));
            });
        }
    }
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

function prepararEdicion(btn) {
    const fila = btn.closest("tr");
    document.getElementById("modalTitulo").innerText = "Editar Información del Alumno";
    document.getElementById("editRowIndex").value = "1"; // Indica Edición (cualquier valor distinto a -1)

    const get = i => fila.cells[i].innerText.trim();

    setFormValues({
        matricula: get(0),
        nombre: get(1),
        correoInst: get(2),
        correoAlt: get(3),
        tel: get(4),
        ingreso: get(5),
        pierdeCalidad: get(7)
        // Por ahora el CRUD de AlumnoDAO solo maneja estos datos básicos.
        // Si quieres editar la tesis o beca, necesitarás métodos extra en el DAO.
    });

    // Proteger matrícula en edición (es llave primaria)
    document.getElementById("regMatricula").readOnly = true; 
    modal.style.display = "block";
}

let matriculaAEliminar = null;
function confirmarEliminar(btn) {
    const fila = btn.closest("tr");
    matriculaAEliminar = fila.cells[0].innerText.trim();
    modalEliminar.style.display = "block";
    
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
        nombre: val("regNombre"),
        correoInst: val("regCorreoInst"),
        correoAlt: val("regCorreoAlt"),
        telefono: val("regTel"),
        trimIngreso: val("regIngreso"),
        trimPierde: val("regPierdeCalidad"),
        
        // Beca
        cvu: val("regCVU"),
        estatusBeca: val("regEstatusBeca"),
        fechaInicioBeca: val("regFechaInicioBeca"),
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
