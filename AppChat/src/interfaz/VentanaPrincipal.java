package interfaz;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.awt.*;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.TitledBorder;
import java.util.List;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Mensaje;
import tds.BubbleText;
import controlador.Controlador;

public class VentanaPrincipal extends JFrame implements ActionListener {

	private JPanel contenedor;
	private JButton contactos, premium, buscarMensajes, logout, exportarConversacion;
	private JPanel panelChat, panelMensajes;
	private JList<Contacto> listaContactos;

	public VentanaPrincipal() {
		initialize();
		setVisible(true);
	}

	private void initialize() {
		setSize(new Dimension(900, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		JPanel panel_superior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		panel_superior.setPreferredSize(new Dimension(900, 70));
		panel_superior.setBorder(new TitledBorder(null, null, TitledBorder.LEADING, TitledBorder.TOP, null, null));

		int diametro = 50;
		FotoPerfil fotoPerfil = new FotoPerfil(Controlador.INSTANCE.getUsuarioActual().getImagen(), diametro);
		panel_superior.add(fotoPerfil);

		contactos = new JButton("Contactos");
		contactos.setPreferredSize(new Dimension(150, 40));
		contactos.setMargin(new Insets(2, 2, 2, 2));
		contactos.addActionListener(this);
		panel_superior.add(contactos);

		premium = new JButton("Premium");
		premium.addActionListener(this);
		panel_superior.add(premium);

		buscarMensajes = new JButton("Buscar Mensajes");
		buscarMensajes.addActionListener(this);
		panel_superior.add(buscarMensajes);

		logout = new JButton("Logout");
		logout.addActionListener(this);
		panel_superior.add(logout);
		
		exportarConversacion = new JButton("Exportar conversacion");
	    exportarConversacion.setEnabled(false);
	    exportarConversacion.addActionListener(e -> exportarConversacion());
	    panel_superior.add(exportarConversacion);

		ImageIcon icon = null;
		if (Controlador.INSTANCE.getUsuarioActual().isPremium()) {
			try {
				BufferedImage imagenPremium = ImageIO.read(getClass().getResource("/premium.png"));
				int w = 30, h = 30;
				Image scaled = imagenPremium.getScaledInstance(w, h, Image.SCALE_SMOOTH);
				icon = new ImageIcon(scaled);
			} catch (Exception ex) {
				ex.printStackTrace();
				icon = null;
			}
		}
		JLabel iconoPremium = new JLabel(icon);
		iconoPremium.setToolTipText("Usuario Premium");
		panel_superior.add(iconoPremium);

		getContentPane().add(panel_superior, BorderLayout.NORTH);

		List<Contacto> contactosList = Controlador.INSTANCE.getContactosOrdenadosCronologicamente();
		listaContactos = new JList<>(contactosList.toArray(new Contacto[0]));
		listaContactos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaContactos.setBackground(Color.WHITE);

		// Para que muestre el Panel Detallado de cada contacto
		listaContactos.setCellRenderer(new ListCellRenderer<Contacto>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends Contacto> list, Contacto contacto, int index,
					boolean isSelected, boolean cellHasFocus) {
				ContactoPanelDetallado panel = new ContactoPanelDetallado(contacto);
				panel.setBackground(isSelected ? new Color(200, 230, 255) : Color.WHITE);
				panel.setOpaque(true);

				if (contacto.getNombre().startsWith("+")) {
					JPanel wrapper = new JPanel();
					wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
					wrapper.setBackground(panel.getBackground());
					wrapper.setOpaque(true);

					wrapper.add(panel);

					JPanel panelBoton = new JPanel();
					panelBoton.setLayout(new BoxLayout(panelBoton, BoxLayout.X_AXIS));
					panelBoton.setOpaque(false);

					JButton addButton = new JButton("Añadir");
					addButton.setPreferredSize(new Dimension(80, 28));
					addButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
					addButton.setAlignmentX(Component.LEFT_ALIGNMENT);

					panelBoton.add(addButton);
					panelBoton.add(Box.createHorizontalGlue());
					wrapper.add(Box.createVerticalStrut(4));
					wrapper.add(panelBoton);

					return wrapper;
				} else {
					return panel;
				}
			}
		});
		listaContactos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = listaContactos.locationToIndex(e.getPoint());
				if (index >= 0) {
					Contacto contacto = listaContactos.getModel().getElementAt(index);
					if (contacto.getNombre().startsWith("+")) {
						Rectangle cellBounds = listaContactos.getCellBounds(index, index);
						// Estimate button area (adjust as needed)
						int buttonHeight = 28;
						int buttonY = cellBounds.y + cellBounds.height - buttonHeight;
						if (e.getY() >= buttonY && e.getY() <= buttonY + buttonHeight) {
							new VentanaAddContacto(VentanaPrincipal.this,
									((ContactoIndividual) contacto).getTelefono());
						}
					}
				}
			}
		});

		JScrollPane scrollContactos = new JScrollPane(listaContactos);
		scrollContactos.setPreferredSize(new Dimension(220, 0));

		panelChat = new JPanel(new BorderLayout());
		panelChat.setBackground(Controlador.VERDE_CLARO);

		mostrarPanelChatPorDefecto();

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollContactos, panelChat);
		splitPane.setDividerLocation(220);
		getContentPane().add(splitPane, BorderLayout.CENTER);

		listaContactos.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Contacto seleccionado = listaContactos.getSelectedValue();
				panelChat.removeAll();
				if (seleccionado != null) {
					exportarConversacion.setEnabled(seleccionado != null);
					panelChat.setLayout(new BorderLayout());
					panelMensajes = new PanelChat(VentanaPrincipal.this, seleccionado);
					panelChat.add(panelMensajes, BorderLayout.CENTER);
					panelChat.revalidate();
					panelChat.repaint();
					cargarChat(seleccionado);
				} else {
					mostrarPanelChatPorDefecto();
				}

			}
		});
	}

	private void mostrarPanelChatPorDefecto() {
		panelChat.removeAll();
		JLabel label = new JLabel("Por favor seleccione un chat para ver sus mensajes", SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.PLAIN, 18));
		label.setForeground(Color.DARK_GRAY);
		panelChat.setLayout(new GridBagLayout());
		panelChat.add(label, new GridBagConstraints());
		panelChat.revalidate();
		panelChat.repaint();
	}

	private void cargarChat(Contacto contactoSeleccionado) {
		SwingUtilities.invokeLater(() -> {
			List<Mensaje> mensajes = Controlador.INSTANCE.getMensajesDeContacto(contactoSeleccionado);
			((PanelChat) panelMensajes).cargarChat(mensajes);
		});
	}

	public void actualizarListaContactos() {
		Contacto selected = listaContactos.getSelectedValue();
		List<Contacto> contactosList = Controlador.INSTANCE.getContactosOrdenadosCronologicamente();
		listaContactos.setListData(contactosList.toArray(new Contacto[0]));
		if (contactosList.isEmpty()) {
			mostrarPanelChatPorDefecto();
		} else if (selected != null) {
			int idx = contactosList.indexOf(selected);
			if (idx >= 0) {
				listaContactos.setSelectedIndex(idx);
			} else {
				listaContactos.setSelectedIndex(0);
			}
		} else {
			listaContactos.setSelectedIndex(0);
		}
	}

	public void seleccionarChat(Contacto contacto) {
		listaContactos.setSelectedValue(contacto, true);
	}
	
	private void exportarConversacion() {
	    Contacto contacto = listaContactos.getSelectedValue();
	    if (contacto == null || !(contacto instanceof ContactoIndividual)) {
	        JOptionPane.showMessageDialog(this, "Seleccione un chat primero o un chat individual");
	        return;
	    }
	    List<Mensaje> mensajes = Controlador.INSTANCE.getMensajesDeContacto(contacto);
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setDialogTitle("Guardar PDF de la conversación");
	    fileChooser.setSelectedFile(new java.io.File("conversacion.pdf"));
	    int userSelection = fileChooser.showSaveDialog(this);
	    if (userSelection != JFileChooser.APPROVE_OPTION) return;
	    java.io.File fileToSave = fileChooser.getSelectedFile();

	    try {
	        Document document = new Document();
	        PdfWriter.getInstance(document, new java.io.FileOutputStream(fileToSave));
	        document.open();

	        // Title
	        document.add(new Paragraph("Conversación con: " + contacto.getNombre(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
	        document.add(new Paragraph(" "));

	        for (Mensaje m : mensajes) {
	            String autor = m.getEmisor().getNombre();
	            String fecha = m.getFechaEnvio().toString();
	            String texto = m.getTexto() != null ? m.getTexto() : "[Emoji]";
	            document.add(new Paragraph(autor + " (" + fecha + "):"));
	            document.add(new Paragraph(texto));
	            document.add(new Paragraph(" "));
	        }
	        document.close();
	        JOptionPane.showMessageDialog(this, "Conversación exportada correctamente.");
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error al exportar la conversación.");
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == contactos) {
			Controlador.cambiarVentana(this, VentanaContactos::new);
		}
		if (src == buscarMensajes) {
			Controlador.cambiarVentana(this, VentanaBusquedaMensajes::new);
		}
		if (src == logout) {
			Controlador.INSTANCE.setUsuarioActual(null);
			Controlador.cambiarVentana(this, VentanaLogin::new);
		}
		if (src == premium) {
			boolean esPremium = Controlador.INSTANCE.getUsuarioActual().isPremium();
			if (esPremium) {
				int res = JOptionPane.showConfirmDialog(this, "¿Quieres cancelar tu suscripción premium?",
						"Cancelar Premium", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (res == JOptionPane.YES_OPTION) {
					Controlador.INSTANCE.cancelarPremium();
					JOptionPane.showMessageDialog(this, "Suscripción premium cancelada.");

				}
			} else {
				double precio = Controlador.INSTANCE.calcularPrecioPremium();
				int res = JOptionPane.showConfirmDialog(this,
						"¿Quieres convertirte en premium?\nPrecio a pagar: " + precio, "Activar Premium",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (res == JOptionPane.YES_OPTION) {
					Controlador.INSTANCE.activarPremium();
					JOptionPane.showMessageDialog(this, "¡Ahora eres premium!");

				}
			}
		}
	}
}