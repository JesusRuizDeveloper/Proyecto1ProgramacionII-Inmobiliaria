package co.edu.uptc.inmobiliaria.Management;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.inmobiliaria.DAO.DaoJson.DaoInmobiliariaJson;
import co.edu.uptc.inmobiliaria.DAO.DaoMysql.DaoInmobiliariaMysql;
import co.edu.uptc.inmobiliaria.Model.Inmobiliaria;
import co.edu.uptc.inmobiliaria.Util.Validaciones;

public class InmobialiariaManagement {
    //Instancias de clases DAO
    DaoInmobiliariaJson daoJson = new DaoInmobiliariaJson();
    DaoInmobiliariaMysql daoMysql = new DaoInmobiliariaMysql();
    Validaciones validaciones = new Validaciones();

    //Creacion de listas
    List<Inmobiliaria> inmobiliarias = new ArrayList<>();

    /*    
    private int id;
    private String nombre;
    private String telefono;
    private String direccion;*/

    //Crear inmobiliaria
    public boolean crearInmobiliaria(int id, String nombre, String telefono, String direccion){
        inmobiliarias = daoJson.leerArchivo();
        if(!validaciones.existeIdInmobiliariaPorId(inmobiliarias, id)){
            Inmobiliaria i = new Inmobiliaria(id, nombre, telefono, direccion);
            inmobiliarias.add(i);
            validaciones.cargarDatos(inmobiliarias);
            return true;
        }
        return false;
    }

    //Eliminar inmobiliaria
    public boolean eliminarInmobiliariaPorId(int id){
        inmobiliarias = daoJson.leerArchivo();
        if(validaciones.existeIdInmobiliariaPorId(inmobiliarias, id)){
            inmobiliarias.removeIf(inmobiliaria -> inmobiliaria.getId() == id);
            validaciones.cargarDatos(inmobiliarias);
            return true;
        }
        return false;
    }

    //Listar inmobiliarias
    public List<Inmobiliaria> listarInmobiliarias(){
        inmobiliarias = daoJson.leerArchivo();
        return inmobiliarias;
    }

    //Actualizar inmobiliarias
    public boolean modificarNombrePorId(int id, String nombreNuevo){
        inmobiliarias = daoJson.leerArchivo();
        if(validaciones.existeIdInmobiliariaPorId(inmobiliarias, id)){
            inmobiliarias.stream().filter(inmobiliaria -> inmobiliaria.getId() == id).findFirst().orElse(null).setNombre(nombreNuevo);
            validaciones.cargarDatos(inmobiliarias);
            return true;
            }
            return false;
        }

    public boolean modificarTelefonoPorId(int id, String telefono){
        inmobiliarias = daoJson.leerArchivo();
        if(validaciones.existeIdInmobiliariaPorId(inmobiliarias, id)){
            inmobiliarias.stream().filter(inmobiliaria -> inmobiliaria.getId() == id).findFirst().orElse(null).setTelefono(telefono);
            validaciones.cargarDatos(inmobiliarias);
            return true;
            }
            return false;
        }

    public boolean modificarDireccionPorId(int id, String direccion){
        inmobiliarias = daoJson.leerArchivo();
        if(validaciones.existeIdInmobiliariaPorId(inmobiliarias, id)){
            inmobiliarias.stream().filter(inmobiliaria -> inmobiliaria.getId() == id).findFirst().orElse(null).setDireccion(direccion);
            validaciones.cargarDatos(inmobiliarias);
            return true;
            }
            return false;
        }
    
}




