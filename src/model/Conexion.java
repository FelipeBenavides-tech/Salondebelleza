package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private final String URL = "jdbc:postgresql://localhost:5432/DbSalonDeBelleza";
    private final String USER = "postgres";
    private final String PASSWORD = "0000";

    public Connection conectar() {
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver"); // Asegúrate de que el driver esté cargado
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de PostgreSQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos.");
            e.printStackTrace();
        }
        return con;
    }
}
