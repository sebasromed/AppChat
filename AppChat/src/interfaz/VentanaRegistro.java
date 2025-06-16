package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.*;

import controlador.Controlador;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.stream.Stream;
import java.awt.Component;
import com.toedter.calendar.JDateChooser;

public class VentanaRegistro {
	private JFrame ventanaReg;
	private JPanel panel, panelBotones;
	private JTextField textNombre, textApellidos, textTelefono, textPassword, textPassword2, textSaludo;
	private JLabel nombre, apellidos, telefono, password, password2, 
						fecha, saludo, imagen;
	private FotoPerfil iconoImagen;
	private JButton botonCancelar, botonAceptar;
	private Controlador controlador;
	private JDateChooser fechaChooser;
	private JTextField textImagen;
	private JButton botonImagen;
	
	public VentanaRegistro(Controlador controlador) {
		this.controlador = controlador;
		inicializar();
		panelBotones();
	}
	
	private void inicializar(){
		ventanaReg = new JFrame();
		ventanaReg.setResizable(false);
		ventanaReg.setSize(new Dimension(700, 435));
		
		ventanaReg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventanaReg.getContentPane().setLayout(new BoxLayout(ventanaReg.getContentPane(), BoxLayout.X_AXIS));
		
		panel = new JPanel();
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		ventanaReg.getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		nombre = new JLabel("nombre: ");
		GridBagConstraints gbc_nombre = new GridBagConstraints();
		gbc_nombre.ipady = 30;
		gbc_nombre.insets = new Insets(0, 0, 5, 15);
		gbc_nombre.anchor = GridBagConstraints.EAST;
		gbc_nombre.gridx = 0;
		gbc_nombre.gridy = 0;
		panel.add(nombre, gbc_nombre);
		
		textNombre = new JTextField();
		GridBagConstraints gbc_textNombre = new GridBagConstraints();
		gbc_textNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNombre.gridwidth = 3;
		gbc_textNombre.insets = new Insets(15, 0, 5, 15);
		gbc_textNombre.anchor = GridBagConstraints.WEST;
		gbc_textNombre.gridx = 1;
		gbc_textNombre.gridy = 0;
		panel.add(textNombre, gbc_textNombre);
		textNombre.setColumns(10);
		
		apellidos = new JLabel("apellidos:");
		GridBagConstraints gbc_apellidos = new GridBagConstraints();
		gbc_apellidos.ipady = 30;
		gbc_apellidos.anchor = GridBagConstraints.EAST;
		gbc_apellidos.insets = new Insets(0, 0, 5, 15);
		gbc_apellidos.gridx = 0;
		gbc_apellidos.gridy = 1;
		panel.add(apellidos, gbc_apellidos);
		
		textApellidos = new JTextField();
		GridBagConstraints gbc_textApellidos = new GridBagConstraints();
		gbc_textApellidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_textApellidos.gridwidth = 3;
		gbc_textApellidos.anchor = GridBagConstraints.WEST;
		gbc_textApellidos.insets = new Insets(0, 0, 5, 15);
		gbc_textApellidos.gridx = 1;
		gbc_textApellidos.gridy = 1;
		panel.add(textApellidos, gbc_textApellidos);
		textApellidos.setColumns(10);
		
		telefono = new JLabel("telefono:");
		GridBagConstraints gbc_telefono = new GridBagConstraints();
		gbc_telefono.ipady = 30;
		gbc_telefono.anchor = GridBagConstraints.EAST;
		gbc_telefono.insets = new Insets(0, 0, 5, 15);
		gbc_telefono.gridx = 0;
		gbc_telefono.gridy = 2;
		panel.add(telefono, gbc_telefono);
		
		textTelefono = new JTextField();
		GridBagConstraints gbc_textTelefono = new GridBagConstraints();
		gbc_textTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_textTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_textTelefono.gridx = 1;
		gbc_textTelefono.gridy = 2;
		panel.add(textTelefono, gbc_textTelefono);
		textTelefono.setColumns(10);
		
		password = new JLabel("contraseña:");
		GridBagConstraints gbc_password = new GridBagConstraints();
		gbc_password.ipady = 30;
		gbc_password.anchor = GridBagConstraints.EAST;
		gbc_password.insets = new Insets(0, 5, 5, 15);
		gbc_password.gridx = 0;
		gbc_password.gridy = 3;
		panel.add(password, gbc_password);
		
		textPassword = new JPasswordField();
		GridBagConstraints gbc_textPassword = new GridBagConstraints();
		gbc_textPassword.insets = new Insets(0, 0, 5, 5);
		gbc_textPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPassword.gridx = 1;
		gbc_textPassword.gridy = 3;
		panel.add(textPassword, gbc_textPassword);
		textPassword.setColumns(10);
		
		password2 = new JLabel("contraseña:");
		GridBagConstraints gbc_password2 = new GridBagConstraints();
		gbc_password2.anchor = GridBagConstraints.EAST;
		gbc_password2.insets = new Insets(0, 10, 5, 20);
		gbc_password2.gridx = 2;
		gbc_password2.gridy = 3;
		panel.add(password2, gbc_password2);
		
		textPassword2 = new JPasswordField();
		GridBagConstraints gbc_textPassword2 = new GridBagConstraints();
		gbc_textPassword2.insets = new Insets(0, 0, 5, 15);
		gbc_textPassword2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPassword2.gridx = 3;
		gbc_textPassword2.gridy = 3;
		panel.add(textPassword2, gbc_textPassword2);
		textPassword2.setColumns(10);
		
		fecha = new JLabel("fecha:");
		GridBagConstraints gbc_fecha = new GridBagConstraints();
		gbc_fecha.ipady = 30;
		gbc_fecha.anchor = GridBagConstraints.EAST;
		gbc_fecha.insets = new Insets(0, 0, 5, 15);
		gbc_fecha.gridx = 0;
		gbc_fecha.gridy = 4;
		panel.add(fecha, gbc_fecha);
		
		fechaChooser = new JDateChooser();
		fechaChooser.setDateFormatString("dd-MM-yyyy");
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.gridx = 1;
		gbc_dateChooser.gridy = 4;
		panel.add(fechaChooser, gbc_dateChooser);
		
		imagen = new JLabel("imagen:");
		GridBagConstraints gbc_imagen = new GridBagConstraints();
		gbc_imagen.anchor = GridBagConstraints.WEST;
		gbc_imagen.insets = new Insets(0, 10, 5, 5);
		gbc_imagen.gridx = 2;
		gbc_imagen.gridy = 4;
		panel.add(imagen, gbc_imagen);
		
		try {
			String path = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";
			URL url = new URL(path);
			BufferedImage image = ImageIO.read(url);
			int diametro = 100;
		
			iconoImagen = new FotoPerfil(image, diametro);
			GridBagConstraints gbc_iconoImagen = new GridBagConstraints();
			gbc_iconoImagen.insets = new Insets(0, 0, 5, 0);
			gbc_iconoImagen.gridx = 3;
			gbc_iconoImagen.gridy = 5;
			iconoImagen.setMinimumSize(new Dimension(diametro, diametro));
			iconoImagen.setMaximumSize(new Dimension(diametro, diametro));
			iconoImagen.setPreferredSize(new Dimension(diametro, diametro));
			
			panel.add(iconoImagen, gbc_iconoImagen);
		
		} catch (Exception e) {
			//Error cargando la URL
		}
		
		textImagen = new JTextField();
		GridBagConstraints gbc_textImagen = new GridBagConstraints();
		gbc_textImagen.insets = new Insets(0, 0, 5, 15);
		gbc_textImagen.fill = GridBagConstraints.HORIZONTAL;
		gbc_textImagen.gridx = 3;
		gbc_textImagen.gridy = 4;
		panel.add(textImagen, gbc_textImagen);
		textImagen.setColumns(10);
		
		botonImagen = new JButton("Establecer");
		GridBagConstraints gbc_botonImagen = new GridBagConstraints();
		gbc_botonImagen.gridx = 3;
		gbc_botonImagen.gridy = 6;
		panel.add(botonImagen, gbc_botonImagen);
		actionEstablecer();
		
		saludo = new JLabel("saludo:");
		GridBagConstraints gbc_saludo = new GridBagConstraints();
		gbc_saludo.ipady = 30;
		gbc_saludo.anchor = GridBagConstraints.EAST;
		gbc_saludo.insets = new Insets(0, 0, 5, 15);
		gbc_saludo.gridx = 0;
		gbc_saludo.gridy = 5;
		panel.add(saludo, gbc_saludo);
		
		textSaludo = new JTextField();
		GridBagConstraints gbc_textSaludo = new GridBagConstraints();
		gbc_textSaludo.fill = GridBagConstraints.BOTH;
		gbc_textSaludo.anchor = GridBagConstraints.WEST;
		gbc_textSaludo.insets = new Insets(0, 0, 5, 5);
		gbc_textSaludo.gridx = 1;
		gbc_textSaludo.gridy = 5;
		panel.add(textSaludo, gbc_textSaludo);
		textSaludo.setColumns(10);
		
		ventanaReg.setVisible(true);
	}
	
