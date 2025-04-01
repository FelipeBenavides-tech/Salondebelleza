
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:postgresql://localhost:5432/salon_belleza";
    private static final String USER = "postgres"; 
    private static final String PASSWORD = "0000"; 
    
    public static Connection conectar() {
        try {
            // Registrar el driver
            Class.forName("org.postgresql.Driver");
            // Establecer la conexión
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se pudo cargar el driver de PostgreSQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error: No se pudo conectar a la base de datos.");
            e.printStackTrace();
        }
        return null;
    }
}