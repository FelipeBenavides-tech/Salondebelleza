/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Loguin extends JFrame {
    private JPanel datos, botonesPanel, detalles;
    private Container contenedor;
    private JLabel lid, lnombre, lapellido, lcorreo, lcontrasena, ltipousuario, imagenLabel;
    public JButton buscar, eliminar, actualizar, registrar, enviar, limpiar,volver;
    public JTextField uid, unombre, uapellido, ucorreo, ucontrasena, utipousuario;
    public JTable tabla;
    private JScrollPane miscroll;
    private TitledBorder titulo, titulo2;
    private String lista[] = { "", "Bibliotecario", "Seminarista", "Usuario Biblioteca", "Administrador" };
    public JComboBox listaE;
    public DefaultTableModel modelos;

    public Loguin() {
        super("Administrador");
        contenedor = getContentPane();
        contenedor.setLayout(new BorderLayout());

        // Panel de datos
        titulo = new TitledBorder("Usuarios");
        datos = new JPanel();
        datos.setBorder(titulo);
        datos.setLayout(new GridLayout(6, 2, 5, 5));
        datos.setBackground(Color.BLACK);  // Fondo negro
        titulo.setTitleColor(Color.WHITE);  // Título en blanco

        lid = new JLabel("ID");
        lnombre = new JLabel("Nombre");
        lapellido = new JLabel("Apellido");
        lcorreo = new JLabel("Correo");
        lcontrasena = new JLabel("Contraseña");
        ltipousuario = new JLabel("Tipo de usuario");

        // Cambiar color de texto a blanco
        lid.setForeground(Color.WHITE);
        lnombre.setForeground(Color.WHITE);
        lapellido.setForeground(Color.WHITE);
        lcorreo.setForeground(Color.WHITE);
        lcontrasena.setForeground(Color.WHITE);
        ltipousuario.setForeground(Color.WHITE);

        uid = new JTextField(10);
        unombre = new JTextField(10);
        uapellido = new JTextField(10);
        ucorreo = new JTextField(10);
        ucontrasena = new JTextField(10);
        utipousuario = new JTextField(10);

        listaE = new JComboBox(lista);
        uid.setEditable(false);

        datos.add(lid);
        datos.add(uid);
        datos.add(lnombre);
        datos.add(unombre);
        datos.add(lapellido);
        datos.add(uapellido);
        datos.add(lcorreo);
        datos.add(ucorreo);
        datos.add(lcontrasena);
        datos.add(ucontrasena);
        datos.add(ltipousuario);
        datos.add(listaE);

        // Panel para la imagen
        JPanel panelConImagen = new JPanel(new BorderLayout());
        panelConImagen.setBackground(Color.BLACK); // Fondo negro
        imagenLabel = new JLabel();
        imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Cargar imagen desde un archivo
        //ImageIcon imagenIcono = new ImageIcon(getClass().getResource("/recursos/administrador.png"));
 // Reemplaza con la ruta de tu imagen
       // Image imagen = imagenIcono.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        //imagenLabel.setIcon(new ImageIcon(imagen));

        panelConImagen.add(imagenLabel, BorderLayout.CENTER);

        // Combinar los paneles (datos y imagen)
        JPanel datosEImagen = new JPanel(new BorderLayout());
        datosEImagen.add(datos, BorderLayout.CENTER);
        datosEImagen.add(panelConImagen, BorderLayout.EAST); // Imagen a la derecha

        // Panel de botones
        botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botonesPanel.setBackground(Color.BLACK);  // Fondo negro
        buscar = crearBotonEstilizado("Buscar");
        eliminar = crearBotonEstilizado("Eliminar");
        actualizar = crearBotonEstilizado("Actualizar");
        registrar = crearBotonEstilizado("Registrar");
        enviar = crearBotonEstilizado("Enviar");
        limpiar = crearBotonEstilizado("Limpiar");
        volver = crearBotonEstilizado("volver");

        botonesPanel.add(buscar);
        botonesPanel.add(eliminar);
        botonesPanel.add(actualizar);
        botonesPanel.add(registrar);
        botonesPanel.add(enviar);
        botonesPanel.add(limpiar);
        botonesPanel.add(volver);

        // Panel de detalles (tabla)
        modelos = new DefaultTableModel();
        modelos.addColumn("ID");
        modelos.addColumn("Nombres");
        modelos.addColumn("Apellidos");
        modelos.addColumn("Correo");
        modelos.addColumn("Contraseña");
        modelos.addColumn("Tipo Usuario"); 

        tabla = new JTable(modelos);
        tabla.setBackground(new Color(173, 216, 230));  // Fondo azul claro para la tabla
        miscroll = new JScrollPane(tabla);
        miscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        miscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        detalles = new JPanel(new BorderLayout());
        detalles.setBackground(Color.BLACK);  // Fondo negro en el panel de detalles
        titulo2 = new TitledBorder("Datos Trabajador");
        titulo2.setTitleColor(Color.WHITE);  // Título del borde en blanco
        detalles.setBorder(titulo2);
        detalles.add(miscroll, BorderLayout.CENTER);

        // Agregar todo al contenedor principal
        contenedor.add(datosEImagen, BorderLayout.NORTH); // Usar datos e imagen combinados
        contenedor.add(botonesPanel, BorderLayout.CENTER);
        contenedor.add(detalles, BorderLayout.SOUTH);
    }

    // Método para crear botones estilizados
    private JButton crearBotonEstilizado(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(100, 149, 237));  // Color azul
        boton.setForeground(Color.WHITE);  // Texto blanco
        boton.setFont(new Font("Arial", Font.BOLD, 14));  // Fuente en negrita
        return boton;
    }

    public static void main(String[] args) {
        // Crear y mostrar la GUI
        SwingUtilities.invokeLater(() -> {
            Loguin gui = new Loguin();
            gui.setSize(800, 800);
            gui.setVisible(true);
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}