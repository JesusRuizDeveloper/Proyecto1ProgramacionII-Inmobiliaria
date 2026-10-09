package co.edu.uptc.inmobiliaria.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {

    private static final String URL = "jdbc:mysql://localhost:3306/inmobiliaria";

    public static void main(String[] args) {
        try (Connection conexion = obtenerConexion()) {
            System.out.println("Conexión exitosa a MySQL");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public static Connection obtenerConexion() throws SQLException {
        
        String usuario = System.getenv("MYSQL_USER");
        String contrasena = System.getenv("MYSQL_PASSWORD");

        if (usuario == null || contrasena == null) {
            throw new IllegalStateException(
                    "Debes configurar las variables MYSQL_USER y MYSQL_PASSWORD."
            );
        }

        return DriverManager.getConnection(URL, usuario, contrasena);
    }
} 
    

