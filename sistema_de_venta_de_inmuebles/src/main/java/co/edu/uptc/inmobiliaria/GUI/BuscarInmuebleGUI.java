package co.edu.uptc.inmobiliaria.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.inmobiliaria.Enums.TipoContrato;
import co.edu.uptc.inmobiliaria.Enums.TipoDeInmueble;
import co.edu.uptc.inmobiliaria.Management.InmobiliariaManagement;
import co.edu.uptc.inmobiliaria.Model.Inmueble;

/**
 * Consulta de inmuebles para el cliente. Solo busca y muestra:
 * no tiene botones para agregar, modificar ni eliminar.
 */
public class BuscarInmuebleGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    // Solo existe una inmobiliaria y su id es 1
    private static final int ID_INMOBILIARIA = 1;
    private static final String TODOS = "Todos";

    private InmobiliariaManagement management;

    // Para mostrar el precio sin notacion cientifica (2500 en vez de 2500.0)
    private DecimalFormat formatoPrecio = new DecimalFormat("0.##", DecimalFormatSymbols.getInstance(Locale.US));

    // Componentes
    private JTextField txtUbicacion;
    private JTextField txtPrecioMin;
    private JTextField txtPrecioMax;
    private JComboBox<String> cmbTipo;
    private JComboBox<String> cmbContrato;
    private JButton btnDisponibles;
    private JButton btnBuscar;
    private JButton btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modelo;

    public BuscarInmuebleGUI(InmobiliariaManagement management) {
        this.management = management;
        configurarVentana();
        crearComponentes();
        crearEventos();
        mostrarEnTabla(obtenerTodos()); // al abrir se ven todos los inmuebles
    }

    // ventana
    private void configurarVentana() {
        setTitle("Buscar inmuebles");
        setSize(900, 560);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    // componentes
    private void crearComponentes() {
        // Filtros (4 filas x 2 columnas)
        JPanel panelFiltros = new JPanel(new GridLayout(4, 2, 10, 8));
        panelFiltros.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        txtUbicacion = new JTextField();
        txtPrecioMin = new JTextField();
        txtPrecioMax = new JTextField();
        cmbTipo = new JComboBox<String>(nombresConTodos(TipoDeInmueble.values()));
        cmbContrato = new JComboBox<String>(nombresConTodos(TipoContrato.values()));

        // El precio usa dos campos (minimo y maximo) en una sola fila
        JPanel panelPrecio = new JPanel(new GridLayout(1, 4, 5, 0));
        panelPrecio.add(new JLabel("Mín:", SwingConstants.RIGHT));
        panelPrecio.add(txtPrecioMin);
        panelPrecio.add(new JLabel("Máx:", SwingConstants.RIGHT));
        panelPrecio.add(txtPrecioMax);

        panelFiltros.add(new JLabel("Ubicación (ciudad o zona):"));
        panelFiltros.add(txtUbicacion);
        panelFiltros.add(new JLabel("Precio:"));
        panelFiltros.add(panelPrecio);
        panelFiltros.add(new JLabel("Tipo de inmueble:"));
        panelFiltros.add(cmbTipo);
        panelFiltros.add(new JLabel("Tipo de contrato:"));
        panelFiltros.add(cmbContrato);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        btnDisponibles = new JButton("VER DISPONIBLES");
        btnBuscar = new JButton("BUSCAR");
        btnLimpiar = new JButton("LIMPIAR");
        panelBotones.add(btnDisponibles);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFiltros, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);
        add(panelNorte, BorderLayout.NORTH);

        // Tabla de resultados (las celdas no se pueden editar)
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

        add(new JLabel("Doble clic sobre un inmueble para ver su información.", SwingConstants.CENTER),
                BorderLayout.SOUTH);
    }

    // ---------------------------------------------------------
    // 3. Eventos
    // ---------------------------------------------------------
    private void crearEventos() {
        btnDisponibles.addActionListener(e -> verDisponibles());
        btnBuscar.addActionListener(e -> buscar());
        btnLimpiar.addActionListener(e -> limpiar());

        // Doble clic en una fila: muestra la informacion del inmueble
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    verDetalle();
                }
            }
        });
    }

    // acciones de los botones
    private void verDisponibles() {
        List<Inmueble> lista = obtenerDisponibles();
        mostrarEnTabla(lista);
        if (lista.isEmpty()) {
            mostrarInfo("No hay inmuebles disponibles.");
        }
    }

    /** Aplica solo los filtros que tengan algo escrito/seleccionado y deja los que cumplen todos. */
    private void buscar() {
        List<Inmueble> resultado = null; // null = todavia no se aplico ningun filtro

        // Filtro por ubicacion
        String ubicacion = txtUbicacion.getText().trim();
        if (!ubicacion.isEmpty()) {
            resultado = interseccion(resultado, buscarPorUbicacion(ubicacion));
        }

        // Filtro por precio (minimo y/o maximo)
        String textoMin = txtPrecioMin.getText().trim();
        String textoMax = txtPrecioMax.getText().trim();
        if (!textoMin.isEmpty() || !textoMax.isEmpty()) {
            Double minimo = textoMin.isEmpty() ? Double.valueOf(0) : leerPrecio(textoMin);
            Double maximo = textoMax.isEmpty() ? Double.valueOf(Double.MAX_VALUE) : leerPrecio(textoMax);
            if (minimo == null || maximo == null) {
                return; // leerPrecio ya aviso del error
            }
            if (minimo > maximo) {
                mostrarError("El precio mínimo no puede ser mayor que el máximo.");
                return;
            }
            resultado = interseccion(resultado, buscarPorPrecio(minimo, maximo));
        }

        // Filtro por tipo de inmueble
        String tipo = (String) cmbTipo.getSelectedItem();
        if (!TODOS.equals(tipo)) {
            resultado = interseccion(resultado, buscarPorTipo(TipoDeInmueble.valueOf(tipo)));
        }

        // Filtro por tipo de contrato
        String contrato = (String) cmbContrato.getSelectedItem();
        if (!TODOS.equals(contrato)) {
            resultado = interseccion(resultado, buscarPorContrato(TipoContrato.valueOf(contrato)));
        }

        // Sin filtros: se muestran todos
        if (resultado == null) {
            resultado = obtenerTodos();
        }

        mostrarEnTabla(resultado);
        if (resultado.isEmpty()) {
            mostrarInfo("No se encontraron inmuebles con esos filtros.");
        }
    }

    private void limpiar() {
        txtUbicacion.setText("");
        txtPrecioMin.setText("");
        txtPrecioMax.setText("");
        cmbTipo.setSelectedIndex(0);
        cmbContrato.setSelectedIndex(0);
        mostrarEnTabla(obtenerTodos());
    }

    private void verDetalle() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            return;
        }
        String detalle = "ID: " + modelo.getValueAt(fila, 0)
                + "\nUbicación: " + modelo.getValueAt(fila, 1)
                + "\nDirección: " + modelo.getValueAt(fila, 2)
                + "\nPrecio: " + modelo.getValueAt(fila, 3)
                + "\nTipo de inmueble: " + modelo.getValueAt(fila, 4)
                + "\nTipo de contrato: " + modelo.getValueAt(fila, 5)
                + "\nDisponible: " + modelo.getValueAt(fila, 6);
        JOptionPane.showMessageDialog(this, detalle, "Información del inmueble", JOptionPane.INFORMATION_MESSAGE);
    }

    // apoyo
    private void mostrarEnTabla(List<Inmueble> lista) {
        modelo.setRowCount(0);
        for (Inmueble i : lista) {
            modelo.addRow(new Object[] { i.getId(), i.getUbicacion(), i.getDireccion(),
                    formatoPrecio.format(i.getPrecio()), i.getTipoDeInmueble(), i.getTipoContrato(),
                    i.getEstaDisponible() ? "Sí" : "No" });
        }
    }

    /** Devuelve los inmuebles que estan en las dos listas. Si la primera es null, devuelve la segunda. */
    private List<Inmueble> interseccion(List<Inmueble> primera, List<Inmueble> segunda) {
        if (primera == null) {
            return segunda;
        }
        List<Inmueble> comunes = new ArrayList<Inmueble>();
        for (Inmueble a : primera) {
            for (Inmueble b : segunda) {
                if (a.getId() == b.getId()) {
                    comunes.add(a);
                    break;
                }
            }
        }
        return comunes;
    }

    /** Convierte un precio escrito a numero. Devuelve null (y avisa) si es invalido. */
    private Double leerPrecio(String texto) {
        try {
            double valor = Double.parseDouble(texto);
            if (valor < 0) {
                mostrarError("El precio no puede ser negativo.");
                return null;
            }
            return valor;
        } catch (NumberFormatException ex) {
            mostrarError("El precio debe ser un número (use punto decimal, sin comas).");
            return null;
        }
    }

    /** Crea las opciones del combo: "Todos" y luego el nombre de cada valor del enum. */
    private String[] nombresConTodos(Enum<?>[] valores) {
        String[] nombres = new String[valores.length + 1];
        nombres[0] = TODOS;
        for (int i = 0; i < valores.length; i++) {
            nombres[i + 1] = valores[i].name();
        }
        return nombres;
    }

    // metodos que llaman a management
    private List<Inmueble> obtenerTodos() {
        try {
            return sinNulos(management.listarInmuebles(ID_INMOBILIARIA));
        } catch (Exception ex) {
            mostrarError("Error al listar: " + ex.getMessage());
            return new ArrayList<Inmueble>();
        }
    }

    private List<Inmueble> obtenerDisponibles() {
        try {
            return sinNulos(management.listarInmueblesDisponibles(ID_INMOBILIARIA));
        } catch (Exception ex) {
            mostrarError("Error al listar disponibles: " + ex.getMessage());
            return new ArrayList<Inmueble>();
        }
    }

    private List<Inmueble> buscarPorUbicacion(String ubicacion) {
        try {
            return sinNulos(management.buscarPorUbicacion(ID_INMOBILIARIA, ubicacion));
        } catch (Exception ex) {
            mostrarError("Error al buscar por ubicación: " + ex.getMessage());
            return new ArrayList<Inmueble>();
        }
    }

    private List<Inmueble> buscarPorPrecio(double minimo, double maximo) {
        try {
            return sinNulos(management.buscarPorPrecio(ID_INMOBILIARIA, minimo, maximo));
        } catch (Exception ex) {
            mostrarError("Error al buscar por precio: " + ex.getMessage());
            return new ArrayList<Inmueble>();
        }
    }

    private List<Inmueble> buscarPorTipo(TipoDeInmueble tipo) {
        try {
            return sinNulos(management.buscarPorTipo(ID_INMOBILIARIA, tipo));
        } catch (Exception ex) {
            mostrarError("Error al buscar por tipo: " + ex.getMessage());
            return new ArrayList<Inmueble>();
        }
    }

    private List<Inmueble> buscarPorContrato(TipoContrato contrato) {
        try {
            return sinNulos(management.buscarPorContrato(ID_INMOBILIARIA, contrato));
        } catch (Exception ex) {
            mostrarError("Error al buscar por contrato: " + ex.getMessage());
            return new ArrayList<Inmueble>();
        }
    }

    /** Si Management devuelve null, se trabaja con una lista vacia. */
    private List<Inmueble> sinNulos(List<Inmueble> lista) {
        if (lista == null) {
            return new ArrayList<Inmueble>();
        }
        return lista;
    }

    // mensajes
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
