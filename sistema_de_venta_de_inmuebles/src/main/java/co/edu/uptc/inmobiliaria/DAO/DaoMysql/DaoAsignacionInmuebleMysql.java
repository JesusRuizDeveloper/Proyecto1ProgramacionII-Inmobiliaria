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

public class DaoAsignacionInmuebleMysql {

    public boolean asignarAPropietario(int idInmobiliaria, int idPropietario, int idInmueble)
            throws SQLException {
        return ejecutarAsignacion(QueryConstants.ASIGNAR_INMUEBLE_A_PROPIETARIO,
                idInmobiliaria, idPropietario, idInmueble);
    }

    public boolean quitarDePropietario(int idInmobiliaria, int idPropietario, int idInmueble)
            throws SQLException {
        return ejecutarAsignacion(QueryConstants.QUITAR_INMUEBLE_DE_PROPIETARIO,
                idInmobiliaria, idPropietario, idInmueble);
    }

    public boolean asignarAAdministrador(int idInmobiliaria, int idAdministrador, int idInmueble)
            throws SQLException {
        return ejecutarAsignacion(QueryConstants.ASIGNAR_INMUEBLE_A_ADMINISTRADOR,
                idInmobiliaria, idAdministrador, idInmueble);
    }

    public boolean quitarDeAdministrador(int idInmobiliaria, int idAdministrador, int idInmueble)
            throws SQLException {
        return ejecutarAsignacion(QueryConstants.QUITAR_INMUEBLE_DE_ADMINISTRADOR,
                idInmobiliaria, idAdministrador, idInmueble);
    }

    public List<Inmueble> listarInmueblesPorPropietario(int idInmobiliaria, int idPropietario)
            throws SQLException {
        return listarInmuebles(QueryConstants.LISTAR_INMUEBLES_POR_PROPIETARIO,
                idInmobiliaria, idPropietario);
    }

    public List<Inmueble> listarInmueblesPorAdministrador(int idInmobiliaria, int idAdministrador)
            throws SQLException {
        return listarInmuebles(QueryConstants.LISTAR_INMUEBLES_POR_ADMINISTRADOR,
                idInmobiliaria, idAdministrador);
    }

    private List<Inmueble> listarInmuebles(String sql, int idInmobiliaria, int idEntidad)
            throws SQLException {
        List<Inmueble> inmuebles = new ArrayList<>();
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idInmobiliaria);
            ps.setInt(2, idEntidad);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    inmuebles.add(new Inmueble(
                            rs.getInt("id"),
                            rs.getString("ubicacion"),
                            rs.getString("direccion"),
                            rs.getDouble("precio"),
                            TipoContrato.valueOf(rs.getString("tipo_contrato")),
                            TipoDeInmueble.valueOf(rs.getString("tipo_inmueble")),
                            rs.getBoolean("esta_disponible")));
                }
            }
        }
        return inmuebles;
    }

    private boolean ejecutarAsignacion(String sql, int idInmobiliaria, int idPersona, int idInmueble)
            throws SQLException {
        try (Connection conexion = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idInmobiliaria);
            ps.setInt(2, idPersona);
            ps.setInt(3, idInmueble);
            return ps.executeUpdate() == 1;
        }
    }
}
