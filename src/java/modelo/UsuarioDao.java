package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {

    public boolean validarUsuario(String idusuario, String contrasena) {
        boolean acceso = false;
        Connection con = null;
        PreparedStatement ps;
        ResultSet rs;

        try {
            con = ConexionBD.getInstancia().getConexion();
            String sql = "SELECT * FROM usuario WHERE idusuario = ? AND contrasena = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, idusuario);
            ps.setString(2, contrasena);
            rs = ps.executeQuery();

            if (rs.next()) {
                acceso = true;
            }

        } catch (SQLException e) {
            System.out.println("Error al validar usuario: " + e.getMessage());
        } finally {
            ConexionBD.getInstancia().closeConexion(con);
        }

        return acceso;
    }

    public int obtenerRol(String idusuario) {
        int rol = -1;
        Connection con = null;
        PreparedStatement ps;
        ResultSet rs;

        try {
            con = ConexionBD.getInstancia().getConexion();
            String sql = "SELECT codrol FROM usuario WHERE idusuario = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, idusuario);
            rs = ps.executeQuery();

            if (rs.next()) {
                rol = rs.getInt("codrol");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener rol: " + e.getMessage());
        } finally {
            ConexionBD.getInstancia().closeConexion(con);
        }

        return rol;
    }

    public int obtenerCodEmp(String idusuario) {
        int codemp = -1;
        Connection con = null;
        PreparedStatement ps;
        ResultSet rs;

        try {
            con = ConexionBD.getInstancia().getConexion();
            String sql = "SELECT codemp FROM usuario WHERE idusuario = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, idusuario);
            rs = ps.executeQuery();

            if (rs.next()) {
                codemp = rs.getInt("codemp");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener codemp: " + e.getMessage());
        } finally {
            ConexionBD.getInstancia().closeConexion(con);
        }

        return codemp;
    }

public EmpleadoDto obtenerEmpleadoPorCodEmp(int codemp) {
    EmpleadoDto empleado = null;
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    String sql = "SELECT e.codemp, e.nomemp, e.apeemp, e.dniemp, c.nomcargo, e.fotoemp " +
                 "FROM empleado e " +
                 "JOIN cargo c ON e.codcargo = c.codcargo " +
                 "WHERE e.codemp = ?";

    try {
        con = ConexionBD.getInstancia().getConexion();
        pst = con.prepareStatement(sql);
        pst.setInt(1, codemp);
        rs = pst.executeQuery();

        if (rs.next()) {
            int codigo = rs.getInt("codemp");
            String nombre = rs.getString("nomemp");
            String apellido = rs.getString("apeemp");
            String dni = rs.getString("dniemp");
            String nomcargo = rs.getString("nomcargo");
            String foto = rs.getString("fotoemp"); 

            empleado = new EmpleadoDto(codigo, nombre, apellido, dni, nomcargo, foto);
        }

    } catch (SQLException e) {
    } finally {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (con != null) con.close();
        } catch (SQLException ex) {
        }
    }

    return empleado;
}

    
    public String obtenerNombreCargo(int codemp) {
        String nombreCargo = null;
        String sql = "SELECT c.nomcargo FROM empleado e " +
                     "JOIN cargo c ON e.codcargo = c.codcargo " +
                     "WHERE e.codemp = ?";
        try (Connection con = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codemp);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nombreCargo = rs.getString("nomcargo");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener nombre del cargo: " + e.getMessage());
        }
        return nombreCargo;
    }

    public String obtenerCmp(int codemp) {
        String cmp = null;
        String sql = "SELECT cmp FROM medico WHERE codemp = ?";
        try (Connection con = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codemp);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cmp = rs.getString("cmp");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener CMP: " + e.getMessage());
        }
        return cmp;
    }

    public String obtenerRne(int codemp) {
        String rne = null;
        String sql = "SELECT rne FROM medico WHERE codemp = ?";
        try (Connection con = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codemp);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rne = rs.getString("rne");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener RNE: " + e.getMessage());
        }
        return rne;
    }
    
    public MedicoDto obtenerMedicoPorCodEmp(int codemp) {
        MedicoDto medico = null;
        String sql = "SELECT cmp, rne FROM medico WHERE codemp = ?";

        try (Connection con = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codemp);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                medico = new MedicoDto();
                medico.setCmp(rs.getString("cmp"));
                medico.setRne(rs.getString("rne"));
            }

        } catch (SQLException e) {
        }

        return medico;
    }

}
