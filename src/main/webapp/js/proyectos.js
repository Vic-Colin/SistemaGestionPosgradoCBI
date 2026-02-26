document.addEventListener("DOMContentLoaded", () => {
    const inputMatricula = document.getElementById("filtroMatricula");
    const inputTitulo = document.getElementById("filtroTitulo");
    const inputAsesor = document.getElementById("filtroAsesor");
    const tbody = document.getElementById("tbodyProyectos");

    function cargarProyectos() {
        const matricula = inputMatricula.value.trim();
        const titulo = inputTitulo.value.trim();
        const asesor = inputAsesor.value.trim();

        const url = `../ProyectosServlet?matricula=${encodeURIComponent(matricula)}&titulo=${encodeURIComponent(titulo)}&asesor=${encodeURIComponent(asesor)}`;

        fetch(url)
            .then(response => {
                if (!response.ok) throw new Error("Error en la red");
                return response.json();
            })
            .then(data => {
                tbody.innerHTML = "";
                
                if (data.length === 0) {
                    tbody.innerHTML = '<tr><td colspan="6" style="text-align:center;">No se encontraron proyectos</td></tr>';
                    return;
                }

                data.forEach(p => {
                    const val = (v) => v ? v : '<span style="color:#aaa;">No asignado</span>';
                    
                    const fila = `
                        <tr>
                            <td>${p.matricula}</td>
                            <td>${p.nombreAlumno}</td>
                            <td><span class="status-badge">${val(p.estatusUam)}</span></td>
                            <td><strong>${val(p.tituloTesis)}</strong></td>
                            <td>${val(p.director)}</td>
                            <td>${val(p.codirector)}</td>
                            <td>${val(p.areaConcentracion)}</td>
                        </tr>
                    `;
                    tbody.innerHTML += fila;
                });
            })
            .catch(error => {
                console.error("Error cargando proyectos:", error);
                tbody.innerHTML = '<tr><td colspan="6" style="color:red; text-align:center;">Error al cargar datos</td></tr>';
            });
    }

// ================== EXPORTAR A PDF ==================
document.getElementById("btnExportarPDF")?.addEventListener("click", () => {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF('l', 'pt', 'letter'); 

    doc.setFontSize(16);
    doc.text("Reporte de Proyectos y Tesis - Posgrado CBI", 40, 40);

    doc.autoTable({
        html: '#tablaProyectos',
        startY: 50,
        theme: 'grid',
        styles: { fontSize: 8, cellPadding: 3 },
        headStyles: { fillColor: [205, 3, 46] }
    });

    doc.save("Reporte_Proyectos.pdf");
});

    let timeout;
    const delayCarga = () => {
        clearTimeout(timeout);
        timeout = setTimeout(cargarProyectos, 300);
    };

    // Escuchar eventos de teclado en los 3 filtros
    inputMatricula.addEventListener("input", delayCarga);
    inputTitulo.addEventListener("input", delayCarga);
    inputAsesor.addEventListener("input", delayCarga);

    // Carga inicial
    cargarProyectos();
});