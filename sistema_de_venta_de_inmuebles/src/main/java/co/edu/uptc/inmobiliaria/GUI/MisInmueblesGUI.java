package co.edu.uptc.inmobiliaria.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.inmobiliaria.Enums.TipoContrato;
import co.edu.uptc.inmobiliaria.Enums.TipoDeInmueble;
import co.edu.uptc.inmobiliaria.Management.InmobiliariaManagement;
import co.edu.uptc.inmobiliaria.Management.InmuebleManagement;
import co.edu.uptc.inmobiliaria.Model.Inmueble;

/**
 * Inmuebles de UN propietario.
 * Solo muestra y modifica los inmuebles de ese propietario.
 * Al agregar, el propietario se asigna automaticamente.
 */
public class MisInmueblesGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    // Solo existe una inmobiliaria y su id es 1
    private static final int ID_INMOBILIARIA = 1;

    private int idPropietario;
    private InmobiliariaManagement management;
    private InmuebleManagement inmuebleManagement = new InmuebleManagement();

    // Para mostrar el precio sin notacion cientifica (2500 en vez de 2500.0)
    private DecimalFormat formatoPrecio = new DecimalFormat("0.##", DecimalFormatSymbols.getInstance(Locale.US));

    // Componentes
    private JTextField txtId;
    private JTextField txtUbicacion;
    private JTextField txtDireccion;
    private JTextField txtPrecio;
    private JComboBox<TipoDeInmueble> cmbTipo;
    private JComboBox<TipoContrato> cmbContrato;
    private JCheckBox chkDisponible;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modelo;

    public MisInmueblesGUI(int idPropietario, InmobiliariaManagement management) {
        this.idPropietario = idPropietario;
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
        setTitle("Mis inmuebles - Propietario " + idPropietario);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    // ---------------------------------------------------------
    // 2. Componentes
    // ---------------------------------------------------------
    private void crearComponentes() {
        // Formulario (7 filas x 2 columnas). El propietario no se escribe: es el que inicio sesion
        JPanel panelForm = new JPanel(new GridLayout(7, 2, 10, 6));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        txtId = new JTextField();
        txtUbicacion = new JTextField();
        txtDireccion = new JTextField();
        txtPrecio = new JTextField();
        cmbTipo = new JComboBox<TipoDeInmueble>(TipoDeInmueble.values());
        cmbContrato = new JComboBox<TipoContrato>(TipoContrato.values());
        chkDisponible = new JCheckBox("Sí", true);

        panelForm.add(new JLabel("ID:"));
        panelForm.add(txtId);
        panelForm.add(new JLabel("Ubicación (ciudad o zona):"));
        panelForm.add(txtUbicacion);
        panelForm.add(new JLabel("Dirección:"));
        panelForm.add(txtDireccion);
        panelForm.add(new JLabel("Precio:"));
        panelForm.add(txtPrecio);
        panelForm.add(new JLabel("Tipo de inmueble:"));
        panelForm.add(cmbTipo);
        panelForm.add(new JLabel("Tipo de contrato:"));
        panelForm.add(cmbContrato);
        panelForm.add(new JLabel("Disponible:"));
        panelForm.add(chkDisponible);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        btnAgregar = new JButton("AGREGAR");
        btnActualizar = new JButton("ACTUALIZAR");
        btnEliminar = new JButton("ELIMINAR");
        btnLimpiar = new JButton("LIMPIAR");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelForm, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);
        add(panelNorte, BorderLayout.NORTH);

        // Tabla (las celdas no se pueden editar)
        String[] columnas = { "ID", "Ubicación", "Dirección", "Precio", "Tipo", "Contrato", "Disponible" };
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
        btnLimpiar.addActionListener(e -> limpiar());

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
        Inmueble inmueble = leerInmueble();
        if (inmueble == null) {
            return;
        }

        if (!guardarInmueble(inmueble)) {
            mostrarError("No se pudo agregar. Es posible que el ID ya exista.");
            return;
        }

        // El inmueble queda a nombre del propietario que inicio sesion
        if (!asignarAPropietario(inmueble.getId())) {
            borrarInmueble(inmueble.getId()); // se deshace para no dejar un inmueble sin dueño
            mostrarError("No se pudo asignar el inmueble a su cuenta. No se agregó.");
            return;
        }

        mostrarInfo("Inmueble agregado.");
        limpiar();
        cargarTabla();
    }

    private void actualizar() {
        Inmueble inmueble = leerInmueble();
        if (inmueble == null) {
            return;
        }
        if (!esMio(inmueble.getId())) {
            mostrarError("Ese inmueble no existe entre sus inmuebles.");
            return;
        }
        if (!modificarInmueble(inmueble)) {
            mostrarError("No se pudo actualizar el inmueble.");
            return;
        }

        // Se actualiza tambien la copia que guarda el propietario
        quitarDePropietario(inmueble.getId());
        asignarAPropietario(inmueble.getId());

        mostrarInfo("Inmueble actualizado.");
        limpiar();
        cargarTabla();
    }

    private void eliminar() {
        Integer id = leerId();
        if (id == null) {
            return;
        }
        if (!esMio(id)) {
            mostrarError("Ese inmueble no existe entre sus inmuebles.");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el inmueble " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        quitarDePropietario(id);
        if (borrarInmueble(id)) {
            mostrarInfo("Inmueble eliminado.");
            limpiar();
            cargarTabla();
        } else {
            mostrarError("No se pudo eliminar el inmueble.");
        }
    }

    // ---------------------------------------------------------
    // 5. Tabla y formulario
    // ---------------------------------------------------------
    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Inmueble i : obtenerMisInmuebles()) {
            modelo.addRow(new Object[] { i.getId(), i.getUbicacion(), i.getDireccion(),
                    formatoPrecio.format(i.getPrecio()), i.getTipoDeInmueble(), i.getTipoContrato(),
                    i.getEstaDisponible() ? "Sí" : "No" });
        }
    }

    private void seleccionarFila() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            return;
        }
        txtId.setText(String.valueOf(modelo.getValueAt(fila, 0)));
        txtUbicacion.setText(String.valueOf(modelo.getValueAt(fila, 1)));
        txtDireccion.setText(String.valueOf(modelo.getValueAt(fila, 2)));
        txtPrecio.setText(String.valueOf(modelo.getValueAt(fila, 3)));
        cmbTipo.setSelectedItem(modelo.getValueAt(fila, 4));
        cmbContrato.setSelectedItem(modelo.getValueAt(fila, 5));
        chkDisponible.setSelected("Sí".equals(modelo.getValueAt(fila, 6)));
    }

    private void limpiar() {
        txtId.setText("");
        txtUbicacion.setText("");
        txtDireccion.setText("");
        txtPrecio.setText("");
        cmbTipo.setSelectedIndex(0);
        cmbContrato.setSelectedIndex(0);
        chkDisponible.setSelected(true);
        tabla.clearSelection();
    }

    // ---------------------------------------------------------
    // 6. Lectura y validacion del formulario
    // ---------------------------------------------------------
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

    /** Arma un Inmueble con los datos del formulario. Devuelve null (y avisa) si algo es invalido. */
    private Inmueble leerInmueble() {
        Integer id = leerId();
        if (id == null) {
            return null;
        }
        String ubicacion = txtUbicacion.getText().trim();
        String direccion = txtDireccion.getText().trim();
        if (ubicacion.isEmpty() || direccion.isEmpty()) {
            mostrarError("Complete ubicación y dirección.");
            return null;
        }

        double precio;
        try {
            precio = Double.parseDouble(txtPrecio.getText().trim());
        } catch (NumberFormatException ex) {
            mostrarError("El precio debe ser un número (use punto decimal, sin comas).");
            return null;
        }
        if (precio <= 0) {
            mostrarError("El precio debe ser mayor que cero.");
            return null;
        }

        Inmueble inmueble = new Inmueble();
        inmueble.setId(id);
        inmueble.setUbicacion(ubicacion);
        inmueble.setDireccion(direccion);
        inmueble.setPrecio(precio);
        inmueble.setTipoDeInmueble((TipoDeInmueble) cmbTipo.getSelectedItem());
        inmueble.setTipoContrato((TipoContrato) cmbContrato.getSelectedItem());
        inmueble.setEstaDisponible(chkDisponible.isSelected());
        return inmueble;
    }

    /** Verdadero si el inmueble esta en la lista de este propietario. */
    private boolean esMio(int idInmueble) {
        for (Inmueble i : obtenerMisInmuebles()) {
            if (i.getId() == idInmueble) {
                return true;
            }
        }
        return false;
    }

    // ---------------------------------------------------------
    // 7. UNICOS metodos que llaman a Management
    // ---------------------------------------------------------
    private List<Inmueble> obtenerMisInmuebles() {
        try {
            List<Inmueble> lista = management.listarInmueblesPorPropietario(ID_INMOBILIARIA, idPropietario);
            if (lista != null) {
                return lista;
            }
        } catch (Exception ex) {
            mostrarError("Error al listar: " + ex.getMessage());
        }
        return new ArrayList<Inmueble>();
    }

    private boolean guardarInmueble(Inmueble i) {
        try {
            return management.agregarInmueble(ID_INMOBILIARIA, i.getId(), i.getUbicacion(), i.getDireccion(),
                    i.getPrecio(), i.getTipoContrato(), i.getTipoDeInmueble(), i.getEstaDisponible());
        } catch (Exception ex) {
            mostrarError("Error al agregar: " + ex.getMessage());
            return false;
        }
    }

    private boolean modificarInmueble(Inmueble i) {
        try {
            int id = i.getId();
            // Se aplican todos los cambios; si alguno falla, el resultado es false
            boolean ok = inmuebleManagement.cambiarUbicacionInmueble(ID_INMOBILIARIA, id, i.getUbicacion());
            ok = inmuebleManagement.cambiarDireccionInmueble(ID_INMOBILIARIA, id, i.getDireccion()) && ok;
            ok = inmuebleManagement.cambiarPrecioInmueble(ID_INMOBILIARIA, id, i.getPrecio()) && ok;
            ok = inmuebleManagement.cambiarTipoContratoInmueble(ID_INMOBILIARIA, id, i.getTipoContrato()) && ok;
            ok = inmuebleManagement.cambiarTipoDeInmueble(ID_INMOBILIARIA, id, i.getTipoDeInmueble()) && ok;
            ok = inmuebleManagement.cambiarDisponibilidadInmueble(ID_INMOBILIARIA, id, i.getEstaDisponible()) && ok;
            return ok;
        } catch (Exception ex) {
            mostrarError("Error al actualizar: " + ex.getMessage());
            return false;
        }
    }

    private boolean borrarInmueble(int id) {
        try {
            return management.eliminarInmueble(ID_INMOBILIARIA, id);
        } catch (Exception ex) {
            mostrarError("Error al eliminar: " + ex.getMessage());
            return false;
        }
    }

    private boolean asignarAPropietario(int idInmueble) {
        try {
            return management.agregarInmuebleAPropietario(ID_INMOBILIARIA, idPropietario, idInmueble);
        } catch (Exception ex) {
            mostrarError("Error al asignar el inmueble: " + ex.getMessage());
            return false;
        }
    }

    private boolean quitarDePropietario(int idInmueble) {
        try {
            return management.eliminarInmuebleDePropietario(ID_INMOBILIARIA, idPropietario, idInmueble);
        } catch (Exception ex) {
            mostrarError("Error al quitar el inmueble: " + ex.getMessage());
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
