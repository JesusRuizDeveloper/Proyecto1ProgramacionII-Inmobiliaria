package co.edu.uptc.inmobiliaria.Model;

import co.edu.uptc.inmobiliaria.Enums.Rol;

public class Cliente extends Persona{

    public Cliente(int id, String nombre, String telefono){
        super(id, nombre, telefono, Rol.CLIENTE);
    }
    
    public Cliente(){
        super(Rol.CLIENTE);
    }

    @Override
    public String toString() {
        return "Cliente []";
    }

}
