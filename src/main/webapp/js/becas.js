document.addEventListener("DOMContentLoaded", () => {
    const inputCVU = document.getElementById("buscarCVU");
    const selectGeneracion = document.getElementById("filtroGeneracion");
    const selectEstatus = document.getElementById("filtroEstatus");
    const tbody = document.getElementById("tbodyBecas");

    // --- 1. Cargar el select de generaciones dinámicamente ---
    const cargarGeneraciones = () => {
        const fechaActual = new Date();
        const anioCorto = fechaActual.getFullYear() % 100;
        const trimestres = ['O', 'P', 'I']; // Otoño, Primavera, Invierno

        // Generamos los últimos 10 años
        for (let i = 0; i <= 10; i++) {
            const anio = anioCorto - i;
            const anioFormateado = anio < 10 ? `0${anio}` : anio;
            
            trimestres.forEach(trim => {
                const valor = `${anioFormateado}-${trim}`;
                const opcion = document.createElement("option");
                opcion.value = valor;
                opcion.textContent = valor;
                selectGeneracion.appendChild(opcion);
            });
        }
    };
    cargarGeneraciones();

    // --- 2. Función principal para cargar becas ---
    function cargarBecas() {
        const cvu = inputCVU.value.trim();
        const generacion = selectGeneracion.value;
        const estatus = selectEstatus.value;

        // Construimos la URL con los 3 filtros
        // Usamos ../ porque el JSP está en la carpeta /jsp/
        const url = `../BecaServlet?cvu=${cvu}&generacion=${generacion}&estatus=${estatus}`;

        fetch(url)
            .then(response => {
                if (!response.ok) throw new Error("Error en la red");
                return response.json();
            })
            .then(data => {
                tbody.innerHTML = "";
                if (data.length === 0) {
                    tbody.innerHTML = '<tr><td colspan="8" style="text-align:center;">No se encontraron registros</td></tr>';
                    return;
                }

                // Renderizamos cada fila de la tabla
                data.forEach(beca => {
                    // Función auxiliar para formatear si es null
                    const val = (v) => v ? v : "-"; 
                    // Función para clase CSS del estatus
                    const claseEstatus = beca.estatusBeca ? `status-badge ${beca.estatusBeca.toLowerCase()}` : "";

                    const fila = `
                        <tr>
                            <td>${beca.matricula}</td>
                            <td>${beca.nombreAlumno}</td>
                            <td>${val(beca.cvu)}</td>
                            <td>${val(beca.trimestreIngreso)}</td>
                            <td>${val(beca.fechaFinBeca)}</td>
                            <td>${val(beca.fechaMaxConahcyt)}</td>
                            <td><span class="${claseEstatus}">${val(beca.estatusBeca)}</span></td>
                            <td>${val(beca.fechaTitulacion)}</td>
                        </tr>
                    `;
                    tbody.innerHTML += fila;
                });
            })
            .catch(error => {
                console.error("Error cargando becas:", error);
                tbody.innerHTML = '<tr><td colspan="8" style="color:red; text-align:center;">Error al cargar datos</td></tr>';
            });
    }

    // --- 3. Eventos para filtrado en tiempo real con Debounce ---
    let timeout;
    const delayCarga = () => {
        clearTimeout(timeout);
        timeout = setTimeout(cargarBecas, 300); // Espera 300ms después de dejar de escribir
    };

    inputCVU.addEventListener("input", delayCarga);
    selectGeneracion.addEventListener("change", cargarBecas);
    selectEstatus.addEventListener("change", cargarBecas);

    // Carga inicial (trae todo porque los filtros están vacíos)
    cargarBecas();
});