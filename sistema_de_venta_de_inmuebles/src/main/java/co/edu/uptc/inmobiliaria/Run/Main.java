package co.edu.uptc.inmobiliaria.Run;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.inmobiliaria.DAO.DaoJson.DaoInmobiliariaJson;
import co.edu.uptc.inmobiliaria.Management.InmobialiariaManagement;
import co.edu.uptc.inmobiliaria.Model.Inmobiliaria;

public class Main {
    public static void main(String[] args) {
            InmobialiariaManagement inmo = new InmobialiariaManagement();
            inmo.crearInmobiliaria(1, "Primera", "3126549865", "Centro");
            //inmo.crearInmobiliaria(2, "Primera", "3126549865", "Centro");
            //inmo.crearInmobiliaria(3, "Primera", "3126549865", "Centro");
            //inmo.crearInmobiliaria(0, "Primera", "3126549865", "Centro");
            //inmo.eliminarInmobiliariaPorId(0);
            //inmo.eliminarInmobiliariaPorId(1);
            //inmo.eliminarInmobiliariaPorId(2);
            //inmo.eliminarInmobiliariaPorId(3);
            //inmo.modificarNombrePorId(0, "Segunda");
            //inmo.modificarDireccionPorId(0, "Norte");
            //inmo.modificarTelefonoPorId(0, "222222222222222");
            //inmo.listarInmobiliarias().forEach(System.out::println);
    }
}