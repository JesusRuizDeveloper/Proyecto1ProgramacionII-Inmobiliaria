package co.edu.uptc.inmobiliaria.DAO.DaoMysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.inmobiliaria.Model.Inmobiliaria;
import co.edu.uptc.inmobiliaria.Util.ConexionMySQL;
import co.edu.uptc.inmobiliaria.Util.QueryConstants;

public class DaoInmobiliariaMysql {

    public List<Inmobiliaria> listarInmobiliarias() throws SQLException {
        List<Inmobiliaria> inmobiliarias = new ArrayList<>();

        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.LISTAR_INMOBILIARIAS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                inmobiliarias.add(mapearInmobiliaria(rs));
            }
        }
        return inmobiliarias;
    }

    public Inmobiliaria buscarInmobiliariaPorId(int id) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.BUSCAR_INMOBILIARIA_POR_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapearInmobiliaria(rs) : null;
            }
        }
    }

    public boolean crearInmobiliaria(Inmobiliaria inmobiliaria) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.CREAR_INMOBILIARIA)) {
            ps.setInt(1, inmobiliaria.getId());
            ps.setString(2, inmobiliaria.getNombre());
            ps.setString(3, inmobiliaria.getTelefono());
            ps.setString(4, inmobiliaria.getDireccion());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean eliminarInmobiliariaPorId(int id) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.ELIMINAR_INMOBILIARIA_POR_ID)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean modificarNombrePorId(int id, String nombre) throws SQLException {
        return actualizarDato(QueryConstants.ACTUALIZAR_NOMBRE_INMOBILIARIA, nombre, id);
    }

    public boolean modificarTelefonoPorId(int id, String telefono) throws SQLException {
        return actualizarDato(QueryConstants.ACTUALIZAR_TELEFONO_INMOBILIARIA, telefono, id);
    }

    public boolean modificarDireccionPorId(int id, String direccion) throws SQLException {
        return actualizarDato(QueryConstants.ACTUALIZAR_DIRECCION_INMOBILIARIA, direccion, id);
    }

    private boolean actualizarDato(String sql, String valor, int id) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, valor);
            ps.setInt(2, id);
            return ps.executeUpdate() == 1;
        }
    }

    private Inmobiliaria mapearInmobiliaria(ResultSet rs) throws SQLException {
        Inmobiliaria inmobiliaria = new Inmobiliaria();
        inmobiliaria.setId(rs.getInt("id"));
        inmobiliaria.setNombre(rs.getString("nombre"));
        inmobiliaria.setTelefono(rs.getString("telefono"));
        inmobiliaria.setDireccion(rs.getString("direccion"));
        return inmobiliaria;
    }
}
