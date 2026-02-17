document.addEventListener("DOMContentLoaded", function () {
    const inputNoEco = document.getElementById("buscarNoEconomico");
    const inputCVU = document.getElementById("buscarCVU");

    function cargarProfesores() {

        const noEco = inputNoEco.value;
        const cvu = inputCVU.value;

        fetch("ProfesoradoServlet?noEconomico=" + encodeURIComponent(noEco) +
              "&cvu=" + encodeURIComponent(cvu))
        .then(response => response.json())
        .then(data => {

            const tbody = document.getElementById("tbodyProfesores");
            tbody.innerHTML = "";

            if (data.length === 0) {
                tbody.innerHTML = `
                    <tr>
                        <td colspan="6">No hay registros disponibles para mostrar.</td>
                    </tr>
                `;
                return;
            }

            data.forEach(profesor => {

                const fila = `
                    <tr>
                        <td>${profesor.noEconomico}</td>
                        <td>${profesor.nombreCompleto}</td>
                        <td>${profesor.cvu}</td>
                        <td>${profesor.programa}</td>
                        <td>${profesor.correoInstitucional}</td>
                        <td>${profesor.categoria}</td>
                    </tr>
                `;

                tbody.innerHTML += fila;
            });

        })
        .catch(error => {
            console.error("Error al cargar profesores:", error);
        });
    }

    // 🔥 Cargar todos al inicio
    cargarProfesores();

    // 🔥 Filtro dinámico
    inputNoEco.addEventListener("keyup", cargarProfesores);
    inputCVU.addEventListener("keyup", cargarProfesores);


});


