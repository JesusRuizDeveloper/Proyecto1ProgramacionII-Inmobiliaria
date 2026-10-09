package co.edu.uptc.inmobiliaria.DAO.DaoMysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.inmobiliaria.Model.Propietario;
import co.edu.uptc.inmobiliaria.Util.ConexionMySQL;
import co.edu.uptc.inmobiliaria.Util.QueryConstants;

public class DaoPropietarioMysql {

    public List<Propietario> listarPropietarios() throws SQLException {
        List<Propietario> propietarios = new ArrayList<>();
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.LISTAR_PROPIETARIOS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                propietarios.add(mapear(rs));
            }
        }
        return propietarios;
    }

    public Propietario buscarPropietarioPorId(int idInmobiliaria, int id) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.BUSCAR_PROPIETARIO_POR_ID)) {
            ps.setInt(1, idInmobiliaria);
            ps.setInt(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public boolean crearPropietario(int idInmobiliaria, Propietario propietario) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.CREAR_PROPIETARIO)) {
            ps.setInt(1, idInmobiliaria);
            ps.setInt(2, propietario.getId());
            ps.setString(3, propietario.getNombre());
            ps.setString(4, propietario.getTelefono());
            ps.setString(5, propietario.getClave());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean eliminarPropietarioPorId(int idInmobiliaria, int id) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.ELIMINAR_PROPIETARIO_POR_ID)) {
            ps.setInt(1, idInmobiliaria);
            ps.setInt(2, id);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean cambiarNombre(int idInmobiliaria, int id, String nombre) throws SQLException {
        return actualizar(QueryConstants.ACTUALIZAR_NOMBRE_PROPIETARIO, nombre, idInmobiliaria, id);
    }

    public boolean cambiarTelefono(int idInmobiliaria, int id, String telefono) throws SQLException {
        return actualizar(QueryConstants.ACTUALIZAR_TELEFONO_PROPIETARIO, telefono, idInmobiliaria, id);
    }

    public boolean cambiarClave(int idInmobiliaria, int id, String clave) throws SQLException {
        return actualizar(QueryConstants.ACTUALIZAR_CLAVE_PROPIETARIO, clave, idInmobiliaria, id);
    }

    private boolean actualizar(String sql, String valor, int idInmobiliaria, int id) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, valor);
            ps.setInt(2, idInmobiliaria);
            ps.setInt(3, id);
            return ps.executeUpdate() == 1;
        }
    }

    private Propietario mapear(ResultSet rs) throws SQLException {
        Propietario propietario = new Propietario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("telefono"),
                rs.getString("clave"));
        propietario.setInmuebles(new DaoAsignacionInmuebleMysql()
                .listarInmueblesPorPropietario(rs.getInt("id_inmobiliaria"), rs.getInt("id")));
        return propietario;
    }
}
