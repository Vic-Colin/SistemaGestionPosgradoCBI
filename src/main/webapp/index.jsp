<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
    const contextPath = "${pageContext.request.contextPath}";
</script>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Sistema Gestión de Posgrado CBI</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="login-container">
        <div class="header-logos">
            <img src="img/LogoA2Digitales.jpg" class="logo-uam" alt="Logo UAM">
            <img src="img/CBI.png" class="logo-cbi" alt="Logo CBI">
        </div>
        
        <div class="red-divider"></div>

        <div class="content-area">
            <h1 class="main-title">Sistema de Gestión de Posgrado</h1>
            <h2 class="sub-title">Iniciar sesión</h2>

            <form class="login-form"
                id="loginForm"
                method="POST"
                action="${pageContext.request.contextPath}/LoginServlet">
                <input type="text" name="usuario" placeholder="USUARIO" required>
                <input type="password" name="password" placeholder="CONTRASEÑA" required>
                <p class="footer-note">
                    El usuario y contraseña son proporcionados por el jefe de departamento.
                </p>
                
                <button type="submit" class="btn-submit">ENTRAR</button>
            </form>
        </div>
        <div class="watermark-logo-index"> </div>
    </div>
    <div class="bottom-bar"></div>
    <div id="errorModal" class="modalerror">
        <div class="modalerror-content">
            <span id="closeModalerror">&times;</span>
            <p id="modalMessage"></p>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/index.js"></script>

</body>
</html>