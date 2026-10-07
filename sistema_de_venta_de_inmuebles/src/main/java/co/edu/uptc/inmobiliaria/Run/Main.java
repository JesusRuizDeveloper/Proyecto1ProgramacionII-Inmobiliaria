package co.edu.uptc.inmobiliaria.Run;

import co.edu.uptc.inmobiliaria.Management.InmobiliariaManagement;
import co.edu.uptc.inmobiliaria.Util.Validaciones;

public class Main {
    public static void main(String[] args) {
        Validaciones validaciones = new Validaciones();
            InmobiliariaManagement inmo = new InmobiliariaManagement();
            //inmo.crearInmobiliaria(1, "Primera", "3126549865", "Centro");
            //inmo.crearInmobiliaria(2, "Primera", "3126549865", "Centro");
            //inmo.crearInmobiliaria(3, "Primera", "3126549865", "Centro");
            //inmo.crearInmobiliaria(0, "Primera", "3126549865", "Centro");
            //inmo.eliminarInmobiliariaPorId(0);
            //inmo.eliminarInmobiliariaPorId(1);
            //inmo.eliminarInmobiliariaPorId(2);
            //inmo.eliminarInmobiliariaPorId(3);
            //inmo.modificarNombrePorId(3, "Segunda");
            //inmo.modificarDireccionPorId(3, "Norte");
            //inmo.modificarTelefonoPorId(3, "222222222222222");
            //inmo.listarInmobiliarias().forEach(System.out::println);
            inmo.agregarAdministrador(1, 0, "Jesus", "3226548765", "1234");
            inmo.agregarAdministrador(1, 0, "Jesus", "3226548765", "1234");
            inmo.agregarAdministrador(1, 2, "Manuel", "111111111", "5678");
            inmo.agregarAdministrador(1, 3, "Carlos", "222222222", "9012");
    }
}