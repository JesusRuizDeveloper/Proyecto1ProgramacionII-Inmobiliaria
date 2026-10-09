package co.edu.uptc.inmobiliaria.DAO.DaoMysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.inmobiliaria.Model.Cliente;
import co.edu.uptc.inmobiliaria.Util.ConexionMySQL;
import co.edu.uptc.inmobiliaria.Util.QueryConstants;

public class DaoClienteMysql {

    public List<Cliente> listarClientes() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.LISTAR_CLIENTES);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                clientes.add(mapear(rs));
            }
        }
        return clientes;
    }

    public Cliente buscarClientePorId(int idInmobiliaria, int id) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.BUSCAR_CLIENTE_POR_ID)) {
            ps.setInt(1, idInmobiliaria);
            ps.setInt(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public boolean crearCliente(int idInmobiliaria, Cliente cliente) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.CREAR_CLIENTE)) {
            ps.setInt(1, idInmobiliaria);
            ps.setInt(2, cliente.getId());
            ps.setString(3, cliente.getNombre());
            ps.setString(4, cliente.getTelefono());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean eliminarClientePorId(int idInmobiliaria, int id) throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(QueryConstants.ELIMINAR_CLIENTE_POR_ID)) {
            ps.setInt(1, idInmobiliaria);
            ps.setInt(2, id);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean cambiarNombre(int idInmobiliaria, int id, String nombre) throws SQLException {
        return actualizar(QueryConstants.ACTUALIZAR_NOMBRE_CLIENTE, nombre, idInmobiliaria, id);
    }

    public boolean cambiarTelefono(int idInmobiliaria, int id, String telefono) throws SQLException {
        return actualizar(QueryConstants.ACTUALIZAR_TELEFONO_CLIENTE, telefono, idInmobiliaria, id);
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

    private Cliente mapear(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("telefono"));
    }
}
