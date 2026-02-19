
document.addEventListener("DOMContentLoaded", function () {

    const inputCVU = document.getElementById("buscarCVU");
    const inputNoEco = document.getElementById("buscarNoEconomico");
    const tablaBody = document.querySelector("#tablaProfesores tbody");

    function cargarProfesores() {

        const cvu = inputCVU.value;
        const numeroEconomico = inputNoEco.value;

        fetch(`ProfesoradoServlet?cvu=${cvu}&numeroEconomico=${numeroEconomico}`)
            .then(response => response.json())
            .then(data => {

                const tbody = document.getElementById("tbodyProfesores");
                tbody.innerHTML = "";

                data.forEach(profesor => {

                    const fila = `
                        <tr>
                            <td>${profesor.numeroEconomico}</td>
                            <td>${profesor.nombreCompleto}</td>
                            <td>${profesor.cvu || "-"}</td>
                            <td>${profesor.nivelSni || "-"}</td>
                            <td>${profesor.tipoDedicacion}</td>
                            <td>${profesor.departamento}</td>
                            <td>${profesor.fechaIngresoNucleo}</td> 
                            <td>${profesor.correoInstitucional || "-"}</td>
                        </tr>
                    `;

                    tbody.innerHTML += fila;
                });

            })
            .catch(error => console.error("Error:", error));
    }

    
    let timeout;

    function cargarConDelay() {
        clearTimeout(timeout);
        timeout = setTimeout(cargarProfesores, 300);
    }
    // Escuchar cuando escriben
    inputCVU.addEventListener("keyup", cargarConDelay);
    inputNoEco.addEventListener("keyup", cargarConDelay);

    // Cargar al abrir la página
    cargarProfesores();
});
