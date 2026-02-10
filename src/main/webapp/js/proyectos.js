document.addEventListener("DOMContentLoaded", () => {
    const inputMatricula = document.getElementById("filtroMatricula");
    const inputTitulo = document.getElementById("filtroTitulo");
    const inputDirector = document.getElementById("filtroDirector");
    const tablaBody = document.querySelector("#tablaProyectos tbody");

    const aplicarFiltros = () => {
        const txt = inputDirector.value.toLowerCase().trim();
        const qTit = inputTitulo.value.toLowerCase();
        const qMat = inputMatricula.value.toLowerCase();

        Array.from(tablaBody.rows).forEach(row => {

            const matricula = row.cells[0].textContent.toLowerCase();
            const titulo = row.cells[2].textContent.toLowerCase();
            const asesor = (row.cells[3].innerText + row.cells[4].innerText).toLowerCase();
        
            const matchTxt = txt === "" || asesor.includes(txt);
            const matchMat = qMat === "" || matricula.includes(qMat);
            const matchTit = qTit === "" || titulo.includes(qTit);
        
            if (matchTxt && matchMat && matchTit ) {
                    row.style.display = "";
                } else {
                    row.style.display = "none";
                }
        });
    };


    inputMatricula.addEventListener("input", aplicarFiltros);
    inputTitulo.addEventListener("input", aplicarFiltros);
    inputDirector.addEventListener("input", aplicarFiltros);
});