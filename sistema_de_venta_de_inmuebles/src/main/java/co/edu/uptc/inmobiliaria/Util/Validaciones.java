package co.edu.uptc.inmobiliaria.Util;

import java.util.List;

import co.edu.uptc.inmobiliaria.DAO.DaoJson.DaoInmobiliariaJson;
import co.edu.uptc.inmobiliaria.Interfaces.General;
import co.edu.uptc.inmobiliaria.Model.Inmobiliaria;

public class Validaciones {
    DaoInmobiliariaJson daoJson = new DaoInmobiliariaJson();

    public <T extends General> boolean existeIdObjetoPorId(List<T> objetos, int id){
        if(objetos.stream().anyMatch(objeto -> objeto.getId() == id)){
            return true;
        }
        return false;
    }

    //Cargar datos
    public void cargarDatos(List<Inmobiliaria> inmobiliarias){
        daoJson.escribirArchivo(inmobiliarias);
    }
}
