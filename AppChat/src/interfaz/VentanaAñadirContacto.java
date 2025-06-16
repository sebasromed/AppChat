package interfaz;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class VentanaAñadirContacto {

	private JFrame frame;
	private JTextField txtTelfono;
	private JTextField txtNombre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAñadirContacto window = new VentanaAñadirContacto();
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
	public VentanaAñadirContacto() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(new Dimension(450,200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\alfon\\OneDrive - UNIVERSIDAD DE MURCIA\\Universidad\\4º Año\\TDS\\Practicas\\workSpace\\MavenTDSChat\\src\\main\\resources\\alerta.png"));
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Introduzca el nombre del contacto y su teléfono");
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		panel_1.add(Box.createRigidArea(new Dimension(20,10)));
		
		txtNombre = new JTextField();
		txtNombre.setText("nombre");
		fixSize(txtNombre, 300, 20);
		panel_1.add(txtNombre);
		txtNombre.setColumns(10);
		
		panel_1.add(Box.createRigidArea(new Dimension(20,7)));
		
		txtTelfono = new JTextField();
		txtTelfono.setText("teléfono");
		fixSize(txtTelfono, 300, 20);
		panel_1.add(txtTelfono);
		txtTelfono.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new FlowLayout());
		frame.getContentPane().add(panel_2, BorderLayout.SOUTH);
		
		
		JButton btnNewButton = new JButton("Aceptar");
		panel_2.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancelar");
		panel_2.add(btnNewButton_1);
	}
	
	private void fixSize(JComponent c, int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}

}
