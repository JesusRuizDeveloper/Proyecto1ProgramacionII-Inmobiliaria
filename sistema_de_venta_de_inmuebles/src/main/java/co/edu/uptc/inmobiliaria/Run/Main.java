package co.edu.uptc.inmobiliaria.Run;

import javax.swing.SwingUtilities;

import co.edu.uptc.inmobiliaria.Enums.TipoContrato;
import co.edu.uptc.inmobiliaria.Enums.TipoDeInmueble;
import co.edu.uptc.inmobiliaria.GUI.LoginGUI;
import co.edu.uptc.inmobiliaria.Management.AdministradorManagement;
import co.edu.uptc.inmobiliaria.Management.ClienteManagement;
import co.edu.uptc.inmobiliaria.Management.InmobiliariaManagement;
import co.edu.uptc.inmobiliaria.Management.LoginManagement;

public class Main {
    public static void main(String[] args) {
        //Para inicializar el GUI
        InmobiliariaManagement inmo = new InmobiliariaManagement();
        LoginManagement loginManagement = new LoginManagement();
        AdministradorManagement adminMana = new AdministradorManagement();
        ClienteManagement clienteMana = new ClienteManagement();
        
        // Cargar o inicializar datos si es necesario
        inmo.crearInmobiliaria();
        inmo.agregarAdministrador(1, 1, "Juan", "33333333", "1234");
        inmo.agregarCliente(1, 1, "Clien", "3224569875");
        inmo.agregarPropietario(1, 1, "Pro", "3243215", "1234");
        inmo.agregarInmueble(1, 1, "Norteasd", "Calle", 20000, TipoContrato.ARRIENDO, TipoDeInmueble.APARTAESTUDIO, true);
        adminMana.cambiarNombreAdministrador(1, 1, "Pedrangas");
        adminMana.cambiarTelefonoAdministrador(1, 1, "99999999");
        clienteMana.cambiarNombreCliente(1, 1, "Carlangas");
        


        // Lanzar la interfaz gráfica en el Event Dispatch Thread (EDT) de Swing
        SwingUtilities.invokeLater(() -> {
            LoginGUI loginGUI = new LoginGUI(loginManagement, inmo);
            loginGUI.setVisible(true);
        });
    }
}
