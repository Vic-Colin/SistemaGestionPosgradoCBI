document.addEventListener("DOMContentLoaded", () => {
    const inputCvu = document.getElementById("filtroCvu");
    const inputProg = document.getElementById("filtroPrograma");
    const tabla = document.querySelector("#tablaProfesores tbody");

    const filtrar = () => {
        const valCvu = inputCvu.value.toLowerCase();
        const valProg = inputProg.value.toLowerCase();
        const filas = tabla.querySelectorAll("tr");

        filas.forEach(fila => {
            // Validar que la fila tenga celdas (evitar error con el mensaje de "No hay registros")
            if (fila.cells.length < 2) return;

            const textoCvu = fila.cells[2].textContent.toLowerCase();
            const textoProg = fila.cells[3].textContent.toLowerCase();

            const coincide = textoCvu.includes(valCvu) && textoProg.includes(valProg);
            fila.style.display = coincide ? "" : "none";
        });
    };

    inputCvu.addEventListener("input", filtrar);
    inputProg.addEventListener("input", filtrar);
});

