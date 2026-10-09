package co.edu.uptc.inmobiliaria.DAO.DaoMysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.inmobiliaria.Model.Administrador;
import co.edu.uptc.inmobiliaria.Util.ConexionMySQL;
import co.edu.uptc.inmobiliaria.Util.QueryConstants;

public class DaoAdministradorMysql {

    public List<Administrador> listarAdministradores() throws SQLException {
        List<Administrador> administradores = new ArrayList<>();
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.LISTAR_ADMINISTRADORES);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                administradores.add(mapear(rs));
            }
        }
        return administradores;
    }

    public Administrador buscarAdministradorPorId(int idInmobiliaria, int id) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.BUSCAR_ADMINISTRADOR_POR_ID)) {
            ps.setInt(1, idInmobiliaria);
            ps.setInt(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public boolean crearAdministrador(int idInmobiliaria, Administrador administrador) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.CREAR_ADMINISTRADOR)) {
            ps.setInt(1, idInmobiliaria);
            ps.setInt(2, administrador.getId());
            ps.setString(3, administrador.getNombre());
            ps.setString(4, administrador.getTelefono());
            ps.setString(5, administrador.getClave());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean eliminarAdministradorPorId(int idInmobiliaria, int id) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.ELIMINAR_ADMINISTRADOR_POR_ID)) {
            ps.setInt(1, idInmobiliaria);
            ps.setInt(2, id);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean cambiarNombre(int idInmobiliaria, int id, String nombre) throws SQLException {
        return actualizar(QueryConstants.ACTUALIZAR_NOMBRE_ADMINISTRADOR, nombre, idInmobiliaria, id);
    }

    public boolean cambiarTelefono(int idInmobiliaria, int id, String telefono) throws SQLException {
        return actualizar(QueryConstants.ACTUALIZAR_TELEFONO_ADMINISTRADOR, telefono, idInmobiliaria, id);
    }

    public boolean cambiarClave(int idInmobiliaria, int id, String clave) throws SQLException {
        return actualizar(QueryConstants.ACTUALIZAR_CLAVE_ADMINISTRADOR, clave, idInmobiliaria, id);
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

    private Administrador mapear(ResultSet rs) throws SQLException {
        Administrador administrador = new Administrador(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("telefono"),
                rs.getString("clave"));
        administrador.setInmuebles(new DaoAsignacionInmuebleMysql()
                .listarInmueblesPorAdministrador(rs.getInt("id_inmobiliaria"), rs.getInt("id")));
        return administrador;
    }
}
