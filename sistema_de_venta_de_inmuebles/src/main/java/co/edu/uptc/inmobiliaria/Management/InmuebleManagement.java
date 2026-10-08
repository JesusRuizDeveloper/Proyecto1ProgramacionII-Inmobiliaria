package co.edu.uptc.inmobiliaria.Management;

import java.util.List;

import co.edu.uptc.inmobiliaria.DAO.DaoJson.DaoInmobiliariaJson;
import co.edu.uptc.inmobiliaria.DTO.ResultadoInmueble;
import co.edu.uptc.inmobiliaria.Enums.TipoContrato;
import co.edu.uptc.inmobiliaria.Enums.TipoDeInmueble;
import co.edu.uptc.inmobiliaria.Model.Inmobiliaria;

public class InmuebleManagement {
    DaoInmobiliariaJson daoJson = new DaoInmobiliariaJson();
    List<Inmobiliaria> inmobiliarias = daoJson.leerArchivo();
    InmobiliariaManagement inmoManage = new InmobiliariaManagement();

        public boolean cambiarUbicacionInmueble(int idInmo, int idInmueble, String ubicacion) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoInmueble resultado = inmoManage.buscarInmueble(idInmo, idInmueble);

            if(resultado.getExisteInmueble()) {
                resultado.getInmueble().setUbicacion(ubicacion);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

    public boolean cambiarDireccionInmueble(int idInmo, int idInmueble, String direccion) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoInmueble resultado = inmoManage.buscarInmueble(idInmo, idInmueble);

            if(resultado.getExisteInmueble()) {
                resultado.getInmueble().setDireccion(direccion);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

    public boolean cambiarPrecioInmueble(int idInmo, int idInmueble, double precio) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoInmueble resultado = inmoManage.buscarInmueble(idInmo, idInmueble);

            if(resultado.getExisteInmueble()) {
                resultado.getInmueble().setPrecio(precio);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

    public boolean cambiarTipoContratoInmueble(int idInmo, int idInmueble, TipoContrato tipoContrato) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoInmueble resultado = inmoManage.buscarInmueble(idInmo, idInmueble);

            if(resultado.getExisteInmueble()) {
                resultado.getInmueble().setTipoContrato(tipoContrato);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
        }

    public boolean cambiarTipoDeInmueble(int idInmo, int idInmueble, TipoDeInmueble tipoDeInmueble) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoInmueble resultado = inmoManage.buscarInmueble(idInmo, idInmueble);

            if(resultado.getExisteInmueble()) {
                resultado.getInmueble().setTipoDeInmueble(tipoDeInmueble);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

    public boolean cambiarDisponibilidadInmueble(int idInmo, int idInmueble, boolean estaDisponible) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoInmueble resultado = inmoManage.buscarInmueble(idInmo, idInmueble);

            if(resultado.getExisteInmueble()) {
                resultado.getInmueble().setEstaDisponible(estaDisponible);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

}