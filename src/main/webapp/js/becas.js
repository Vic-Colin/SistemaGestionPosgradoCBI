document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("modalBeca");
    const modalDel = document.getElementById("modalEliminarBeca");
    const form = document.getElementById("formBeca");
    const tablaBody = document.getElementById("tablaBecas").getElementsByTagName("tbody")[0];
    
    let filaSeleccionada = null;

    // --- FILTROS ---
    const filtroTexto = document.getElementById("filtroBeca");
    const filtroEstatus = document.getElementById("filtroEstatusBeca");

    function filtrar() {
        const txt = filtroTexto.value.toLowerCase();
        const est = filtroEstatus.value;

        Array.from(tablaBody.rows).forEach(row => {
            const matchTxt = row.cells[0].innerText.toLowerCase().includes(txt) || 
                             row.cells[2].innerText.toLowerCase().includes(txt);
            const matchEst = est === "" || row.cells[6].innerText.includes(est);
            
            row.style.display = (matchTxt && matchEst) ? "" : "none";
        });
    }

    filtroTexto.addEventListener("input", filtrar);
    filtroEstatus.addEventListener("change", filtrar);

    // --- FUNCIONES MODAL ---
    window.abrirModalBeca = () => {
        document.getElementById("modalTituloBeca").innerText = "Registrar Información de Beca";
        document.getElementById("editRowIndexBeca").value = "-1";
        form.reset();
        modal.style.display = "block";
    };

    window.cerrarModalBeca = () => {modal.style.display = "none";};
    window.cerrarModalEliminarBeca = () => {modalDel.style.display = "none";};

    window.prepararEdicionBeca = (btn) => {
        const fila = btn.closest("tr");
        document.getElementById("modalTituloBeca").innerText = "Editar Beca";
        document.getElementById("editRowIndexBeca").value = fila.sectionRowIndex;

        document.getElementById("becaMatricula").value = fila.cells[0].innerText;
        document.getElementById("becaNombre").value = fila.cells[1].innerText;
        document.getElementById("becaCVU").value = fila.cells[2].innerText;
        document.getElementById("becaIngreso").value = fila.cells[3].innerText;
        document.getElementById("becaFin").value = fila.cells[4].innerText;
        document.getElementById("becaMax").value = fila.cells[5].innerText;
        document.getElementById("becaEstatus").value = fila.cells[6].innerText.trim();
        document.getElementById("becaTit").value = fila.cells[7].innerText;

        modal.style.display = "block";
    };

    // --- GUARDAR ---
    form.addEventListener("submit", (e) => {
        e.preventDefault();
        const index = document.getElementById("editRowIndexBeca").value;
        
        const html = `
            <td>${document.getElementById("becaMatricula").value}</td>
            <td>${document.getElementById("becaNombre").value}</td>
            <td>${document.getElementById("becaCVU").value || "-"}</td>
            <td>${document.getElementById("becaIngreso").value}</td>
            <td>${document.getElementById("becaFin").value || "-"}</td>
            <td>${document.getElementById("becaMax").value || "-"}</td>
            <td><span class="status-badge">${document.getElementById("becaEstatus").value}</span></td>
            <td>${document.getElementById("becaTit").value || "-"}</td>
            <td>
                <div style="display: flex; gap: 8px;">
                    <button class="btn-action-icon edit" onclick="prepararEdicionBeca(this)">✏️</button>
                    <button class="btn-action-icon delete" onclick="confirmarEliminarBeca(this)">🗑️</button>
                </div>
            </td>
        `;

        if (index === "-1") {
            tablaBody.insertRow().innerHTML = html;
        } else {
            tablaBody.rows[index].innerHTML = html;
        }
        cerrarModalBeca();
        filtrar();
    });

    // --- ELIMINAR ---
    window.confirmarEliminarBeca = (btn) => {
        filaSeleccionada = btn.closest("tr");
        modalDel.style.display = "block";
    };

    document.getElementById("btnConfirmarBorradoBeca").onclick = () => {
        if (filaSeleccionada) {
            filaSeleccionada.remove();
            cerrarModalEliminarBeca();
        }
    };
});
