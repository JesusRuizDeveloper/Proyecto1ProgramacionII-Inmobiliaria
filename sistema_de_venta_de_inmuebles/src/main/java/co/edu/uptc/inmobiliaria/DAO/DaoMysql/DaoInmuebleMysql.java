package co.edu.uptc.inmobiliaria.DAO.DaoMysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.inmobiliaria.Enums.TipoContrato;
import co.edu.uptc.inmobiliaria.Enums.TipoDeInmueble;
import co.edu.uptc.inmobiliaria.Model.Inmueble;
import co.edu.uptc.inmobiliaria.Util.ConexionMySQL;
import co.edu.uptc.inmobiliaria.Util.QueryConstants;

public class DaoInmuebleMysql {

    public List<Inmueble> listarInmuebles() throws SQLException {
        List<Inmueble> inmuebles = new ArrayList<>();
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.LISTAR_INMUEBLES);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                inmuebles.add(mapear(rs));
            }
        }
        return inmuebles;
    }

    public Inmueble buscarInmueblePorId(int idInmobiliaria, int id) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.BUSCAR_INMUEBLE_POR_ID)) {
            ps.setInt(1, idInmobiliaria);
            ps.setInt(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public boolean crearInmueble(int idInmobiliaria, Inmueble inmueble) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.CREAR_INMUEBLE)) {
            ps.setInt(1, idInmobiliaria);
            ps.setInt(2, inmueble.getId());
            ps.setString(3, inmueble.getUbicacion());
            ps.setString(4, inmueble.getDireccion());
            ps.setDouble(5, inmueble.getPrecio());
            ps.setString(6, inmueble.getTipoContrato().name());
            ps.setString(7, inmueble.getTipoDeInmueble().name());
            ps.setBoolean(8, inmueble.getEstaDisponible());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean eliminarInmueblePorId(int idInmobiliaria, int id) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.ELIMINAR_INMUEBLE_POR_ID)) {
            ps.setInt(1, idInmobiliaria);
            ps.setInt(2, id);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean cambiarUbicacion(int idInmobiliaria, int id, String ubicacion) throws SQLException {
        return actualizarTexto(QueryConstants.ACTUALIZAR_UBICACION_INMUEBLE, ubicacion, idInmobiliaria, id);
    }

    public boolean cambiarDireccion(int idInmobiliaria, int id, String direccion) throws SQLException {
        return actualizarTexto(QueryConstants.ACTUALIZAR_DIRECCION_INMUEBLE, direccion, idInmobiliaria, id);
    }

    public boolean cambiarTipoContrato(int idInmobiliaria, int id, TipoContrato tipoContrato)
            throws SQLException {
        return actualizarTexto(QueryConstants.ACTUALIZAR_TIPO_CONTRATO_INMUEBLE,
                tipoContrato.name(), idInmobiliaria, id);
    }

    public boolean cambiarTipoDeInmueble(int idInmobiliaria, int id, TipoDeInmueble tipoDeInmueble)
            throws SQLException {
        return actualizarTexto(QueryConstants.ACTUALIZAR_TIPO_INMUEBLE,
                tipoDeInmueble.name(), idInmobiliaria, id);
    }

    public boolean cambiarPrecio(int idInmobiliaria, int id, double precio) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.ACTUALIZAR_PRECIO_INMUEBLE)) {
            ps.setDouble(1, precio);
            ps.setInt(2, idInmobiliaria);
            ps.setInt(3, id);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean cambiarDisponibilidad(int idInmobiliaria, int id, boolean disponible)
            throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.ACTUALIZAR_DISPONIBILIDAD_INMUEBLE)) {
            ps.setBoolean(1, disponible);
            ps.setInt(2, idInmobiliaria);
            ps.setInt(3, id);
            return ps.executeUpdate() == 1;
        }
    }

    private boolean actualizarTexto(String sql, String valor, int idInmobiliaria, int id)
            throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, valor);
            ps.setInt(2, idInmobiliaria);
            ps.setInt(3, id);
            return ps.executeUpdate() == 1;
        }
    }

    private Inmueble mapear(ResultSet rs) throws SQLException {
        return new Inmueble(
                rs.getInt("id"),
                rs.getString("ubicacion"),
                rs.getString("direccion"),
                rs.getDouble("precio"),
                TipoContrato.valueOf(rs.getString("tipo_contrato")),
                TipoDeInmueble.valueOf(rs.getString("tipo_inmueble")),
                rs.getBoolean("esta_disponible"));
    }
}
