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
    public boolean crearInmobiliaria(int id, String nombre, String telefono, String direccion) {
    Inmobiliaria inmobiliaria = new Inmobiliaria(id, nombre, telefono, direccion);
    return daoMysql.crearInmobiliaria(inmobiliaria);
}


    //Eliminar inmobiliaria
public boolean eliminarInmobiliariaPorId(int id) {
    try {
        return daoMysql.eliminarInmobiliariaPorId(id);
    } catch (java.sql.SQLException e) {
        throw new IllegalStateException("Error al eliminar la inmobiliaria en MySQL", e);
    }
}

    //Buscar inmobiliaria por id
    public ResultadoInmobiliaria buscarInmobiliariaPorId(int id) {
        try {
            
            Inmobiliaria inmobiliaria = daoMysql.buscarInmobiliariaPorId(id);

            ResultadoInmobiliaria resultado = new ResultadoInmobiliaria();
            resultado.setInmobiliaria(inmobiliaria);
            resultado.setExisteInmobiliaria(inmobiliaria != null);
            return resultado;

    }   catch (java.sql.SQLException e) {

            throw new IllegalStateException("Error al buscar la inmobiliaria en MySQL", e);
    }
}

    //Listar inmobiliarias
    public List<Inmobiliaria> listarInmobiliarias() {
    inmobiliarias = daoMysql.listarInmobiliarias();
    return inmobiliarias;
    }


    // <----Actualizar inmobiliarias---->

    //Modificar nombre
    public boolean modificarNombrePorId(int id, String nombreNuevo) {
    try {
        return daoMysql.modificarNombrePorId(id, nombreNuevo);
    } catch (java.sql.SQLException e) {
        throw new IllegalStateException("Error al modificar el nombre en MySQL", e);
    }
}

    //Modificar telefono
    public boolean modificarTelefonoPorId(int id, String telefono) {
    try {
        return daoMysql.modificarTelefonoPorId(id, telefono);
    } catch (java.sql.SQLException e) {
        throw new IllegalStateException("Error al modificar el teléfono en MySQL", e);
    }
}

    //Modificar direccion
    public boolean modificarDireccionPorId(int id, String direccion) {
    try {
        return daoMysql.modificarDireccionPorId(id, direccion);
    } catch (java.sql.SQLException e) {
        throw new IllegalStateException("Error al modificar la dirección en MySQL", e);
    }
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

    public boolean cambiarNombreAdministrador(int idInmo, int idAdmin, String nombre) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoAdministrador resultado = buscarAdministrador(idInmo, idAdmin);

        if(resultado.getExisteAdministrador()) {
            resultado.getAdministrador().setNombre(nombre);
            daoJson.escribirArchivo(inmobiliarias);
            return true;
        }
        return false;
    }

    public boolean cambiarTelefonoAdministrador(int idInmo, int idAdmin, String telefono) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoAdministrador resultado = buscarAdministrador(idInmo, idAdmin);

            if(resultado.getExisteAdministrador()) {
                resultado.getAdministrador().setTelefono(telefono);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

    public boolean cambiarClaveAdministrador(int idInmo, int idAdmin, String clave) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoAdministrador resultado = buscarAdministrador(idInmo, idAdmin);

            if(resultado.getExisteAdministrador()) {
                resultado.getAdministrador().setClave(clave);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

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

    public boolean cambiarNombrePropietario(int idInmo, int idPropietario, String nombre) {
        inmobiliarias = daoJson.leerArchivo();
        ResultadoPropietario resultado = buscarPropietario(idInmo, idPropietario);

        if(resultado.getExistePropietario()) {
            resultado.getPropietario().setNombre(nombre);
            daoJson.escribirArchivo(inmobiliarias);
            return true;
        }
        return false;
    }

    public boolean cambiarTelefonoPropietario(int idInmo, int idPropietario, String telefono) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoPropietario resultado = buscarPropietario(idInmo, idPropietario);

            if(resultado.getExistePropietario()) {
                resultado.getPropietario().setTelefono(telefono);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

    public boolean cambiarClavePropietario(int idInmo, int idPropietario, String clave) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoPropietario resultado = buscarPropietario(idInmo, idPropietario);

            if(resultado.getExistePropietario()) {
                resultado.getPropietario().setClave(clave);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

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

    public boolean cambiarNombreCliente(int idInmo, int idCliente, String nombre) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoCliente resultado = buscarCliente(idInmo, idCliente);

            if(resultado.getExisteCliente()) {
                resultado.getCliente().setNombre(nombre);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

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

    public boolean cambiarUbicacionInmueble(int idInmo, int idInmueble, String ubicacion) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoInmueble resultado = buscarInmueble(idInmo, idInmueble);

            if(resultado.getExisteInmueble()) {
                resultado.getInmueble().setUbicacion(ubicacion);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

    public boolean cambiarDireccionInmueble(int idInmo, int idInmueble, String direccion) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoInmueble resultado = buscarInmueble(idInmo, idInmueble);

            if(resultado.getExisteInmueble()) {
                resultado.getInmueble().setDireccion(direccion);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

    public boolean cambiarPrecioInmueble(int idInmo, int idInmueble, double precio) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoInmueble resultado = buscarInmueble(idInmo, idInmueble);

            if(resultado.getExisteInmueble()) {
                resultado.getInmueble().setPrecio(precio);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

    public boolean cambiarTipoContratoInmueble(int idInmo, int idInmueble, TipoContrato tipoContrato) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoInmueble resultado = buscarInmueble(idInmo, idInmueble);

            if(resultado.getExisteInmueble()) {
                resultado.getInmueble().setTipoContrato(tipoContrato);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
        }

    public boolean cambiarTipoDeInmueble(int idInmo, int idInmueble, TipoDeInmueble tipoDeInmueble) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoInmueble resultado = buscarInmueble(idInmo, idInmueble);

            if(resultado.getExisteInmueble()) {
                resultado.getInmueble().setTipoDeInmueble(tipoDeInmueble);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

    public boolean cambiarDisponibilidadInmueble(int idInmo, int idInmueble, boolean estaDisponible) {
            inmobiliarias = daoJson.leerArchivo();
            ResultadoInmueble resultado = buscarInmueble(idInmo, idInmueble);

            if(resultado.getExisteInmueble()) {
                resultado.getInmueble().setEstaDisponible(estaDisponible);
                daoJson.escribirArchivo(inmobiliarias);
                return true;
            }
            return false;
    }

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