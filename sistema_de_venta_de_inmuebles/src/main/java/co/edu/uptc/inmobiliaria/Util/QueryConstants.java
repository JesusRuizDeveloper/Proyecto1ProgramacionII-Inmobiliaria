package co.edu.uptc.inmobiliaria.Util;

public final class QueryConstants {

    private QueryConstants() {
    }

    public static final String LISTAR_INMOBILIARIAS =
            "SELECT id, nombre, telefono, direccion FROM inmobiliaria ORDER BY id";
    public static final String BUSCAR_INMOBILIARIA_POR_ID =
            "SELECT id, nombre, telefono, direccion FROM inmobiliaria WHERE id = ?";
    public static final String CREAR_INMOBILIARIA =
            "INSERT INTO inmobiliaria (id, nombre, telefono, direccion) VALUES (?, ?, ?, ?)";
    public static final String ELIMINAR_INMOBILIARIA_POR_ID =
            "DELETE FROM inmobiliaria WHERE id = ?";
    public static final String ACTUALIZAR_NOMBRE_INMOBILIARIA =
            "UPDATE inmobiliaria SET nombre = ? WHERE id = ?";
    public static final String ACTUALIZAR_TELEFONO_INMOBILIARIA =
            "UPDATE inmobiliaria SET telefono = ? WHERE id = ?";
    public static final String ACTUALIZAR_DIRECCION_INMOBILIARIA =
            "UPDATE inmobiliaria SET direccion = ? WHERE id = ?";

    public static final String LISTAR_ADMINISTRADORES =
            "SELECT id_inmobiliaria, id, nombre, telefono, clave FROM administrador ORDER BY id_inmobiliaria, id";
    public static final String BUSCAR_ADMINISTRADOR_POR_ID =
            "SELECT id_inmobiliaria, id, nombre, telefono, clave FROM administrador "
                    + "WHERE id_inmobiliaria = ? AND id = ?";
    public static final String CREAR_ADMINISTRADOR =
            "INSERT INTO administrador (id_inmobiliaria, id, nombre, telefono, clave) VALUES (?, ?, ?, ?, ?)";
    public static final String ELIMINAR_ADMINISTRADOR_POR_ID =
            "DELETE FROM administrador WHERE id_inmobiliaria = ? AND id = ?";
    public static final String ACTUALIZAR_NOMBRE_ADMINISTRADOR =
            "UPDATE administrador SET nombre = ? WHERE id_inmobiliaria = ? AND id = ?";
    public static final String ACTUALIZAR_TELEFONO_ADMINISTRADOR =
            "UPDATE administrador SET telefono = ? WHERE id_inmobiliaria = ? AND id = ?";
    public static final String ACTUALIZAR_CLAVE_ADMINISTRADOR =
            "UPDATE administrador SET clave = ? WHERE id_inmobiliaria = ? AND id = ?";

    public static final String LISTAR_PROPIETARIOS =
            "SELECT id_inmobiliaria, id, nombre, telefono, clave FROM propietario ORDER BY id_inmobiliaria, id";
    public static final String BUSCAR_PROPIETARIO_POR_ID =
            "SELECT id_inmobiliaria, id, nombre, telefono, clave FROM propietario "
                    + "WHERE id_inmobiliaria = ? AND id = ?";
    public static final String CREAR_PROPIETARIO =
            "INSERT INTO propietario (id_inmobiliaria, id, nombre, telefono, clave) VALUES (?, ?, ?, ?, ?)";
    public static final String ELIMINAR_PROPIETARIO_POR_ID =
            "DELETE FROM propietario WHERE id_inmobiliaria = ? AND id = ?";
    public static final String ACTUALIZAR_NOMBRE_PROPIETARIO =
            "UPDATE propietario SET nombre = ? WHERE id_inmobiliaria = ? AND id = ?";
    public static final String ACTUALIZAR_TELEFONO_PROPIETARIO =
            "UPDATE propietario SET telefono = ? WHERE id_inmobiliaria = ? AND id = ?";
    public static final String ACTUALIZAR_CLAVE_PROPIETARIO =
            "UPDATE propietario SET clave = ? WHERE id_inmobiliaria = ? AND id = ?";

