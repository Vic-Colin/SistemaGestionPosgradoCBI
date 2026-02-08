document.addEventListener("DOMContentLoaded", () => {
    const tablaBody = document.querySelector("#tablaBecas tbody");
    const filtroGen = document.getElementById("filtroGeneracionBeca");

    // --- LÓGICA IDÉNTICA A ALUMNOS: GENERAR TRIMESTRES UAM ---
    const cargarGeneraciones = () => {
        const fechaActual = new Date();
        const añoActualFull = fechaActual.getFullYear();
        const añoCortoActual = añoActualFull % 100;
        const trimestres = ['O', 'P', 'I']; // Otoño, Primavera, Invierno

        for (let i = 0; i <= 10; i++) {
            const añoProc = añoCortoActual - i;
            // Formato de dos dígitos (ej. 09 si fuera 2009)
            const añoFormateado = añoProc < 10 && añoProc >= 0 ? `0${añoProc}` : añoProc;
            
            trimestres.forEach(trim => {
                const valor = `${añoFormateado}-${trim}`;
                const opcion = document.createElement("option");
                opcion.value = valor;
                opcion.textContent = valor;
                filtroGen.appendChild(opcion);
            });
        }
    };

    cargarGeneraciones();

    // --- ELEMENTOS DE FILTRO ---
    const filtroTexto = document.getElementById("filtroBeca");
    const filtroEstatus = document.getElementById("filtroEstatusBeca");
    const filtroFechaMax = document.getElementById("filtroFechaMax");

    function filtrar() {
        const txt = filtroTexto.value.toLowerCase().trim();
        const gen = filtroGen.value; // ej: "24-O"
        const est = filtroEstatus.value;
        const fMax = filtroFechaMax.value.toLowerCase().trim();

        Array.from(tablaBody.rows).forEach(row => {
            // Índices de celdas según tu tabla en Becas.jsp:
            // 0:Matrícula, 1:Nombre, 2:CVU, 3:Ingreso (Generación), 5:Fecha Máx, 6:Estatus
            const textoMatCVU = (row.cells[0].innerText + row.cells[2].innerText).toLowerCase();
            const textoIngreso = row.cells[3].innerText.trim(); 
            const textoFechaMax = row.cells[5].innerText.toLowerCase();
            const textoEstatus = row.cells[6].innerText;

            const matchTxt = txt === "" || textoMatCVU.includes(txt);
            const matchGen = gen === "" || textoIngreso === gen; // Comparación exacta igual que en Alumnos
            const matchEst = est === "" || textoEstatus.includes(est);
            const matchFMax = fMax === "" || textoFechaMax.includes(fMax);

            if (matchTxt && matchGen && matchEst && matchFMax) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        });
    }

    // Eventos de escucha
    filtroTexto.addEventListener("input", filtrar);
    filtroGen.addEventListener("change", filtrar);
    filtroEstatus.addEventListener("change", filtrar);
    filtroFechaMax.addEventListener("input", filtrar);

    // --- CIERRE DE MODALES ---
    window.onclick = (event) => {
        const modales = document.querySelectorAll('.modal');
        modales.forEach(m => {
            if (event.target == m) m.style.display = "none";
        });
    };
});