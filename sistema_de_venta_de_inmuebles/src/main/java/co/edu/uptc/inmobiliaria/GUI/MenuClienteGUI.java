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
 * Menu del cliente: solo puede consultar inmuebles.
 */
public class MenuClienteGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private LoginManagement loginManagement;
    private InmobiliariaManagement management;

    // Componentes
    private JButton btnBuscarInmuebles;
    private JButton btnCerrarSesion;

    public MenuClienteGUI(LoginManagement loginManagement, InmobiliariaManagement management) {
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
        setTitle("Menú Cliente");
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
        JLabel lblTitulo = new JLabel("MENÚ CLIENTE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 60, 25, 60));
        btnBuscarInmuebles = new JButton("Buscar inmuebles");
        btnCerrarSesion = new JButton("Cerrar sesión");
        panelBotones.add(btnBuscarInmuebles);
        panelBotones.add(btnCerrarSesion);
        add(panelBotones, BorderLayout.CENTER);
    }

    // ---------------------------------------------------------
    // 3. Eventos
    // ---------------------------------------------------------
    private void crearEventos() {
        btnBuscarInmuebles.addActionListener(e -> abrirBuscarInmuebles());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    private void abrirBuscarInmuebles() {
        new BuscarInmuebleGUI(management).setVisible(true);
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
