<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Alumnado - Datos Generales</title>
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
            <h1 class="main-title-dash">Lista del Alumnado</h1>
            <h2 class="welcome-user">Datos Generales - Posgrado MCC</h2>
            
            <div class="table-controls">
                <div class="filters-group">
                    <input type="text" placeholder="Buscar Matrícula..." class="search-input" id="filtroMatricula">
                    <select class="filter-select" id="filtroTrimestre">
                        <option value="">Trimestre Ingreso (Todos)</option>
                        <c:set var="anioActual" value="25" />
                        <c:forEach var="anio" begin="0" end="${anioActual - 16}">
                            <c:set var="a" value="${anioActual - anio}" />
                            <option value="${a}-O">${a}-O</option>
                            <option value="${a}-P">${a}-P</option>
                            <option value="${a}-I">${a}-I</option>
                        </c:forEach>
                    </select>
                    <select class="filter-select" id="filtroEstatus">
                        <option value="">Estatus UAM (Todos)</option>
                        <option value="Vigente">Vigente</option>
                        <option value="Titulado">Titulado</option>
                        <option value="Baja">Baja</option>
                    </select>
                </div>
                
                <div class="actions-group">
                    <button type="button" class="btn-nuevo" onclick="abrirModal()">+ Nuevo Alumno</button>
                    <button class="btn-reporte-top">Generar reporte</button>
                </div>
            </div>

            <div class="table-container" style="overflow-x: auto;">
                <table class="alumnos-table" id="tablaAlumnos">
                    <thead>
                        <tr>
                            <th>Matrícula</th>
                            <th>Nombre</th>
                            <th>Correo institucional</th>
                            <th>Correo alternativo</th>
                            <th>Teléfono</th>
                            <th>Ingreso</th>
                            <th>Estatus UAM</th>
                            <th>Número de Acta</th>
                            <th>Fecha de titulación</th>
                            <th>Comentarios</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>2243804633</td>
                            <td>Flores Martínez Grecia Nefertari</td>
                            <td>al2243804633@azc.uam.mx</td>
                            <td>greciam.0102@gmail.com</td>
                            <td>5525612361</td>
                            <td>24-O</td>
                            <td><span class="status-badge">Vigente</span></td>
                            <td>-</td>
                            <td>-</td>
                            <td>Sin comentarios</td>
                            <td>
                                <div style="display: flex; gap: 8px;">
                                    <button class="btn-action-icon edit" onclick="prepararEdicion(this)" title="Editar">✏️</button>
                                    <button class="btn-action-icon delete" onclick="confirmarEliminar(this)" title="Eliminar">🗑️</button>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </main>
    </div>

    <div id="modalRegistro" class="modal">
        <div class="modal-content" style="width: 700px;">
            <span class="close-modal" onclick="cerrarModal()">&times;</span>
            <h2 id="modalTitulo" style="color: #CD032E;">Registrar Nuevo Alumno</h2>
        
            <form id="formNuevoAlumno" class="registro-form">
                <input type="hidden" id="editRowIndex" value="-1">
            
                <div class="form-row">
                    <input type="text" id="regMatricula" placeholder="Matrícula" required>
                    <input type="text" id="regNombre" placeholder="Nombre Completo" required>
                </div>
                <div class="form-row">
                    <input type="email" id="regCorreoInst" placeholder="Correo Institucional">
                    <input type="email" id="regCorreoAlt" placeholder="Correo Alternativo">
                </div>
                <div class="form-row">
                    <input type="text" id="regTel" placeholder="Teléfono">
                    <select id="regIngreso">
                        <option value="">Ingreso...</option>
                        <c:forEach var="anio" begin="0" end="${25 - 16}">
                            <c:set var="a" value="${25 - anio}" />
                            <option value="${a}-O">${a}-O</option>
                            <option value="${a}-P">${a}-P</option>
                            <option value="${a}-I">${a}-I</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-row">
                    <select id="regEstatus">
                        <option value="Vigente">Vigente</option>
                        <option value="Vigente (RCA)">Vigente (RCA)</option>
                        <option value="Titulado">Titulado</option>
                        <option value="Baja">Baja</option>
                        <option value="Egresado">Egresado</option>
                    </select>
                    <input type="text" id="regActa" placeholder="Número de Acta">
                </div>
                <div class="form-row">
                    <input type="text" id="regFechaTit" placeholder="Fecha de Titulación">
                    <input type="text" id="regComentarios" placeholder="Comentarios">
                </div>

                <div class="form-actions">
                    <button type="button" class="btn-cancelar" onclick="cerrarModal()">Cancelar</button>
                    <button type="submit" id="btnGuardarForm" class="btn-guardar">Guardar Datos</button>
                </div>
            </form>
        </div>
    </div>

    <div id="modalEliminar" class="modal">
        <div class="modal-content" style="width: 400px; text-align: center;">
            <h2 style="color: #CD032E;">¿Estás seguro?</h2>
            <p>Esta acción eliminará al alumno de la lista permanentemente.</p>
            <div class="form-actions" style="justify-content: center;">
                <button class="btn-cancelar" onclick="cerrarModalEliminar()">No, Cancelar</button>
                <button class="btn-guardar" id="btnConfirmarBorrado" style="background: #CD032E;">Sí, Eliminar</button>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/alumnos.js"></script>
</body>
</html>