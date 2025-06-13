package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import modelo.AseguradoDao;
import modelo.AseguradoDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

@WebServlet(name = "ExportarAseguradosServlet", urlPatterns = {"/ExportarAseguradosServlet"})
public class ExportarAseguradosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AseguradoDao dao = new AseguradoDao();
        List<AseguradoDto> asegurados = dao.listarAseguradosConCertificados();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Asegurados");

            // Cabecera
            Row header = sheet.createRow(0);
            String[] columnas = {"Código", "Nombres", "Apellidos", "Certificado", "F. Emisión", "F. Inicio", "F. Fin", "Plan", "Estado"};
            for (int i = 0; i < columnas.length; i++) {
                header.createCell(i).setCellValue(columnas[i]);
            }

            // Datos
            int fila = 1;
            for (AseguradoDto a : asegurados) {
                Row row = sheet.createRow(fila++);
                row.createCell(0).setCellValue(a.getCodaseg());
                row.createCell(1).setCellValue(a.getNomaseg());
                row.createCell(2).setCellValue(a.getApeaseg());
                row.createCell(3).setCellValue(a.getCodcertif());
                row.createCell(4).setCellValue(a.getFechaemi().toString());
                row.createCell(5).setCellValue(a.getFechainicio().toString());
                row.createCell(6).setCellValue(a.getFechafin().toString());
                row.createCell(7).setCellValue(a.getNomplan());
                row.createCell(8).setCellValue(a.getStatus());
            }

            // Ajustar columnas
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Configurar respuesta HTTP
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=asegurados.xlsx");

            // Escribir Excel al output
            workbook.write(response.getOutputStream());
        }
    }
}
