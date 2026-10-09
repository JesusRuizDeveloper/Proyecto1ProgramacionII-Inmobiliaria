package co.edu.uptc.inmobiliaria.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import co.edu.uptc.inmobiliaria.Enums.Rol;
import co.edu.uptc.inmobiliaria.Management.InmobiliariaManagement;
import co.edu.uptc.inmobiliaria.Management.LoginManagement;
import co.edu.uptc.inmobiliaria.Model.Persona;

/**
 * Ventana de inicio de sesion.
 * Solo habla con Management (nunca con DAO ni Util).
 */
public class LoginGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    // Management para el login (llega por constructor)
    private LoginManagement loginManagement;

    // Management general: LoginGUI no lo usa, pero se lo pasa a los menus
    private InmobiliariaManagement management;

    // Componentes
    
    private JComboBox<Rol> cmbRol; // combo box es un menu desplegable para elegir el rol
    private JTextField txtId; //campo de texto para escribir el id
    private JPasswordField txtClave; //campo de texto que oculta lo que se escribe
    private JLabel lblClave;
    private JButton btnIngresar; // boton de ingresar

    //inyeccion de dependencias de metodos que necesita para funcionar
    public LoginGUI(LoginManagement loginManagement, InmobiliariaManagement management) {
        this.loginManagement = loginManagement;
        this.management = management;
        configurarVentana();
        crearComponentes();
        crearEventos();
    }

    //----------configuracion de la ventana
    private void configurarVentana() {
        setTitle("Plataforma Inmobiliaria - Login");
        setSize(400, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //hace que el programa se cierre completamente al darle x
        setLocationRelativeTo(null); // centrada en pantalla
        setResizable(false); // impide que se cambie el tamaño
        setLayout(new BorderLayout()); // Divide el espacio en 5 regiones: n, s, e, o y centro
    }

    //---------componentes visuales dentro de la ventana
    private void crearComponentes() {
        JLabel lblTitulo = new JLabel("PLATAFORMA INMOBILIARIA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18)); //tipo de fuente del titulo
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10)); //crea margen transparente
        add(lblTitulo, BorderLayout.NORTH); //ubica el titulo en la parte superior

        // crea una caja 3 filas, 2 columnas
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 10, 15)); //crea una caja con estructura tabla, 10: espacio horizontal entre columnas, 15: espacio vertical entre filas
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

        cmbRol = new JComboBox<Rol>(Rol.values()); //menu desplegable con los roles
        txtId = new JTextField(); //caja de texto para el id
        txtClave = new JPasswordField();
        lblClave = new JLabel("Contraseña:"); //guarda en una variable global para poder deshabilitarlo después si se selecciona el rol CLIENTE


        panelForm.add(new JLabel("Rol:")); 
        panelForm.add(cmbRol);
        panelForm.add(new JLabel("ID:"));
        panelForm.add(txtId);
        panelForm.add(lblClave);
        panelForm.add(txtClave);
        add(panelForm, BorderLayout.CENTER);

        // caja boton 
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        btnIngresar = new JButton("INGRESAR");
        panelBoton.add(btnIngresar);
        add(panelBoton, BorderLayout.SOUTH);

        // Estado inicial segun el rol seleccionado
        actualizarCampoClave();
    }

    //----------- actionListener
    private void crearEventos() {
        cmbRol.addActionListener(e -> actualizarCampoClave()); // Al cambiar el rol, se habilita o no la contraseña
        btnIngresar.addActionListener(e -> ingresar()); // boton ingresar
        getRootPane().setDefaultButton(btnIngresar); // enter en los campos tambien ingresa
    }

    // si el rol es CLIENTE, la contraseña se deshabilita 
    private void actualizarCampoClave() {
        Rol rol = (Rol) cmbRol.getSelectedItem();
        boolean esCliente = (rol == Rol.CLIENTE);
        txtClave.setEnabled(!esCliente);
        lblClave.setEnabled(!esCliente);
        if (esCliente) {
            txtClave.setText("");
        }
    }

    // logica del boton: validar -> autenticar -> abrir menu
    private void ingresar() {
        Rol rol = (Rol) cmbRol.getSelectedItem();
        String textoId = txtId.getText().trim();
        String clave = new String(txtClave.getPassword()).trim();

        // Validaciones de GUI
        if (textoId.isEmpty()) {
            mostrarError("Ingrese el ID:");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(textoId);
        } catch (NumberFormatException ex) {
            mostrarError("El ID debe ser un número entero.");
            return;
        }

        if (rol != Rol.CLIENTE && clave.isEmpty()) {
            mostrarError("Ingrese la contraseña.");
            return;
        }

        // Llamada a Management (aislada en autenticar)
        Persona persona = autenticar(id, clave, rol);

        if (persona == null) {
            mostrarError("Datos incorrectos. Verifique rol, ID y contraseña.");
            return;
        }

        abrirMenu(persona, rol);
    }

    //metodo que llamam al management para el login
    private Persona autenticar(int id, String clave, Rol rol) {
        try {
            // TODO(MANAGEMENT): firma provisional, confirmar con el equipo
            return loginManagement.iniciarSesion(id, clave, rol);
        } catch (Exception ex) {
            // Si Management falla, no se cae la ventana
            mostrarError("Error al iniciar sesión: " + ex.getMessage());
            return null;
        }
    }

    // abrir menu segun el rol
    private void abrirMenu(Persona persona, Rol rol) {
        switch (rol) {
        case ADMINISTRADOR:
            new MenuAdministradorGUI(loginManagement, management).setVisible(true);
            break;
        case PROPIETARIO:
            new MenuPropietarioGUI(persona.getId(), loginManagement, management).setVisible(true);
            break;
        case CLIENTE:
            new MenuClienteGUI(loginManagement, management).setVisible(true);
            break;
        }

        dispose(); // cierra el login
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}