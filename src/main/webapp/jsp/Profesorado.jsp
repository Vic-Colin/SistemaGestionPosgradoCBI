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
                    <input type="text" placeholder="CVU..." class="search-input" id="buscarCVU">
                    <input type="text" placeholder="No. Económico..." class="search-input" id="buscarNoEconomico">
                </div>
                
                <div class="actions-group">
                    <button class="btn-reporte-top">Generar reporte</button>
                </div>
            </div>

            <div class="table-container">
                <table id="tablaProfesores" class="alumnos-table">
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
                    <tbody id="tbodyProfesores">
                        <!-- Se llenará dinámicamente -->
                    </tbody>
                </table>
            </div>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/js/profesorado.js"></script>
</body>
</html>