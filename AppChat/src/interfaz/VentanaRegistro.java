package interfaz;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import controlador.Controlador;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Stream;
import com.toedter.calendar.JDateChooser;

public class VentanaRegistro {
    private JFrame ventanaReg;
    private JPanel panelCentral, panelImagen, panelBotones;
    private JTextField textNombre, textApellidos, textTelefono, textPassword, textPassword2, textImagen;
    private JTextArea textSaludo;
    private JLabel notaRequerimiento;
    private JLabel nombre, apellidos, telefono, password, password2, fecha, saludo, imagen;
    private FotoPerfil iconoImagen;
    private JButton botonCancelar, botonAceptar, botonImagen;
    private Controlador controlador;
    private JDateChooser fechaChooser;

    public VentanaRegistro(Controlador controlador) {
        this.controlador = controlador;
        inicializar();
    }

    private void inicializar() {
        ventanaReg = new JFrame("Registro de Usuario");
        ventanaReg.setResizable(false);
        ventanaReg.setSize(new Dimension(850, 520));
        ventanaReg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaReg.setLocationRelativeTo(null);
        ventanaReg.setLayout(new BorderLayout(20, 10));

        // WhatsApp-like light green
        Color bgColor = new Color(220, 248, 198);

        // Panel Central (Formulario)
        panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(bgColor);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        notaRequerimiento = new JLabel("Los campos marcados con * son obligatorios");
        notaRequerimiento.setForeground(new Color(37, 211, 102));
        notaRequerimiento.setFont(notaRequerimiento.getFont().deriveFont(Font.BOLD));
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panelCentral.add(notaRequerimiento, gbc);
        gbc.gridwidth = 1;
        row++;

        nombre = new JLabel("Nombre*:");
        gbc.gridx = 0; gbc.gridy = row;
        panelCentral.add(nombre, gbc);

        textNombre = new JTextField(12);
        gbc.gridx = 1; gbc.gridy = row++;
        panelCentral.add(textNombre, gbc);

        apellidos = new JLabel("Apellidos*:");
        gbc.gridx = 0; gbc.gridy = row;
        panelCentral.add(apellidos, gbc);

        textApellidos = new JTextField(12);
        gbc.gridx = 1; gbc.gridy = row++;
        panelCentral.add(textApellidos, gbc);

        telefono = new JLabel("Teléfono*:");
        gbc.gridx = 0; gbc.gridy = row;
        panelCentral.add(telefono, gbc);

        textTelefono = new JTextField(12);
        gbc.gridx = 1; gbc.gridy = row++;
        panelCentral.add(textTelefono, gbc);

        password = new JLabel("Contraseña*:");
        gbc.gridx = 0; gbc.gridy = row;
        panelCentral.add(password, gbc);

        textPassword = new JPasswordField(12);
        gbc.gridx = 1; gbc.gridy = row++;
        panelCentral.add(textPassword, gbc);

        password2 = new JLabel("Repetir contraseña*:");
        gbc.gridx = 0; gbc.gridy = row;
        panelCentral.add(password2, gbc);

        textPassword2 = new JPasswordField(12);
        gbc.gridx = 1; gbc.gridy = row++;
        panelCentral.add(textPassword2, gbc);

        fecha = new JLabel("Fecha nacimiento*:");
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panelCentral.add(fecha, gbc);
        row++;

        fechaChooser = new JDateChooser();
        fechaChooser.setDateFormatString("dd-MM-yyyy");
        fechaChooser.setPreferredSize(new Dimension(260, 28));
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panelCentral.add(fechaChooser, gbc);
        gbc.gridwidth = 1;
        row++;

        saludo = new JLabel("Saludo:");
        gbc.gridx = 0; gbc.gridy = row;
        panelCentral.add(saludo, gbc);

        // Large, fixed-size JTextArea for Saludo (no scroll)
        textSaludo = new JTextArea(3, 20);
        textSaludo.setLineWrap(true);
        textSaludo.setWrapStyleWord(true);
        textSaludo.setFont(new JTextField().getFont());
        textSaludo.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
                if (getLength() + str.length() <= 100) {
                    super.insertString(offs, str, a);
                }
            }
        });
        textSaludo.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        textSaludo.setBackground(Color.WHITE);
        textSaludo.setPreferredSize(new Dimension(220, 48));
        setPlaceholder(textSaludo, "Máx. 100 caracteres");
        gbc.gridx = 1; gbc.gridy = row++;
        panelCentral.add(textSaludo, gbc);

        // Panel Imagen de Perfil (GridBagLayout for perfect centering)
        panelImagen = new JPanel(new GridBagLayout());
        panelImagen.setBorder(BorderFactory.createTitledBorder("Imagen de perfil*"));
        panelImagen.setPreferredSize(new Dimension(180, 260));
        panelImagen.setBackground(bgColor);

        GridBagConstraints imgGbc = new GridBagConstraints();
        imgGbc.gridx = 0;
        imgGbc.gridy = 0;
        imgGbc.weightx = 1.0;
        imgGbc.weighty = 1.0;
        imgGbc.anchor = GridBagConstraints.CENTER;
        imgGbc.fill = GridBagConstraints.NONE;

        try {
            String path = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";
            URL url = new URL(path);
            BufferedImage image = ImageIO.read(url);
            int diametro = 100;
            iconoImagen = new FotoPerfil(image, diametro);
            panelImagen.add(iconoImagen, imgGbc);
        } catch (Exception e) {
            // Error loading image
        }

        // Add the rest of the controls below the image (adjust gridy accordingly)
        imgGbc.gridy++;
        imgGbc.weighty = 0;
        imgGbc.insets = new Insets(10, 0, 0, 0);
        imgGbc.fill = GridBagConstraints.HORIZONTAL;

        textImagen = new JTextField();
        textImagen.setMaximumSize(new Dimension(150, 25));
        setPlaceholder(textImagen, "Link a la imagen");
        panelImagen.add(textImagen, imgGbc);

        imgGbc.gridy++;
        botonImagen = new JButton("Establecer imagen");
        panelImagen.add(botonImagen, imgGbc);

        actionEstablecer();

        // Panel Botones
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelBotones.setBackground(bgColor);
        botonCancelar = new JButton("Cancelar");
        botonAceptar = new JButton("Aceptar");
        panelBotones.add(botonCancelar);
        panelBotones.add(botonAceptar);

        actionCancelar();
        actionAceptar();

        // Add panels to window
        ventanaReg.getContentPane().setBackground(bgColor);
        ventanaReg.add(panelCentral, BorderLayout.CENTER);
        ventanaReg.add(panelImagen, BorderLayout.EAST);
        ventanaReg.add(panelBotones, BorderLayout.SOUTH);

        ventanaReg.setVisible(true);
    }

    // Placeholder para un campo de texto
    private void setPlaceholder(JTextField field, String placeholder) {
        field.setForeground(Color.GRAY);
        field.setText(placeholder);
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
    
    // Placeholder para un área de texto (como saludo)
    private void setPlaceholder(JTextArea area, String placeholder) {
        area.setForeground(Color.GRAY);
        area.setText(placeholder);
        area.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (area.getText().equals(placeholder)) {
                    area.setText("");
                    area.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (area.getText().isEmpty()) {
                    area.setForeground(Color.GRAY);
                    area.setText(placeholder);
                }
            }
        });
    }

    private void actionCancelar() {
        botonCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                controlador.cambiarVentanaLogin(ventanaReg);
            }
        });
    }

    private void actionAceptar() {
        botonAceptar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String valorNombre = textNombre.getText().trim();
                String valorApellidos = textApellidos.getText().trim();
                String valorTelefono = textTelefono.getText().trim();
                String valorPassword = textPassword.getText().trim();
                String valorPassword2 = textPassword2.getText().trim();
                LocalDate valorFechaNacimiento = fechaChooser.getDate() != null
                        ? fechaChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        : null;
                String valorSaludo = textSaludo.getText().trim();
                String valorImagen = textImagen.getText().trim();
                LocalDate valorFechaRegistro = LocalDate.now();

                if (Stream.of(valorNombre, valorApellidos, valorTelefono, valorPassword, valorPassword2, valorImagen).anyMatch(v -> v.isBlank())
                        || valorFechaNacimiento == null
                        || valorImagen.equals("Link a la imagen")) {
                    JOptionPane.showMessageDialog(ventanaReg, "Por favor, rellene todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!valorPassword.equals(valorPassword2)) {
                    JOptionPane.showMessageDialog(ventanaReg, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (valorSaludo.isBlank()) {
                        valorSaludo = "Hey there, I'm using AppChat";
                    }
                    if (controlador.registrarUsuario(valorNombre, valorApellidos, valorTelefono, valorPassword,
                            valorFechaNacimiento, valorSaludo, valorImagen, valorFechaRegistro)) {
                        controlador.cambiarVentanaPrincipal(ventanaReg);
                    } else {
                        JOptionPane.showMessageDialog(ventanaReg, "Teléfono ya registrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void actionEstablecer() {
        botonImagen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String valorImagen = textImagen.getText().trim();
                if (valorImagen == null || valorImagen.isBlank() || valorImagen.equals("Link a la imagen")) {
                    valorImagen = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";
                }
                try {
                    URL url = new URL(valorImagen);
                    BufferedImage image = ImageIO.read(url);
                    iconoImagen.setImage(image);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(ventanaReg, "No se pudo cargar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                iconoImagen.revalidate();
                iconoImagen.repaint();
            }
        });
    }
}