package co.edu.uptc.inmobiliaria.Model;

import co.edu.uptc.inmobiliaria.Enums.TipoContrato;
import co.edu.uptc.inmobiliaria.Enums.TipoDeInmueble;
import co.edu.uptc.inmobiliaria.Interfaces.General;

public class Inmueble implements General{
    private int id;
    private String ubicacion;
    private String direccion;
    private double precio;
    private TipoContrato tipoContrato;
    private TipoDeInmueble tipoDeInmueble;
    private boolean estaDisponible;
    
    public Inmueble(int id, String ubicacion, String direccion, double precio, TipoContrato tipoContrato,
            TipoDeInmueble tipoDeInmueble, boolean estaDisponible) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.direccion = direccion;
        this.precio = precio;
        this.tipoContrato = tipoContrato;
        this.tipoDeInmueble = tipoDeInmueble;
        this.estaDisponible = estaDisponible;
    }

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

    public boolean getEstaDisponible() {
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
