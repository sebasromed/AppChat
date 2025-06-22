package interfaz;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import controlador.Controlador;
import modelo.Mensaje;

public class VentanaBusquedaMensajes extends JFrame implements ActionListener {

	private static final String PLACEHOLDER_BUSCAR = "Texto del mensaje";
	private static final String PLACEHOLDER_TELEFONO = "Teléfono del contacto";
	private static final String PLACEHOLDER_CONTACTO = "Nombre del contacto";

	private JPanel panelCentral, panelIconos, panelBusqueda, panelMensajes;
	private JTextField campoContacto, campoTelefono, campoTextoBuscar;
	private JButton botonBuscar;
	private BufferedImage imagenLupa;
	private ImageIcon iconoLupa;

	public VentanaBusquedaMensajes() {
		inicializar();
		setVisible(true);
	}

	private void inicializar() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Buscar Mensajes");
		setBounds(100, 100, 811, 540);
		setLocationRelativeTo(null);

		panelCentral = new JPanel();
		panelCentral.setBackground(Controlador.VERDE_CLARO);
		panelCentral.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		setContentPane(panelCentral);

		cargarIconoLupa();

		crearPanelIconos();
		crearPanelBusqueda();
		crearPanelMensajes();
	}

	private void cargarIconoLupa() {
		try {
			imagenLupa = ImageIO.read(getClass().getResource("/icono-lupa.png"));
			int w = 20, h = 20;
			Image scaled = imagenLupa.getScaledInstance(w, h, Image.SCALE_SMOOTH);
			iconoLupa = new ImageIcon(scaled);
		} catch (Exception ex) {
			ex.printStackTrace();
			iconoLupa = null;
		}
	}

	private void crearPanelIconos() {
		panelIconos = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panelIconos.setBackground(Controlador.VERDE_CLARO);
		panelIconos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
		panelIconos.setPreferredSize(new Dimension(10, 36));
		JLabel labelLupa = new JLabel();
		labelLupa.setPreferredSize(new Dimension(20, 20));
		labelLupa.setIcon(iconoLupa);
		panelIconos.add(labelLupa);
		panelCentral.add(panelIconos);
	}

	private void crearPanelBusqueda() {
		panelBusqueda = new JPanel();
		panelBusqueda.setBackground(Controlador.VERDE_CLARO);
		// Use a LineBorder with VERDE_MEDIO for the entire border
		TitledBorder border = new TitledBorder(new LineBorder(Controlador.VERDE_MEDIO, 2), "Buscar",
				TitledBorder.LEADING, TitledBorder.TOP, null, Controlador.VERDE_MEDIO);
		panelBusqueda.setBorder(border);
		panelCentral.add(panelBusqueda);

		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		gbl.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelBusqueda.setLayout(gbl);

		JLabel lupaBuscar = new JLabel();
		lupaBuscar.setPreferredSize(new Dimension(20, 20));
		lupaBuscar.setIcon(iconoLupa);
		GridBagConstraints gbc_lupaBuscar = new GridBagConstraints();
		gbc_lupaBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_lupaBuscar.anchor = GridBagConstraints.EAST;
		gbc_lupaBuscar.gridx = 1;
		gbc_lupaBuscar.gridy = 0;
		panelBusqueda.add(lupaBuscar, gbc_lupaBuscar);

		campoTextoBuscar = new JTextField(PLACEHOLDER_BUSCAR);
		campoTextoBuscar.setColumns(10);
		addPlaceholderBehavior(campoTextoBuscar, PLACEHOLDER_BUSCAR);
		GridBagConstraints gbc_campoTextoBuscar = new GridBagConstraints();
		gbc_campoTextoBuscar.gridwidth = 14;
		gbc_campoTextoBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_campoTextoBuscar.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoTextoBuscar.gridx = 2;
		gbc_campoTextoBuscar.gridy = 0;
		panelBusqueda.add(campoTextoBuscar, gbc_campoTextoBuscar);

		JLabel lupaTelefono = new JLabel();
		lupaTelefono.setPreferredSize(new Dimension(20, 20));
		lupaTelefono.setIcon(iconoLupa);
		GridBagConstraints gbc_lupaTelefono = new GridBagConstraints();
		gbc_lupaTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lupaTelefono.anchor = GridBagConstraints.EAST;
		gbc_lupaTelefono.gridx = 1;
		gbc_lupaTelefono.gridy = 1;
		panelBusqueda.add(lupaTelefono, gbc_lupaTelefono);

		campoTelefono = new JTextField(PLACEHOLDER_TELEFONO);
		campoTelefono.setColumns(10);
		addPlaceholderBehavior(campoTelefono, PLACEHOLDER_TELEFONO);
		GridBagConstraints gbc_campoTelefono = new GridBagConstraints();
		gbc_campoTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_campoTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoTelefono.gridx = 2;
		gbc_campoTelefono.gridy = 1;
		panelBusqueda.add(campoTelefono, gbc_campoTelefono);

		JLabel lupaContacto = new JLabel();
		lupaContacto.setPreferredSize(new Dimension(20, 20));
		lupaContacto.setIcon(iconoLupa);
		GridBagConstraints gbc_lupaContacto = new GridBagConstraints();
		gbc_lupaContacto.insets = new Insets(0, 0, 5, 5);
		gbc_lupaContacto.anchor = GridBagConstraints.EAST;
		gbc_lupaContacto.gridx = 4;
		gbc_lupaContacto.gridy = 1;
		panelBusqueda.add(lupaContacto, gbc_lupaContacto);

		campoContacto = new JTextField(PLACEHOLDER_CONTACTO);
		campoContacto.setColumns(10);
		addPlaceholderBehavior(campoContacto, PLACEHOLDER_CONTACTO);
		GridBagConstraints gbc_campoContacto = new GridBagConstraints();
		gbc_campoContacto.gridwidth = 6;
		gbc_campoContacto.insets = new Insets(0, 0, 5, 5);
		gbc_campoContacto.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoContacto.gridx = 5;
		gbc_campoContacto.gridy = 1;
		panelBusqueda.add(campoContacto, gbc_campoContacto);

		botonBuscar = new JButton("Buscar");
		botonBuscar.setFont(new Font("Liberation Mono", Font.BOLD, 13));
		botonBuscar.addActionListener(this);
		GridBagConstraints gbc_botonBuscar = new GridBagConstraints();
		gbc_botonBuscar.gridwidth = 2;
		gbc_botonBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_botonBuscar.gridx = 14;
		gbc_botonBuscar.gridy = 1;
		panelBusqueda.add(botonBuscar, gbc_botonBuscar);
	}

	private void crearPanelMensajes() {
		panelMensajes = new JPanel();
		panelMensajes.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelMensajes.setBackground(Controlador.VERDE_CLARO);
		panelMensajes.setLayout(new BorderLayout(0, 0));
		panelCentral.add(panelMensajes);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(210, 233, 255));
		panelMensajes.add(scrollPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == botonBuscar) {
			String texto = campoTextoBuscar.getText().equals(PLACEHOLDER_BUSCAR) ? "" : campoTextoBuscar.getText();
			String tel = campoTelefono.getText().equals(PLACEHOLDER_TELEFONO) ? "" : campoTelefono.getText();
			String contacto = campoContacto.getText().equals(PLACEHOLDER_CONTACTO) ? "" : campoContacto.getText();
			realizarBusqueda(texto, tel, contacto);
		}
	}

	private void realizarBusqueda(String texto, String telefono, String nombreContacto) {
		List<Mensaje> resultados = Controlador.INSTANCE.buscarMensajes(texto, telefono, nombreContacto);
		mostrarResultados(resultados);
	}

	private void mostrarResultados(List<Mensaje> mensajes) {
		DefaultListModel<Mensaje> modelo = new DefaultListModel<>();
		for (Mensaje mensaje : mensajes) {
			modelo.addElement(mensaje);
		}
		JList<Mensaje> listaMensajes = new JList<>(modelo);
		listaMensajes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					int index = listaMensajes.locationToIndex(e.getPoint());
					if (index >= 0) {
						Mensaje mensaje = listaMensajes.getModel().getElementAt(index);
						// Open main window and select the chat
						Controlador.cambiarVentana(VentanaBusquedaMensajes.this, () -> {
							VentanaPrincipal vp = new VentanaPrincipal();
							vp.seleccionarChat(mensaje.getReceptor());
							return vp;
						});
					}
				}
			}
		});
		listaMensajes.setCellRenderer(new BusquedaCellRenderer());
		JScrollPane scrollPane = new JScrollPane(listaMensajes);
		panelMensajes.removeAll();
		panelMensajes.add(scrollPane, BorderLayout.CENTER);
		panelMensajes.revalidate();
		panelMensajes.repaint();
	}

	private void addPlaceholderBehavior(JTextField textField, String placeholderText) {
		textField.setForeground(Color.GRAY);
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textField.getText().equals(placeholderText)) {
					textField.setText("");
					textField.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (textField.getText().isEmpty()) {
					textField.setText(placeholderText);
					textField.setForeground(Color.GRAY);
				}
			}
		});
	}

	private static class BusquedaCellRenderer extends JPanel implements ListCellRenderer<Mensaje> {
		private static final long serialVersionUID = 1L;
		private JLabel emisor;
		private JLabel receptor;
		private JTextArea mensajeTexto;

		public BusquedaCellRenderer() {
			emisor = new JLabel();
			receptor = new JLabel();
			mensajeTexto = new JTextArea();
			mensajeTexto.setWrapStyleWord(true);
			mensajeTexto.setLineWrap(true);
			mensajeTexto.setOpaque(false);
			mensajeTexto.setEditable(false);
			mensajeTexto.setFont(new Font("SansSerif", Font.PLAIN, 12));

			setLayout(new BorderLayout(10, 10));
			setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			JPanel header = new JPanel(new GridLayout(1, 2, 10, 0));
			header.setOpaque(false);
			emisor.setFont(new Font("SansSerif", Font.BOLD, 14));
			receptor.setFont(new Font("SansSerif", Font.ITALIC, 12));
			emisor.setForeground(new Color(34, 139, 34));
			receptor.setForeground(new Color(70, 130, 180));

			header.add(emisor);
			header.add(receptor);

			add(header, BorderLayout.NORTH);
			add(mensajeTexto, BorderLayout.CENTER);
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends Mensaje> list, Mensaje mensaje, int index,
				boolean isSelected, boolean cellHasFocus) {
			emisor.setText("Emisor: " + mensaje.getEmisor().getNombreCompleto());
			receptor.setText("Receptor: " + mensaje.getReceptor().getNombre());
			mensajeTexto.setText(mensaje.getTexto());

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			return this;
		}
	}

}