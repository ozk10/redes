<%@ page import="modelo.UsuarioDto" %>
<%
    UsuarioDto usuario = (UsuarioDto) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/vista/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Inicio</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<div class="container index">
    <h1>Bienvenido, <%= session.getAttribute("idusuario") %></h1>
    <p>Accediste como rol <%= session.getAttribute("idrol") %>.</p>
</div>
</html>
