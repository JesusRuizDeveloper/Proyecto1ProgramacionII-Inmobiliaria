package co.edu.uptc.inmobiliaria.Management;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.inmobiliaria.DAO.DaoJson.DaoInmobiliariaJson;
import co.edu.uptc.inmobiliaria.DAO.DaoMysql.DaoInmobiliariaMysql;
import co.edu.uptc.inmobiliaria.DTO.ResultadoAdministrador;
import co.edu.uptc.inmobiliaria.DTO.ResultadoInmobiliaria;
import co.edu.uptc.inmobiliaria.Model.Administrador;
import co.edu.uptc.inmobiliaria.Model.Cliente;
import co.edu.uptc.inmobiliaria.Model.Inmobiliaria;
import co.edu.uptc.inmobiliaria.Model.Inmueble;
import co.edu.uptc.inmobiliaria.Model.Propietario;
import co.edu.uptc.inmobiliaria.Util.Validaciones;

public class InmobiliariaManagement {

    //Instancias de clases DAO
    DaoInmobiliariaJson daoJson = new DaoInmobiliariaJson();
    DaoInmobiliariaMysql daoMysql = new DaoInmobiliariaMysql();
    Validaciones validaciones = new Validaciones();

    //Creacion de listas
    List<Inmobiliaria> inmobiliarias = new ArrayList<>();


    //<-----Logica de inmobiliaria----->

