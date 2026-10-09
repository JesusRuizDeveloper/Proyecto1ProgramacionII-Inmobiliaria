package co.edu.uptc.inmobiliaria.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.inmobiliaria.DTO.ResultadoAdministrador;
import co.edu.uptc.inmobiliaria.Management.AdministradorManagement;
import co.edu.uptc.inmobiliaria.Management.InmobiliariaManagement;
import co.edu.uptc.inmobiliaria.Model.Administrador;

/**
 * Ventana CRUD de administradores.
 * Solo habla con Management (nunca con DAO ni Util).
 */
public class AdministradorGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    // Solo existe una inmobiliaria y su id es 1
    private static final int ID_INMOBILIARIA = 1;

    // Management: agregar, eliminar, buscar y listar
    private InmobiliariaManagement management;
    // Management: modificar datos (nombre, telefono, clave)
    private AdministradorManagement administradorManagement = new AdministradorManagement();

    // Componentes
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JPasswordField txtClave;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnListar;
    private JButton btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modelo;

    public AdministradorGUI(InmobiliariaManagement management) {
        this.management = management;
        configurarVentana();
        crearComponentes();
        crearEventos();
        cargarTabla();
    }

    // ventana
    private void configurarVentana() {
        setTitle("Administradores");
        setSize(700, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // cierra solo esta ventana
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    // componentes
    private void crearComponentes() {
        // Formulario (4 filas x 2 columnas)
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 8));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtTelefono = new JTextField();
        txtClave = new JPasswordField();

        panelForm.add(new JLabel("ID:"));
        panelForm.add(txtId);
        panelForm.add(new JLabel("Nombre:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Teléfono:"));
        panelForm.add(txtTelefono);
        panelForm.add(new JLabel("Clave (vacía = no cambiar):"));
        panelForm.add(txtClave);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        btnAgregar = new JButton("AGREGAR");
        btnActualizar = new JButton("ACTUALIZAR");
        btnEliminar = new JButton("ELIMINAR");
        btnBuscar = new JButton("BUSCAR");
        btnListar = new JButton("LISTAR");
        btnLimpiar = new JButton("LIMPIAR");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnListar);
        panelBotones.add(btnLimpiar);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelForm, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);
        add(panelNorte, BorderLayout.NORTH);

        // Tabla (las celdas no se pueden editar)
        String[] columnas = { "ID", "Nombre", "Teléfono", "Rol" };
        modelo = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    // eventos
    private void crearEventos() {
        btnAgregar.addActionListener(e -> agregar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnBuscar.addActionListener(e -> buscar());
        btnListar.addActionListener(e -> cargarTabla());
        btnLimpiar.addActionListener(e -> limpiar());

        // Al seleccionar una fila, se copian sus datos al formulario
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });
    }

    // acciones de los botones
    private void agregar() {
        Integer id = leerId();
        if (id == null) {
            return;
        }
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String clave = new String(txtClave.getPassword()).trim();

        if (nombre.isEmpty() || telefono.isEmpty() || clave.isEmpty()) {
            mostrarError("Complete nombre, teléfono y clave.");
            return;
        }

        if (guardarAdministrador(id, nombre, telefono, clave)) {
            mostrarInfo("Administrador agregado.");
            limpiar();
            cargarTabla();
        } else {
            mostrarError("No se pudo agregar. Es posible que el ID ya exista.");
        }
    }

    private void actualizar() {
        Integer id = leerId();
        if (id == null) {
            return;
        }
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String clave = new String(txtClave.getPassword()).trim();

        if (nombre.isEmpty() || telefono.isEmpty()) {
            mostrarError("Complete nombre y teléfono.");
            return;
        }

        if (modificarAdministrador(id, nombre, telefono, clave)) {
            mostrarInfo("Administrador actualizado.");
            limpiar();
            cargarTabla();
        } else {
            mostrarError("No se pudo actualizar. Verifique que el ID exista.");
        }
    }

    private void eliminar() {
        Integer id = leerId();
        if (id == null) {
            return;
        }

        // Debe quedar al menos un administrador para poder ingresar al sistema
        if (obtenerAdministradores().size() <= 1) {
            mostrarError("Debe existir al menos un administrador.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el administrador " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        if (borrarAdministrador(id)) {
            mostrarInfo("Administrador eliminado.");
            limpiar();
            cargarTabla();
        } else {
            mostrarError("No se pudo eliminar. Verifique que el ID exista.");
        }
    }

    private void buscar() {
        Integer id = leerId();
        if (id == null) {
            return;
        }

        Administrador admin = consultarAdministrador(id);
        if (admin == null) {
            mostrarError("No existe un administrador con el ID " + id + ".");
            return;
        }

        // Se muestra solo el encontrado
        modelo.setRowCount(0);
        modelo.addRow(new Object[] { admin.getId(), admin.getNombre(), admin.getTelefono(), admin.getRol() });
        txtNombre.setText(admin.getNombre());
        txtTelefono.setText(admin.getTelefono());
        txtClave.setText("");
    }

    // tabla y formulario
    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Administrador a : obtenerAdministradores()) {
            modelo.addRow(new Object[] { a.getId(), a.getNombre(), a.getTelefono(), a.getRol() });
        }
    }

    private void seleccionarFila() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtId.setText(String.valueOf(modelo.getValueAt(fila, 0)));
            txtNombre.setText(String.valueOf(modelo.getValueAt(fila, 1)));
            txtTelefono.setText(String.valueOf(modelo.getValueAt(fila, 2)));
            txtClave.setText("");
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtClave.setText("");
        tabla.clearSelection();
    }

    /** Lee el ID del formulario. Devuelve null (y avisa) si esta vacio o no es numerico. */
    private Integer leerId() {
        String texto = txtId.getText().trim();
        if (texto.isEmpty()) {
            mostrarError("Ingrese el ID.");
            return null;
        }
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException ex) {
            mostrarError("El ID debe ser un número entero.");
            return null;
        }
    }

    // metodos que llaman a management
    private boolean guardarAdministrador(int id, String nombre, String telefono, String clave) {
        try {
            return management.agregarAdministrador(ID_INMOBILIARIA, id, nombre, telefono, clave);
        } catch (Exception ex) {
            mostrarError("Error al agregar: " + ex.getMessage());
            return false;
        }
    }

    private boolean modificarAdministrador(int id, String nombre, String telefono, String clave) {
        try {
            // Si el primer cambio falla, el administrador no existe
            if (!administradorManagement.cambiarNombreAdministrador(ID_INMOBILIARIA, id, nombre)) {
                return false;
            }
            boolean ok = administradorManagement.cambiarTelefonoAdministrador(ID_INMOBILIARIA, id, telefono);
            // La clave solo se cambia si el usuario escribio una nueva
            if (!clave.isEmpty()) {
                ok = ok && administradorManagement.cambiarClaveAdministrador(ID_INMOBILIARIA, id, clave);
            }
            return ok;
        } catch (Exception ex) {
            mostrarError("Error al actualizar: " + ex.getMessage());
            return false;
        }
    }

    private boolean borrarAdministrador(int id) {
        try {
            return management.eliminarAdministrador(ID_INMOBILIARIA, id);
        } catch (Exception ex) {
            mostrarError("Error al eliminar: " + ex.getMessage());
            return false;
        }
    }

    /** Devuelve el administrador o null si no existe (el DTO trae el dato y si existe). */
    private Administrador consultarAdministrador(int id) {
        try {
            ResultadoAdministrador resultado = management.buscarAdministrador(ID_INMOBILIARIA, id);
            if (resultado != null && resultado.getExisteAdministrador()) {
                return resultado.getAdministrador();
            }
        } catch (Exception ex) {
            mostrarError("Error al buscar: " + ex.getMessage());
        }
        return null;
    }

    private List<Administrador> obtenerAdministradores() {
        try {
            List<Administrador> lista = management.listarAdministradores(ID_INMOBILIARIA);
            if (lista != null) {
                return lista;
            }
        } catch (Exception ex) {
            mostrarError("Error al listar: " + ex.getMessage());
        }
        return new ArrayList<Administrador>();
    }

    // mensajes
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
