package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* El patrón creacional Singleton asegura que una clase pueda 
ser instanciada una vez */

public class ConexionBD {
    
    private static final String URL = "jdbc:mysql://localhost:3306/redes";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    private static ConexionBD unicaInstancia; // inicio del patrón Singleton
    
    //Constructor privado para evitar instanciación desde otra clase
    private ConexionBD(){
        
    } 
    
    /* Método que proporciona la única forma de instanciar esta clase
    desde cualquier otra clase. */
    
    public static ConexionBD getInstancia(){
        // Comprueba si la clase ha sido instanciada
        if (unicaInstancia==null){
            // Crea la instancia
            unicaInstancia=new ConexionBD();
        }
        // Retorna la instancia existente
        return unicaInstancia;
    }
    
    public Connection getConexion() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        return conn;
    }
    
    public  void closeConexion(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
