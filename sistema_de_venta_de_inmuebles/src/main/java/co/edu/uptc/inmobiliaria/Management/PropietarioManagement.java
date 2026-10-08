package co.edu.uptc.inmobiliaria.Management;

import java.util.List;

import co.edu.uptc.inmobiliaria.DAO.DaoJson.DaoInmobiliariaJson;
import co.edu.uptc.inmobiliaria.DTO.ResultadoPropietario;
import co.edu.uptc.inmobiliaria.Model.Inmobiliaria;

public class PropietarioManagement {
    DaoInmobiliariaJson daoJson = new DaoInmobiliariaJson();
    List<Inmobiliaria> inmobiliarias = daoJson.leerArchivo();
    InmobiliariaManagement inmoManage = new InmobiliariaManagement();

    public boolean cambiarNombrePropietario(int idInmo, int idPropietario, String nombre) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoPropietario resultado = inmoManage.buscarPropietario(idInmo, idPropietario);

        if(resultado.getExistePropietario()) {
            resultado.getPropietario().setNombre(nombre);
            daoJson.escribirArchivo(inmobiliarias);
            return true;
        }
        return false;
    }

    public boolean cambiarTelefonoPropietario(int idInmo, int idPropietario, String telefono) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoPropietario resultado = inmoManage.buscarPropietario(idInmo, idPropietario);

            if(resultado.getExistePropietario()) {
                resultado.getPropietario().setTelefono(telefono);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

    public boolean cambiarClavePropietario(int idInmo, int idPropietario, String clave) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoPropietario resultado = inmoManage.buscarPropietario(idInmo, idPropietario);

            if(resultado.getExistePropietario()) {
                resultado.getPropietario().setClave(clave);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }
}