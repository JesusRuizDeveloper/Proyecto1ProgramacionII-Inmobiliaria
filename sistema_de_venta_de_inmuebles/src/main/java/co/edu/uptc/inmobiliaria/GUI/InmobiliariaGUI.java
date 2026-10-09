package co.edu.uptc.inmobiliaria.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import co.edu.uptc.inmobiliaria.DTO.ResultadoInmobiliaria;
import co.edu.uptc.inmobiliaria.Management.InmobiliariaManagement;
import co.edu.uptc.inmobiliaria.Model.Inmobiliaria;

/**
 * Informacion de la inmobiliaria: se puede editar nombre, telefono y direccion.
 * Tambien muestra cuantos registros tiene cada lista.
 */
public class InmobiliariaGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private InmobiliariaManagement management;

    // Id de la inmobiliaria cargada (se llena en cargarDatos)
    private int idInmobiliaria;

    // Componentes
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtDireccion;
    private JLabel lblAdministradores;
    private JLabel lblPropietarios;
    private JLabel lblClientes;
    private JLabel lblInmuebles;
    private JButton btnGuardar;
    private JButton btnRecargar;

    public InmobiliariaGUI(InmobiliariaManagement management) {
        this.management = management;
        configurarVentana();
        crearComponentes();
        crearEventos();
        cargarDatos();
    }

    // ---------------------------------------------------------
    // 1. Ventana
    // ---------------------------------------------------------
    private void configurarVentana() {
        setTitle("Información de la inmobiliaria");
        setSize(480, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    // ---------------------------------------------------------
    // 2. Componentes
    // ---------------------------------------------------------
    private void crearComponentes() {
        // Formulario: datos editables y contadores
        JPanel panelForm = new JPanel(new GridLayout(7, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        txtNombre = new JTextField();
        txtTelefono = new JTextField();
        txtDireccion = new JTextField();
        lblAdministradores = new JLabel("0");
        lblPropietarios = new JLabel("0");
        lblClientes = new JLabel("0");
        lblInmuebles = new JLabel("0");

        panelForm.add(new JLabel("Nombre:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Teléfono:"));
        panelForm.add(txtTelefono);
        panelForm.add(new JLabel("Dirección:"));
        panelForm.add(txtDireccion);
        panelForm.add(new JLabel("Administradores:"));
        panelForm.add(lblAdministradores);
        panelForm.add(new JLabel("Propietarios:"));
        panelForm.add(lblPropietarios);
        panelForm.add(new JLabel("Clientes:"));
        panelForm.add(lblClientes);
        panelForm.add(new JLabel("Inmuebles:"));
        panelForm.add(lblInmuebles);
        add(panelForm, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        btnGuardar = new JButton("GUARDAR CAMBIOS");
        btnRecargar = new JButton("RECARGAR");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnRecargar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // ---------------------------------------------------------
    // 3. Eventos
    // ---------------------------------------------------------
    private void crearEventos() {
        btnGuardar.addActionListener(e -> guardar());
        btnRecargar.addActionListener(e -> cargarDatos());
    }

    // ---------------------------------------------------------
    // 4. Acciones
    // ---------------------------------------------------------
    /** Trae la inmobiliaria desde Management y llena el formulario. */
    private void cargarDatos() {
        Inmobiliaria inmo = consultarInmobiliaria();
        if (inmo == null) {
            mostrarError("No se encontró la inmobiliaria.");
            return;
        }
        idInmobiliaria = inmo.getId();
        txtNombre.setText(inmo.getNombre());
        txtTelefono.setText(inmo.getTelefono());
        txtDireccion.setText(inmo.getDireccion());
        lblAdministradores.setText(String.valueOf(contar(inmo.getAdministradores())));
        lblPropietarios.setText(String.valueOf(contar(inmo.getPropietarios())));
        lblClientes.setText(String.valueOf(contar(inmo.getClientes())));
        lblInmuebles.setText(String.valueOf(contar(inmo.getInmuebles())));
    }

    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if (nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            mostrarError("Complete nombre, teléfono y dirección.");
            return;
        }

        if (guardarCambios(nombre, telefono, direccion)) {
            mostrarInfo("Información actualizada.");
            cargarDatos();
        } else {
            mostrarError("No se pudo actualizar la información.");
        }
    }

    /** Cantidad de elementos de una lista (0 si es null). */
    private int contar(java.util.List<?> lista) {
        return lista == null ? 0 : lista.size();
    }

    // ---------------------------------------------------------
    // 5. UNICOS metodos que llaman a Management
    // ---------------------------------------------------------
    /** Devuelve la inmobiliaria o null si no existe (el DTO trae el dato y si existe). */
    private Inmobiliaria consultarInmobiliaria() {
        try {
            ResultadoInmobiliaria resultado = management.buscarInmobiliariaPorId();
            if (resultado != null && resultado.getExisteInmobiliaria()) {
                return resultado.getInmobiliaria();
            }
        } catch (Exception ex) {
            mostrarError("Error al cargar la inmobiliaria: " + ex.getMessage());
        }
        return null;
    }

    private boolean guardarCambios(String nombre, String telefono, String direccion) {
        try {
            boolean ok = management.modificarNombrePorId(idInmobiliaria, nombre);
            ok = management.modificarTelefonoPorId(idInmobiliaria, telefono) && ok;
            ok = management.modificarDireccionPorId(idInmobiliaria, direccion) && ok;
            return ok;
        } catch (Exception ex) {
            mostrarError("Error al guardar: " + ex.getMessage());
            return false;
        }
    }

    // ---------------------------------------------------------
    // Mensajes
    // ---------------------------------------------------------
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
