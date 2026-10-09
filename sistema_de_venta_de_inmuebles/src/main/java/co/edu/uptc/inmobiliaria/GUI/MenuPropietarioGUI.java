package co.edu.uptc.inmobiliaria.GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import co.edu.uptc.inmobiliaria.Management.InmobiliariaManagement;
import co.edu.uptc.inmobiliaria.Management.LoginManagement;

/**
 * Menu del propietario. Recibe el id del propietario que inicio sesion
 * y se lo pasa a MisInmueblesGUI para que solo vea sus inmuebles.
 */
public class MenuPropietarioGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private int idPropietario;
    private LoginManagement loginManagement;
    private InmobiliariaManagement management;

    // Componentes
    private JButton btnMisInmuebles;
    private JButton btnCerrarSesion;

    public MenuPropietarioGUI(int idPropietario, LoginManagement loginManagement, InmobiliariaManagement management) {
        this.idPropietario = idPropietario;
        this.loginManagement = loginManagement;
        this.management = management;
        configurarVentana();
        crearComponentes();
        crearEventos();
    }

    // ---------------------------------------------------------
    // 1. Ventana
    // ---------------------------------------------------------
    private void configurarVentana() {
        setTitle("Menú Propietario");
        setSize(380, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    // ---------------------------------------------------------
    // 2. Componentes
    // ---------------------------------------------------------
    private void crearComponentes() {
        JLabel lblTitulo = new JLabel("MENÚ PROPIETARIO (ID " + idPropietario + ")", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 60, 25, 60));
        btnMisInmuebles = new JButton("Mis inmuebles");
        btnCerrarSesion = new JButton("Cerrar sesión");
        panelBotones.add(btnMisInmuebles);
        panelBotones.add(btnCerrarSesion);
        add(panelBotones, BorderLayout.CENTER);
    }

    // ---------------------------------------------------------
    // 3. Eventos
    // ---------------------------------------------------------
    private void crearEventos() {
        btnMisInmuebles.addActionListener(e -> abrirMisInmuebles());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    private void abrirMisInmuebles() {
        new MisInmueblesGUI(idPropietario, management).setVisible(true);
    }

    // Cierra este menu y vuelve al login
    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Desea cerrar la sesión?", "Cerrar sesión", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            new LoginGUI(loginManagement, management).setVisible(true);
            dispose();
        }
    }
}
