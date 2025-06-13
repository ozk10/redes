package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import modelo.UsuarioDao;
import modelo.HashUtil;
import modelo.EmpleadoDto;
import modelo.MedicoDto;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet", "/CerrarSesion"})
public class UsuarioServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idusuario = request.getParameter("idusuario");
        String contrasena = request.getParameter("contrasena");

        // Hasheamos la contraseña antes de enviarla a la BD
        String hashContrasena = HashUtil.sha256(contrasena);

        UsuarioDao usuarioDao = new UsuarioDao();
        boolean acceso = usuarioDao.validarUsuario(idusuario, hashContrasena);

        if (acceso) {
            int rol = usuarioDao.obtenerRol(idusuario);
            int codemp = usuarioDao.obtenerCodEmp(idusuario);
            EmpleadoDto empleado = usuarioDao.obtenerEmpleadoPorCodEmp(codemp);

            HttpSession sesion = request.getSession();
            sesion.setAttribute("idusuario", idusuario);
            sesion.setAttribute("idrol", rol);
            sesion.setAttribute("empleado", empleado);

            // Si es médico, obtener también CMP y RNE
            if ("Médico".equalsIgnoreCase(empleado.getNomcargo())) {
                MedicoDto medico = usuarioDao.obtenerMedicoPorCodEmp(codemp);
                sesion.setAttribute("medico", medico);
            }

            // Redirección según el rol del usuario
            if (rol == 1) {
                response.sendRedirect("vista/admision.jsp");
            } else if (rol == 2) {
                response.sendRedirect("vista/medico.jsp");
            } else {
                response.sendRedirect("login.jsp");
            }
        } else {
            response.sendRedirect("vista/error.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("cerrar".equalsIgnoreCase(accion)) {
            HttpSession sesion = request.getSession(false);
            if (sesion != null) {
                sesion.invalidate();
            }
            response.sendRedirect("vista/login.jsp");
        } else {
            response.sendRedirect("vista/login.jsp");
        }
    }
}
