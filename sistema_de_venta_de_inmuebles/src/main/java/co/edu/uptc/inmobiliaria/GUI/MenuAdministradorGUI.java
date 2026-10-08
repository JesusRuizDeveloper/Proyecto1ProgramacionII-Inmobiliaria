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
 * Menu principal del administrador.
 * No llama a Management: solo abre las demas ventanas y les pasa las instancias.
 */
public class MenuAdministradorGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    // Instancias unicas que llegan por constructor
    private LoginManagement loginManagement;
    private InmobiliariaManagement management;

    // Componentes
    private JButton btnAdministradores;
    private JButton btnPropietarios;
    private JButton btnClientes;
    private JButton btnInmuebles;
    private JButton btnInmobiliaria;
    private JButton btnCerrarSesion;

    public MenuAdministradorGUI(LoginManagement loginManagement, InmobiliariaManagement management) {
        this.loginManagement = loginManagement;
        this.management = management;
        configurarVentana();
        crearComponentes();
        crearEventos();
    }

    private void configurarVentana() {
        setTitle("Menú Administrador");
        setSize(380, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        // Norte: titulo
        JLabel lblTitulo = new JLabel("MENÚ ADMINISTRADOR", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // Centro: 6 botones en una columna
        JPanel panelBotones = new JPanel(new GridLayout(6, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(15, 60, 20, 60));

        btnAdministradores = new JButton("Administradores");
        btnPropietarios = new JButton("Propietarios");
        btnClientes = new JButton("Clientes");
        btnInmuebles = new JButton("Inmuebles");
        btnInmobiliaria = new JButton("Información inmobiliaria");
        btnCerrarSesion = new JButton("Cerrar sesión");

        panelBotones.add(btnAdministradores);
        panelBotones.add(btnPropietarios);
        panelBotones.add(btnClientes);
        panelBotones.add(btnInmuebles);
        panelBotones.add(btnInmobiliaria);
        panelBotones.add(btnCerrarSesion);
        add(panelBotones, BorderLayout.CENTER);
    }

    private void crearEventos() {
        btnAdministradores.addActionListener(e -> abrirAdministradores());
        btnPropietarios.addActionListener(e -> abrirPropietarios());
        btnClientes.addActionListener(e -> abrirClientes());
        btnInmuebles.addActionListener(e -> abrirInmuebles());
        btnInmobiliaria.addActionListener(e -> abrirInmobiliaria());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    private void abrirAdministradores() {
        // new AdministradorGUI(management).setVisible(true);
        avisoPendiente("AdministradorGUI");
    }

    private void abrirPropietarios() {
        // new PropietarioGUI(management).setVisible(true);
        avisoPendiente("PropietarioGUI");
    }

    private void abrirClientes() {
        // new ClienteGUI(management).setVisible(true);
        avisoPendiente("ClienteGUI");
    }

    private void abrirInmuebles() {
        // new InmuebleGUI(management).setVisible(true);
        avisoPendiente("InmuebleGUI");
    }

    private void abrirInmobiliaria() {
        // new InmobiliariaGUI(management).setVisible(true);
        avisoPendiente("InmobiliariaGUI");
    }

    // cierra este menu y vuelve al login
    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Desea cerrar la sesión?", "Cerrar sesión", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            new LoginGUI(loginManagement, management).setVisible(true);
            dispose();
        }
    }

    // Temporal: se borra cuando existan todas las ventanas
    private void avisoPendiente(String nombreVentana) {
        JOptionPane.showMessageDialog(this, nombreVentana + " aún no está creada.");
    }
}