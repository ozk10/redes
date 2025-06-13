<%@ page import="modelo.EmpleadoDto" %>
<%@ page session="true" %>
<%
    EmpleadoDto empleado = (EmpleadoDto) session.getAttribute("empleado");
    if (empleado == null) {
        response.sendRedirect("../vista/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admisión</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

    <header class="barra-superior">
        <div class="titulo">Módulo de Admisión</div>
        <div class="info-usuario">
            Bienvenid@ <strong><%= empleado.getNomemp() %> <%= empleado.getApeemp() %></strong><br>
            Código: <%= empleado.getCodemp() %> | Cargo: <%= empleado.getNomcargo() %>
        </div>
    </header>

    <nav class="barra-menu">
        <a href="admision.jsp"><i class="fa fa-id-badge"></i> Mis Datos</a>
        <a href="pacientes.jsp"><i class="fa fa-user"></i> Pacientes</a>
        <a href="#"><i class="fa fa-calendar"></i> Citas</a>
        <a href="<%= request.getContextPath() %>/UsuarioServlet?accion=cerrar">Cerrar Sesión</a>
    </nav>

    <main class="contenido">
        <h2>Datos del Empleado</h2>
        <table class="tabla-empleado">
            <tr>
                <td class="columna-foto">
                    <img src="<%= request.getContextPath() %>/vista/fotos/<%= empleado.getFotoemp() %>" alt="Foto del Empleado" class="foto-empleado">
                </td>
                <td class="columna-datos">
                    <p><strong>Código:</strong> <%= empleado.getCodemp() %></p>
                    <p><strong>Nombre:</strong> <%= empleado.getNomemp() %></p>
                    <p><strong>Apellido:</strong> <%= empleado.getApeemp() %></p>
                    <p><strong>Cargo:</strong> <%= empleado.getNomcargo() %></p>
                </td>
            </tr>
        </table>
    </main>


</body>
</html>
