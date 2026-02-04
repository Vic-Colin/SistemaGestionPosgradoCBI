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
                    <input type="text" placeholder="Filtrar por nombre del proyecto..." class="search-input" id="filtroNombre">
                    <input type="text" placeholder="Filtrar por responsable..." class="search-input" id="filtroResponsable">
                </div>
                
                <div class="actions-group">
                    <button class="btn-nuevo" onclick="abrirModalProyecto()">+ Nuevo Proyecto</button>
                    <button class="btn-reporte-top">Generar reporte</button>
                </div>
            </div>

            <div class="table-container">
                <table class="alumnos-table" id="tablaProyectos">
                    <thead>
                        <tr>
                            <th>ID Proyecto</th>
                            <th>Nombre del Proyecto</th>
                            <th>Responsable</th>
                            <th>Línea de Investigación</th>
                            <th>Fecha Inicio</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="proj" items="${listaProyectos}">
                            <tr>
                                <td>${proj.id}</td>
                                <td>${proj.nombre}</td>
                                <td>${proj.responsable}</td>
                                <td>${proj.linea}</td>
                                <td>${proj.fechaInicio}</td>
                                <td><span class="status-badge">${proj.estado}</span></td>
                            </tr>
                        </c:forEach>
                        
                        <c:if test="${empty listaProyectos}">
                            <tr>
                                <td colspan="6" style="text-align: center; padding: 30px; color: #999;">
                                    No se encontraron proyectos registrados.
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </main>        
    </div>

    <div id="modalProyecto" class="modal">
        <div class="modal-content" style="width: 700px;">
            <span class="close-modal" onclick="cerrarModalProyecto()">&times;</span>
            <h2 id="modalTitulo" style="color: #CD032E;">Registrar Nuevo Proyecto</h2>
            <p style="color: #888; margin-bottom: 20px;">Ingrese la información detallada del proyecto de investigación.</p>
    
            <form id="formProyecto" class="registro-form">
                <div class="form-row">
                    <div class="form-group-slim" style="flex: 1;">
                        <label class="label-mini">Nombre del Proyecto</label>
                        <input type="text" id="projNombre" placeholder="Ej. Inteligencia Artificial en Medicina" required>
                    </div>
                </div>
        
                <div class="form-row">
                    <div class="form-group-slim">
                        <label class="label-mini">ID Proyecto</label>
                        <input type="text" id="projId" placeholder="Código" required>
                    </div>
                    <div class="form-group-slim" style="flex: 2;">
                        <label class="label-mini">Responsable</label>
                        <input type="text" id="projResponsable" placeholder="Nombre del Profesor" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group-slim">
                        <label class="label-mini">Línea de Investigación</label>
                        <select id="projLinea">
                            <option value="Sistemas Digitales">Sistemas Digitales</option>
                            <option value="Optimización">Optimización</option>
                            <option value="Computación Científica">Computación Científica</option>
                        </select>
                    </div>
                    <div class="form-group-slim">
                        <label class="label-mini">Estado Actual</label>
                        <select id="projEstado">
                            <option value="Activo">Activo</option>
                            <option value="En Revisión">En Revisión</option>
                            <option value="Concluido">Concluido</option>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group-slim">
                        <label class="label-mini">Fecha de Inicio</label>
                        <input type="date" id="projFechaInicio">
                    </div>
                    <div class="form-group-slim" style="flex: 2;">
                        <label class="label-mini">Descripción Breve</label>
                        <textarea id="projDesc" rows="2" placeholder="Notas adicionales..." style="width: 100%; border-radius: 8px; border: 1px solid #ddd; padding: 8px;"></textarea>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="button" class="btn-cancelar" onclick="cerrarModalProyecto()">Cancelar</button>
                    <button type="submit" class="btn-guardar">Guardar Proyecto</button>
                </div>
            </form>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/proyectos.js"></script>
</body>
</html>