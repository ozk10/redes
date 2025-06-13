package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import modelo.AseguradoDao;
import modelo.AseguradoDto;
import modelo.CertificadoDto;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(name = "AseguradoServlet", urlPatterns = {"/AseguradoServlet"})
public class AseguradoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String accion = request.getParameter("accion");
        AseguradoDao dao = new AseguradoDao();

        if ("listar".equalsIgnoreCase(accion)) {
            String dni = request.getParameter("dni");
            List<AseguradoDto> asegurados;

            if (dni != null && !dni.isEmpty()) {
                asegurados = dao.listarAseguradosPorDni(dni);
            } else {
                asegurados = dao.listarAseguradosConCertificados();
            }

            JSONArray jsonArray = new JSONArray();
            for (AseguradoDto a : asegurados) {
                JSONObject json = new JSONObject();
                json.put("codaseg", a.getCodaseg());
                json.put("nomaseg", a.getNomaseg());
                json.put("apeaseg", a.getApeaseg());
                json.put("codcertif", a.getCodcertif());
                json.put("fechaemi", a.getFechaemi().toString());
                json.put("fechainicio", a.getFechainicio().toString());
                json.put("fechafin", a.getFechafin().toString());
                json.put("nomplan", a.getNomplan());
                json.put("status", a.getStatus());
                jsonArray.put(json);
            }

            response.setContentType("application/json");
            response.getWriter().write(jsonArray.toString());

        } else {
            // Modo por defecto: obtener asegurado por código
            int codaseg = Integer.parseInt(request.getParameter("codaseg"));
            AseguradoDto asegurado = dao.obtenerAseguradoPorCodigo(codaseg);
            List<CertificadoDto> certificados = dao.obtenerCertificadosPorCodAsegurado(codaseg);

            JSONObject json = new JSONObject();
            json.put("nombre", asegurado.getNomaseg());
            json.put("apellido", asegurado.getApeaseg());
            json.put("dni", asegurado.getDniaseg());

            JSONArray certArray = new JSONArray();
            for (CertificadoDto cert : certificados) {
                JSONObject certJson = new JSONObject();
                certJson.put("codigo", cert.getCodcertif());
                certJson.put("fechaini", cert.getFechainicio().toString());
                certJson.put("fechafin", cert.getFechafin().toString());
                certArray.put(certJson);
            }

            json.put("certificados", certArray);

            response.setContentType("application/json");
            response.getWriter().write(json.toString());
        }
    }
}
