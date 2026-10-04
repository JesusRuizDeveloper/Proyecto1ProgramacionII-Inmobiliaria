package co.edu.uptc.inmobiliaria.Model;

import co.edu.uptc.inmobiliaria.Enums.TipoContrato;
import co.edu.uptc.inmobiliaria.Enums.TipoDeInmueble;

public class Inmueble {
    private int id;
    private String ubicacion;
    private String direccion;
    private double precio;
    private TipoContrato tipoContrato;
    private TipoDeInmueble tipoDeInmueble;
    private boolean estaDisponible;
    
    public Inmueble() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public TipoDeInmueble getTipoDeInmueble() {
        return tipoDeInmueble;
    }

    public void setTipoDeInmueble(TipoDeInmueble tipoDeInmueble) {
        this.tipoDeInmueble = tipoDeInmueble;
    }

    public boolean isEstaDisponible() {
        return estaDisponible;
    }

    public void setEstaDisponible(boolean estaDisponible) {
        this.estaDisponible = estaDisponible;
    }

    public String toString() {
        return "Inmueble [id=" + id + ", ubicacion=" + ubicacion + ", direccion=" + direccion + ", precio=" + precio
                + ", tipoContrato=" + tipoContrato + ", tipoDeInmueble=" + tipoDeInmueble + ", estaDisponible="
                + estaDisponible + "]";
    }

    
    
}
