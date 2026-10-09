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

import co.edu.uptc.inmobiliaria.DTO.ResultadoPropietario;
import co.edu.uptc.inmobiliaria.Management.PropietarioManagement;
import co.edu.uptc.inmobiliaria.Management.InmobiliariaManagement;
import co.edu.uptc.inmobiliaria.Model.Propietario;

/**
 * Ventana CRUD de propietarios.
 * Desde aqui tambien se pueden ver los inmuebles de un propietario.
 * Solo habla con Management (nunca con DAO ni Util).
 */
public class PropietarioGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    // Solo existe una inmobiliaria y su id es 1
    private static final int ID_INMOBILIARIA = 1;

    // Management: agregar, eliminar, buscar y listar
    private InmobiliariaManagement management;
    // Management: modificar datos (nombre, telefono, clave)
    private PropietarioManagement propietarioManagement = new PropietarioManagement();

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
    private JButton btnVerInmuebles;
    private JTable tabla;
    private DefaultTableModel modelo;

    public PropietarioGUI(InmobiliariaManagement management) {
        this.management = management;
        configurarVentana();
        crearComponentes();
        crearEventos();
        cargarTabla();
    }

    // ---------------------------------------------------------
    // 1. Ventana
    // ---------------------------------------------------------
    private void configurarVentana() {
        setTitle("Propietarios");
        setSize(860, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // cierra solo esta ventana
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    // ---------------------------------------------------------
    // 2. Componentes
    // ---------------------------------------------------------
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
        btnVerInmuebles = new JButton("VER MIS INMUEBLES");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnListar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVerInmuebles);

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

    // ---------------------------------------------------------
    // 3. Eventos
    // ---------------------------------------------------------
    private void crearEventos() {
        btnAgregar.addActionListener(e -> agregar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnBuscar.addActionListener(e -> buscar());
        btnListar.addActionListener(e -> cargarTabla());
        btnLimpiar.addActionListener(e -> limpiar());
        btnVerInmuebles.addActionListener(e -> verInmuebles());

        // Al seleccionar una fila, se copian sus datos al formulario
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });
    }

    // ---------------------------------------------------------
    // 4. Acciones de los botones
    // ---------------------------------------------------------
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

        if (guardarPropietario(id, nombre, telefono, clave)) {
            mostrarInfo("Propietario agregado.");
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

        if (modificarPropietario(id, nombre, telefono, clave)) {
            mostrarInfo("Propietario actualizado.");
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

        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el propietario " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        if (borrarPropietario(id)) {
            mostrarInfo("Propietario eliminado.");
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

        Propietario prop = consultarPropietario(id);
        if (prop == null) {
            mostrarError("No existe un propietario con el ID " + id + ".");
            return;
        }

        // Se muestra solo el encontrado
        modelo.setRowCount(0);
        modelo.addRow(new Object[] { prop.getId(), prop.getNombre(), prop.getTelefono(), prop.getRol() });
        txtNombre.setText(prop.getNombre());
        txtTelefono.setText(prop.getTelefono());
        txtClave.setText("");
    }

    /** Abre la ventana con los inmuebles del propietario escrito en el ID. */
    private void verInmuebles() {
        Integer id = leerId();
        if (id == null) {
            return;
        }
        if (consultarPropietario(id) == null) {
            mostrarError("No existe un propietario con el ID " + id + ".");
            return;
        }
        new MisInmueblesGUI(id, management).setVisible(true);
    }

    // ---------------------------------------------------------
    // 5. Tabla y formulario
    // ---------------------------------------------------------
    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Propietario a : obtenerPropietarios()) {
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

    // ---------------------------------------------------------
    // 6. UNICOS metodos que llaman a Management
    // ---------------------------------------------------------
    private boolean guardarPropietario(int id, String nombre, String telefono, String clave) {
        try {
            return management.agregarPropietario(ID_INMOBILIARIA, id, nombre, telefono, clave);
        } catch (Exception ex) {
            mostrarError("Error al agregar: " + ex.getMessage());
            return false;
        }
    }

    private boolean modificarPropietario(int id, String nombre, String telefono, String clave) {
        try {
            // Si el primer cambio falla, el propietario no existe
            if (!propietarioManagement.cambiarNombrePropietario(ID_INMOBILIARIA, id, nombre)) {
                return false;
            }
            boolean ok = propietarioManagement.cambiarTelefonoPropietario(ID_INMOBILIARIA, id, telefono);
            // La clave solo se cambia si el usuario escribio una nueva
            if (!clave.isEmpty()) {
                ok = ok && propietarioManagement.cambiarClavePropietario(ID_INMOBILIARIA, id, clave);
            }
            return ok;
        } catch (Exception ex) {
            mostrarError("Error al actualizar: " + ex.getMessage());
            return false;
        }
    }

    private boolean borrarPropietario(int id) {
        try {
            return management.eliminarPropietario(ID_INMOBILIARIA, id);
        } catch (Exception ex) {
            mostrarError("Error al eliminar: " + ex.getMessage());
            return false;
        }
    }

    /** Devuelve el propietario o null si no existe (el DTO trae el dato y si existe). */
    private Propietario consultarPropietario(int id) {
        try {
            ResultadoPropietario resultado = management.buscarPropietario(ID_INMOBILIARIA, id);
            if (resultado != null && resultado.getExistePropietario()) {
                return resultado.getPropietario();
            }
        } catch (Exception ex) {
            mostrarError("Error al buscar: " + ex.getMessage());
        }
        return null;
    }

    private List<Propietario> obtenerPropietarios() {
        try {
            List<Propietario> lista = management.listarPropietarios(ID_INMOBILIARIA);
            if (lista != null) {
                return lista;
            }
        } catch (Exception ex) {
            mostrarError("Error al listar: " + ex.getMessage());
        }
        return new ArrayList<Propietario>();
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
