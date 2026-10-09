package co.edu.uptc.inmobiliaria.Management;

import java.util.List;
import co.edu.uptc.inmobiliaria.DAO.DaoJson.DaoInmobiliariaJson;
import co.edu.uptc.inmobiliaria.Model.Inmobiliaria;
import co.edu.uptc.inmobiliaria.Model.Propietario;

public class PropietarioManagement {
    DaoInmobiliariaJson daoJson = new DaoInmobiliariaJson();
    List<Inmobiliaria> inmobiliarias = daoJson.leerArchivo();

    public boolean cambiarNombrePropietario(int idInmo, int idPropietario, String nombre) {
        inmobiliarias = daoJson.leerArchivo();
        Inmobiliaria inmobiliaria = inmobiliarias.stream().filter(inmo -> inmo.getId() == idInmo).findFirst().orElse(null);
        if (inmobiliaria == null) return false;
        Propietario propietario = inmobiliaria.getPropietarios().stream().filter(prop -> prop.getId() == idPropietario).findFirst().orElse(null);
        if (propietario == null) return false;
        propietario.setNombre(nombre);
        daoJson.escribirArchivo(inmobiliarias);
        return true;
    }

    public boolean cambiarTelefonoPropietario(int idInmo, int idPropietario, String telefono) {
        inmobiliarias = daoJson.leerArchivo();
        Inmobiliaria inmobiliaria = inmobiliarias.stream().filter(inmo -> inmo.getId() == idInmo).findFirst().orElse(null);
        if (inmobiliaria == null) return false;
        Propietario propietario = inmobiliaria.getPropietarios().stream().filter(prop -> prop.getId() == idPropietario).findFirst().orElse(null);
        if (propietario == null) return false;
        propietario.setTelefono(telefono);
        daoJson.escribirArchivo(inmobiliarias);
        return true;
    }

    public boolean cambiarClavePropietario(int idInmo, int idPropietario, String clave) {
        inmobiliarias = daoJson.leerArchivo();
        Inmobiliaria inmobiliaria = inmobiliarias.stream().filter(inmo -> inmo.getId() == idInmo).findFirst().orElse(null);
        if (inmobiliaria == null) return false;
        Propietario propietario = inmobiliaria.getPropietarios().stream().filter(prop -> prop.getId() == idPropietario).findFirst().orElse(null);
        if (propietario == null) return false;
        propietario.setClave(clave);
        daoJson.escribirArchivo(inmobiliarias);
        return true;
    }
}