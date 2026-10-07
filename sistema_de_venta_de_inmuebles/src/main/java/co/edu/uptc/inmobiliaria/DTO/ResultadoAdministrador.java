package co.edu.uptc.inmobiliaria.DTO;

import co.edu.uptc.inmobiliaria.Model.Administrador;

public class ResultadoAdministrador {
    Administrador administrador;
    boolean existeAdministrador;
    
    public ResultadoAdministrador(Administrador administrador, boolean existeAdministrador) {
        this.administrador = administrador;
        this.existeAdministrador = existeAdministrador;
    }

    public ResultadoAdministrador() {
    
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public boolean getExisteAdministrador() {
        return existeAdministrador;
    }

    public void setExisteAdministrador(boolean existeAdministrador) {
        this.existeAdministrador = existeAdministrador;
    }
    
    
}
