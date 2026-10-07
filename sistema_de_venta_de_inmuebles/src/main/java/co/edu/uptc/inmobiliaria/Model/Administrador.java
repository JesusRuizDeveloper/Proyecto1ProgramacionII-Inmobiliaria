package co.edu.uptc.inmobiliaria.Model;
import co.edu.uptc.inmobiliaria.Interfaces.General;

public class Administrador extends Persona{
    
    private String contraseña;

    public Administrador() {
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public String toString() {
        return "Administrador [contraseña=" + contraseña + "]";
    }
    
}
