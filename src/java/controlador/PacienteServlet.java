package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import modelo.PacienteDao;
import modelo.PacienteDto;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.GenericValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebServlet(name = "PacienteServlet", urlPatterns = {"/PacienteServlet"})
public class PacienteServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(PacienteServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("registrar".equalsIgnoreCase(accion)) {
            registrarPaciente(request, response);
        } else if ("actualizar".equalsIgnoreCase(accion)) {
            actualizarPaciente(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no reconocida");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("buscar".equalsIgnoreCase(accion)) {
            String codpacStr = request.getParameter("codpac");

            if (codpacStr != null && !codpacStr.isEmpty()) {
                try {
                    int codpac = Integer.parseInt(codpacStr);
                    PacienteDao pacienteDao = new PacienteDao();
                    PacienteDto paciente = pacienteDao.obtenerPacientePorCodigo(codpac);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    if (paciente != null) {
                        // Construir JSON manualmente
                        String json = "{"
                                + "\"codpac\":" + paciente.getCodpac() + ","
                                 + "\"fechaalta\":\"" + paciente.getFechaalta() + "\","
                                + "\"codaseg\":" + paciente.getCodaseg() + ","
                                + "\"telefpac\":\"" + paciente.getTelefpac() + "\","
                                + "\"correopac\":\"" + paciente.getCorreopac() + "\","
                                + "\"fechanac\":\"" + paciente.getFechanac() + "\","
                                + "\"nomaseg\":\"" + paciente.getNomaseg() + "\","
                                + "\"apeaseg\":\"" + paciente.getApeaseg() + "\""
                                + "}";

                        response.getWriter().write(json);
                    } else {
                        // Retornar un JSON vacío
                        response.getWriter().write("{}");
                    }
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Código inválido");
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Código no proporcionado");
            }
        }

        // Otras acciones (por ejemplo, listar, registrar, etc.)
    }


    
    private void registrarPaciente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("Inicio del registro de paciente.");

        try {
            int codaseg = validarCodAsegurado(request, response);
            String telefpac = validarTelefono(request, response);
            Date fechaalta = validarFechaAlta(request, response);
            Date fechanac = validarFechaNacimiento(request, response);
            String correopac = validarCorreo(request, response);

            if (response.isCommitted()) return; // Si ya se envió un error o redirección

            PacienteDao dao = new PacienteDao();
            int codpac = dao.obtenerUltimoCodPaciente() + 1;

            PacienteDto nuevo = new PacienteDto(codpac, fechaalta, codaseg, telefpac, correopac, fechanac);
            dao.registrarPaciente(nuevo);

            logger.info("Paciente registrado exitosamente: codpac={}, codaseg={}", codpac, codaseg);
            response.sendRedirect("vista/pacientes.jsp");

        } catch (ServletException | IOException e) {
            logger.error("Error al registrar paciente: {}", e.getMessage(), e);
            request.setAttribute("mensaje", "Error al registrar el paciente.");
            request.getRequestDispatcher("vista/pacientes.jsp").forward(request, response);
        }
    }

    private void actualizarPaciente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("Inicio de actualización de paciente.");

        try {
            int codpac = Integer.parseInt(request.getParameter("codpac"));
            String telefpac = validarTelefono(request, response);
            Date fechanac = validarFechaNacimiento(request, response);
            String correopac = validarCorreo(request, response);

            if (response.isCommitted()) return;

            PacienteDto actualizado = new PacienteDto(codpac, telefpac, correopac, fechanac);
            new PacienteDao().actualizarPaciente(actualizado);

            logger.info("Paciente actualizado correctamente: codpac={}", codpac);
            response.sendRedirect("vista/pacientes.jsp");

        } catch (ServletException | IOException | NumberFormatException e) {
            logger.error("Error al actualizar paciente: {}", e.getMessage(), e);
            request.setAttribute("mensaje", "Error al actualizar el paciente.");
            request.getRequestDispatcher("vista/pacientes.jsp").forward(request, response);
        }
    }

    // -------------------------
    // Métodos de validación
    // -------------------------

    private int validarCodAsegurado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String codasegStr = request.getParameter("codaseg");

        if (codasegStr == null || codasegStr.trim().isEmpty()) {
            request.setAttribute("mensaje", "Debe seleccionar un asegurado.");
            request.getRequestDispatcher("vista/pacientes.jsp").forward(request, response);
            return -1;
        }

        try {
            return Integer.parseInt(codasegStr);
        } catch (NumberFormatException e) {
            request.setAttribute("mensaje", "Código de asegurado inválido.");
            request.getRequestDispatcher("vista/pacientes.jsp").forward(request, response);
            return -1;
        }
    }

    private String validarTelefono(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String telefpac = request.getParameter("telefpac");

        if (telefpac != null && !telefpac.trim().isEmpty()) {
            String regex = "^(\\d{9}|\\d{3}[- ]?\\d{3}[- ]?\\d{3})$";
            if (!GenericValidator.matchRegexp(telefpac, regex)) {
                request.setAttribute("mensaje", "El teléfono ingresado no tiene un formato válido.");
                request.getRequestDispatcher("vista/pacientes.jsp").forward(request, response);
                return null;
            }
        }

        return telefpac;
    }

    private Date validarFechaAlta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fechaaltaStr = request.getParameter("fechaalta");

        if (fechaaltaStr == null || fechaaltaStr.trim().isEmpty()) {
            request.setAttribute("mensaje", "La fecha de alta es obligatoria.");
            request.getRequestDispatcher("vista/pacientes.jsp").forward(request, response);
            return null;
        }

        try {
            LocalDate fecha = LocalDate.parse(fechaaltaStr);
            if (!fecha.equals(LocalDate.now())) {
                request.setAttribute("mensaje", "La fecha de alta debe ser igual a la fecha actual.");
                request.getRequestDispatcher("vista/pacientes.jsp").forward(request, response);
                return null;
            }
            return Date.valueOf(fecha);
        } catch (DateTimeParseException e) {
            request.setAttribute("mensaje", "La fecha de alta tiene un formato incorrecto.");
            request.getRequestDispatcher("vista/pacientes.jsp").forward(request, response);
            return null;
        }
    }

    private Date validarFechaNacimiento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fechanacStr = request.getParameter("fechanac");

        if (fechanacStr == null || fechanacStr.trim().isEmpty()) {
            request.setAttribute("mensaje", "La fecha de nacimiento es obligatoria.");
            request.getRequestDispatcher("vista/pacientes.jsp").forward(request, response);
            return null;
        }

        try {
            LocalDate fecha = LocalDate.parse(fechanacStr);
            if (fecha.isAfter(LocalDate.now())) {
                request.setAttribute("mensaje", "La fecha de nacimiento no puede ser futura.");
                request.getRequestDispatcher("vista/pacientes.jsp").forward(request, response);
                return null;
            }
            return Date.valueOf(fecha);
        } catch (DateTimeParseException e) {
            request.setAttribute("mensaje", "Fecha de nacimiento inválida.");
            request.getRequestDispatcher("vista/pacientes.jsp").forward(request, response);
            return null;
        }
    }

    private String validarCorreo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correopac = request.getParameter("correopac");

        EmailValidator validator = EmailValidator.getInstance();
        if (!validator.isValid(correopac)) {
            request.setAttribute("mensaje", "Correo electrónico inválido.");
            request.getRequestDispatcher("vista/pacientes.jsp").forward(request, response);
            return null;
        }

        return correopac;
    }
}
