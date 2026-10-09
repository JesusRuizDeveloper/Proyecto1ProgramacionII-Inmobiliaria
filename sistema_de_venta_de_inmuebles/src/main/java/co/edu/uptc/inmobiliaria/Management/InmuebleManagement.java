package co.edu.uptc.inmobiliaria.Management;

import java.util.List;
import co.edu.uptc.inmobiliaria.DAO.DaoJson.DaoInmobiliariaJson;
import co.edu.uptc.inmobiliaria.Enums.TipoContrato;
import co.edu.uptc.inmobiliaria.Enums.TipoDeInmueble;
import co.edu.uptc.inmobiliaria.Model.Inmobiliaria;
import co.edu.uptc.inmobiliaria.Model.Inmueble;

public class InmuebleManagement {
    DaoInmobiliariaJson daoJson = new DaoInmobiliariaJson();
    List<Inmobiliaria> inmobiliarias = daoJson.leerArchivo();

    public boolean cambiarUbicacionInmueble(int idInmo, int idInmueble, String ubicacion) {
        inmobiliarias = daoJson.leerArchivo();
        Inmobiliaria inmobiliaria = inmobiliarias.stream().filter(inmo -> inmo.getId() == idInmo).findFirst().orElse(null);
        if (inmobiliaria == null) return false;
        Inmueble inmueble = inmobiliaria.getInmuebles().stream().filter(inm -> inm.getId() == idInmueble).findFirst().orElse(null);
        if (inmueble == null) return false;
        inmueble.setUbicacion(ubicacion);
        daoJson.escribirArchivo(inmobiliarias);
        return true;
    }

    public boolean cambiarDireccionInmueble(int idInmo, int idInmueble, String direccion) {
        inmobiliarias = daoJson.leerArchivo();
        Inmobiliaria inmobiliaria = inmobiliarias.stream().filter(inmo -> inmo.getId() == idInmo).findFirst().orElse(null);
        if (inmobiliaria == null) return false;
        Inmueble inmueble = inmobiliaria.getInmuebles().stream().filter(inm -> inm.getId() == idInmueble).findFirst().orElse(null);
        if (inmueble == null) return false;
        inmueble.setDireccion(direccion);
        daoJson.escribirArchivo(inmobiliarias);
        return true;
    }

    public boolean cambiarPrecioInmueble(int idInmo, int idInmueble, double precio) {
        inmobiliarias = daoJson.leerArchivo();
        Inmobiliaria inmobiliaria = inmobiliarias.stream().filter(inmo -> inmo.getId() == idInmo).findFirst().orElse(null);
        if (inmobiliaria == null) return false;
        Inmueble inmueble = inmobiliaria.getInmuebles().stream().filter(inm -> inm.getId() == idInmueble).findFirst().orElse(null);
        if (inmueble == null) return false;
        inmueble.setPrecio(precio);
        daoJson.escribirArchivo(inmobiliarias);
        return true;
    }

    public boolean cambiarTipoContratoInmueble(int idInmo, int idInmueble, TipoContrato tipoContrato) {
        inmobiliarias = daoJson.leerArchivo();
        Inmobiliaria inmobiliaria = inmobiliarias.stream().filter(inmo -> inmo.getId() == idInmo).findFirst().orElse(null);
        if (inmobiliaria == null) return false;
        Inmueble inmueble = inmobiliaria.getInmuebles().stream().filter(inm -> inm.getId() == idInmueble).findFirst().orElse(null);
        if (inmueble == null) return false;
        inmueble.setTipoContrato(tipoContrato);
        daoJson.escribirArchivo(inmobiliarias);
        return true;
    }

    public boolean cambiarTipoDeInmueble(int idInmo, int idInmueble, TipoDeInmueble tipoDeInmueble) {
        inmobiliarias = daoJson.leerArchivo();
        Inmobiliaria inmobiliaria = inmobiliarias.stream().filter(inmo -> inmo.getId() == idInmo).findFirst().orElse(null);
        if (inmobiliaria == null) return false;
        Inmueble inmueble = inmobiliaria.getInmuebles().stream().filter(inm -> inm.getId() == idInmueble).findFirst().orElse(null);
        if (inmueble == null) return false;
        inmueble.setTipoDeInmueble(tipoDeInmueble);
        daoJson.escribirArchivo(inmobiliarias);
        return true;
    }

    public boolean cambiarDisponibilidadInmueble(int idInmo, int idInmueble, boolean estaDisponible) {
        inmobiliarias = daoJson.leerArchivo();
        Inmobiliaria inmobiliaria = inmobiliarias.stream().filter(inmo -> inmo.getId() == idInmo).findFirst().orElse(null);
        if (inmobiliaria == null) return false;
        Inmueble inmueble = inmobiliaria.getInmuebles().stream().filter(inm -> inm.getId() == idInmueble).findFirst().orElse(null);
        if (inmueble == null) return false;
        inmueble.setEstaDisponible(estaDisponible);
        daoJson.escribirArchivo(inmobiliarias);
        return true;
    }
}