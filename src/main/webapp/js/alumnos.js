/**
 * alumnos.js - Gestión de Expediente Maestro (13 Columnas)
 */
/**
 * Genera las opciones de los select de forma dinámica basado en el año actual
 */
function configurarCamposDinamicos() {
    const fecha = new Date();
    const anioActualCorto = fecha.getFullYear() % 100;
    
    const regIngreso = document.getElementById("regIngreso");
    const regPierde = document.getElementById("regPierdeCalidad");
    //const regEstatusBeca = document.getElementById("regEstatusBeca");
    //regEstatusBeca.innerHTML='<option value="" disabled selected> Selecciona...</option>';

    if (regIngreso) {
        // Limpiar y agregar opciones desde el 2016 hasta el año actual
        regIngreso.innerHTML = '<option value="" disabled selected>Trimestre Ingreso...</option>';
        for (let y = anioActualCorto; y >= 16; y--) {
            ["O", "P", "I"].forEach(t => {
                regIngreso.add(new Option(`${y}-${t}`, `${y}-${t}`));
            });
        }
    }

    if (regPierde) {
        // Limpiar y agregar opciones desde el año actual hasta 6 años al futuro
        regPierde.innerHTML = '<option value="" disabled selected>Selecciona...</option>';
        for (let y = anioActualCorto; y <= anioActualCorto + 6; y++) {
            ["O", "P", "I"].forEach(t => {
                regPierde.add(new Option(`${y}-${t}`, `${y}-${t}`));
            });
        }
    }
}

