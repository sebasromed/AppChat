package interfaz;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controlador.Controlador;
import modelo.Contacto;
import modelo.Mensaje;
import tds.BubbleText;

import java.util.List;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelChat extends JPanel implements ActionListener {
	private JPanel panelMensajes;
	private JScrollPane scrollMensajes, scrollAreaTexto;
	private JPanel panelEnvioMensajes;
	private JTextField textoMensajeAEnviar;
	private JButton botonEmojis, botonEnviar;

	private VentanaPrincipal ventanaPrincipal;

	private Contacto contactoSeleccionado;

	public PanelChat(VentanaPrincipal ventanaPrincipal, Contacto contactoSeleccionado) {
		this.ventanaPrincipal = ventanaPrincipal;
		this.contactoSeleccionado = contactoSeleccionado;

		setLayout(new BorderLayout());

		panelMensajes = new JPanel();
		panelMensajes.setLayout(new BoxLayout(panelMensajes, BoxLayout.Y_AXIS));
		panelMensajes.setBackground(Controlador.VERDE_CLARO);

		scrollMensajes = new JScrollPane(panelMensajes);
		scrollMensajes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollMensajes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar barraScroll = scrollMensajes.getVerticalScrollBar();
		barraScroll.setValue(barraScroll.getMaximum());

		panelEnvioMensajes = new JPanel(new BorderLayout(5, 5));
		panelEnvioMensajes.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panelEnvioMensajes.setBackground(Controlador.VERDE_CLARO);

		botonEmojis = new JButton("\u263A"); // Carita feliz para los emojis :)
		botonEmojis.setFont(new Font("Arial", Font.PLAIN, 28));
		botonEmojis.addActionListener(this);
		panelEnvioMensajes.add(botonEmojis, BorderLayout.WEST);

		textoMensajeAEnviar = new JTextField(30);
		textoMensajeAEnviar.setFont(new Font("Arial", Font.PLAIN, 14));
		panelEnvioMensajes.add(textoMensajeAEnviar, BorderLayout.CENTER);

		botonEnviar = new JButton("\u2192"); // Flecha hacia la derecha -> para enviar
		botonEnviar.setFont(new Font("Arial", Font.PLAIN, 28));
		botonEnviar.addActionListener(this);
		panelEnvioMensajes.add(botonEnviar, BorderLayout.EAST);

		add(scrollMensajes, BorderLayout.CENTER);
		add(panelEnvioMensajes, BorderLayout.SOUTH);

		panelMensajes.revalidate();
		panelMensajes.repaint();

	}

	private void mostrarMenuEmojis() {
		JPopupMenu menuEmoticonos = new JPopupMenu();
		int emojisPerRow = 5;
		int totalEmojis = BubbleText.MAXICONO;
		int rows = (int) Math.ceil((double) totalEmojis / emojisPerRow);

		JPanel panelGrid = new JPanel(new GridLayout(rows, emojisPerRow, 2, 2));
		for (int i = 0; i < totalEmojis; i++) {
			JLabel iconoEmoji = new JLabel(BubbleText.getEmoji(i), SwingConstants.CENTER);
			iconoEmoji.setName(Integer.toString(i));
			iconoEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
			iconoEmoji.setOpaque(true);
			iconoEmoji.setBackground(Color.WHITE);
			iconoEmoji.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					enviarEmoji(Integer.parseInt(iconoEmoji.getName()), contactoSeleccionado);
					menuEmoticonos.setVisible(false);
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					iconoEmoji.setBackground(new Color(220, 220, 220));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					iconoEmoji.setBackground(Color.WHITE);
				}
			});
			panelGrid.add(iconoEmoji);
		}
		menuEmoticonos.add(panelGrid);
		menuEmoticonos.show(botonEmojis, 0, botonEmojis.getHeight());
	}

	public void enviarEmoji(int emoticono, Contacto contacto) {
		if (contacto == null) {
			JOptionPane.showMessageDialog(this, "Por favor, seleccione un contacto para enviar el mensaje.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (emoticono == -1) {
			JOptionPane.showMessageDialog(this, "El mensaje no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			Controlador.INSTANCE.enviarMensaje(contacto, emoticono);
			ventanaPrincipal.actualizarListaContactos();
		}
	}

	public void enviarTexto(String texto, Contacto contacto) {
		if (contacto == null) {
			JOptionPane.showMessageDialog(this, "Por favor, seleccione un contacto para enviar el mensaje.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (texto.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "El mensaje no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			Controlador.INSTANCE.enviarMensaje(contacto, texto);
			ventanaPrincipal.actualizarListaContactos();
		}
	}

	public void cargarChat(List<Mensaje> mensajes) {
		panelMensajes.removeAll();
		for (Mensaje mensaje : mensajes) {
			BubbleText burbuja;
			String emisor;
			Color colorBurbuja;
			int direccion;

			if (mensaje.getEmisor().equals(Controlador.INSTANCE.getUsuarioActual())) {
				colorBurbuja = Controlador.VERDE_MEDIO;
				emisor = "Tú";
				direccion = BubbleText.SENT;
			} else {
				colorBurbuja = Color.LIGHT_GRAY; // Mensajes recibidos
				emisor = contactoSeleccionado.getNombre();
				direccion = BubbleText.RECEIVED;
			}

			// Si el texto está vacío, es un emoji
			if (mensaje.getTexto().isEmpty()) {
				burbuja = new BubbleText(panelMensajes, mensaje.getIdEmoji(), colorBurbuja, emisor, direccion, 12);
			} else {
				burbuja = new BubbleText(panelMensajes, mensaje.getTexto(), colorBurbuja, emisor, direccion);
			}

			int maxWidth = scrollMensajes.getViewport().getWidth() - 20; // Padding para que la barra de scroll no corte
																			// los mensajes
			burbuja.setMaximumSize(new java.awt.Dimension(maxWidth, Integer.MAX_VALUE));
			burbuja.setPreferredSize(new java.awt.Dimension(maxWidth, burbuja.getPreferredSize().height));
			panelMensajes.add(burbuja);

		}
		panelMensajes.revalidate();
		panelMensajes.repaint();
	}

	public void clearMessages() {
		panelMensajes.removeAll();
		panelMensajes.revalidate();
		panelMensajes.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == botonEnviar) {
			String textoMensaje = textoMensajeAEnviar.getText().trim();
			enviarTexto(textoMensaje, contactoSeleccionado);
			textoMensajeAEnviar.setText("");
		} else if (src == botonEmojis) {
			mostrarMenuEmojis();
		}
	}
}
