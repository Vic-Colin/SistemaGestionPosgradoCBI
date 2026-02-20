<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Becas SECIHTI - MCC</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="board-container">
        <header class="header-logos-dash">
            <div class="logo-uam-dash"></div>
            <div class="logo-cbi-dash"></div>
        </header>

        <nav class="red-nav-bar">
            <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="btn-exit" style="margin-right: auto; margin-left: 40px;">
                <span class="exit-text">← REGRESAR</span>
            </a>
            <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn-exit">
                <img src="${pageContext.request.contextPath}/img/Out.png" alt="Salir" class="exit-icon">
                <span class="exit-text">SALIR</span>
            </a>
        </nav>

        <main class="dashboard-content">
            <h1 class="main-title-dash">Becas SECIHTI</h1>
            <h2 class="welcome-user">Consulta de Vigencias</h2>
            
            <div class="table-controls">
                <div class="filters-group">
                    <input type="text" placeholder="Buscar por CVU..." class="search-input" id="buscarCVU">
                    
                    <select class="filter-select" id="filtroGeneracion">
                        <option value="">Generación (Todas)</option>
                    </select>

                    <select class="filter-select" id="filtroEstatus">
                        <option value="">Estatus Beca (Todos)</option>
                        <option value="Vigente">Vigente</option>
                        <option value="Baja">Baja</option>
                        <option value="No tuvo beca">No tuvo beca</option>
                        <option value="Concluida">Concluida</option>
                    </select>
                </div>
                
                <div class="actions-group">
                    <button class="btn-reporte-top">Exportar PDF</button>
                </div>
            </div>

            <div class="table-container" style="overflow-x: auto;">
                <table class="alumnos-table" id="tablaBecas">
                    <thead>
                        <tr>
                            <th>Matrícula</th>
                            <th>Nombre</th>
                            <th>CVU CONAHCyT</th>
                            <th>Ingreso</th>
                            <th>Fin de Beca</th>
                            <th>Fecha Máx.</th>
                            <th>Estatus Beca</th>
                            <th>Titulación</th>
                        </tr>
                    </thead>
                    <tbody id ="tbodyBecas">
                        <!-- Se llenará dinámicamente -->
                    </tbody>
                </table>
            </div>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/js/becas.js"></script>
</body>
</html>