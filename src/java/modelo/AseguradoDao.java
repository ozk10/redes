package modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AseguradoDao {
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    public AseguradoDto obtenerAseguradoPorCodigo(int codaseg) {
        AseguradoDto asegurado = null;
        try {
            con = ConexionBD.getInstancia().getConexion();
            String sql = "SELECT nomaseg, apeaseg, dniaseg FROM asegurado WHERE codaseg = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, codaseg);
            rs = pst.executeQuery();
            if (rs.next()) {
                asegurado = new AseguradoDto();
                asegurado.setCodaseg(codaseg);
                asegurado.setNomaseg(rs.getString("nomaseg"));
                asegurado.setApeaseg(rs.getString("apeaseg"));
                asegurado.setDniaseg(rs.getString("dniaseg"));
            }
        } catch (SQLException e) {
        } finally {
            cerrarRecursos();
        }
        return asegurado;
    }

    public List<CertificadoDto> obtenerCertificadosPorCodAsegurado(int codaseg) {
        List<CertificadoDto> lista = new ArrayList<>();
        try {
            con = ConexionBD.getInstancia().getConexion();
            String sql = "SELECT c.codcertif, c.fechaemi, c.fechainicio, c.fechafin, p.nomplan " +
                         "FROM certificado c " +
                         "JOIN plan p ON c.codplan = p.codplan " +
                         "WHERE c.codcertif IN (SELECT codcertif FROM asegurado WHERE codaseg = ?)";
            pst = con.prepareStatement(sql);
            pst.setInt(1, codaseg);
            rs = pst.executeQuery();
            while (rs.next()) {
                CertificadoDto cert = new CertificadoDto();
                cert.setCodcertif(rs.getString("codcertif"));
                cert.setFechaemi(rs.getDate("fechaemi"));
                cert.setFechainicio(rs.getDate("fechainicio"));
                cert.setFechafin(rs.getDate("fechafin"));
                cert.setNomplan(rs.getString("nomplan"));
                lista.add(cert);
            }
        } catch (SQLException e) {
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    
    public List<AseguradoDto> listarAseguradosConCertificados() {
        List<AseguradoDto> lista = new ArrayList<>();

        try {
            con = ConexionBD.getInstancia().getConexion();
            String sql = """
                SELECT 
                    a.codaseg,
                    a.nomaseg,
                    a.apeaseg,
                    c.codcertif,
                    c.fechaemi,
                    c.fechainicio,
                    c.fechafin,
                    p.nomplan
                FROM asegurado a
                INNER JOIN certificado c ON a.codcertif = c.codcertif
                INNER JOIN plan p ON c.codplan = p.codplan
            """;
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            LocalDate hoy = LocalDate.now();

            while (rs.next()) {
                AseguradoDto dto = new AseguradoDto();
                dto.setCodaseg(rs.getInt("codaseg"));
                dto.setNomaseg(rs.getString("nomaseg"));
                dto.setApeaseg(rs.getString("apeaseg"));
                dto.setCodcertif(rs.getString("codcertif"));
                dto.setFechaemi(rs.getDate("fechaemi"));
                dto.setFechainicio(rs.getDate("fechainicio"));
                dto.setFechafin(rs.getDate("fechafin"));
                dto.setNomplan(rs.getString("nomplan"));

                // Calcular estado
                LocalDate fechaEmi = rs.getDate("fechaemi").toLocalDate();
                LocalDate fechaInicio = rs.getDate("fechainicio").toLocalDate();
                LocalDate fechaFin = rs.getDate("fechafin").toLocalDate();

                String status;
                if (hoy.isBefore(fechaInicio)) {
                    status = "Carente";
                } else if (!hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin)) {
                    status = "Vigente";
                } else {
                    status = "Vencido";
                }

                dto.setStatus(status);
                lista.add(dto);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar asegurados: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }

        return lista;
    }
        
    public List<AseguradoDto> listarAseguradosPorDni(String dni) {
        List<AseguradoDto> lista = new ArrayList<>();

        try {
            con = ConexionBD.getInstancia().getConexion();
            String sql = """
                SELECT 
                    a.codaseg,
                    a.nomaseg,
                    a.apeaseg,
                    c.codcertif,
                    c.fechaemi,
                    c.fechainicio,
                    c.fechafin,
                    p.nomplan
                FROM asegurado a
                INNER JOIN certificado c ON a.codcertif = c.codcertif
                INNER JOIN plan p ON c.codplan = p.codplan
                WHERE a.dniaseg = ?
            """;
            pst = con.prepareStatement(sql);
            pst.setString(1, dni);
            rs = pst.executeQuery();

            LocalDate hoy = LocalDate.now();

            while (rs.next()) {
                AseguradoDto dto = new AseguradoDto();
                dto.setCodaseg(rs.getInt("codaseg"));
                dto.setNomaseg(rs.getString("nomaseg"));
                dto.setApeaseg(rs.getString("apeaseg"));
                dto.setCodcertif(rs.getString("codcertif"));
                dto.setFechaemi(rs.getDate("fechaemi"));
                dto.setFechainicio(rs.getDate("fechainicio"));
                dto.setFechafin(rs.getDate("fechafin"));
                dto.setNomplan(rs.getString("nomplan"));

                // Calcular estado
                LocalDate fechaEmi = rs.getDate("fechaemi").toLocalDate();
                LocalDate fechaInicio = rs.getDate("fechainicio").toLocalDate();
                LocalDate fechaFin = rs.getDate("fechafin").toLocalDate();

                String status;
                if (hoy.isBefore(fechaInicio)) {
                    status = "Carente";
                } else if (!hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin)) {
                    status = "Vigente";
                } else {
                    status = "Vencido";
                }

                dto.setStatus(status);
                lista.add(dto);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar asegurado por DNI: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }

        return lista;
    }

    
    private void cerrarRecursos() {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            // No cerramos `con` porque la maneja el Singleton
        } catch (SQLException e) {
        }
    }
}
