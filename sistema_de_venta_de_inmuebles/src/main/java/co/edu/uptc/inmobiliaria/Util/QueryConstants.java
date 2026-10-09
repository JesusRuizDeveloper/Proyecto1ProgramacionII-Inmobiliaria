package co.edu.uptc.inmobiliaria.Util;

public class QueryConstants {

        public static final String LISTAR_INMOBILIARIAS =
        "SELECT id, nombre, telefono, direccion FROM inmobiliaria";

        public static final String CREAR_INMOBILIARIA =
        "INSERT INTO inmobiliaria (id, nombre, telefono, direccion) VALUES (?, ?, ?, ?)";

        public static final String BUSCAR_INMOBILIARIA_POR_ID =
        "SELECT id, nombre, telefono, direccion FROM inmobiliaria WHERE id = ?";

        public static final String ELIMINAR_INMOBILIARIA_POR_ID =
        "DELETE FROM inmobiliaria WHERE id = ?";

        public static final String ACTUALIZAR_NOMBRE_INMOBILIARIA =
        "UPDATE inmobiliaria SET nombre = ? WHERE id = ?";

        public static final String ACTUALIZAR_TELEFONO_INMOBILIARIA =
        "UPDATE inmobiliaria SET telefono = ? WHERE id = ?";

        public static final String ACTUALIZAR_DIRECCION_INMOBILIARIA =
        "UPDATE inmobiliaria SET direccion = ? WHERE id = ?";
}