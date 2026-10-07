package co.edu.uptc.inmobiliaria.Model;

import java.util.List;

public class Propietario extends Persona{
    private List<Inmueble> inmuebles; 

    public Propietario() {
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
