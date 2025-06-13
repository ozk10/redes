package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDao {
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    public int obtenerUltimoCodPaciente() {
        int ultimoCodigo = 0;
        try {
            con = ConexionBD.getInstancia().getConexion();
            String sql = "SELECT MAX(codpac) FROM paciente";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                ultimoCodigo = rs.getInt(1);
            }
        } catch (SQLException e) {
        } finally {
            cerrarRecursos();
        }
        return ultimoCodigo;
    }

    public boolean registrarPaciente(PacienteDto paciente) {
        boolean registrado = false;
        try {
            con = ConexionBD.getInstancia().getConexion();
            String sql = "INSERT INTO paciente (codpac, fechaalta, codaseg, telefpac, correopac, fechanac) VALUES (?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(sql);
            pst.setInt(1, paciente.getCodpac());
            pst.setDate(2, paciente.getFechaalta());
            pst.setInt(3, paciente.getCodaseg());
            pst.setString(4, paciente.getTelefpac());
            pst.setString(5, paciente.getCorreopac());
            pst.setDate(6, paciente.getFechanac());
            registrado = pst.executeUpdate() > 0;
        } catch (SQLException e) {
        } finally {
            cerrarRecursos();
        }
        return registrado;
    }

    public boolean actualizarPaciente(PacienteDto paciente) {
        boolean actualizado = false;
        try {
            con = ConexionBD.getInstancia().getConexion();
            String sql = "UPDATE paciente SET telefpac = ?, correopac = ?, fechanac = ? WHERE codpac = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, paciente.getTelefpac());
            pst.setString(2, paciente.getCorreopac());
            pst.setDate(3, paciente.getFechanac());
            pst.setInt(4, paciente.getCodpac());

            actualizado = pst.executeUpdate() > 0;
        } catch (SQLException e) {
            // Puedes agregar log si deseas
        } finally {
            cerrarRecursos();
        }
        return actualizado;
    }


    public List<PacienteDto> listarPacientes() {
        List<PacienteDto> lista = new ArrayList<>();
        try {
            con = ConexionBD.getInstancia().getConexion();
            String sql = "SELECT * FROM paciente";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                PacienteDto paciente = new PacienteDto();
                paciente.setCodpac(rs.getInt("codpac"));
                paciente.setFechaalta(rs.getDate("fechaalta"));
                paciente.setCodaseg(rs.getInt("codaseg"));
                paciente.setTelefpac(rs.getString("telefpac"));
                paciente.setCorreopac(rs.getString("correopac"));
                paciente.setFechanac(rs.getDate("fechanac"));
                lista.add(paciente);
            }
        } catch (SQLException e) {
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    public PacienteDto obtenerPacientePorCodigo(int codpac) {
        PacienteDto paciente = null;
        try {
            con = ConexionBD.getInstancia().getConexion();
            String sql = "SELECT p.*, a.nomaseg, a.apeaseg FROM paciente p " +
             "INNER JOIN asegurado a ON p.codaseg = a.codaseg " +
             "WHERE p.codpac = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, codpac);
            rs = pst.executeQuery();

            if (rs.next()) {
                paciente = new PacienteDto();
                paciente.setCodpac(rs.getInt("codpac"));
                paciente.setFechaalta(rs.getDate("fechaalta"));
                paciente.setCodaseg(rs.getInt("codaseg"));
                paciente.setTelefpac(rs.getString("telefpac"));
                paciente.setCorreopac(rs.getString("correopac"));
                paciente.setFechanac(rs.getDate("fechanac"));
                
                paciente.setNomaseg(rs.getString("nomaseg"));
                paciente.setApeaseg(rs.getString("apeaseg"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener paciente por código: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return paciente;
    }




    private void cerrarRecursos() {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            // No cerramos `con` porque lo gestiona la clase Singleton
        } catch (SQLException e) {
        }
    }
}
