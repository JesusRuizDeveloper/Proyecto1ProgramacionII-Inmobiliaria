package co.edu.uptc.inmobiliaria.Management;

import java.util.List;

import co.edu.uptc.inmobiliaria.DAO.DaoJson.DaoInmobiliariaJson;
import co.edu.uptc.inmobiliaria.Model.Administrador;
import co.edu.uptc.inmobiliaria.Model.Inmobiliaria;

public class AdministradorManagement {
    DaoInmobiliariaJson daoJson = new DaoInmobiliariaJson();
    List<Inmobiliaria> inmobiliarias = daoJson.leerArchivo();
    InmobiliariaManagement inmoManage = new InmobiliariaManagement();


    public boolean cambiarNombreAdministrador(int idInmo, int idAdmin, String nombre) {
        inmobiliarias = daoJson.leerArchivo();
        Inmobiliaria inmobiliaria = inmobiliarias.stream().filter(inmo -> inmo.getId() == idInmo).findFirst().orElse(null);

        if (inmobiliaria == null) {
            return false;
        }
        Administrador administrador = inmobiliaria.getAdministradores().stream().filter(admin -> admin.getId() == idAdmin).findFirst().orElse(null);

        if (administrador == null) {
            return false;
        }
        administrador.setNombre(nombre);
        daoJson.escribirArchivo(inmobiliarias);
        return true;
    }

    public boolean cambiarTelefonoAdministrador(int idInmo, int idAdmin, String telefono) {
        Inmobiliaria inmobiliaria = inmobiliarias.stream().filter(inmo -> inmo.getId() == idInmo).findFirst().orElse(null);
        if (inmobiliaria == null) {
            inmobiliarias = daoJson.leerArchivo();
            inmobiliaria = inmobiliarias.stream().filter(inmo -> inmo.getId() == idInmo).findFirst().orElse(null);
        }
        if (inmobiliaria == null) return false;
        Administrador administrador = inmobiliaria.getAdministradores().stream().filter(admin -> admin.getId() == idAdmin).findFirst().orElse(null);
        if (administrador == null) return false;
        administrador.setTelefono(telefono);
        daoJson.escribirArchivo(inmobiliarias);
        return true;
    }

    public boolean cambiarClaveAdministrador(int idInmo, int idAdmin, String clave) {
        Inmobiliaria inmobiliaria = inmobiliarias.stream().filter(inmo -> inmo.getId() == idInmo).findFirst().orElse(null);
        if (inmobiliaria == null) {
            inmobiliarias = daoJson.leerArchivo();
            inmobiliaria = inmobiliarias.stream().filter(inmo -> inmo.getId() == idInmo).findFirst().orElse(null);
        }
        if (inmobiliaria == null) return false;
        Administrador administrador = inmobiliaria.getAdministradores().stream().filter(admin -> admin.getId() == idAdmin).findFirst().orElse(null);
        if (administrador == null) return false;
        administrador.setClave(clave);
        daoJson.escribirArchivo(inmobiliarias);
        return true;
        }
}