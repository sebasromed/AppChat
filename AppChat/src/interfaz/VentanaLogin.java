package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.BorderUIResource.LineBorderUIResource;

import controlador.Controlador;

public class VentanaLogin extends JFrame implements ActionListener {
	
	private JFrame ventaLog;
	private JPanel panelNorte, panelCentro, panelCentro_norte, panelCentro_sur, panelCentro_centro, panelSur;
	private JLabel lblNorte;
	private JTextField telefono, contraseña;
	private JButton aceptar, cancelar, registrar;
	private Controlador controlador;
	
	
	public VentanaLogin(Controlador controlador) {
		inicializar();
		this.controlador = controlador;
		this.ventaLog.setVisible(true);
	}
	

	private void inicializar() {
		ventaLog = new JFrame();
		ventaLog.setResizable(false);
		ventaLog.setSize(new Dimension(400,300));
		ventaLog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Titulo
		panelTitulo();
		//Registro
		panelCentro();
		//Botones
		panelBotones();	
	}
	
	private void panelTitulo() {
		panelNorte = new JPanel();
		panelNorte.setLayout(new FlowLayout());	
		ventaLog.getContentPane().add(panelNorte, BorderLayout.NORTH);
		lblNorte = new JLabel();
		lblNorte.setOpaque(true);
		lblNorte.setText("AppChat");
		lblNorte.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		panelNorte.add(lblNorte);
	}
	
	private void panelCentro() {
		panelCentro = new JPanel();
		panelCentro.setBorder(new TitledBorder(new CompoundBorder( new EmptyBorder(0, 30, 0, 30), new LineBorder(Color.BLACK, 2)), "Login"));
		ventaLog.getContentPane().add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(new BorderLayout(0, 0));
		//Panel 2 Centro_norte
		panelCentro_norte = new JPanel();
		fixSize(panelCentro_norte, 400, 35);
		panelCentro.add(panelCentro_norte, BorderLayout.NORTH);			
		panelCentro_norte.setLayout(new BorderLayout(0, 0));
		//Panel 2 Centro_centro
		panelCentro_centro = new JPanel();
		panelCentro_centro.setLayout(new BoxLayout(panelCentro_centro, BoxLayout.Y_AXIS));
		fixSize(panelCentro_centro, 400, 100);
		
		telefono = new JTextField();
		contraseña = new JTextField();
		telefono.setEditable(true);
		telefono.setText("telefono");
		contraseña.setEditable(true);
		contraseña.setText("contraseña");
		
		telefono.setPreferredSize(new Dimension(200, 20));
		telefono.setMinimumSize(new Dimension(200, 20));
		telefono.setMaximumSize(new Dimension(200, 20));
		
		contraseña.setPreferredSize(new Dimension(200, 20));
		contraseña.setMinimumSize(new Dimension(200, 20));
		contraseña.setMaximumSize(new Dimension(200, 20));
		
		panelCentro_centro.add(contraseña);
		panelCentro_centro.add(Box.createRigidArea(new Dimension(20,20)));
		panelCentro_centro.add(telefono);		
		
		telefono.setColumns(10);
		contraseña.setColumns(10);
		
		panelCentro.add(panelCentro_centro);
	}
	
	private void panelBotones() {
		panelSur = new JPanel();
		panelSur.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		registrar = new JButton("Registrar");
		actionRegistrar();
		cancelar = new JButton("Cancelar");
		actionCancelar();
		aceptar = new JButton("Aceptar");
		actionAceptar();
		
		panelSur.add(registrar);
		panelSur.add(Box.createRigidArea(new Dimension(40,30)));
		panelSur.add(aceptar);
		panelSur.add(cancelar);
		
		ventaLog.getContentPane().add(panelSur, BorderLayout.SOUTH);
	}
	
	private void actionAceptar() {
		aceptar.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				String tlf = telefono.getText().trim();
				@SuppressWarnings("deprecation")
				String passw = contraseña.getText();
				// Llama al controlador para comprobar si el usuario estaba registrado
				if(!controlador.loginUsuario(tlf, passw)) {
					//JOptionPane.showMessageDialog(lblPass, "Hey, u made a mistake, i dont't know why, but u can't login", "CHECK YOUR LOGIN" , JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	private void actionCancelar() {
		cancelar.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				ventaLog.dispose();
			}
		});
	}
	
	private void actionRegistrar() {
		registrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
			controlador.cambiarVentanaRegistrar(ventaLog);
			}
		});
	}
	
	
	private void fixSize(JPanel pane, int x, int y) {
		pane.setPreferredSize(new Dimension(x, y));
		pane.setMinimumSize(new Dimension(x, y));
		pane.setMaximumSize(new Dimension(x, y));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
