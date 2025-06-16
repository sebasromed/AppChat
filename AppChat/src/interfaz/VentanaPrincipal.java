package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import controlador.Controlador;

public class VentanaPrincipal {

	private JFrame frame;
	private JPanel contenedor;
	private Controlador controlador;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	/**
	 * Create the application.
	 */
	public VentanaPrincipal(Controlador controlador) {
		this.controlador = controlador;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(new Dimension(700, 600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		//Creación de panel supeior
		JPanel panel_superior = new JPanel();
		panel_superior.setPreferredSize(new Dimension(700, 70));
		panel_superior.setMinimumSize(new Dimension(700, 70));
		panel_superior.setMaximumSize(new Dimension(700, 70));
		panel_superior.setBorder(new TitledBorder(null, null, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_superior.setLayout(new BoxLayout(panel_superior, BoxLayout.X_AXIS));
		
		//Añadir panel superior a la ventana
		frame.getContentPane().add(panel_superior, BorderLayout.NORTH);
		
		//(1)
		JComboBox<String> elegir_busqueda = new JComboBox();
		elegir_busqueda.setPreferredSize(new Dimension(100, 25));
		elegir_busqueda.setMinimumSize(new Dimension(100, 25));
		elegir_busqueda.setMaximumSize(new Dimension(100, 25));
		elegir_busqueda.setVisible(true);
		elegir_busqueda.addItem("teléfono");
		elegir_busqueda.addItem("Nombre");
		panel_superior.add(elegir_busqueda);
		
		
		
		
		
		
		
	}

}
