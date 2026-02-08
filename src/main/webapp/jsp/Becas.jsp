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
            <h1 class="main-title-dash">Becas SECIHTI (CONAHCyT)</h1>
            <h2 class="welcome-user">Control de Vigencias y CVU</h2>
            
            <div class="table-controls">
                <div class="filters-group">
                    <input type="text" placeholder="Buscar por CVU..." class="search-input" id="filtroBeca">
                    
                    <select class="filter-select" id="filtroGeneracionBeca">
                        <option value="">Generación (Todas)</option>
                    </select>

                    <select class="filter-select" id="filtroEstatusBeca">
                        <option value="">Estatus Beca (Todos)</option>
                        <option value="Vigente">Vigente</option>
                        <option value="Baja">Baja</option>
                        <option value="No tuvo beca">No tuvo beca</option>
                        <option value="Concluida">Concluida</option>
                    </select>

                    <input type="text" placeholder="Fecha Máx. (ej. 2027)" class="search-input" id="filtroFechaMax">
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
                    <tbody>
                        <tr>
                            <td>2243804633</td>
                            <td>Flores Martínez Grecia Nefertari</td>
                            <td>2072716</td>
                            <td>24-O</td>
                            <td>30/11/2026</td>
                            <td>30/11/2027</td>
                            <td><span class="status-badge">Vigente</span></td>
                            <td>-</td>
                        </tr>
                        <c:forEach var="beca" items="${listaBecas}">
                            <tr>
                                <td>${beca.matricula}</td>
                                <td>${beca.nombre}</td>
                                <td>${beca.cvu}</td>
                                <td>${beca.ingreso}</td>
                                <td>${beca.finBeca}</td>
                                <td>${beca.fechaMax}</td>
                                <td><span class="status-badge">${beca.estatus}</span></td>
                                <td>${beca.titulacion}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/js/becas.js"></script>
</body>
</html>