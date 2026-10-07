package co.edu.uptc.inmobiliaria.DTO;

import co.edu.uptc.inmobiliaria.Model.Cliente;

public class ResultadoCliente {
    Cliente cliente;
    boolean existeCliente;
    
    public ResultadoCliente() {
    }

    public ResultadoCliente(Cliente cliente, boolean existeCliente) {
        this.cliente = cliente;
        this.existeCliente = existeCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean getExisteCliente() {
        return existeCliente;
    }

    public void setExisteCliente(boolean existeCliente) {
        this.existeCliente = existeCliente;
    }

    
}
