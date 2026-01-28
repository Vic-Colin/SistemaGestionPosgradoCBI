/**
 * alumnos.js - Lógica para la gestión de alumnos
 */

document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("modalRegistro");
    const modalEliminar = document.getElementById("modalEliminar");
    const form = document.getElementById("formNuevoAlumno");
    const tablaBody = document.getElementById("tablaAlumnos").getElementsByTagName("tbody")[0];
    
    // Referencias a filtros
    const filtroMatricula = document.getElementById("filtroMatricula");
    const filtroTrimestre = document.getElementById("filtroTrimestre");
    const filtroEstatus = document.getElementById("filtroEstatus");

    let filaAEliminar = null;

    // --- FUNCIONES DE FILTRADO ---
    function aplicarFiltros() {
        const valMat = filtroMatricula.value.toLowerCase();
        const valTri = filtroTrimestre.value;
        const valEst = filtroEstatus.value;

        Array.from(tablaBody.rows).forEach(row => {
            const txtMat = row.cells[0].innerText.toLowerCase();
            const txtTri = row.cells[5].innerText;
            const txtEst = row.cells[6].innerText;

            const coincideMat = txtMat.includes(valMat);
            const coincideTri = valTri === "" || txtTri === valTri;
            const coincideEst = valEst === "" || txtEst.includes(valEst);

            row.style.display = (coincideMat && coincideTri && coincideEst) ? "" : "none";
        });
    }

    // Escuchar eventos en los filtros
    filtroMatricula.addEventListener("input", aplicarFiltros);
    filtroTrimestre.addEventListener("change", aplicarFiltros);
    filtroEstatus.addEventListener("change", aplicarFiltros);

    // --- FUNCIONES DE MODAL ---
    window.abrirModal = () => {
        document.getElementById("modalTitulo").innerText = "Registrar Nuevo Alumno";
        document.getElementById("editRowIndex").value = "-1";
        form.reset();
        modal.style.display = "block";
    };

    window.cerrarModal = () => modal.style.display = "none";
    window.cerrarModalEliminar = () => modalEliminar.style.display = "none";

    // --- PREPARAR EDICIÓN ---
    window.prepararEdicion = (btn) => {
        const fila = btn.closest("tr");
        document.getElementById("modalTitulo").innerText = "Editar Información del Alumno";
        // Guardamos el índice real de la fila en el cuerpo de la tabla
        document.getElementById("editRowIndex").value = fila.sectionRowIndex;

        document.getElementById("regMatricula").value = fila.cells[0].innerText;
        document.getElementById("regNombre").value = fila.cells[1].innerText;
        document.getElementById("regCorreoInst").value = fila.cells[2].innerText === "-" ? "" : fila.cells[2].innerText;
        document.getElementById("regCorreoAlt").value = fila.cells[3].innerText === "-" ? "" : fila.cells[3].innerText;
        document.getElementById("regTel").value = fila.cells[4].innerText === "-" ? "" : fila.cells[4].innerText;
        document.getElementById("regIngreso").value = fila.cells[5].innerText;
        document.getElementById("regEstatus").value = fila.cells[6].innerText.trim();
        document.getElementById("regActa").value = fila.cells[7].innerText === "-" ? "" : fila.cells[7].innerText;
        document.getElementById("regFechaTit").value = fila.cells[8].innerText === "-" ? "" : fila.cells[8].innerText;
        document.getElementById("regComentarios").value = fila.cells[9].innerText === "Sin comentarios" ? "" : fila.cells[9].innerText;

        modal.style.display = "block";
    };

    // --- GUARDAR (CREAR O EDITAR) ---
    form.addEventListener("submit", (e) => {
        e.preventDefault();
        const index = document.getElementById("editRowIndex").value;
        
        const datos = {
            m: document.getElementById("regMatricula").value,
            n: document.getElementById("regNombre").value,
            ci: document.getElementById("regCorreoInst").value || "-",
            ca: document.getElementById("regCorreoAlt").value || "-",
            t: document.getElementById("regTel").value || "-",
            i: document.getElementById("regIngreso").value || "-",
            e: document.getElementById("regEstatus").value,
            a: document.getElementById("regActa").value || "-",
            ft: document.getElementById("regFechaTit").value || "-",
            c: document.getElementById("regComentarios").value || "Sin comentarios"
        };

        const htmlFila = `
            <td>${datos.m}</td>
            <td>${datos.n}</td>
            <td>${datos.ci}</td>
            <td>${datos.ca}</td>
            <td>${datos.t}</td>
            <td>${datos.i}</td>
            <td><span class="status-badge">${datos.e}</span></td>
            <td>${datos.a}</td>
            <td>${datos.ft}</td>
            <td>${datos.c}</td>
            <td>
                <div style="display: flex; gap: 8px;">
                    <button class="btn-action-icon edit" onclick="prepararEdicion(this)">✏️</button>
                    <button class="btn-action-icon delete" onclick="confirmarEliminar(this)">🗑️</button>
                </div>
            </td>
        `;

        if (index === "-1") {
            const nuevaFila = tablaBody.insertRow();
            nuevaFila.innerHTML = htmlFila;
        } else {
            tablaBody.rows[index].innerHTML = htmlFila;
        }

        cerrarModal();
        aplicarFiltros(); // Re-aplicar filtros por si el nuevo dato no coincide
    });

    // --- ELIMINAR ---
    window.confirmarEliminar = (btn) => {
        filaAEliminar = btn.closest("tr");
        modalEliminar.style.display = "block";
    };

    document.getElementById("btnConfirmarBorrado").onclick = () => {
        if (filaAEliminar) {
            filaAEliminar.remove();
            cerrarModalEliminar();
            filaAEliminar = null;
        }
    };
});