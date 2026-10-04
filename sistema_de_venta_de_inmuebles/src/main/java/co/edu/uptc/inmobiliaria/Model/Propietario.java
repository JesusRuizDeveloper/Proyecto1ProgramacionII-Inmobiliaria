package co.edu.uptc.inmobiliaria.Model;

public class Propietario {
    private int id;
    private String nombre;
    private String telefono;
    
    public Propietario() {
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
        return "Propietario [id=" + id + ", nombre=" + nombre + ", telefono=" + telefono + "]";
    }

    

}
