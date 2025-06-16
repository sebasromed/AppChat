package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import controlador.Controlador;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Component;

public class VentanaContactos {

	private JFrame frame;
	private JButton añadirContacto, crearGrupo;
	private Controlador controlador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaContactos window = new VentanaContactos();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaContactos(Controlador controlador) {
		this.controlador = controlador;
		initialize();
	}
	
	public VentanaContactos() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
		frame.setPreferredSize(new Dimension(450,450));
		frame.setMaximumSize(new Dimension(450,450));
		frame.setMinimumSize(new Dimension(450,450));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new CompoundBorder( new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK, 2)), "lista contactos"));
		panel.setPreferredSize(new Dimension(450,420));
		panel.setMinimumSize(new Dimension(450, 420));
		panel.setMaximumSize(new Dimension(450, 420));
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new TitledBorder(new CompoundBorder( new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK, 2)), "lista contactos"));
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(400,350));
		panel_1.setMinimumSize(new Dimension(400, 350));
		panel_1.setMaximumSize(new Dimension(400, 350));
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		scrollPane.setBorder(new TitledBorder(new CompoundBorder( new EmptyBorder(7, 2, 5, 2), new LineBorder(Color.BLACK, 2)), null));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(180,350));
		scrollPane.setMinimumSize(new Dimension(180, 350));
		scrollPane.setMaximumSize(new Dimension(180, 350));
		
		JPanel bottonPane = new JPanel();
		bottonPane.setLayout(new BoxLayout(bottonPane, BoxLayout.Y_AXIS));
		fixSize(bottonPane, 40, 350);
		JButton b1 = new JButton();
		b1 = new JButton(new ImageIcon(getClass().getResource("/flecha.png")));
		
		JButton b2 = new JButton();
		b2 = new JButton(new ImageIcon(getClass().getResource("/flecha.png")));
		bottonPane.add(Box.createRigidArea(new Dimension(10, 100)));
		bottonPane.add(b1);
		bottonPane.add(Box.createRigidArea(new Dimension(20,10)));
		bottonPane.add(b2);
		panel_1.add(bottonPane);
		
		JScrollPane scrollGrupo = new JScrollPane();
		panel_1.add(scrollGrupo);
		scrollGrupo.setBorder(new TitledBorder(new CompoundBorder( new EmptyBorder(2, 2, 2, 2), new LineBorder(Color.BLACK, 2)), "Contactos"));
		scrollGrupo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollGrupo.setPreferredSize(new Dimension(180,350));
		scrollGrupo.setMinimumSize(new Dimension(180, 350));
		scrollGrupo.setMaximumSize(new Dimension(180, 350));
		
		JPanel panel_botones = new JPanel();
		panel.add(panel_botones);
		fixSize(panel_botones, 400, 30);
		panel_botones.setLayout(new BoxLayout(panel_botones, BoxLayout.X_AXIS));
		
		añadirContacto = new JButton("añadir contacto");
		crearGrupo = new JButton("crear grupo");
		
		panel_botones.add(Box.createRigidArea(new Dimension(30, 10)));
		panel_botones.add(añadirContacto);
		panel_botones.add(Box.createRigidArea(new Dimension(110, 10)));
		panel_botones.add(crearGrupo);
		
		
		
		/* Panel principal*/
		
	}
	
	private void actionAñadirContacto() {
		añadirContacto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				controlador.cambiarVentanaAñadirContacto();
				}
		});
	}
	
	private void actionCrearGrupo() {
		
	}
	

	
	private void fixSize(JPanel p, int x, int y) {
		p.setMaximumSize(new Dimension(x,y));
		p.setMinimumSize(new Dimension(x,y));
		p.setPreferredSize(new Dimension(x,y));
	}

}
