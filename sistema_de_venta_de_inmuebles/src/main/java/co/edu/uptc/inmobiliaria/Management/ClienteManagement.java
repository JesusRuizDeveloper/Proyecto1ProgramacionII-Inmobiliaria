package co.edu.uptc.inmobiliaria.Management;

import java.util.List;

import co.edu.uptc.inmobiliaria.DAO.DaoJson.DaoInmobiliariaJson;
import co.edu.uptc.inmobiliaria.DTO.ResultadoCliente;
import co.edu.uptc.inmobiliaria.Model.Inmobiliaria;

public class ClienteManagement {
    DaoInmobiliariaJson daoJson = new DaoInmobiliariaJson();
    List<Inmobiliaria> inmobiliarias = daoJson.leerArchivo();
    InmobiliariaManagement inmoManage = new InmobiliariaManagement();
    
        public boolean cambiarNombreCliente(int idInmo, int idCliente, String nombre) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoCliente resultado = inmoManage.buscarCliente(idInmo, idCliente);

            if(resultado.getExisteCliente()) {
                resultado.getCliente().setNombre(nombre);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

        public boolean cambiarTelefonoCliente(int idInmo, int idCliente, String telefono) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoCliente resultado = inmoManage.buscarCliente(idInmo, idCliente);

            if(resultado.getExisteCliente()) {
                resultado.getCliente().setTelefono(telefono);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }
}