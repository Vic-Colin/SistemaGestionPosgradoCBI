document.addEventListener("DOMContentLoaded", function () {

    const inputCVU = document.getElementById("buscarCVU");
    const inputNoEco = document.getElementById("buscarNoEconomico");
    // Asegúrate de que este ID coincida con el <tbody> de tu HTML
    const tbody = document.getElementById("tbodyProfesores"); 

    function cargarProfesores() {
        const cvu = inputCVU.value.trim();
        const numeroEconomico = inputNoEco.value.trim(); // Nombre de la variable

        // CORRECCIÓN: Se cambió numEco por numeroEconomico
        fetch(`../ProfesoradoServlet?cvu=${cvu}&numeroEconomico=${numeroEconomico}`)
            .then(response => {
                if (!response.ok) throw new Error("Error en la red");
                return response.json();
            })
            .then(data => {
                tbody.innerHTML = ""; // Limpiar tabla

                if (data.length === 0) {
                    tbody.innerHTML = `<tr><td colspan="8" style="text-align:center;">No se encontraron profesores</td></tr>`;
                    return;
                }

                data.forEach(profesor => {
                    const fila = `
                        <tr>
                            <td>${profesor.numeroEconomico}</td>
                            <td>${profesor.nombreCompleto}</td>
                            <td>${profesor.cvu || "-"}</td>
                            <td>${profesor.nivelSni || "-"}</td>
                            <td>${profesor.tipoDedicacion || "-"}</td>
                            <td>${profesor.departamento || "-"}</td>
                            <td>${profesor.fechaIngresoNucleo || "-"}</td> 
                            <td>${profesor.correoInstitucional || "-"}</td>
                        </tr>
                    `;
                    tbody.innerHTML += fila;
                });
            })
            .catch(error => {
                console.error("Error al obtener profesores:", error);
                tbody.innerHTML = `<tr><td colspan="8" style="color:red; text-align:center;">Error al cargar datos</td></tr>`;
            });
    }

    // Lógica de Delay (Debounce)
    let timeout;
    function cargarConDelay() {
        clearTimeout(timeout);
        timeout = setTimeout(cargarProfesores, 300);
    }

    // Event Listeners
    if (inputCVU) inputCVU.addEventListener("input", cargarConDelay);
    if (inputNoEco) inputNoEco.addEventListener("input", cargarConDelay);

    // Carga inicial
    cargarProfesores();
});