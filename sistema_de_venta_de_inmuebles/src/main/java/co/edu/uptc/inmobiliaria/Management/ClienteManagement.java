package co.edu.uptc.inmobiliaria.Management;

import java.util.List;
import co.edu.uptc.inmobiliaria.DAO.DaoJson.DaoInmobiliariaJson;
import co.edu.uptc.inmobiliaria.Model.Cliente;
import co.edu.uptc.inmobiliaria.Model.Inmobiliaria;

public class ClienteManagement {
    DaoInmobiliariaJson daoJson = new DaoInmobiliariaJson();
    List<Inmobiliaria> inmobiliarias = daoJson.leerArchivo();

    public boolean cambiarNombreCliente(int idInmo, int idCliente, String nombre) {
        inmobiliarias = daoJson.leerArchivo();
        Inmobiliaria inmobiliaria = inmobiliarias.stream().filter(inmo -> inmo.getId() == idInmo).findFirst().orElse(null);
        if (inmobiliaria == null) return false;
        Cliente cliente = inmobiliaria.getClientes().stream().filter(cli -> cli.getId() == idCliente).findFirst().orElse(null);
        if (cliente == null) return false;
        cliente.setNombre(nombre);
        daoJson.escribirArchivo(inmobiliarias);
        return true;
    }

    public boolean cambiarTelefonoCliente(int idInmo, int idCliente, String telefono) {
        inmobiliarias = daoJson.leerArchivo();
        Inmobiliaria inmobiliaria = inmobiliarias.stream().filter(inmo -> inmo.getId() == idInmo).findFirst().orElse(null);
        if (inmobiliaria == null) return false;
        Cliente cliente = inmobiliaria.getClientes().stream().filter(cli -> cli.getId() == idCliente).findFirst().orElse(null);
        if (cliente == null) return false;
        cliente.setTelefono(telefono);
        daoJson.escribirArchivo(inmobiliarias);
        return true;
    }
}