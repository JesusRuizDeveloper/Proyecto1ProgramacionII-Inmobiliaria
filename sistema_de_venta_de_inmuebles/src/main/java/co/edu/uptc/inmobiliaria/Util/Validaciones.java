package co.edu.uptc.inmobiliaria.Util;

import java.util.List;

import co.edu.uptc.inmobiliaria.DAO.DaoJson.DaoInmobiliariaJson;
import co.edu.uptc.inmobiliaria.Interfaces.General;
import co.edu.uptc.inmobiliaria.Model.Inmobiliaria;

public class Validaciones {
    DaoInmobiliariaJson daoJson = new DaoInmobiliariaJson();

    public <T extends General> boolean existeIdInmobiliariaPorId(List<T> inmobiliarias, int id){
        if(inmobiliarias.stream().anyMatch(inmobiliaria -> inmobiliaria.getId() == id)){
            return true;
        }
        return false;
    }

    public <T extends General> boolean existeIdInmobiliariaPorIdGenerico(T t){
        return false;
    }

    //Cargar datos
    public void cargarDatos(List<Inmobiliaria> inmobiliarias){
        daoJson.escribirArchivo(inmobiliarias);
    }
}
