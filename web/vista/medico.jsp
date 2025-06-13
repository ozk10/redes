<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.EmpleadoDto" %>
<%@ page import="modelo.MedicoDto" %>
<%
    HttpSession sesion = request.getSession(false);
    if (sesion == null || sesion.getAttribute("idusuario") == null || (Integer)sesion.getAttribute("idrol") != 2) {
        response.sendRedirect("../vista/login.jsp");
        return;
    }

    EmpleadoDto empleado = (EmpleadoDto) sesion.getAttribute("empleado");
    MedicoDto medico = (MedicoDto) sesion.getAttribute("medico");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Módulo Médico</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
<header class="barra-superior">
    <div class="titulo">Módulo Médico</div>
    <div class="info-usuario">
        Bienvenido, <strong><%= empleado.getNomemp() %> <%= empleado.getApeemp() %></strong> |
        Código: <%= empleado.getCodemp() %> |
        Cargo: <%= empleado.getNomcargo() %>
    </div>
</header>

<nav class="barra-menu">
    <a href="medico.jsp"><i class="fas fa-user-md"></i> Mis Datos</a>
    <a href="#"><i class="fas fa-notes-medical"></i> Historia Clínica</a>
    <a href="#"><i class="fas fa-stethoscope"></i> Consulta</a>
    <a href="#"><i class="fas fa-pills"></i> Medicamentos</a>
    <a href="#"><i class="fas fa-vials"></i> Laboratorio</a>
    <a href="<%= request.getContextPath() %>/UsuarioServlet?accion=cerrar">Cerrar Sesión</a>
</nav>

<main class="contenido">
    <h2>Información del Médico</h2>
    <table class="tabla-empleado">
        <tr>
            <td class="columna-foto">
                <img src="<%= request.getContextPath() %>/vista/fotos/<%= empleado.getFotoemp() %>" alt="Foto" class="foto-empleado">
            </td>
            <td class="columna-datos">
                <p><strong>Código:</strong> <%= empleado.getCodemp() %></p>
                <p><strong>Nombre:</strong> <%= empleado.getNomemp() %></p>
                <p><strong>Apellido:</strong> <%= empleado.getApeemp() %></p>
                <p><strong>Cargo:</strong> <%= empleado.getNomcargo() %></p>
                <p><strong>CMP:</strong> <%= medico != null ? medico.getCmp() : "No registrado" %></p>
                <p><strong>RNE:</strong> <%= medico != null ? medico.getRne() : "No registrado" %></p>
            </td>
        </tr>
    </table>
</main>
</body>
</html>
