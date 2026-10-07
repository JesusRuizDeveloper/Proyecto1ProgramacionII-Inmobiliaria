package co.edu.uptc.inmobiliaria.Model;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.inmobiliaria.Enums.Rol;

public class Propietario extends Persona{
    private List<Inmueble> inmuebles; 
    private String clave;

    public Propietario(int id, String nombre, String telefono, String clave) {
        super(id, nombre, telefono, Rol.PROPIETARIO);
        inmuebles = new ArrayList<>();
        this.clave = clave;
    }

    public Propietario() {
        super(Rol.PROPIETARIO);
        inmuebles = new ArrayList<>();
    }

    public List<Inmueble> getInmuebles() {
        return inmuebles;
    }

    public void setInmuebles(List<Inmueble> inmuebles) {
        this.inmuebles = inmuebles;
    }

    @Override
    public String toString() {
        return "Propietario [inmuebles=" + inmuebles + "]";
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }    

    
    
}
