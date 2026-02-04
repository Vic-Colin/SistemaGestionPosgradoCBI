<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Consulta de Profesorado - CBI</title>
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
            <h1 class="main-title-dash">Consulta de Profesorado</h1>
            <h2 class="welcome-user">Búsqueda y filtrado de personal académico por CVU y Programa.</h2>
            
            <div class="table-controls">
                <div class="filters-group">
                    <input type="text" placeholder="Buscar por CVU..." class="search-input" id="filtroCvu">
                    <input type="text" placeholder="Buscar por Programa..." class="search-input" id="filtroPrograma">
                </div>
                
                <div class="actions-group">
                    <button class="btn-reporte-top">Generar reporte</button>
                </div>
            </div>

            <div class="table-container">
                <table class="alumnos-table" id="tablaProfesores">
                    <thead>
                        <tr>
                            <th>No. Económico</th>
                            <th>Nombre Completo</th>
                            <th>CVU</th>
                            <th>Programa</th>
                            <th>Correo Institucional</th>
                            <th>Categoría</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${listaProfesores}">
                            <tr>
                                <td>${p.noEconomico}</td>
                                <td>${p.nombre}</td>
                                <td>${p.cvu}</td>
                                <td>${p.programa}</td>
                                <td>${p.correo}</td>
                                <td><span class="status-badge">${p.categoria}</span></td>
                            </tr>
                        </c:forEach>
                        
                        <c:if test="${empty listaProfesores}">
                            <tr>
                                <td colspan="6" style="text-align: center; padding: 30px; color: #999;">
                                    No hay registros disponibles para mostrar.
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/js/profesorado.js"></script>
</body>
</html>