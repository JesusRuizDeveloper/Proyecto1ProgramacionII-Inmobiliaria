package co.edu.uptc.inmobiliaria.DTO;

import co.edu.uptc.inmobiliaria.Model.Inmueble;

public class ResultadoInmueble {
    Inmueble inmueble;
    boolean existeInmueble;
    public ResultadoInmueble(Inmueble inmueble, boolean existeInmueble) {
        this.inmueble = inmueble;
        this.existeInmueble = existeInmueble;
    }
    public ResultadoInmueble() {
    }
    public Inmueble getInmueble() {
        return inmueble;
    }
    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }
    public boolean getExisteInmueble() {
        return existeInmueble;
    }
    public void setExisteInmueble(boolean existeInmueble) {
        this.existeInmueble = existeInmueble;
    }

    
}
