package co.edu.uptc.inmobiliaria.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {

    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/inmobiliaria";

    public static Connection obtenerConexion() throws SQLException {
        String url = valorConfigurado("MYSQL_URL", DEFAULT_URL);
        String usuario = System.getenv("MYSQL_USER");
        String contrasena = System.getenv("MYSQL_PASSWORD");

        if (usuario == null || usuario.isBlank()
                || contrasena == null || contrasena.isBlank()) {
            throw new IllegalStateException(
                    "Configura MYSQL_USER y MYSQL_PASSWORD como variables de entorno.");
        }

        return DriverManager.getConnection(url, usuario, contrasena);
    }

    private static String valorConfigurado(String nombre, String valorPorDefecto) {
        String valor = System.getenv(nombre);
        return valor == null || valor.isBlank() ? valorPorDefecto : valor;
    }
}
