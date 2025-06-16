package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;

import controlador.Controlador;

import java.awt.Color;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class VentanaBuscar {

	private JFrame frame;
	private JTextField textField;
	private JTextField txtTelefono;
	private JTextField txtContacto;
	private JButton buscar;
	private Controlador controlador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaBuscar window = new VentanaBuscar();
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
	public VentanaBuscar() {
		initialize();
	}
	
	public VentanaBuscar(Controlador controlador) {
		this.controlador = controlador;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		//frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(400,400));
		frame.setMaximumSize(new Dimension(400,400));
		frame.setMinimumSize(new Dimension(400,400));
		frame.setResizable(false);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("New label");
		panel.add(lblNewLabel);
		
		/*Panel 1*/
		panelBuscar();
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new CompoundBorder( new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK, 2)));
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane);
		
		frame.setVisible(true);
	}

	
	private void panelBuscar() {
		JPanel panel_1 = new JPanel();
		fixSize(panel_1, 400, 95);
		panel_1.setBorder(new TitledBorder(new CompoundBorder( new EmptyBorder(10, 10, 5, 10), new LineBorder(Color.BLACK, 2)), "Buscar"));
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JPanel panel_5 = new JPanel();
		fixSize(panel_5, 400, 30);
		panel_1.add(panel_5);
		
		textField = new JTextField();
		textField.setBorder(new LineBorder(new Color(171, 173, 179), 1, true));
		panel_5.add(textField);
		textField.setColumns(30);
		
		JPanel panel_6 = new JPanel();
		fixSize(panel_6, 400, 35);
		panel_1.add(panel_6);
		panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		txtTelefono = new JTextField();
		txtTelefono.setMaximumSize(new Dimension(400, 80));
		txtTelefono.setText("telefono\r\n");
		txtTelefono.setName("telefono");
		panel_6.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		txtContacto = new JTextField();
		txtContacto.setText("contacto");
		panel_6.add(txtContacto);
		txtContacto.setColumns(10);
		
		buscar = new JButton("Bucar");
		
		panel_6.add(buscar);
	}
	
	// actionBuscar:
	// Cuando se pulse se debe llamar al controlador
	// para que cargue los mensjes
	
	private void fixSize(JPanel panel, int x, int y) {
		panel.setPreferredSize(new Dimension(x, y));
		panel.setMaximumSize(new Dimension(x,y));
		panel.setMinimumSize(new Dimension(x,y));
	}
}
