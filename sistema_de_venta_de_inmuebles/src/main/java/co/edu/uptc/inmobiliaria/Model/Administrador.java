package co.edu.uptc.inmobiliaria.Model;

import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.inmobiliaria.Enums.Rol;

public class Administrador extends Persona{
    
    private String clave;
    private List<Inmueble> inmuebles;

    public Administrador(int id, String nombre, String telefono, String clave) {
        super(id, nombre, telefono, Rol.ADMINISTRADOR);
        this.clave = clave;
        inmuebles = new ArrayList<>();
    }

    public List<Inmueble> getInmuebles() {
        return inmuebles;
    }

    public void setInmuebles(List<Inmueble> inmuebles) {
        this.inmuebles = inmuebles;
    }

    public Administrador(){
        super(Rol.ADMINISTRADOR);
        inmuebles = new ArrayList<>();
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return "Administrador [contraseña=" + clave + "]";
    }
    
}
