package interfaz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.Usuario;

public class VentanaAddContacto extends JDialog implements ActionListener {

	private JPanel panelCampos, panelBoton;
	private JLabel lblTitulo;
	private JTextField textoNombre, textoTelefono;
	private JButton botonAceptar;
	private final Color colorFondo = new Color(220, 248, 198);
	private JFrame parent;
	private String telefonoInicial;

	public VentanaAddContacto(JFrame parent) {
		super(parent, "Añadir Contacto", true);
		this.parent = parent;
		getContentPane().setBackground(colorFondo);
		inicializar(parent);
		setVisible(true);
	}

	public VentanaAddContacto(JFrame parent, String telefonoInicial) {
		super(parent, "Añadir Contacto", true);
		this.parent = parent;
		this.telefonoInicial = telefonoInicial;
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

		int iconWidth = 48;
		int iconHeight = 56;
		ImageIcon iconoAddContacto = new ImageIcon("resources/add-contacto.png");
		Image imagenAddContacto = iconoAddContacto.getImage().getScaledInstance(iconWidth, iconHeight,
				Image.SCALE_SMOOTH);
		JLabel lblIcono = new JLabel(new ImageIcon(imagenAddContacto));
		lblIcono.setVerticalAlignment(SwingConstants.TOP);
		lblIcono.setBackground(colorFondo);
		lblIcono.setOpaque(true);

		JPanel panelTextos = new JPanel(new GridLayout(2, 1, 0, 10));
		panelTextos.setBackground(colorFondo);

		textoNombre = new JTextField("nombre");
		textoNombre.setForeground(Color.GRAY);
		addPlaceholderBehavior(textoNombre, "nombre");

		textoTelefono = new JTextField("teléfono");
		if (telefonoInicial != null && !telefonoInicial.isEmpty()) {
			textoTelefono.setText(telefonoInicial);
			textoTelefono.setForeground(Color.BLACK);
		} else {
			textoTelefono.setForeground(Color.GRAY);
			addPlaceholderBehavior(textoTelefono, "teléfono");
		}

		panelTextos.add(textoNombre);
		panelTextos.add(textoTelefono);

		panelCampos.add(lblIcono, BorderLayout.WEST);
		panelCampos.add(panelTextos, BorderLayout.CENTER);

		add(panelCampos, BorderLayout.CENTER);
	}

	private void panelBoton() {
		panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(this);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == botonAceptar) {
			String valorTelefono = textoTelefono.getText().trim();
			String valorNombre = textoNombre.getText().trim();

			if (valorTelefono.isEmpty() || valorNombre.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Por favor, rellene todos los campos.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			Usuario usuarioConTelefono = Controlador.INSTANCE.getUsuarioPorTelefono(valorTelefono);
			if (usuarioConTelefono == null) {
				JOptionPane.showMessageDialog(this, "No existe un usuario con el teléfono " + valorTelefono, "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				if (Controlador.INSTANCE.registrarContacto(valorNombre, valorTelefono,
						Controlador.INSTANCE.getUsuarioActual())) {
					JOptionPane.showMessageDialog(this,
							"Contacto añadido correctamente: " + valorNombre + " (" + valorTelefono + ")", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
					if (parent instanceof VentanaContactos) {
						((VentanaContactos) parent).refrescarContactos();
					}
					dispose();
				} else {
					JOptionPane.showMessageDialog(this,
							"Ya existe un contacto con el teléfono " + valorTelefono + " o el nombre " + valorNombre,
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	}
}