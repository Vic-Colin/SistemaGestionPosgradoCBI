<%
    if (session.getAttribute("usuarioActivo") == null) {
        response.sendRedirect("../index.jsp");
        return;
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu Principal - Sistema de Gestión de Posgrado CBI</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <div class="board-container">
        <header class="header-logos-dash">
            <div class="logo-uam-dash"></div>
            <div class="logo-cbi-dash"></div>
        </header>

        <nav class="red-nav-bar">
            <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn-exit">
                <img src="../img/Out.png" alt="Icono Salir" class="exit-icon">
                <span class="exit-text">SALIR</span>
            </a>
        </nav>

        <main class="dashboard-content">
            <h1 class="main-title-dash">Sistema de Posgrado de la División de CBI</h1>
            <h2 class="welcome-user">Bienvenido </h2>

            <div class="button-grid">
                <div class="button-column">
                    <a href="${pageContext.request.contextPath}/AlumnadoServlet" class="menu-card">Alumnado</a>
                </div>

                <a href="${pageContext.request.contextPath}/BecaServlet" class="menu-card">Becas</a>
                <a href="${pageContext.request.contextPath}/ProfesoradoServlet" class="menu-card">Profesorado</a>
                <a href="${pageContext.request.contextPath}/ProyectosServlet" class="menu-card">Tesis</a>
            </div>
        </main>

        <div class="watermark-logo"></div>
    </div>
</body>
</html>
