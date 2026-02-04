document.addEventListener("DOMContentLoaded", () => {
    const inputNombre = document.getElementById("filtroNombre");
    const inputResp = document.getElementById("filtroResponsable");
    const tablaBody = document.querySelector("#tablaProyectos tbody");
    const modal = document.getElementById("modalProyecto");
    const form = document.getElementById("formProyecto");


    // Funciones de Control del Modal
    window.abrirModalProyecto = () => {
        form.reset();
        modal.style.display = "block";
    };

    window.cerrarModalProyecto = () => {
        modal.style.display = "none";
    };

    // Cerrar si se hace clic fuera del contenido blanco
    window.onclick = (event) => {
        const modalProj = document.getElementById("modalProyecto");
        if (event.target == modalProj) {
            modalProj.style.display = "none";
        }
    };

    // Manejo del Envío (Esqueleto para el Back)
    form.addEventListener("submit", (e) => {
        e.preventDefault();
        
        // Aquí recolectarías los datos para enviarlos al Servlet
        const datos = {
            id: document.getElementById("projId").value,
            nombre: document.getElementById("projNombre").value,
            responsable: document.getElementById("projResponsable").value,
            estado: document.getElementById("projEstado").value
        };

        console.log("Datos listos para enviar al backend:", datos);
        
        // Simulación: cerrar modal tras "guardar"
        cerrarModalProyecto();
        alert("Proyecto guardado correctamente (Simulación)");
    });
    const aplicarFiltros = () => {
        const queryNombre = inputNombre.value.toLowerCase();
        const queryResp = inputResp.value.toLowerCase();
        const filas = tablaBody.querySelectorAll("tr");

        filas.forEach(fila => {
            if (fila.cells.length < 2) return; // Ignorar mensaje de tabla vacía

            const nombreProj = fila.cells[1].textContent.toLowerCase();
            const responsable = fila.cells[2].textContent.toLowerCase();

            const coincide = nombreProj.includes(queryNombre) && responsable.includes(queryResp);
            fila.style.display = coincide ? "" : "none";
        });
    };

    inputNombre.addEventListener("input", aplicarFiltros);
    inputResp.addEventListener("input", aplicarFiltros);
});


