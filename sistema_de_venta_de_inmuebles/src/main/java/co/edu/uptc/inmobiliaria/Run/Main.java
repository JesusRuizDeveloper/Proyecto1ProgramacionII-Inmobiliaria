package co.edu.uptc.inmobiliaria.Run;

import co.edu.uptc.inmobiliaria.DTO.ResultadoInmobiliaria;
import co.edu.uptc.inmobiliaria.Enums.TipoContrato;
import co.edu.uptc.inmobiliaria.Enums.TipoDeInmueble;
import co.edu.uptc.inmobiliaria.Management.InmobiliariaManagement;

public class Main {
    public static void main(String[] args) {
        InmobiliariaManagement inmo = new InmobiliariaManagement();

        // 1. Crear inmobiliaria inicial
        inmo.crearInmobiliaria(1, "Primera", "3126549865", "Centro");

        // 2. Registrar propietario e inmueble
        inmo.agregarPropietario(1, 0, "Juneto", "3226325984", "clave");
        inmo.agregarInmueble(1, 0, "Norte", "calle 10", 10000000, TipoContrato.ARRIENDO, TipoDeInmueble.APARTAESTUDIO, true);
        inmo.agregarInmuebleAPropietario(1, 0, 0);

        // 3. Consultar la inmobiliaria con DTO y validar respuesta
        ResultadoInmobiliaria inmoDTO = inmo.buscarInmobiliariaPorId(1);

        if (inmoDTO != null && inmoDTO.getInmobiliaria() != null) {
            System.out.println(inmoDTO.getInmobiliaria().toString());
        } else {
            System.out.println("No se encontró la inmobiliaria con ID 1.");
        }
    }
}