package co.edu.uptc.inmobiliaria.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import co.edu.uptc.inmobiliaria.DTO.ResultadoInmueble;
import co.edu.uptc.inmobiliaria.DTO.ResultadoPropietario;
import co.edu.uptc.inmobiliaria.Enums.TipoContrato;
import co.edu.uptc.inmobiliaria.Enums.TipoDeInmueble;
import co.edu.uptc.inmobiliaria.Management.InmobiliariaManagement;
import co.edu.uptc.inmobiliaria.Management.InmuebleManagement;
import co.edu.uptc.inmobiliaria.Model.Inmueble;
import co.edu.uptc.inmobiliaria.Model.Propietario;

/**
 * Ventana CRUD de inmuebles (para el administrador).
 * El inmueble no guarda el id del propietario: la relacion vive en la lista
 * de inmuebles del propietario, por eso se asigna con agregarInmuebleAPropietario.
 */
public class InmuebleGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    // Solo existe una inmobiliaria y su id es 1
    private static final int ID_INMOBILIARIA = 1;

    // Management: agregar, eliminar, buscar y listar
    private InmobiliariaManagement management;
    // Management: modificar datos del inmueble
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
    private JTextField txtIdPropietario;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnListar;
    private JButton btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modelo;

    public InmuebleGUI(InmobiliariaManagement management) {
        this.management = management;
        configurarVentana();
        crearComponentes();
        crearEventos();
        cargarTabla();
    }

    // ventana
    private void configurarVentana() {
        setTitle("Inmuebles");
        setSize(950, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    // componentes
    private void crearComponentes() {
        // Formulario (8 filas x 2 columnas)
        JPanel panelForm = new JPanel(new GridLayout(8, 2, 10, 6));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        txtId = new JTextField();
        txtUbicacion = new JTextField();
        txtDireccion = new JTextField();
        txtPrecio = new JTextField();
        cmbTipo = new JComboBox<TipoDeInmueble>(TipoDeInmueble.values());
        cmbContrato = new JComboBox<TipoContrato>(TipoContrato.values());
        chkDisponible = new JCheckBox("Sí", true);
        txtIdPropietario = new JTextField();

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
        panelForm.add(new JLabel("ID Propietario (opcional):"));
        panelForm.add(txtIdPropietario);

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
        String[] columnas = { "ID", "Ubicación", "Dirección", "Precio", "Tipo", "Contrato", "Disponible", "Propietario" };
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

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });
    }

    // acciones de los botones
    private void agregar() {
        Inmueble inmueble = leerInmueble();
        if (inmueble == null) {
            return;
        }
        Integer idPropietario = leerIdPropietarioOpcional();

        if (!guardarInmueble(inmueble)) {
            mostrarError("No se pudo agregar. Es posible que el ID ya exista.");
            return;
        }

        // Si se escribio un propietario, se le asigna el inmueble
        if (idPropietario != null && !asignarAPropietario(idPropietario, inmueble.getId())) {
            mostrarError("El inmueble se agregó, pero no se pudo asignar al propietario.");
        } else {
            mostrarInfo("Inmueble agregado.");
        }
        limpiar();
        cargarTabla();
    }

    private void actualizar() {
        Inmueble inmueble = leerInmueble();
        if (inmueble == null) {
            return;
        }
        if (consultarInmueble(inmueble.getId()) == null) {
            mostrarError("No existe un inmueble con el ID " + inmueble.getId() + ".");
            return;
        }
        if (!modificarInmueble(inmueble)) {
            mostrarError("No se pudo actualizar el inmueble.");
            return;
        }

        // Se actualiza tambien la copia que guarda el propietario:
        // se quita del propietario actual y se asigna al que indica el formulario
        Integer duenioActual = buscarIdPropietarioDe(inmueble.getId());
        Integer duenioNuevo = leerIdPropietarioOpcional();
        if (duenioActual != null) {
            quitarDePropietario(duenioActual, inmueble.getId());
        }
        if (duenioNuevo != null) {
            asignarAPropietario(duenioNuevo, inmueble.getId());
        }

        mostrarInfo("Inmueble actualizado.");
        limpiar();
        cargarTabla();
    }

    private void eliminar() {
        Integer id = leerId();
        if (id == null) {
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el inmueble " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        // Primero se quita de su propietario (si tiene) y luego de la inmobiliaria
        Integer duenio = buscarIdPropietarioDe(id);
        if (duenio != null) {
            quitarDePropietario(duenio, id);
        }
        if (borrarInmueble(id)) {
            mostrarInfo("Inmueble eliminado.");
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
        Inmueble inmueble = consultarInmueble(id);
        if (inmueble == null) {
            mostrarError("No existe un inmueble con el ID " + id + ".");
            return;
        }

        Integer duenio = buscarIdPropietarioDe(id);
        modelo.setRowCount(0);
        modelo.addRow(crearFila(inmueble, duenio));

        txtUbicacion.setText(inmueble.getUbicacion());
        txtDireccion.setText(inmueble.getDireccion());
        txtPrecio.setText(formatoPrecio.format(inmueble.getPrecio()));
        cmbTipo.setSelectedItem(inmueble.getTipoDeInmueble());
        cmbContrato.setSelectedItem(inmueble.getTipoContrato());
        chkDisponible.setSelected(inmueble.getEstaDisponible());
        txtIdPropietario.setText(duenio == null ? "" : String.valueOf(duenio));
    }

    // tabla y formulario
    private void cargarTabla() {
        modelo.setRowCount(0);
        Map<Integer, Integer> duenios = construirMapaDuenios();
        for (Inmueble i : obtenerInmuebles()) {
            modelo.addRow(crearFila(i, duenios.get(i.getId())));
        }
    }

    private Object[] crearFila(Inmueble i, Integer idPropietario) {
        return new Object[] { i.getId(), i.getUbicacion(), i.getDireccion(),
                formatoPrecio.format(i.getPrecio()), i.getTipoDeInmueble(), i.getTipoContrato(),
                i.getEstaDisponible() ? "Sí" : "No", idPropietario == null ? "-" : idPropietario };
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
        String duenio = String.valueOf(modelo.getValueAt(fila, 7));
        txtIdPropietario.setText("-".equals(duenio) ? "" : duenio);
    }

    private void limpiar() {
        txtId.setText("");
        txtUbicacion.setText("");
        txtDireccion.setText("");
        txtPrecio.setText("");
        cmbTipo.setSelectedIndex(0);
        cmbContrato.setSelectedIndex(0);
        chkDisponible.setSelected(true);
        txtIdPropietario.setText("");
        tabla.clearSelection();
    }

    // lectura y validacion del formulario
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

    /**
     * Arma un Inmueble con los datos del formulario.
     * Devuelve null (y avisa) si algun dato es invalido.
     */
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

        // El propietario es opcional, pero si se escribe debe existir
        String textoPropietario = txtIdPropietario.getText().trim();
        if (!textoPropietario.isEmpty()) {
            Integer idPropietario = leerIdPropietarioOpcional();
            if (idPropietario == null) {
                mostrarError("El ID del propietario debe ser un número entero.");
                return null;
            }
            if (!existePropietario(idPropietario)) {
                mostrarError("No existe un propietario con el ID " + idPropietario + ".");
                return null;
            }
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

    /** ID del propietario escrito en el formulario, o null si esta vacio o no es numerico. */
    private Integer leerIdPropietarioOpcional() {
        String texto = txtIdPropietario.getText().trim();
        if (texto.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    // metodos que llaman a management
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

    /** Devuelve el inmueble o null si no existe (el DTO trae el dato y si existe). */
    private Inmueble consultarInmueble(int id) {
        try {
            ResultadoInmueble resultado = management.buscarInmueble(ID_INMOBILIARIA, id);
            if (resultado != null && resultado.getExisteInmueble()) {
                return resultado.getInmueble();
            }
        } catch (Exception ex) {
            mostrarError("Error al buscar: " + ex.getMessage());
        }
        return null;
    }

    private List<Inmueble> obtenerInmuebles() {
        try {
            List<Inmueble> lista = management.listarInmuebles(ID_INMOBILIARIA);
            if (lista != null) {
                return lista;
            }
        } catch (Exception ex) {
            mostrarError("Error al listar: " + ex.getMessage());
        }
        return new ArrayList<Inmueble>();
    }

    private List<Propietario> obtenerPropietarios() {
        try {
            List<Propietario> lista = management.listarPropietarios(ID_INMOBILIARIA);
            if (lista != null) {
                return lista;
            }
        } catch (Exception ex) {
            mostrarError("Error al listar propietarios: " + ex.getMessage());
        }
        return new ArrayList<Propietario>();
    }

    private boolean existePropietario(int idPropietario) {
        try {
            ResultadoPropietario resultado = management.buscarPropietario(ID_INMOBILIARIA, idPropietario);
            return resultado != null && resultado.getExistePropietario();
        } catch (Exception ex) {
            mostrarError("Error al buscar propietario: " + ex.getMessage());
            return false;
        }
    }

    private boolean asignarAPropietario(int idPropietario, int idInmueble) {
        try {
            return management.agregarInmuebleAPropietario(ID_INMOBILIARIA, idPropietario, idInmueble);
        } catch (Exception ex) {
            mostrarError("Error al asignar al propietario: " + ex.getMessage());
            return false;
        }
    }

    private boolean quitarDePropietario(int idPropietario, int idInmueble) {
        try {
            return management.eliminarInmuebleDePropietario(ID_INMOBILIARIA, idPropietario, idInmueble);
        } catch (Exception ex) {
            mostrarError("Error al quitar del propietario: " + ex.getMessage());
            return false;
        }
    }

    // Quien es el dueño de cada inmueble
    /** Recorre los propietarios y arma un mapa: id del inmueble -> id del propietario. */
    private Map<Integer, Integer> construirMapaDuenios() {
        Map<Integer, Integer> duenios = new HashMap<Integer, Integer>();
        for (Propietario p : obtenerPropietarios()) {
            if (p.getInmuebles() != null) {
                for (Inmueble i : p.getInmuebles()) {
                    duenios.put(i.getId(), p.getId());
                }
            }
        }
        return duenios;
    }

    /** Devuelve el id del propietario del inmueble, o null si no tiene. */
    private Integer buscarIdPropietarioDe(int idInmueble) {
        return construirMapaDuenios().get(idInmueble);
    }

    // mensajes
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
