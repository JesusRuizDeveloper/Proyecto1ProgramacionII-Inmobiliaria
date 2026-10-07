package co.edu.uptc.inmobiliaria.Model;

import co.edu.uptc.inmobiliaria.Enums.Rol;
import co.edu.uptc.inmobiliaria.Interfaces.General;

public class Persona implements General{
    private int id;
    private String nombre;
    private String telefono;
    private Rol rol;
    
    public Persona(int id, String nombre, String telefono, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.rol = rol;
    }

    public Persona(Rol rol) {
        this.rol = rol;
    }

    public Persona() {
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Persona [id=" + id + ", nombre=" + nombre + ", telefono=" + telefono + ", rol=" + rol + "]";
    }

    
}
