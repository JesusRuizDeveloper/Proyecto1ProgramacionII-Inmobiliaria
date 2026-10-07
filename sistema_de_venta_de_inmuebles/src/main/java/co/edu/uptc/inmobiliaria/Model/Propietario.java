package co.edu.uptc.inmobiliaria.Model;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.inmobiliaria.Enums.Rol;

public class Propietario extends Persona{
    private List<Inmueble> inmuebles; 

    public Propietario(int id, String nombre, String telefono) {
        super(id, nombre, telefono, Rol.PROPIETARIO);
        inmuebles = new ArrayList<>();
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

    
    
}
