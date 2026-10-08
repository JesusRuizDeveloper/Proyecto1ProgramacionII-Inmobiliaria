package co.edu.uptc.inmobiliaria.Management;

import java.util.List;

import co.edu.uptc.inmobiliaria.DAO.DaoJson.DaoInmobiliariaJson;
import co.edu.uptc.inmobiliaria.DTO.ResultadoAdministrador;
import co.edu.uptc.inmobiliaria.Model.Inmobiliaria;

public class AdministradorManagement {
    DaoInmobiliariaJson daoJson = new DaoInmobiliariaJson();
    List<Inmobiliaria> inmobiliarias = daoJson.leerArchivo();
    InmobiliariaManagement inmoManage = new InmobiliariaManagement();

    public boolean cambiarNombreAdministrador(int idInmo, int idAdmin, String nombre) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoAdministrador resultado = inmoManage.buscarAdministrador(idInmo, idAdmin);

        if(resultado.getExisteAdministrador()) {
            resultado.getAdministrador().setNombre(nombre);
            daoJson.escribirArchivo(inmobiliarias);
            return true;
        }
        return false;
    }

    public boolean cambiarTelefonoAdministrador(int idInmo, int idAdmin, String telefono) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoAdministrador resultado = inmoManage.buscarAdministrador(idInmo, idAdmin);

            if(resultado.getExisteAdministrador()) {
                resultado.getAdministrador().setTelefono(telefono);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

    public boolean cambiarClaveAdministrador(int idInmo, int idAdmin, String clave) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoAdministrador resultado = inmoManage.buscarAdministrador(idInmo, idAdmin);

            if(resultado.getExisteAdministrador()) {
                resultado.getAdministrador().setClave(clave);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }
}