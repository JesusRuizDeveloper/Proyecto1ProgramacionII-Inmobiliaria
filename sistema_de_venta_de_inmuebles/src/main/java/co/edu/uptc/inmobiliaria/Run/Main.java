package co.edu.uptc.inmobiliaria.Run;

import co.edu.uptc.inmobiliaria.DTO.ResultadoInmobiliaria;
import co.edu.uptc.inmobiliaria.Management.ClienteManagement;
import co.edu.uptc.inmobiliaria.Management.InmobiliariaManagement;
import co.edu.uptc.inmobiliaria.Util.Validaciones;

public class Main {
    public static void main(String[] args) {
        Validaciones validaciones = new Validaciones();
        ClienteManagement clienteManagement = new ClienteManagement();
        InmobiliariaManagement inmo = new InmobiliariaManagement();
        ResultadoInmobiliaria inmoDTO = inmo.buscarInmobiliariaPorId(1);
        
        inmo.crearInmobiliaria();
        System.out.println(inmoDTO.getInmobiliaria().toString());
    }
}