    //Crear inmobiliaria
    public boolean crearInmobiliaria(int id, String nombre, String telefono, String direccion){
        inmobiliarias = daoJson.leerArchivo();

        if(!validaciones.existeIdObjetoPorId(inmobiliarias, id)){
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

        if(validaciones.existeIdObjetoPorId(inmobiliarias, id)){
            inmobiliarias.removeIf(inmobiliaria -> inmobiliaria.getId() == id);
            validaciones.cargarDatos(inmobiliarias);
            return true;
        }
        return false;
    }

    //Buscar inmobiliaria por id
    public ResultadoInmobiliaria buscarInmobiliariaPorId(int id){
        ResultadoInmobiliaria inmoDTO = new ResultadoInmobiliaria(); 
        Inmobiliaria inmo = new Inmobiliaria();

        inmo = inmobiliarias.stream().filter(inmobiliaria -> inmobiliaria.getId() == id).findFirst().orElse(null);
        inmoDTO.setInmobiliaria(inmo);
        inmoDTO.setExisteInmobiliaria(inmo != null);
        return inmoDTO;
    }

    //Listar inmobiliarias
    public List<Inmobiliaria> listarInmobiliarias(){
        inmobiliarias = daoJson.leerArchivo();
        return inmobiliarias;
    }


    // <----Actualizar inmobiliarias---->

    //Modificar nombre
    public boolean modificarNombrePorId(int id, String nombreNuevo){
        inmobiliarias = daoJson.leerArchivo();

        if(validaciones.existeIdObjetoPorId(inmobiliarias, id)){
            inmobiliarias.stream().filter(inmobiliaria -> inmobiliaria.getId() == id).findFirst().orElse(null).setNombre(nombreNuevo);
            validaciones.cargarDatos(inmobiliarias);
            return true;
            }
            return false;
        }

    //Modificar telefono
    public boolean modificarTelefonoPorId(int id, String telefono){
        inmobiliarias = daoJson.leerArchivo();

        if(validaciones.existeIdObjetoPorId(inmobiliarias, id)){
            inmobiliarias.stream().filter(inmobiliaria -> inmobiliaria.getId() == id).findFirst().orElse(null).setTelefono(telefono);
            validaciones.cargarDatos(inmobiliarias);
            return true;
            }
            return false;
        }

    //Modificar direccion
    public boolean modificarDireccionPorId(int id, String direccion){
        inmobiliarias = daoJson.leerArchivo();

        if(validaciones.existeIdObjetoPorId(inmobiliarias, id)){
            inmobiliarias.stream().filter(inmobiliaria -> inmobiliaria.getId() == id).findFirst().orElse(null).setDireccion(direccion);
            validaciones.cargarDatos(inmobiliarias);
            return true;
            }
            return false;
        }



    ////<------Metodos para la agregacion de entidades------>

    //Agregar un administrador
    public boolean agregarAdministrador(int idInmo, int idAdmin, String nombre, String telefono, String clave){
        inmobiliarias = daoJson.leerArchivo();
        ResultadoInmobiliaria resulInmoDTO = buscarInmobiliariaPorId(idInmo);
        ResultadoAdministrador resulAdminDTO = buscarAdministrador(idInmo, idAdmin);
        Administrador admin = new Administrador(idAdmin, nombre, telefono, clave); 
        List<Administrador> admins = resulInmoDTO.getInmobiliaria().getAdministradores(); 
        admins.add(admin);

        if(resulInmoDTO.getExisteInmobiliaria()){
            if(!resulAdminDTO.getExisteAdministrador()){
                resulInmoDTO.getInmobiliaria().setAdministradores(admins);
                daoJson.escribirArchivo(inmobiliarias);
            }else{
                return false;
            }
        }else{
            return false;
        }
        
        return false;
    }

    public boolean agregarPropietario() {
        return false;
    }

    public boolean agregarCliente() {
        return false;
    }

    public boolean agregarInmueble() {
        return false;
    }

    ////Metodos funcionales (Logica de los entidades)

//<-----Logica de administrador----->

public boolean actualizarAdministrador() {
    return false;
}

public boolean eliminarAdministrador() {
    return false;
}

public ResultadoAdministrador buscarAdministrador(int idInmobiliaria, int idAdmin) {
        ResultadoAdministrador resulAdminDTO = new ResultadoAdministrador();
        ResultadoInmobiliaria inmoDTO = buscarInmobiliariaPorId(idInmobiliaria);
        Inmobiliaria inmo = inmoDTO.getInmobiliaria();

        if(inmoDTO.getExisteInmobiliaria()){
            resulAdminDTO.setAdministrador(inmo.getAdministradores().stream().filter(admin -> admin.getId() == idAdmin).findFirst().orElse(null));
            resulAdminDTO.setExisteAdministrador(resulAdminDTO.getAdministrador() != null);
            return resulAdminDTO;
        }
        resulAdminDTO.setAdministrador(null);
        resulAdminDTO.setExisteAdministrador(false);
        return resulAdminDTO;
}

public List<Administrador> listarAdministradores() {
    return null;
}



//<-----Logica de propietario----->

public boolean actualizarPropietario() {
    return false;
}

public boolean eliminarPropietario() {
    return false;
}

public Propietario buscarPropietario() {
    return null;
}

public List<Propietario> listarPropietarios() {
    return null;
}

public boolean agregarInmuebleAPropietario() {
    return false;
}

public boolean eliminarInmuebleDePropietario() {
    return false;
}

public List<Inmueble> listarInmueblesPorPropietario() {
    return null;
}



//<-----Logica de cliente----->



public boolean actualizarCliente() {
    return false;
}

public boolean eliminarCliente() {
    return false;
}

public Cliente buscarCliente() {
    return null;
}

public List<Cliente> listarClientes() {
    return null;
}



//<-----Logica de inmueble----->



public boolean actualizarInmueble() {
    return false;
}

public boolean eliminarInmueble() {
    return false;
}

public Inmueble buscarInmueble() {
    return null;
}

public List<Inmueble> listarInmuebles() {
    return null;
}

public List<Inmueble> listarInmueblesDisponibles() {
    return null;
}

public List<Inmueble> buscarPorUbicacion() {
    return null;
}

public List<Inmueble> buscarPorPrecio() {
    return null;
}

public List<Inmueble> buscarPorTipo() {
    return null;
}

public List<Inmueble> buscarPorContrato() {
    return null;
}

}