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
                    <input type="text" placeholder="Buscar por Matrícula o CVU..." class="search-input" id="filtroBeca">
                    
                    <select class="filter-select" id="filtroEstatusBeca">
                        <option value="">Estatus Beca (Todos)</option>
                        <option value="Vigente">Vigente</option>
                        <option value="Baja">Baja</option>
                        <option value="No tuvo beca">No tuvo beca</option>
                        <option value="Concluida">Concluida</option>
                    </select>
                </div>
                
                <div class="actions-group">
                    <button type="button" class="btn-nuevo" onclick="abrirModalBeca()">+ Registrar Beca</button>
                    <button class="btn-reporte-top">Exportar Excel</button>
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
                            <th>Acciones</th>
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
                            <td>
                                <div style="display: flex; gap: 8px;">
                                    <button class="btn-action-icon edit" onclick="prepararEdicionBeca(this)">✏️</button>
                                    <button class="btn-action-icon delete" onclick="confirmarEliminarBeca(this)">🗑️</button>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </main>
    </div>

    <div id="modalBeca" class="modal">
        <div class="modal-content" style="width: 650px;">
            <span class="close-modal" onclick="cerrarModalBeca()">&times;</span>
            <h2 id="modalTituloBeca" style="color: #CD032E;">Registrar Información de Beca</h2>
            
            <form id="formBeca" class="registro-form">
                <input type="hidden" id="editRowIndexBeca" value="-1">
                
                <div class="form-row">
                    <input type="text" id="becaMatricula" placeholder="Matrícula del Alumno" required>
                    <input type="text" id="becaNombre" placeholder="Nombre Completo" required>
                </div>
                <div class="form-row">
                    <input type="text" id="becaCVU" placeholder="CVU CONAHCyT">
                    <input type="text" id="becaIngreso" placeholder="Trimestre Ingreso (ej. 24-O)">
                </div>
                <div class="form-row">
                    <input type="text" id="becaFin" placeholder="Fecha Fin de Beca">
                    <input type="text" id="becaMax" placeholder="Fecha Máx. CONAHCyT">
                </div>
                <div class="form-row">
                    <select id="becaEstatus">
                        <option value="Vigente">Vigente</option>
                        <option value="No tuvo beca">No tuvo beca</option>
                        <option value="Baja">Baja</option>
                        <option value="Concluida">Concluida</option>
                    </select>
                    <input type="text" id="becaTit" placeholder="Fecha de Titulación">
                </div>
                <textarea id="becaComentario" placeholder="Observaciones de la beca..." rows="2"></textarea>

                <div class="form-actions">
                    <button type="button" class="btn-cancelar" onclick="cerrarModalBeca()">Cancelar</button>
                    <button type="submit" class="btn-guardar">Guardar Registro</button>
                </div>
            </form>
        </div>
    </div>

    <div id="modalEliminarBeca" class="modal">
        <div class="modal-content" style="width: 350px; text-align: center;">
            <h2 style="color: #CD032E;">¿Eliminar registro?</h2>
            <p>Se borrará la información de beca de este alumno.</p>
            <div class="form-actions" style="justify-content: center;">
                <button class="btn-cancelar" onclick="cerrarModalEliminarBeca()">Cancelar</button>
                <button class="btn-guardar" id="btnConfirmarBorradoBeca" style="background: #CD032E;">Eliminar</button>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/becas.js"></script>
</body>
</html>
