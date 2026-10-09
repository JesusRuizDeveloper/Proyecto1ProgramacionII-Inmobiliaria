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

    public List<Inmobiliaria> listarInmobiliarias() {
        
        List<Inmobiliaria> inmobiliarias = new ArrayList<>();

        String sql = QueryConstants.LISTAR_INMOBILIARIAS;

        try (Connection conexion = ConexionMySQL.obtenerConexion();
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Inmobiliaria inmo = new Inmobiliaria();
                inmo.setId(rs.getInt("id"));
                inmo.setNombre(rs.getString("nombre"));
                inmo.setTelefono(rs.getString("telefono"));
                inmo.setDireccion(rs.getString("direccion"));
                inmobiliarias.add(inmo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inmobiliarias;
    }

public Inmobiliaria buscarInmobiliariaPorId(int id) throws SQLException {
    String sql = QueryConstants.BUSCAR_INMOBILIARIA_POR_ID;

    try (Connection conexion = ConexionMySQL.obtenerConexion();
        PreparedStatement ps = conexion.prepareStatement(sql)) {

        ps.setInt(1, id);

///rs es el resultado de la busqueda en mysql
        try (ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {

                Inmobiliaria inmobiliaria = new Inmobiliaria();
                inmobiliaria.setId(rs.getInt("id"));
                inmobiliaria.setNombre(rs.getString("nombre"));
                inmobiliaria.setTelefono(rs.getString("telefono"));
                inmobiliaria.setDireccion(rs.getString("direccion"));
                return inmobiliaria;
            }
        }
    }

    return null;
}


    public boolean crearInmobiliaria(Inmobiliaria inmo) {
        String sql = QueryConstants.CREAR_INMOBILIARIA;

        try (Connection conexion = ConexionMySQL.obtenerConexion();
            PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, inmo.getId());
            ps.setString(2, inmo.getNombre());
            ps.setString(3, inmo.getTelefono());
            ps.setString(4, inmo.getDireccion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean eliminarInmobiliariaPorId(int id) throws SQLException {
    try (Connection conexion = ConexionMySQL.obtenerConexion();
        PreparedStatement ps = conexion.prepareStatement(
            QueryConstants.ELIMINAR_INMOBILIARIA_POR_ID)) {

        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }
}

    public boolean modificarNombrePorId(int id, String nombre) throws SQLException {

        return actualizarDato( QueryConstants.ACTUALIZAR_NOMBRE_INMOBILIARIA, nombre, id);
}

public boolean modificarTelefonoPorId(int id, String telefono) throws SQLException {

    return actualizarDato( QueryConstants.ACTUALIZAR_TELEFONO_INMOBILIARIA, telefono, id);
}

public boolean modificarDireccionPorId(int id, String direccion) throws SQLException {

    return actualizarDato(QueryConstants.ACTUALIZAR_DIRECCION_INMOBILIARIA, direccion, id);
}

private boolean actualizarDato(String sql, String valor, int id) throws SQLException {
    try (Connection conexion = ConexionMySQL.obtenerConexion();
    
        PreparedStatement ps = conexion.prepareStatement(sql)) {

        ps.setString(1, valor);
        ps.setInt(2, id);
        return ps.executeUpdate() > 0;
    }
}
}
    
