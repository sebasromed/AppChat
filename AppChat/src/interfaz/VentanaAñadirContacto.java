package interfaz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VentanaAñadirContacto extends JDialog {

    private JPanel panelCampos, panelBoton;
    private JLabel lblTitulo;
    private JTextField textoNombre, textoTelefono;
    private JButton botonAceptar;
    Color colorFondo = new Color(220, 248, 198);

    public VentanaAñadirContacto(JFrame parent) {
        super(parent, "Añadir Contacto", true);
        getContentPane().setBackground(colorFondo);
        inicializar(parent);
        setVisible(true);
    }

    private void inicializar(JFrame parent) {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        panelTitulo();
        panelCampos();
        panelBoton();

        pack();
        setLocationRelativeTo(parent);
    }

    private void panelTitulo() {
        lblTitulo = new JLabel("Introduzca el nombre del contacto y su teléfono");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        add(lblTitulo, BorderLayout.NORTH);
    }

    private void panelCampos() {

        panelCampos = new JPanel(new BorderLayout(10, 0));
        panelCampos.setBorder(new EmptyBorder(10, 15, 10, 15));
        panelCampos.setBackground(colorFondo);

        // Load and scale the icon to be tall (about 2 fields high)
        int iconWidth = 48;
        int iconHeight = 56;
        ImageIcon iconoAddContacto = new ImageIcon("resources/add-contacto.png");
        Image imagenAddContacto = iconoAddContacto.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        JLabel lblIcono = new JLabel(new ImageIcon(imagenAddContacto));
        lblIcono.setVerticalAlignment(SwingConstants.TOP);
        lblIcono.setBackground(colorFondo);
        lblIcono.setOpaque(true);

        // Panel for the two text fields
        JPanel panelTextos = new JPanel(new GridLayout(2, 1, 0, 10));
        panelTextos.setBackground(colorFondo);

        textoNombre = new JTextField("nombre");
        textoNombre.setForeground(Color.GRAY);
        addPlaceholderBehavior(textoNombre, "nombre");

        textoTelefono = new JTextField("teléfono");
        textoTelefono.setForeground(Color.GRAY);
        addPlaceholderBehavior(textoTelefono, "teléfono");

        panelTextos.add(textoNombre);
        panelTextos.add(textoTelefono);

        panelCampos.add(lblIcono, BorderLayout.WEST);
        panelCampos.add(panelTextos, BorderLayout.CENTER);

        add(panelCampos, BorderLayout.CENTER);
    }

    private void panelBoton() {
        panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(e -> dispose());
        panelBoton.add(botonAceptar);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelBoton.setBackground(colorFondo);
        add(panelBoton, BorderLayout.SOUTH);
    }

    private void addPlaceholderBehavior(JTextField field, String placeholder) {
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
    }
}