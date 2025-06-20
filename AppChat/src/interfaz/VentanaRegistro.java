package interfaz;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import controlador.Controlador;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Stream;
import com.toedter.calendar.JDateChooser;

public class VentanaRegistro extends JFrame implements ActionListener {

    private JPanel panelCentral, panelImagen, panelBotones;
    private JTextField textNombre, textApellidos, textTelefono, textPassword, textPassword2;
    private JTextArea textSaludo;
    private JLabel notaRequerimiento;
    private JLabel nombre, apellidos, telefono, password, password2, fecha, saludo;
    private FotoPerfil iconoImagen;
    private JButton botonCancelar, botonAceptar, botonElegirImagen;
    private JDateChooser fechaChooser;
    private String rutaImagenSeleccionada = null;
    
    private static final String PLACEHOLDER_SALUDO = "Máx. 100 caracteres";

    public VentanaRegistro() {
        inicializar();
        setVisible(true);
    }

    private void inicializar() {
        setTitle("Registro de Usuario");
        setResizable(false);
        setSize(new Dimension(850, 520));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 10));

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
        setPlaceholder(textSaludo, PLACEHOLDER_SALUDO);
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

        int diametro = 100;
        BufferedImage defaultImage = null;
        try {
            defaultImage = ImageIO.read(getClass().getResource("/blank-profile-circle.png"));
            iconoImagen = new FotoPerfil(defaultImage, diametro);
            panelImagen.add(iconoImagen, imgGbc);
        } catch (IOException ex) {
            // If not found, defaultImage remains null
        }

        // Add the rest of the controls below the image (adjust gridy accordingly)
        imgGbc.gridy++;
        imgGbc.weighty = 0;
        imgGbc.insets = new Insets(10, 0, 0, 0);
        imgGbc.fill = GridBagConstraints.HORIZONTAL;


        imgGbc.gridy++;
        botonElegirImagen = new JButton("Elegir");
        botonElegirImagen.addActionListener(this);
        panelImagen.add(botonElegirImagen, imgGbc);
        
        rutaImagenSeleccionada = null;

        // Panel Botones
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelBotones.setBackground(bgColor);
        botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(this);
        
        botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(this);
        
        panelBotones.add(botonCancelar);
        panelBotones.add(botonAceptar);

        // Add panels to window
        getContentPane().setBackground(bgColor);
        add(panelCentral, BorderLayout.CENTER);
        add(panelImagen, BorderLayout.EAST);
        add(panelBotones, BorderLayout.SOUTH);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == botonCancelar) {
            Controlador.cambiarVentana(this, VentanaLogin::new);
        } else if (src == botonAceptar) {
            String valorNombre = textNombre.getText().trim();
            String valorApellidos = textApellidos.getText().trim();
            String valorTelefono = textTelefono.getText().trim();
            String valorPassword = textPassword.getText().trim();
            String valorPassword2 = textPassword2.getText().trim();
            LocalDate valorFechaNacimiento = fechaChooser.getDate() != null
                    ? fechaChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    : null;
            String valorSaludo = textSaludo.getText().trim();
            LocalDate valorFechaRegistro = LocalDate.now();

            if (Stream.of(valorNombre, valorApellidos, valorTelefono, valorPassword, valorPassword2).anyMatch(v -> isBlank(v))
                    || valorFechaNacimiento == null) {
                JOptionPane.showMessageDialog(this, "Por favor, rellene todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!valorPassword.equals(valorPassword2)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (rutaImagenSeleccionada == null) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione una foto de perfil.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (valorSaludo.equals(PLACEHOLDER_SALUDO) || isBlank(valorSaludo)) {
                    valorSaludo = "Hey there, I'm using AppChat";
                }
                try {
                    String valorImagen = Controlador.INSTANCE.guardarImagenPerfil(rutaImagenSeleccionada, valorTelefono);
                    if (Controlador.INSTANCE.registrarUsuario(valorNombre, valorApellidos, valorTelefono, valorPassword,
                            valorFechaNacimiento, valorSaludo, valorImagen, valorFechaRegistro)) {
                        Controlador.cambiarVentana(this, VentanaLogin::new);
                    } else {
                        JOptionPane.showMessageDialog(this, "Teléfono ya registrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(this, "No se pudo guardar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }
            }
        } else if (src == botonElegirImagen) {
            PanelArrastraImagen dialog = new PanelArrastraImagen(this);
            List<File> files = dialog.showDialog();
            if (!files.isEmpty()) {
                try {
                    BufferedImage image = ImageIO.read(files.get(0));
                    if (image != null) {
                        iconoImagen.setImage(image);
                        iconoImagen.revalidate();
                        iconoImagen.repaint();
                        rutaImagenSeleccionada = files.get(0).getPath();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    // Metodo auxiliar para usar el metodo isBlank (no disponible en Java 8)
    private boolean isBlank(String str) {
		return str == null || str.trim().isEmpty();
	}
}