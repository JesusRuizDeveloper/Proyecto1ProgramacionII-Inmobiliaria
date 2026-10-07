package co.edu.uptc.inmobiliaria.DTO;

import co.edu.uptc.inmobiliaria.Model.Propietario;

public class ResultadoPropietario {
    Propietario propietario;
    boolean existePropietario;
    public ResultadoPropietario(Propietario propietario, boolean existePropietario) {
        this.propietario = propietario;
        this.existePropietario = existePropietario;
    }
    public ResultadoPropietario() {
    }
    public Propietario getPropietario() {
        return propietario;
    }
    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }
    public boolean getExistePropietario() {
        return existePropietario;
    }
    public void setExistePropietario(boolean existePropietario) {
        this.existePropietario = existePropietario;
    }

    
    
}
