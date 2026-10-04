package co.edu.uptc.inmobiliaria.Model;
import java.util.List;

public class Inmobiliaria {
    private int id;
    private String nombre;
    private String telefono;
    private String direccion;
    private List<Inmueble> inmuebles;
    
    public Inmobiliaria() {
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
        return "Inmobiliaria [id=" + id + ", nombre=" + nombre + ", telefono=" + telefono + ", direccion=" + direccion
                + ", inmuebles=" + inmuebles + "]";
    }
    
    
}
