package co.edu.uptc.inmobiliaria.Management;

import co.edu.uptc.inmobiliaria.DTO.ResultadoAdministrador;
import co.edu.uptc.inmobiliaria.DTO.ResultadoCliente;
import co.edu.uptc.inmobiliaria.DTO.ResultadoPropietario;
import co.edu.uptc.inmobiliaria.Enums.Rol;
import co.edu.uptc.inmobiliaria.Model.Persona;

/**
 * Inicio de sesion segun el rol:
 *  - ADMINISTRADOR -> ID + clave
 *  - PROPIETARIO   -> ID + clave
 *  - CLIENTE       -> solo ID
 */
public class LoginManagement {

    // Solo existe una inmobiliaria y su id es 1
    private static final int ID_INMOBILIARIA = 1;

    private InmobiliariaManagement inmoManage = new InmobiliariaManagement();

    /** Devuelve la Persona si los datos son correctos, o null si no lo son. */
    public Persona iniciarSesion(int id, String clave, Rol rol) {
        if (rol == null) {
            return null;
        }

        // Recarga los datos del JSON: los metodos buscar* trabajan sobre la lista
        // en memoria, que esta vacia hasta que alguien lee el archivo
        inmoManage.listarInmobiliarias();

        switch (rol) {
        case ADMINISTRADOR:
            ResultadoAdministrador resAdmin = inmoManage.buscarAdministrador(ID_INMOBILIARIA, id);
            if (resAdmin.getExisteAdministrador()
                    && claveCorrecta(resAdmin.getAdministrador().getClave(), clave)) {
                return resAdmin.getAdministrador();
            }
            return null;

        case PROPIETARIO:
            ResultadoPropietario resProp = inmoManage.buscarPropietario(ID_INMOBILIARIA, id);
            if (resProp.getExistePropietario()
                    && claveCorrecta(resProp.getPropietario().getClave(), clave)) {
                return resProp.getPropietario();
            }
            return null;

        case CLIENTE:
            ResultadoCliente resCliente = inmoManage.buscarCliente(ID_INMOBILIARIA, id);
            if (resCliente.getExisteCliente()) {
                return resCliente.getCliente();
            }
            return null;

        default:
            return null;
        }
    }

    // Compara las claves sin fallar si alguna es null
    private boolean claveCorrecta(String claveGuardada, String claveIngresada) {
        return claveGuardada != null && claveGuardada.equals(claveIngresada);
    }
}
