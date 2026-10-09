package co.edu.uptc.inmobiliaria.Run;

import co.edu.uptc.inmobiliaria.Management.InmobiliariaManagement;

public class PruebaDaoMysql {
    public static void main(String[] args) {

        InmobiliariaManagement management = new InmobiliariaManagement();

        System.out.println("Nombre actualizado: "
                + management.modificarNombrePorId(9999, "Prueba actualizada"));
        System.out.println("Teléfono actualizado: "
                + management.modificarTelefonoPorId(9999, "3007654321"));
        System.out.println("Dirección actualizada: "
                + management.modificarDireccionPorId(9999, "Dirección temporal"));

        var resultado = management.buscarInmobiliariaPorId(9999);
        System.out.println("Datos después de actualizar: " + resultado.getInmobiliaria());

        System.out.println("Registro de prueba eliminado: "
                + management.eliminarInmobiliariaPorId(9999));
        System.out.println("¿Sigue existiendo?: "
                + management.buscarInmobiliariaPorId(9999).getExisteInmobiliaria());
    }
}