	private void panelBotones() {
		panelBotones = new JPanel();
		GridBagConstraints gbc_panelBotones = new GridBagConstraints();
		gbc_panelBotones.insets = new Insets(0, 0, 0, 5);
		gbc_panelBotones.gridx = 1;
		gbc_panelBotones.gridy = 6;
		panel.add(panelBotones, gbc_panelBotones);
		
		botonCancelar = new JButton("Cancelar");
		panelBotones.add(botonCancelar);
		actionCancelar();
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panelBotones.add(horizontalStrut);
		
		botonAceptar = new JButton("Aceptar");
		panelBotones.add(botonAceptar);
		actionAceptar();
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
				Date valorFecha = fechaChooser.getDate(); 
				String valorSaludo = textSaludo.getText().trim();
				String valorImagen = textImagen.getText().trim();
				
				if (Stream.of(valorNombre, valorApellidos, valorTelefono, valorPassword, valorPassword2).anyMatch(v -> v.isBlank())
					|| valorFecha == null) {
					System.err.println("Campo Vacio");
					//Hay algun campo vacio y hay que mostrar error
				} else if (!valorPassword.equals(valorPassword2)) {
					System.err.println("Las contraseñas no coinciden");
					//Se han rellenado los campos de contraseñas pero no coinciden
				}
				else {
					//Si no hay saludo, establecer uno por defecto
					if (valorSaludo.isBlank()) {
						valorSaludo = "Hey there, I'm using AppChat";
					}
					if (!controlador.registrarUsuario(valorNombre, 
													  valorApellidos, 
													  valorTelefono, 
													  valorPassword, 
													  new java.sql.Date(valorFecha.getTime()), //JCalendar devuelve un objeto de tipo java.util.Date, y queremos un objeto java.sql.Date 
													  valorSaludo, 
													  valorImagen)) {
						System.out.println("OK");
						controlador.cambiarVentanaPrincipal(ventanaReg);
					}
					else {
						System.err.println("Error al registrar");
						//Error al registrarse
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
				if (valorImagen == null || valorImagen.isBlank()) { 
					valorImagen = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";
				}	
				
				try {
					URL url = new URL(valorImagen);
					BufferedImage image = ImageIO.read(url);
					iconoImagen.setImage(image);
				} catch (IOException e1) {
					// Error al cargar la URL
				}
				iconoImagen.revalidate();
				iconoImagen.repaint();
			}
		});
	}
	

}