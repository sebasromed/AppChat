package interfaz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import controlador.Controlador;

public class VentanaLogin extends JFrame implements ActionListener {

    private JPanel panelNorte, panelCentro, panelCentro_norte, panelCentro_centro, panelSur;
    private JLabel lblNorte, lblTelefono, lblContraseña;
    private JTextField telefono;
    private JPasswordField contraseña;
    private JButton aceptar, cancelar, registrar;

    // Medium, legible green
    private final Color verdeMedio = new Color(76, 175, 80);

    public VentanaLogin() {
        inicializar();
        setVisible(true);
    }

    private void inicializar() {
        setResizable(false);
        setSize(new Dimension(500, 350)); // Larger window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null); // Center on screen

        panelTitulo();
        panelCentro();
        panelBotones();
    }

    private void panelTitulo() {
        panelNorte = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panelNorte.setBackground(Color.WHITE);
        getContentPane().add(panelNorte, BorderLayout.NORTH);

        lblNorte = new JLabel("AppChat") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setFont(getFont());
                g2.setColor(new Color(0, 0, 0, 60));
                g2.drawString(getText(), 4, getBaseline(getWidth(), getHeight()) + 4);
                g2.setColor(verdeMedio);
                g2.drawString(getText(), 0, getBaseline(getWidth(), getHeight()));
                g2.dispose();
            }
        };
        lblNorte.setFont(new Font("Comic Sans MS", Font.BOLD, 42));
        lblNorte.setOpaque(false);
        panelNorte.add(lblNorte);
    }

    private void panelCentro() {
        panelCentro = new JPanel();
        panelCentro.setBorder(new TitledBorder(
            new CompoundBorder(
                new EmptyBorder(0, 30, 0, 30),
                new LineBorder(verdeMedio, 2)
            ),
            "Login",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Comic Sans MS", Font.BOLD, 16),
            verdeMedio
        ));
        panelCentro.setBackground(Color.WHITE);
        getContentPane().add(panelCentro, BorderLayout.CENTER);
        panelCentro.setLayout(new BorderLayout(0, 0));

        panelCentro_centro = new JPanel(new GridBagLayout());
        panelCentro_centro.setBackground(Color.WHITE);
        // Add horizontal padding to the fields
        panelCentro_centro.setBorder(new EmptyBorder(0, 30, 0, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 10);

        // Teléfono label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        lblTelefono = new JLabel("Teléfono:");
        panelCentro_centro.add(lblTelefono, gbc);

        // Teléfono field
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        telefono = new JTextField();
        telefono.setPreferredSize(new Dimension(200, 25));
        panelCentro_centro.add(telefono, gbc);

        // Vertical space between fields
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelCentro_centro.add(Box.createVerticalStrut(20), gbc);
        gbc.gridwidth = 1;

        // Contraseña label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        lblContraseña = new JLabel("Contraseña:");
        panelCentro_centro.add(lblContraseña, gbc);

        // Contraseña field
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        contraseña = new JPasswordField();
        contraseña.setPreferredSize(new Dimension(200, 25));
        panelCentro_centro.add(contraseña, gbc);

        panelCentro.add(panelCentro_centro, BorderLayout.CENTER);
    }

    private void panelBotones() {
        panelSur = new JPanel();
        panelSur.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelSur.setBackground(Color.WHITE);

        registrar = new JButton("Registrar");
        registrar.addActionListener(this);

        cancelar = new JButton("Cancelar");
        cancelar.addActionListener(this);

        aceptar = new JButton("Aceptar");
        aceptar.addActionListener(this);

        panelSur.add(registrar);
        panelSur.add(Box.createRigidArea(new Dimension(40, 30)));
        panelSur.add(aceptar);
        panelSur.add(cancelar);

        getContentPane().add(panelSur, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == aceptar) {
            String tlf = telefono.getText().trim();
            String passw = new String(contraseña.getPassword()).trim();

            if (tlf.isEmpty() || passw.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, rellene todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (Controlador.INSTANCE.loginUsuario(tlf, passw)) {
                Controlador.cambiarVentana(this, VentanaPrincipal::new);
            } else {
                JOptionPane.showMessageDialog(this, "Teléfono o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (src == cancelar) {
            this.dispose();
        } else if (src == registrar) {
            Controlador.cambiarVentana(this, VentanaRegistro::new);
        }
    }
}