<!DOCTYPE html>
<html>
<head><title>Login</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
    <div class="container">
    <h2>Login de Usuario</h2>
    <form action="<%= request.getContextPath() %>/UsuarioServlet" method="post" class="login-form">
        <div class="form-group">
            <label for="idusuario">Usuario:</label>
            <input type="text" name="idusuario" id="idusuario" required>
        </div>

        <div class="form-group">
            <label for="contrasena">Contraseña:</label>
            <input type="password" name="contrasena" id="contrasena" required>
        </div>

        <div class="form-group boton-login">
            <input type="submit" value="Ingresar">
        </div>
    </form>
    </div>
</body>
</html>
