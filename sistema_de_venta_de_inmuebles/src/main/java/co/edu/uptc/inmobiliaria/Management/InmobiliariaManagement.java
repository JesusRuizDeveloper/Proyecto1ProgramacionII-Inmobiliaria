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

    //Instancias de clases controladoras
    ClienteManagement clienteManagement = new ClienteManagement();

    //<-----Logica de inmobiliaria----->

    //Crear inmobiliaria
    public boolean crearInmobiliaria(){
        inmobiliarias = daoJson.leerArchivo();

        if(!validaciones.existeIdObjetoPorId(inmobiliarias, 1)){
            Inmobiliaria i = new Inmobiliaria(1, "Inmobiliaria", "3229874567", "Centro");
            inmobiliarias.add(i);
            validaciones.cargarDatos(inmobiliarias);
            return true;
        }
            return false;
    }

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
        inmobiliarias = daoJson.leerArchivo();
        ResultadoInmobiliaria inmoDTO = new ResultadoInmobiliaria(); 
        Inmobiliaria inmo = new Inmobiliaria();

        inmo = inmobiliarias.stream().filter(inmobiliaria -> inmobiliaria.getId() == id).findFirst().orElse(null);
        inmoDTO.setInmobiliaria(inmo);
        inmoDTO.setExisteInmobiliaria(inmo != null);
        return inmoDTO;
    }
        
    ////Buscar Inmobiliaria (UNICA)
    public ResultadoInmobiliaria buscarInmobiliariaPorId(){
        inmobiliarias = daoJson.leerArchivo();
        ResultadoInmobiliaria inmoDTO = new ResultadoInmobiliaria(); 
        Inmobiliaria inmo = new Inmobiliaria();

        inmo = inmobiliarias.stream().filter(inmobiliaria -> inmobiliaria.getId() == 1).findFirst().orElse(null);
        inmoDTO.setInmobiliaria(inmo);
        inmoDTO.setExisteInmobiliaria(inmo != null);
        return inmoDTO;
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

    //Listar inmobiliarias
    public List<Inmobiliaria> listarInmobiliarias(){
        inmobiliarias = daoJson.leerArchivo();
        return inmobiliarias;
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
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
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


    public boolean eliminarAdministrador(int idInmo, int id) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoInmobiliaria resulInmoDTO = buscarInmobiliariaPorId(idInmo);
        ResultadoAdministrador resulAdminDTO = buscarAdministrador(idInmo, id);

        if(resulInmoDTO.getExisteInmobiliaria()){
            if (resulAdminDTO.getExisteAdministrador()) {
                resulInmoDTO.getInmobiliaria().getAdministradores().removeIf(admin -> admin.getId() == id);
                validaciones.cargarDatos(inmobiliarias);

                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }
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

    public List<Administrador> listarAdministradores(int idInmobiliaria) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoInmobiliaria inmoDTO = buscarInmobiliariaPorId(idInmobiliaria);

        if(inmoDTO.getExisteInmobiliaria()){
            return inmoDTO.getInmobiliaria().getAdministradores();
        }

        return new ArrayList<>();
    }



    //<-----Logica de propietario----->



    public boolean eliminarPropietario(int idInmo, int id) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoInmobiliaria resulInmoDTO = buscarInmobiliariaPorId(idInmo);
        ResultadoPropietario resulPropietarioDTO = buscarPropietario(idInmo, id);

        if(resulInmoDTO.getExisteInmobiliaria()){
            if (resulPropietarioDTO.getExistePropietario()) {
                resulInmoDTO.getInmobiliaria().getPropietarios().removeIf(prop -> prop.getId() == id);
                validaciones.cargarDatos(inmobiliarias);

                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }
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

    public List<Propietario> listarPropietarios(int idInmobiliaria) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoInmobiliaria inmoDTO = buscarInmobiliariaPorId(idInmobiliaria);

        if(inmoDTO.getExisteInmobiliaria()){
            return inmoDTO.getInmobiliaria().getPropietarios();
        }

        return new ArrayList<>();
    }

    public boolean agregarInmuebleAPropietario(int idInmo, int idPropietario, int idInmueble) {
        inmobiliarias = daoJson.leerArchivo();

        ResultadoInmobiliaria resulInmoDTO = buscarInmobiliariaPorId(idInmo);
        ResultadoPropietario resulPropietarioDTO = buscarPropietario(idInmo, idPropietario);
        ResultadoInmueble resulInmuebleDTO = buscarInmueble(idInmo, idInmueble);

        if(resulInmoDTO.getExisteInmobiliaria()){
            if(resulPropietarioDTO.getExistePropietario()){
                if(resulInmuebleDTO.getExisteInmueble()){

                    List<Inmueble> inmuebles = resulPropietarioDTO.getPropietario().getInmuebles();

                    if(!inmuebles.stream().anyMatch(inm -> inm.getId() == idInmueble)){
                        inmuebles.add(resulInmuebleDTO.getInmueble());

                        resulPropietarioDTO.getPropietario().setInmuebles(inmuebles);

                        validaciones.cargarDatos(inmobiliarias);

                        return true;
                    }else{
                        return false;
                    }

                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean eliminarInmuebleDePropietario(int idInmo, int idPropietario, int idInmueble) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoInmobiliaria resulInmoDTO = buscarInmobiliariaPorId(idInmo);
        ResultadoPropietario resulPropietarioDTO = buscarPropietario(idInmo, idPropietario);

        if(resulInmoDTO.getExisteInmobiliaria()){
            if (resulPropietarioDTO.getExistePropietario()) {
                resulPropietarioDTO.getPropietario().getInmuebles().removeIf(inm -> inm.getId() == idInmueble);
                validaciones.cargarDatos(inmobiliarias);

                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }
    }

    public List<Inmueble> listarInmueblesPorPropietario(int idInmobiliaria, int idPropietario) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoInmobiliaria inmoDTO = buscarInmobiliariaPorId(idInmobiliaria);
        ResultadoPropietario propietarioDTO = buscarPropietario(idInmobiliaria, idPropietario);

        if(inmoDTO.getExisteInmobiliaria()){
            if(propietarioDTO.getExistePropietario()){
                return propietarioDTO.getPropietario().getInmuebles();
            }
        }

        return new ArrayList<>();
    }



    //<-----Logica de cliente----->



    public boolean cambiarTelefonoCliente(int idInmo, int idCliente, String telefono) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoCliente resultado = buscarCliente(idInmo, idCliente);

            if(resultado.getExisteCliente()) {
                resultado.getCliente().setTelefono(telefono);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

    public boolean eliminarCliente(int idInmo, int id) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoInmobiliaria resulInmoDTO = buscarInmobiliariaPorId(idInmo);
        ResultadoCliente resulClienteDTO = buscarCliente(idInmo, id);

        if(resulInmoDTO.getExisteInmobiliaria()){
            if (resulClienteDTO.getExisteCliente()) {
                resulInmoDTO.getInmobiliaria().getClientes().removeIf(cli -> cli.getId() == id);
                validaciones.cargarDatos(inmobiliarias);

                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }
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


public List<Cliente> listarClientes(int idInmobiliaria) {
    inmobiliarias = daoJson.leerArchivo();
    ResultadoInmobiliaria inmoDTO = buscarInmobiliariaPorId(idInmobiliaria);

    if(inmoDTO.getExisteInmobiliaria()){
        return inmoDTO.getInmobiliaria().getClientes();
    }

    return new ArrayList<>();
}



    //<-----Logica de inmueble----->



    public boolean eliminarInmueble(int idInmo, int id) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoInmobiliaria resulInmoDTO = buscarInmobiliariaPorId(idInmo);
        ResultadoInmueble resulInmuebleDTO = buscarInmueble(idInmo, id);

        if(resulInmoDTO.getExisteInmobiliaria()){
            if (resulInmuebleDTO.getExisteInmueble()) {
                resulInmoDTO.getInmobiliaria().getInmuebles().removeIf(inm -> inm.getId() == id);
                validaciones.cargarDatos(inmobiliarias);

                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }
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

    public List<Inmueble> listarInmuebles(int idInmobiliaria) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoInmobiliaria inmoDTO = buscarInmobiliariaPorId(idInmobiliaria);

        if(inmoDTO.getExisteInmobiliaria()){
            return inmoDTO.getInmobiliaria().getInmuebles();
        }

        return new ArrayList<>();
    }

    public List<Inmueble> listarInmueblesDisponibles(int idInmobiliaria) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoInmobiliaria inmoDTO = buscarInmobiliariaPorId(idInmobiliaria);

        if(inmoDTO.getExisteInmobiliaria()){
            return inmoDTO.getInmobiliaria().getInmuebles().stream().filter(inm -> inm.getEstaDisponible()).toList();
        }

        return new ArrayList<>();
    }

    public List<Inmueble> buscarPorUbicacion(int idInmobiliaria, String ubicacion) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoInmobiliaria inmoDTO = buscarInmobiliariaPorId(idInmobiliaria);

        if(inmoDTO.getExisteInmobiliaria()){
            return inmoDTO.getInmobiliaria().getInmuebles().stream().filter(inm -> inm.getUbicacion().equalsIgnoreCase(ubicacion)).toList();
        }

        return new ArrayList<>();
    }

    public List<Inmueble> buscarPorPrecio(int idInmobiliaria, double precioMinimo, double precioMaximo) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoInmobiliaria inmoDTO = buscarInmobiliariaPorId(idInmobiliaria);

        if(inmoDTO.getExisteInmobiliaria()){
            return inmoDTO.getInmobiliaria().getInmuebles().stream()
                    .filter(inm -> inm.getPrecio() >= precioMinimo && inm.getPrecio() <= precioMaximo)
                    .toList();
        }

        return new ArrayList<>();
    }

    public List<Inmueble> buscarPorTipo(int idInmobiliaria, TipoDeInmueble tipo) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoInmobiliaria inmoDTO = buscarInmobiliariaPorId(idInmobiliaria);

        if(inmoDTO.getExisteInmobiliaria()){
            return inmoDTO.getInmobiliaria().getInmuebles().stream()
                    .filter(inm -> inm.getTipoDeInmueble().equals(tipo))
                    .toList();
        }

        return new ArrayList<>();
    }

    public List<Inmueble> buscarPorContrato(int idInmobiliaria, TipoContrato contrato) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoInmobiliaria inmoDTO = buscarInmobiliariaPorId(idInmobiliaria);

        if(inmoDTO.getExisteInmobiliaria()){
            return inmoDTO.getInmobiliaria().getInmuebles().stream()
                    .filter(inm -> inm.getTipoContrato().equals(contrato))
                    .toList();
        }

        return new ArrayList<>();
    }

}