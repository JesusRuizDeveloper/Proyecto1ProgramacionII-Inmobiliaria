package co.edu.uptc.inmobiliaria.Model;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.inmobiliaria.Interfaces.General;

public class Inmobiliaria implements General{
    private int id;
    private String nombre;
    private String telefono;
    private String direccion;

    
    private List<Administrador> administradores;
    private List<Inmueble> inmuebles;
    private List<Propietario> propietarios;
    //private List<Persona> clientes;

    
    
    public Inmobiliaria(int id, String nombre, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.administradores = new ArrayList<>();
        this.inmuebles = new ArrayList<>();
        this.propietarios = new ArrayList<>();
        //this.clientes = new ArrayList<>();
    }

    public Inmobiliaria() {
        /*this.administradores = new ArrayList<>();
        this.inmuebles = new ArrayList<>();
        this.propietarios = new ArrayList<>();
        this.clientes = new ArrayList<>();*/
    }

    public List<Propietario> getPropietarios() {
        return propietarios;
    }

    public void setPropietarios(List<Propietario> propietarios) {
        this.propietarios = propietarios;
    }

    //public List<Persona> getClientes() {
        //return clientes;
    //}

    public void setClientes(List<Persona> clientes) {
        //this.clientes = clientes;
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(List<Administrador> administradores) {
        this.administradores = administradores;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public List<Inmueble> getInmuebles() {
        return inmuebles;
    }
    public void setInmuebles(List<Inmueble> inmuebles) {
        this.inmuebles = inmuebles;
    }

    public String toString() {
        return "id = " + id + "\n" +
            "nombre = " + nombre + "\n" +
            "telefono = " + telefono + "\n" +
            "direccion = " + direccion + "\n" +
            "administradores = " + administradores + "\n" +
            "inmuebles = " + inmuebles + "\n" +
            "propietarios = " + propietarios + "\n" +
            "clientes = "  + "\n\n";
    }



}
