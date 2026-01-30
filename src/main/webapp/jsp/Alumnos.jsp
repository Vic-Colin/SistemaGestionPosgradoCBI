<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Alumnado - Datos Generales</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        .modal { display: none; position: fixed; z-index: 1000; left: 0; top: 0; width: 100%; height: 100%; overflow: auto; background-color: rgba(0,0,0,0.4); }
        .modal-content { background-color: #fefefe; margin: 5% auto; padding: 20px; border: 1px solid #888; width: 80%; border-radius: 8px; }
        .close-modal { color: #aaa; float: right; font-size: 28px; font-weight: bold; cursor: pointer; }
        .close-modal:hover, .close-modal:focus { color: black; text-decoration: none; cursor: pointer; }
    </style>
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
            <h1 class="main-title-dash">Lista de Alumnado</h1>
            <h2 class="welcome-user">Datos Generales - Posgrado MCC</h2>
            
            <div class="table-controls">
                <div class="filters-group">
                    <select class="filter-select" id="filtroTrimestre">
                        <option value="">Ingreso (Todos)</option>
                        <%-- Usamos el atributo 'anioActual' enviado por el Servlet --%>
                        <c:forEach var="a" begin="16" end="${anioActual}">
                            <c:set var="y" value="${anioActual - (a - 16)}" />
                            <option value="${y}-O">${y}-O</option>
                            <option value="${y}-P">${y}-P</option>
                            <option value="${y}-I">${y}-I</option>
                        </c:forEach>
                    </select>
                    
                    <select class="filter-select" id="filtroEstatus">
                        <option value="">Estatus UAM (Todos)</option>
                        <option value="Vigente">Vigente</option>
                        <option value="Titulado">Titulado</option>
                        <option value="Baja">Baja</option>
                    </select>

                    <select class="filter-select" id="filtroPierdeCalidad">
                        <option value="">Pierde Calidad (Todos)</option>
                        <c:forEach var="f" begin="${anioActual - 7}" end="${anioActual + 7}">
                            <option value="${f}-O">${f}-O</option>
                            <option value="${f}-P">${f}-P</option>
                            <option value="${f}-I">${f}-I</option>
                        </c:forEach>
                    </select>

                    <select class="filter-select" id="filtroAnioTit">
                        <option value="">Año Titulación (Todos)</option>
                        <c:forEach var="i" begin="0" end="10">
                            <option value="${anioFull - i}">${anioFull - i}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="actions-group">
                    <button type="button" class="btn-nuevo" id="btnAbrirModal">+ Nuevo Alumno</button>
                    <button class="btn-reporte-top">Generar reporte</button>
                </div>
            </div>

            <div class="table-container" style="overflow-x: auto;">
                <table class="alumnos-table" id="tablaAlumnos" style="min-width: 1700px;">
                    <thead>
                        <tr>
                            <th>Matrícula</th>
                            <th>Nombre</th>
                            <th>Correo Inst.</th>
                            <th>Correo Alt.</th>
                            <th>Teléfono</th>
                            <th>Ingreso</th>
                            <th>F. Ingreso UAM</th>
                            <th>Pierde Calidad</th>
                            <th>Estatus UAM</th>
                            <th>Estatus Beca</th>
                            <th>No. Acta</th>
                            <th>F. Titulación</th>
                            <th>Comentarios</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>2181800084</td>
                            <td>Gomez Terán Alejandro</td>
                            <td>-</td>
                            <td>alejandrog.teran@gmail.com</td>
                            <td>-</td>
                            <td>18-I</td>
                            <td>2018-01-15</td>
                            <td>22-I</td>
                            <td><span class="status-badge">Titulado</span></td>
                            <td>NO TUVO BECA</td>
                            <td>-</td>
                            <td>2024-04-25</td>
                            <td>5 trimestres de prórroga</td>
                            <td>
                                <div style="display: flex; gap: 8px;">
                                    <button class="btn-action-icon edit" onclick="prepararEdicion(this)">✏️</button>
                                    <button class="btn-action-icon delete" onclick="confirmarEliminar(this)">🗑️</button>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </main>
    </div>

    <div id="modalRegistro" class="modal">
        <div class="modal-content" style="width: 750px; max-height: 85vh; overflow-y: auto;">
            <span class="close-modal" id="btnCerrarX">&times;</span>
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
                        <option value="" disabled selected>Trimestre Ingreso...</option>
                        <c:forEach var="anio" begin="0" end="${25 - 16}">
                            <c:set var="a" value="${25 - anio}" />
                            <option value="${a}-O">${a}-O</option>
                            <option value="${a}-P">${a}-P</option>
                            <option value="${a}-I">${a}-I</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-row" style="background: #fdfdfd; padding: 10px; border: 1px solid #eee;">
                    <div style="flex:1;">
                        <label style="font-size: 11px; color: #CD032E; font-weight: bold;">Fecha Ingreso UAM:</label>
                        <input type="date" id="regFechaIngUam">
                    </div>
                    <div style="flex:1;">
                        <label style="font-size: 11px; color: #CD032E; font-weight: bold;">Trimestre Pierde Calidad:</label>
                        <select id="regPierdeCalidad">
                            <option value="" disabled selected>Selecciona...</option>
                            <c:forEach var="f" begin="25" end="30">
                                <option value="${f}-O">${f}-O</option>
                                <option value="${f}-P">${f}-P</option>
                                <option value="${f}-I">${f}-I</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-row">
                    <select id="regEstatus">
                        <option value="" disabled selected>Estatus UAM</option>
                        <option value="Vigente">Vigente</option>
                        <option value="Titulado">Titulado</option>
                        <option value="Baja">Baja</option>
                        <option value="Egresado">Egresado</option>
                    </select>
                    <select id="regEstatusBeca">
                        <option value="" disabled selected>Estatus Beca</option>
                        <option value="VIGENTE">VIGENTE</option>
                        <option value="NO TUVO BECA">NO TUVO BECA</option>
                        <option value="CONCLUIDA">CONCLUIDA</option>
                    </select>
                </div>
                <div class="form-row">
                    <input type="text" id="regActa" placeholder="Número de Acta">
                    <div style="flex:1;">
                        <label style="font-size: 11px; color: #CD032E; font-weight: bold;">Fecha Titulación:</label>
                        <input type="date" id="regFechaTit">
                    </div>
                </div>
                <div class="form-row">
                    <input type="text" id="regComentarios" placeholder="Comentarios">
                </div>
                <div class="form-actions" style="margin-top: 20px;">
                    <button type="button" class="btn-cancelar" id="btnCancelarModal">Cancelar</button>
                    <button type="submit" class="btn-guardar">Guardar Datos</button>
                </div>
            </form>
        </div>
    </div>

    <div id="modalEliminar" class="modal">
        <div class="modal-content" style="width: 400px; text-align: center;">
            <h2 style="color: #CD032E;">¿Eliminar?</h2>
            <div class="form-actions" style="justify-content: center;">
                <button class="btn-cancelar" id="btnCancelarEliminar">Cancelar</button>
                <button class="btn-guardar" id="btnConfirmarBorrado" style="background: #CD032E;">Eliminar</button>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/alumnos.js"></script>
</body>
</html>