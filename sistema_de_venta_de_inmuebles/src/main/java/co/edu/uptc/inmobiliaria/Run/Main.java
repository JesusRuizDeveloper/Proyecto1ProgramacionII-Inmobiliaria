package co.edu.uptc.inmobiliaria.Run;

import javax.swing.SwingUtilities;

import co.edu.uptc.inmobiliaria.GUI.LoginGUI;
import co.edu.uptc.inmobiliaria.Management.InmobiliariaManagement;
import co.edu.uptc.inmobiliaria.Management.LoginManagement;

public class Main {
    public static void main(String[] args) {
        //Para inicializar el GUI
        InmobiliariaManagement inmo = new InmobiliariaManagement();
        LoginManagement loginManagement = new LoginManagement();
        
        // Cargar o inicializar datos si es necesario
        inmo.crearInmobiliaria();

        // Lanzar la interfaz gráfica en el Event Dispatch Thread (EDT) de Swing
        SwingUtilities.invokeLater(() -> {
            LoginGUI loginGUI = new LoginGUI(loginManagement, inmo);
            loginGUI.setVisible(true);
        });
    }
}