package co.edu.uptc.inmobiliaria.Model;

import co.edu.uptc.inmobiliaria.Interfaces.General;

public class Cliente implements General{
    private int id;
    private String nombre;
    private String telefono;
    
    public Cliente() {
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

    public String toString() {
        return "Cliente [id=" + id + ", nombre=" + nombre + ", telefono=" + telefono + "]";
    }

    

    

}