document.addEventListener("DOMContentLoaded", () => {
    console.log("Script alumnos.js cargado correctamente.");
    configurarCamposDinamicos();
    // --- REFERENCIAS DOM ---
    const modal = document.getElementById("modalRegistro");
    const modalEliminar = document.getElementById("modalEliminar");
    const form = document.getElementById("formNuevoAlumno");
    const tablaBody = document.querySelector("#tablaAlumnos tbody");
    
    // Botones (Usamos IDs para mayor seguridad)
    const btnNuevo = document.getElementById("btnAbrirModal");
    const btnCerrarX = document.getElementById("btnCerrarX");
    const btnCancelar = document.getElementById("btnCancelarModal");
    const btnCancelarDel = document.getElementById("btnCancelarEliminar");
    
    // --- FUNCIONES DE APERTURA/CIERRE ---
    
    // Abrir Modal Nuevo
    if(btnNuevo) {
        btnNuevo.addEventListener("click", () => {
            document.getElementById("modalTitulo").innerText = "Registrar Nuevo Alumno";
            document.getElementById("editRowIndex").value = "-1";
            form.reset();
            // Reset placeholders
            document.getElementById("regIngreso").value = "";
            document.getElementById("regPierdeCalidad").value = "";
            document.getElementById("regEstatus").value = "";
            document.getElementById("regEstatusBeca").value = "NO TUVO BECA";
            modal.style.display = "block";
        });
    } else {
        console.error("No se encontró el botón 'btnAbrirModal'. Revisa el ID en el JSP.");
    }

    // Cerrar Modales
    const cerrar = () => modal.style.display = "none";
    const cerrarDel = () => modalEliminar.style.display = "none";

    if(btnCerrarX) btnCerrarX.addEventListener("click", cerrar);
    if(btnCancelar) btnCancelar.addEventListener("click", cerrar);
    if(btnCancelarDel) btnCancelarDel.addEventListener("click", cerrarDel);

    // --- FUNCIONES GLOBALES (Para botones dentro de la tabla) ---
    // Deben estar en 'window' para que el onclick="prepararEdicion(this)" funcione
    
    // --- PREPARAR EDICIÓN (ACTUALIZADO) ---
    window.prepararEdicion = (btn) => {
        const fila = btn.closest("tr");
        document.getElementById("modalTitulo").innerText = "Editar Información del Alumno";
        document.getElementById("editRowIndex").value = fila.sectionRowIndex;

        const getVal = (idx) => {
            const txt = fila.cells[idx].innerText.trim();
            return (txt === "-" || txt === "Sin comentarios") ? "" : txt;
        };

        // Seteamos los campos existentes
        document.getElementById("regMatricula").value = getVal(0);
        document.getElementById("regNombre").value = getVal(1);
        document.getElementById("regCorreoInst").value = getVal(2);
        document.getElementById("regCorreoAlt").value = getVal(3);
        document.getElementById("regTel").value = getVal(4);
        document.getElementById("regIngreso").value = getVal(5); 
        document.getElementById("regFechaIngUam").value = getVal(6); 
        document.getElementById("regPierdeCalidad").value = getVal(7); 
        document.getElementById("regEstatus").value = fila.cells[8].innerText.trim(); 
        document.getElementById("regEstatusBeca").value = fila.cells[9].innerText.trim(); 
        document.getElementById("regActa").value = getVal(10);
        document.getElementById("regFechaTit").value = getVal(11); 
        document.getElementById("regComentarios").value = getVal(12);

        // Los campos CVU e Inicio/Fin de beca no están en la tabla de alumnos, 
        // pero se cargarán desde el backend en el futuro. Por ahora los dejamos limpios o con lógica dummy.
        document.getElementById("regCVU").value = ""; 
        document.getElementById("regFechaInicioBeca").value = "";
        document.getElementById("regFechaFinBeca").value = "";

        modal.style.display = "block";
    };

    let filaAEliminar = null;
    window.confirmarEliminar = (btn) => {
        filaAEliminar = btn.closest("tr");
        modalEliminar.style.display = "block";
    };

    document.getElementById("btnConfirmarBorrado").addEventListener("click", () => {
        if(filaAEliminar) {
            filaAEliminar.remove();
            filaAEliminar = null;
            cerrarDel();
            aplicarFiltros();
        }
    });

    // --- GUARDAR DATOS (ACTUALIZADO) ---
    form.addEventListener("submit", (e) => {
        e.preventDefault();
        const index = document.getElementById("editRowIndex").value;
        
        const val = (id) => document.getElementById(id).value || "-";
        
        // Creamos un objeto que ya tiene TODO lo de alumno y beca
        const d = {
            m: val("regMatricula"),
            n: val("regNombre"),
            ci: document.getElementById("regCorreoInst").value || "-",
            ca: document.getElementById("regCorreoAlt").value || "-",
            t: val("regTel"),
            i: val("regIngreso"),
            fiu: val("regFechaIngUam"),
            pc: val("regPierdeCalidad"),
            e: document.getElementById("regEstatus").value || "Vigente",
            // Campos de Beca
            eb: document.getElementById("regEstatusBeca").value || "-",
            cvu: val("regCVU"),
            fbi: val("regFechaInicioBeca"),
            fbf: val("regFechaFinBeca"),
            fm: document.getElementById("regFechaMax").value || "-",
            // Otros
            a: val("regActa"),
            ft: val("regFechaTit"),
            c: document.getElementById("regComentarios").value || "Sin comentarios"
        };

        // Imprimimos en consola para verificar que el FRONTEND ya captura la beca
        console.log("Expediente Maestro capturado para matrícula: " + d.m, d);

        const html = `
            <td>${d.m}</td>
            <td>${d.n}</td>
            <td>${d.ci}</td>
            <td>${d.ca}</td>
            <td>${d.t}</td>
            <td>${d.i}</td>
            <td>${d.fiu}</td>
            <td>${d.pc}</td>
            <td><span class="status-badge">${d.e}</span></td>
            <td>${d.eb}</td>
            <td>${d.a}</td>
            <td>${d.ft}</td>
            <td>${d.c}</td>
            <td>
                <div style="display: flex; gap: 8px;">
                    <button class="btn-action-icon edit" onclick="prepararEdicion(this)">✏️</button>
                    <button class="btn-action-icon delete" onclick="confirmarEliminar(this)">🗑️</button>
                </div>
            </td>
        `;

        if (index === "-1") {
            const row = tablaBody.insertRow();
            row.innerHTML = html;
        } else {
            tablaBody.rows[index].innerHTML = html;
        }

        cerrar();
        aplicarFiltros();
    });

    // --- FILTROS ---
    // Referencias a los elementos del DOM (Nota: filtroMatricula ya no existe)
    const filtroMatricula = document.getElementById("filtroMatricula");
    const filtroTrimestre = document.getElementById("filtroTrimestre");
    const filtroPierde = document.getElementById("filtroPierdeCalidad");
    const filtroAnioTit = document.getElementById("filtroAnioTit");
    const filtroEstatus = document.getElementById("filtroEstatus");

    function aplicarFiltros() {        
        // Obtenemos valores (si el filtro no existe por alguna razón, usamos cadena vacía)
        const valMat = filtroMatricula ? filtroMatricula.value.toLowerCase().trim() : "";
        const valTri = filtroTrimestre ? filtroTrimestre.value : "";
        const valPierde = filtroPierde ? filtroPierde.value : "";
        const valAnio = filtroAnioTit ? filtroAnioTit.value : "";
        const valEst = filtroEstatus ? filtroEstatus.value : "";

        Array.from(tablaBody.rows).forEach(row => {
            // Obtenemos el texto de las celdas específicas según tu estructura de 13 columnas
            const txtMat = row.cells[0].innerText.toLowerCase(); //Columna Matricula
            const txtTri = row.cells[5].innerText;       // Columna Ingreso
            const txtPierde = row.cells[7].innerText;    // Columna Pierde Calidad
            const txtEst = row.cells[8].innerText;       // Columna Estatus
            const txtFechaTit = row.cells[11].innerText; // Columna F. Titulación
            
            // Lógica de comparación
            const coincideMat = valMat === "" || txtMat.includes(valMat);
            const coincideTri = valTri === "" || txtTri === valTri;
            const coincidePierde = valPierde === "" || txtPierde === valPierde;
            const coincideEst = valEst === "" || txtEst.includes(valEst);
            
            // Para el año, verificamos si la fecha completa INCLUYE el año seleccionado
            // Funciona tanto para "2024-05-10" como para "10/05/2024"
            const coincideAnio = valAnio === "" || txtFechaTit.includes(valAnio);

            // Mostrar solo si CUMPLE TODAS las condiciones
            if (coincideMat && coincideTri && coincidePierde && coincideEst && coincideAnio) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        });
    }

    // Event Listeners (Detectar cambios en los select)
    if(filtroMatricula) filtroMatricula.addEventListener("input", aplicarFiltros);
    if(filtroTrimestre) filtroTrimestre.addEventListener("change", aplicarFiltros);
    if(filtroPierde) filtroPierde.addEventListener("change", aplicarFiltros);
    if(filtroAnioTit) filtroAnioTit.addEventListener("change", aplicarFiltros);
    if(filtroEstatus) filtroEstatus.addEventListener("change", aplicarFiltros);
});