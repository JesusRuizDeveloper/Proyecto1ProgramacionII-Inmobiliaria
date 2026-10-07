package co.edu.uptc.inmobiliaria.DTO;

import co.edu.uptc.inmobiliaria.Model.Inmobiliaria;

public class ResultadoInmobiliaria {
    Inmobiliaria inmobiliaria;
    boolean existeInmobiliaria;
    
    public ResultadoInmobiliaria(Inmobiliaria inmobiliaria, boolean existeInmobiliaria) {
        this.inmobiliaria = inmobiliaria;
        this.existeInmobiliaria = existeInmobiliaria;
    }

    public ResultadoInmobiliaria() {
    }

    public Inmobiliaria getInmobiliaria() {
        return inmobiliaria;
    }

    public void setInmobiliaria(Inmobiliaria inmobiliaria) {
        this.inmobiliaria = inmobiliaria;
    }

    public boolean getExisteInmobiliaria() {
        return existeInmobiliaria;
    }

    public void setExisteInmobiliaria(boolean existeInmobiliaria) {
        this.existeInmobiliaria = existeInmobiliaria;
    }

    

    
}
