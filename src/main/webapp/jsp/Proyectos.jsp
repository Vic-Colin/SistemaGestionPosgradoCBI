<%-- 
    Document   : Proyectos
    Created on : 4 feb 2026, 3:07:04 p.m.
    Author     : CASH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Proyectos - CBI</title>
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
            <h1 class="main-title-dash">Lista de Proyectos</h1>
            <h2 class="welcome-user">Consulta de proyectos de investigación y desarrollo.</h2>
            
            <div class="table-controls">
                <div class="filters-group">
                    <input type="text" id="filtroMatricula" placeholder="Matrícula..." class="search-input">
                    <input type="text" id="filtroTitulo" placeholder="Título de tesis" class="search-input">
                    <input type="text" id="filtroAsesor" placeholder="Director/Codirector" class="search-input">
                </div>
                
                <div class="actions-group">
                    <button class="btn-reporte-top">Exportar PDF</button>
                </div>
            </div>

            <div class="table-container">
                <table class="alumnos-table" id="tablaProyectos">
                    <thead>
                        <tr>
                            <th>Matrícula</th>
                            <th>Nombre del Alumno</th>
                            <th style="width: 30%;">Título de Tesis (ICR)</th>
                            <th>Director (Asesor)</th>
                            <th>Codirector (Coasesor)</th>
                            <th>Área de Concentración</th>
                        </tr>
                    </thead>
                    <tbody id="tbodyProyectos">
                        <!-- Se llenará dinámicamente -->
                    </tbody>

                </table>
            </div>
        </main>        
    </div>
    <script src="${pageContext.request.contextPath}/js/proyectos.js"></script>
</body>
</html>