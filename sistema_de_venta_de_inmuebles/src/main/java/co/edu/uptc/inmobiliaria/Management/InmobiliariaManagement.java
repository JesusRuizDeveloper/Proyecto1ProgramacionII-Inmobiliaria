package co.edu.uptc.inmobiliaria.Management;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.inmobiliaria.DAO.DaoJson.DaoInmobiliariaJson;
import co.edu.uptc.inmobiliaria.DAO.DaoMysql.DaoInmobiliariaMysql;
import co.edu.uptc.inmobiliaria.DTO.ResultadoAdministrador;
import co.edu.uptc.inmobiliaria.DTO.ResultadoCliente;
import co.edu.uptc.inmobiliaria.DTO.ResultadoInmobiliaria;
import co.edu.uptc.inmobiliaria.DTO.ResultadoInmueble;
import co.edu.uptc.inmobiliaria.DTO.ResultadoPropietario;
import co.edu.uptc.inmobiliaria.Enums.TipoContrato;
import co.edu.uptc.inmobiliaria.Enums.TipoDeInmueble;
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
    /// 

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

    public boolean agregarPropietario(int idInmo, int id, String nombre, String telefono, String clave) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoInmobiliaria resulInmoDTO = buscarInmobiliariaPorId(idInmo);

            if(resulInmoDTO.getExisteInmobiliaria()){
                ResultadoPropietario resulPropietarioDTO = buscarPropietario(idInmo, id);

                if(!resulPropietarioDTO.getExistePropietario()){
                    Propietario propietario = new Propietario(id, nombre, telefono, clave); 
                    List<Propietario> propietarios = resulInmoDTO.getInmobiliaria().getPropietarios(); 
                    propietarios.add(propietario);

                    resulInmoDTO.getInmobiliaria().setPropietarios(propietarios);
                    daoJson.escribirArchivo(inmobiliarias);
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
    }

    public boolean agregarCliente(int idInmo, int id, String nombre, String telefono) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoInmobiliaria resulInmoDTO = buscarInmobiliariaPorId(idInmo);

            if(resulInmoDTO.getExisteInmobiliaria()){
                ResultadoCliente resulClienteDTO = buscarCliente(idInmo, id);

                if(!resulClienteDTO.getExisteCliente()){
                    Cliente cliente = new Cliente(id, nombre, telefono); 
                    List<Cliente> clientes = resulInmoDTO.getInmobiliaria().getClientes(); 
                    clientes.add(cliente);

                    resulInmoDTO.getInmobiliaria().setClientes(clientes);
                    daoJson.escribirArchivo(inmobiliarias);
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
    }

    public boolean agregarInmueble(int idInmo, int id, String ubicacion, String direccion, double precio, TipoContrato tipoContrato,
            TipoDeInmueble tipoDeInmueble, boolean estaDisponible) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoInmobiliaria resulInmoDTO = buscarInmobiliariaPorId(idInmo);

            if(resulInmoDTO.getExisteInmobiliaria()){
                ResultadoInmueble resulInmuebleDTO = buscarInmueble(idInmo, id);

                if(!resulInmuebleDTO.getExisteInmueble()){
                    Inmueble inmueble = new Inmueble(id, ubicacion, direccion, precio, tipoContrato, tipoDeInmueble, estaDisponible); 
                    List<Inmueble> inmuebles = resulInmoDTO.getInmobiliaria().getInmuebles(); 
                    inmuebles.add(inmueble);

                    resulInmoDTO.getInmobiliaria().setInmuebles(inmuebles);
                    daoJson.escribirArchivo(inmobiliarias);
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
    }

        
    
    ////Metodos funcionales (Logica de los entidades)

    //<-----Logica de administrador----->

    //Agregar un administrador

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

    public ResultadoPropietario buscarPropietario(int idInmobiliaria, int idPropietario) {
            ResultadoPropietario resulPropietarioDTO = new ResultadoPropietario();
            ResultadoInmobiliaria inmoDTO = buscarInmobiliariaPorId(idInmobiliaria);
            Inmobiliaria inmo = inmoDTO.getInmobiliaria();

            if(inmoDTO.getExisteInmobiliaria()){
                resulPropietarioDTO.setPropietario(inmo.getPropietarios().stream().filter(prop -> prop.getId() == idPropietario).findFirst().orElse(null));
                resulPropietarioDTO.setExistePropietario(resulPropietarioDTO.getPropietario() != null);
                return resulPropietarioDTO;
            }
            resulPropietarioDTO.setPropietario(null);
            resulPropietarioDTO.setExistePropietario(false);
            return resulPropietarioDTO;
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

    public ResultadoCliente buscarCliente(int idInmobiliaria, int idCliente) {
        ResultadoCliente resulClienteDTO = new ResultadoCliente();
        ResultadoInmobiliaria inmoDTO = buscarInmobiliariaPorId(idInmobiliaria);
        Inmobiliaria inmo = inmoDTO.getInmobiliaria();

        if(inmoDTO.getExisteInmobiliaria()){
            resulClienteDTO.setCliente(inmo.getClientes().stream().filter(cli -> cli.getId() == idCliente).findFirst().orElse(null));
            resulClienteDTO.setExisteCliente(
            resulClienteDTO.getCliente() != null);

            return resulClienteDTO;
        }

        resulClienteDTO.setCliente(null);
        resulClienteDTO.setExisteCliente(false);

        return resulClienteDTO;
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

    public ResultadoInmueble buscarInmueble(int idInmobiliaria, int idInmueble) {
            ResultadoInmueble resulInmuebleDTO = new ResultadoInmueble();
            ResultadoInmobiliaria inmoDTO = buscarInmobiliariaPorId(idInmobiliaria);
            Inmobiliaria inmo = inmoDTO.getInmobiliaria();

            if(inmoDTO.getExisteInmobiliaria()){
                resulInmuebleDTO.setInmueble(inmo.getInmuebles().stream().filter(inm -> inm.getId() == idInmueble).findFirst().orElse(null));
                resulInmuebleDTO.setExisteInmueble(resulInmuebleDTO.getInmueble() != null);
                return resulInmuebleDTO;
            }
            resulInmuebleDTO.setInmueble(null);
            resulInmuebleDTO.setExisteInmueble(false);
            return resulInmuebleDTO;
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