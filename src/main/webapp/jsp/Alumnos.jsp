<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Alumnado - Datos Generales</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        /* Estilos específicos para el control del formulario unificado */
        .modal { display: none; position: fixed; z-index: 1000; left: 0; top: 0; width: 100%; height: 100%; overflow: auto; background-color: rgba(0,0,0,0.4); }
        .modal-content { background-color: #fefefe; margin: 2% auto; padding: 20px; border: 1px solid #888; width: 850px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.2); }
        .close-modal { color: #aaa; float: right; font-size: 28px; font-weight: bold; cursor: pointer; }
        .close-modal:hover { color: #000; }
        
        .seccion-titulo { color: #CD032E; font-size: 16px; border-bottom: 2px solid #eee; margin-top: 20px; margin-bottom: 10px; padding-bottom: 5px; font-weight: bold; text-transform: uppercase; }
        
        /* Layout compacto para fechas y selects */
        .form-group-slim { display: flex; flex-direction: column; flex: 1; min-width: 120px; padding: 0 8px; }
        .label-mini { font-size: 11px; color: #CD032E; margin-bottom: 4px; font-weight: bold; }
        
        .form-row { display: flex; gap: 10px; margin-bottom: 12px; align-items: flex-end; }
        .form-row input, .form-row select { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        
        /* Ajustes de tabla */
        .table-container { overflow-x: auto; background: white; border-radius: 8px; padding: 10px; }
        .alumnos-table { width: 100%; border-collapse: collapse; min-width: 1800px; }
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
                    <input type="text" placeholder="Matrícula" class="search-input" id="filtroMatricula">
                    <select class="filter-select" id="filtroTrimIngreso">
                        <option value="">Ingreso (Todos)</option>
                        <c:forEach var="a" begin="16" end="${anioActual}">
                            <c:set var="y" value="${anioActual - (a - 16)}" />
                            <option value="${y}-O">${y}-O</option>
                            <option value="${y}-P">${y}-P</option>
                            <option value="${y}-I">${y}-I</option>
                        </c:forEach>
                    </select>

                    <select class="filter-select" id="filtroTrimPierde">
                        <option value="">Pierde Calidad (Todos)</option>
                        <c:forEach var="f" begin="${anioActual - 5}" end="${anioActual + 7}">
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
                    
                    <select class="filter-select" id="filtroEstatusUam">
                        <option value="">Estatus UAM (Todos)</option>
                        <option value="Vigente">Vigente</option>
                        <option value="Titulado">Titulado</option>
                        <option value="Baja">Baja</option>
                    </select>
                </div>
                
                <div class="actions-group" style="flex-direction: column; gap: 10px; align-items: flex-end;">
                    <button type="button" class="btn-nuevo" id="btnAbrirModal" style="width: 200px; margin: 0; display: flex; justify-content: center; align-items: center;">+ Nuevo Alumno</button>
                    <button class="btn-reporte-top" style="width: 200px; margin: 0; display: flex; justify-content: center; align-items: center;">Generar reporte</button>
                </div>
            </div>

            <div class="table-container">
                <table class="alumnos-table" id="tablaAlumnos">
                    <thead>
                        <tr>
                            <th>Matrícula</th>
                            <th>Nombre</th>
                            <th>Correo Inst.</th>
                            <th>Correo Alt.</th>
                            <th>Teléfono</th>
                            <th>Trim. Ingreso</th>
                            <th>Fecha Ingreso UAM</th>
                            <th>Trim. Pierde Calidad</th>
                            <th>Estatus UAM</th>
                            <th>Estatus Beca</th>
                            <th>No. Acta</th>
                            <th>Fecha Titulación</th>
                            <th>Título de tesis</th>
                            <th>Área de concentración</th>
                            <th>Director (Asesor)</th>
                            <th>Codirector (Coasesor)</th>
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
                            <td>Modelo cinemático computacional para la reproducción del alfabeto dactilológico de la lengua de señas mexicana</td>
                            <td>Sistemas Inteligentes y manejo de la información</td>
                            <td>Dra. Ángeles Belem Priego</td>
                            <td>-</td>
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
        <div class="modal-content">
            
            <div class="modal-header-flex">
                <h2 id="modalTitulo" class="modal-main-title">Expediente de Alumno</h2>
                <!--<div class="activadores-opcionales">
                    <label><input type="checkbox" id="chkBeca"> Agregar información de Beca</label>
                    <label><input type="checkbox" id="chkTitulacion"> Agregar información de Titulación</label>
                    <label><input type="checkbox" id="chkTesis"> Agregar Proyecto de Tesis</label>
                </div>-->
                <div class="estatus-top-right">
                    <label class="label-mini">Estatus UAM:</label>
                    <select id="regEstatus" class="select-estatus-uam">
                        <option value="Vigente">Vigente</option>
                        <option value="Titulado">Titulado</option>
                        <option value="Baja">Baja</option>
                        <option value="Egresado">Baja Temporal</option>
                    </select>
                </div>
                <span class="btn-close-modal" id="btnCerrarX">&times;</span>
            </div>
            <div class="modal-body-scroll">
                <form id="formNuevoAlumno">
                <input type="hidden" id="editRowIndex" value="-1">
            
                <div class="seccion-titulo">Datos Generales*</div>
                
                <div class="form-row">
                    <div class="form-group-slim">
                        <label class="label-mini">Nombre:*</label>
                        <input type="text" id="regNombre" placeholder="Nombre Completo" required style="flex: 1;">
                    </div>
                    <div class="from-group-slim">
                        <label class="label-mini">Matrícula:*</label>
                        <input type="text" id="regMatricula" placeholder="Matrícula" required style="flex: 2;">
                    </div>
                </div>
                <div class="form-row">
                    <div class="from-group-slim">
                        <label class="label-mini">Correo Institucional:*</label>
                        <input type="email" id="regCorreoInst" placeholder="Correo Institucional">
                    </div>
                    <div class="from-group-slim">
                        <label class="label-mini">Correo Alternativo:</label>
                        <input type="email" id="regCorreoAlt" placeholder="Correo Alternativo">
                    </div>
                    <div class="from-group-slim">
                        <label class="label-mini">Teléfono:</label>
                        <input type="text" id="regTel" placeholder="Teléfono" style="width: 150px;">
                    </div>
                </div>
                
                <div class="seccion-titulo">Datos Académicos*</div>
                <div class="form-row">
                    <div class="form-group-slim">
                        <label class="label-mini">Trim. Ingreso:*</label>
                        <select id="regIngreso"></select>
                    </div>
                    <div class="form-group-slim">
                        <label class="label-mini">Fecha Ingreso UAM:*</label>
                        <input type="date" id="regFechaIngUam">
                    </div>
                    <div class="form-group-slim">
                        <label class="label-mini">Trim. Pierde Calidad:*</label>
                        <select id="regPierdeCalidad"></select>
                    </div>
                </div>
                <div class="accordion">
                    <div class="accordion-header" data-target="seccionBeca">
                        <span>Beca SECIHTI</span>
                        <span class="icono">▶</span>
                    </div>

                    <div id="seccionBeca" class="accordion-body">
                        <div class="form-row">
                            <div class="form-group-slim" style="flex: 1;">
                                <label class="label-mini">Número CVU:</label>
                                <input type="text" id="regCVU" placeholder="Número de CVU">
                            </div>
                            <div class="form-group-slim" style="flex: 1;">
                                <label class="label-mini">Estatus Beca*:</label>
                                <select id="regEstatusBeca">
                                    <option value="NO TUVO BECA" selected>Sin Beca</option>
                                    <option value="VIGENTE">VIGENTE</option>
                                    <option value="CONCLUIDA">CONCLUIDA</option>
                                    <option value="SUSPENDIDA">SUSPENDIDA</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group-slim">
                                <label class="label-mini">Inicio de Beca:</label>
                                <input type="date" id="regFechaInicioBeca">
                            </div>
                            <div class="form-group-slim">
                                <label class="label-mini">Fin de Beca:</label>
                                <input type="date" id="regFechaFinBeca">
                            </div>
                            <div class="form-group-slim">
                                <label class="label-mini">Fecha Máxima:</label>
                                <input type="date" id="regFechaMax">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="accordion">
                    <div class="accordion-header" data-target="seccionTitulacion">
                        <span>Titulación</span>
                        <span class="icono">▶</span>
                    </div>

                    <div id="seccionTitulacion" class="accordion-body">
                        <div class="form-row">                
                            <div class="form-group-slim">
                                <label class="label-mini">No. Acta:</label>
                                <input type="text" id="regActa" placeholder="Acta">
                            </div>
                            <div class="form-group-slim">
                                <label class="label-mini">Fecha Titulación:</label>
                                <input type="date" id="regFechaTit">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="accordion">
                    <div class="accordion-header" data-target="seccionTesis">
                        <span>Proyecto de Tesis</span>
                        <span class="icono">▶</span>
                    </div>

                    <div id="seccionTesis" class="accordion-body">
                        <div class="form-row">
                            <div class="from-group-slim">
                                <label class="label-mini">Título de tesis</label>
                                <input type="text" id="alumTituloTesis" placeholder="Título de tesis">
                            </div>
                            <div class="from-group-slim">
                                <label class="label-mini">Área de concentración</label>
                                <input type="text" id="alumArea" placeholder="Área de concentración">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="from-group-slim">
                                <label class="label-mini">Director (Asesor)</label>
                                <input type="text" id="alumDirector" placeholder="Director">
                            </div>
                            <div class="from-group-slim">
                                <label class="label-mini">Codirector (Coasesor)</label>
                                <input type="text" id="alumCodirector" placeholder="Codirector">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-actions" style="margin-top: 25px; text-align: right;">
                    <button type="button" class="btn-cancelar" id="btnCancelarModal">Cancelar</button>
                    <button type="submit" class="btn-guardar">Guardar Expediente Completo</button>
                </div>
            </form>
        </div>
    </div>
    
    <div id="modalEliminar" class="modal">
        <div class="modal-content" style="width: 400px; text-align: center;">
            <h2 style="color: #CD032E;">¿Eliminar Registro?</h2>
            <div class="form-actions" style="justify-content: center; gap: 15px;">
                <button class="btn-cancelar" id="btnCancelarEliminar">No, Cancelar</button>
                <button class="btn-guardar" id="btnConfirmarBorrado" style="background: #CD032E;">Sí, Eliminar</button>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/alumnos.js"></script>
</body>
</html>