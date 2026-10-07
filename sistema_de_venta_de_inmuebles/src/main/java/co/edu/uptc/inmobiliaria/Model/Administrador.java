package co.edu.uptc.inmobiliaria.Model;

import co.edu.uptc.inmobiliaria.Enums.Rol;

public class Administrador extends Persona{
    
    private String clave;

    public Administrador(int id, String nombre, String telefono, String clave) {
        super(id, nombre, telefono, Rol.ADMINISTRADOR);
        this.clave = clave;
    }

    public Administrador(){
        super(Rol.ADMINISTRADOR);
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