    public static final String LISTAR_CLIENTES =
            "SELECT id_inmobiliaria, id, nombre, telefono FROM cliente ORDER BY id_inmobiliaria, id";
    public static final String BUSCAR_CLIENTE_POR_ID =
            "SELECT id_inmobiliaria, id, nombre, telefono FROM cliente "
                    + "WHERE id_inmobiliaria = ? AND id = ?";
    public static final String CREAR_CLIENTE =
            "INSERT INTO cliente (id_inmobiliaria, id, nombre, telefono) VALUES (?, ?, ?, ?)";
    public static final String ELIMINAR_CLIENTE_POR_ID =
            "DELETE FROM cliente WHERE id_inmobiliaria = ? AND id = ?";
    public static final String ACTUALIZAR_NOMBRE_CLIENTE =
            "UPDATE cliente SET nombre = ? WHERE id_inmobiliaria = ? AND id = ?";
    public static final String ACTUALIZAR_TELEFONO_CLIENTE =
            "UPDATE cliente SET telefono = ? WHERE id_inmobiliaria = ? AND id = ?";

    public static final String LISTAR_INMUEBLES =
            "SELECT id_inmobiliaria, id, ubicacion, direccion, precio, tipo_contrato, tipo_inmueble, "
                    + "esta_disponible FROM inmueble ORDER BY id_inmobiliaria, id";
    public static final String BUSCAR_INMUEBLE_POR_ID =
            "SELECT id_inmobiliaria, id, ubicacion, direccion, precio, tipo_contrato, tipo_inmueble, "
                    + "esta_disponible FROM inmueble WHERE id_inmobiliaria = ? AND id = ?";
    public static final String CREAR_INMUEBLE =
            "INSERT INTO inmueble (id_inmobiliaria, id, ubicacion, direccion, precio, tipo_contrato, "
                    + "tipo_inmueble, esta_disponible) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String ELIMINAR_INMUEBLE_POR_ID =
            "DELETE FROM inmueble WHERE id_inmobiliaria = ? AND id = ?";
    public static final String ACTUALIZAR_UBICACION_INMUEBLE =
            "UPDATE inmueble SET ubicacion = ? WHERE id_inmobiliaria = ? AND id = ?";
    public static final String ACTUALIZAR_DIRECCION_INMUEBLE =
            "UPDATE inmueble SET direccion = ? WHERE id_inmobiliaria = ? AND id = ?";
    public static final String ACTUALIZAR_PRECIO_INMUEBLE =
            "UPDATE inmueble SET precio = ? WHERE id_inmobiliaria = ? AND id = ?";
    public static final String ACTUALIZAR_TIPO_CONTRATO_INMUEBLE =
            "UPDATE inmueble SET tipo_contrato = ? WHERE id_inmobiliaria = ? AND id = ?";
    public static final String ACTUALIZAR_TIPO_INMUEBLE =
            "UPDATE inmueble SET tipo_inmueble = ? WHERE id_inmobiliaria = ? AND id = ?";
    public static final String ACTUALIZAR_DISPONIBILIDAD_INMUEBLE =
            "UPDATE inmueble SET esta_disponible = ? WHERE id_inmobiliaria = ? AND id = ?";

    public static final String ASIGNAR_INMUEBLE_A_PROPIETARIO =
            "INSERT INTO propietario_inmueble (id_inmobiliaria, id_propietario, id_inmueble) VALUES (?, ?, ?)";
    public static final String QUITAR_INMUEBLE_DE_PROPIETARIO =
            "DELETE FROM propietario_inmueble WHERE id_inmobiliaria = ? AND id_propietario = ? AND id_inmueble = ?";
    public static final String LISTAR_INMUEBLES_POR_PROPIETARIO =
            "SELECT i.id_inmobiliaria, i.id, i.ubicacion, i.direccion, i.precio, i.tipo_contrato, "
                    + "i.tipo_inmueble, i.esta_disponible FROM inmueble i "
                    + "JOIN propietario_inmueble pi ON pi.id_inmobiliaria = i.id_inmobiliaria "
                    + "AND pi.id_inmueble = i.id WHERE pi.id_inmobiliaria = ? AND pi.id_propietario = ?";
    public static final String LISTAR_INMUEBLES_POR_ADMINISTRADOR =
            "SELECT i.id_inmobiliaria, i.id, i.ubicacion, i.direccion, i.precio, i.tipo_contrato, "
                    + "i.tipo_inmueble, i.esta_disponible FROM inmueble i "
                    + "JOIN administrador_inmueble ai ON ai.id_inmobiliaria = i.id_inmobiliaria "
                    + "AND ai.id_inmueble = i.id WHERE ai.id_inmobiliaria = ? AND ai.id_administrador = ?";
    public static final String ASIGNAR_INMUEBLE_A_ADMINISTRADOR =
            "INSERT INTO administrador_inmueble (id_inmobiliaria, id_administrador, id_inmueble) "
                    + "VALUES (?, ?, ?)";
    public static final String QUITAR_INMUEBLE_DE_ADMINISTRADOR =
            "DELETE FROM administrador_inmueble "
                    + "WHERE id_inmobiliaria = ? AND id_administrador = ? AND id_inmueble = ?";
}
