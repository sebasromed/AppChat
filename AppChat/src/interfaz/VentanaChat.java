package interfaz;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import controlador.Controlador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

public class VentanaChat {
	private JFrame ventana;
	private JPanel contenedor, pantalla;
	private JList<Elemento> lista;
	private JLabel personaje;
	private ArrayList<ImageIcon> imagenes;
	private JButton botonBuscar;
	private Controlador controlador;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaChat window = new VentanaChat();
					window.ventana.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaChat() {
		controlador = Controlador.getUnicaInstancia();
		initialize();
	}
	
	public VentanaChat(Controlador controlador) {
		this.controlador = controlador;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		//-------------------------------
		imagenes=new ArrayList<ImageIcon>();
		imagenes.add(new ImageIcon(getClass().getResource("/pikachu.png")));
		imagenes.add(new ImageIcon(getClass().getResource("/Mujermaravilla.png")));
		imagenes.add(new ImageIcon(getClass().getResource("/shongoku.png")));
		imagenes.add(new ImageIcon(getClass().getResource("/raro.png")));
		
		//-------------------------------
		ventana = new JFrame();
		ventana.setSize(new Dimension(700, 600));
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contenedor=(JPanel) ventana.getContentPane();
		
		JPanel cajaArriba = new JPanel();
		fixSize(cajaArriba, 700, 80);
		cajaArriba.setBorder(new CompoundBorder( new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK, 2)));
		ventana.getContentPane().add(cajaArriba, BorderLayout.NORTH);
		cajaArriba.setLayout(new BoxLayout(cajaArriba, BoxLayout.X_AXIS));
		
		cajaArriba.add(Box.createRigidArea(new Dimension(5,5)));
		
		// Combo box
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"contacto o mensaje", "contacto ", "mensajes"}));
		fixSize(comboBox, 150,30);
		comboBox.setSelectedIndex(0);
        // Añadir un ActionListener para detectar el primer cambio de selección
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Evitar que el usuario seleccione el placeholder de nuevo
                if (comboBox.getSelectedIndex() == 0) {
                    comboBox.setSelectedIndex(-1); // Desmarcar selección si vuelve al placeholder
                }
            }
        });
		cajaArriba.add(comboBox);
		
		
		cajaArriba.add(Box.createRigidArea(new Dimension(5,5)));
		
        
        // Crear el botón con la imagen
        //JButton boton = new JButton(new ImageIcon("C:\\Users\\alfon\\OneDrive - UNIVERSIDAD DE MURCIA\\Universidad\\4º Año\\TDS\\Practicas\\workSpace\\ProyectoAppChat\\resources\\iconoboton.png"));
        botonBuscar = new JButton(new ImageIcon(getClass().getResource("/descarga.png")));
		fixSize(botonBuscar, 40,40);
		actionBotonBuscar();
        // Ajustar la posición del texto e imagen, si hay texto
		botonBuscar.setHorizontalTextPosition(SwingConstants.CENTER);
		botonBuscar.setVerticalTextPosition(SwingConstants.BOTTOM);
        cajaArriba.add(botonBuscar);
		
		cajaArriba.add(Box.createRigidArea(new Dimension(10,10)));
				
		JButton boton2 = new JButton();
		
		boton2.setMaximumSize(new Dimension(40, 40));
		boton2.setMinimumSize(new Dimension(40, 40));
		boton2.setIcon(new ImageIcon(getClass().getResource("/descarga.png")));
		fixSize(boton2, 40,40);
	    cajaArriba.add(boton2);
	    
	    Component rigidArea = Box.createRigidArea(new Dimension(10, 10));
	    cajaArriba.add(rigidArea);
	    
	    JButton btnNewButton = new JButton("Contactos");
	    cajaArriba.add(btnNewButton);
	    
	    Component rigidArea_1 = Box.createRigidArea(new Dimension(10, 10));
	    cajaArriba.add(rigidArea_1);
	    
	    JButton btnNewButton_1 = new JButton("$ Premium");
	    cajaArriba.add(btnNewButton_1);
	    
	    Component rigidArea_1_1 = Box.createRigidArea(new Dimension(10, 10));
	    cajaArriba.add(rigidArea_1_1);
	    
	    JLabel lblNewLabel = new JLabel("Usuario");
	    cajaArriba.add(lblNewLabel);
	    
	    Component rigidArea_1_1_1 = Box.createRigidArea(new Dimension(10, 10));
	    cajaArriba.add(rigidArea_1_1_1);
	    
	  
		
		
		
		pantalla = new JPanel();
		pantalla.setBackground(Color.gray);
		ventana.getContentPane().add(pantalla, BorderLayout.CENTER);
		pantalla.setLayout(new BoxLayout(pantalla, BoxLayout.X_AXIS));
		
		JPanel cajaIzquierda = new JPanel();
		//cajaIzquierda.setBackground(new Color(200, 100, 100));
		cajaIzquierda.setPreferredSize(new Dimension(320, 550));
		cajaIzquierda.setMinimumSize(new Dimension(300, 550));
		cajaIzquierda.setMaximumSize(new Dimension(300, 550));
		pantalla.add(cajaIzquierda);
		cajaIzquierda.setLayout(new BoxLayout(cajaIzquierda, BoxLayout.Y_AXIS));		
		
		/*
		 * El visor tiene que recibir el chat oportuno 
		 * Creando la ventana de la de la conversacion coon los mensajes oportunos
		 */
		JPanel visor = new JPanel();
		visor.setBackground(Color.cyan);
		visor.setPreferredSize(new Dimension(380, 480));
		visor.setMinimumSize(new Dimension(400, 550));
		visor.setMaximumSize(new Dimension(400, 550));
		pantalla.add(visor);
		
		personaje = new JLabel("");
		visor.add(personaje);
		
		
		/*
		 * La lista de valores se tiene que recibir del controlador de chats
		 * Ordenado de más recientes
		 * Una vez esten los chats crearlos en funcion del tipo (agregado, individual o grupal)
		 */
		lista =new JList<Elemento>();
		DefaultListModel<Elemento> model=new DefaultListModel<Elemento>();
		model.addElement(new Elemento("pikachu.png","contacto","323242424"));
		model.addElement(new Elemento("pikachu.png","Mujer Maravilla","24422454"));
		model.addElement(new Elemento("pikachu.png","Shongoku","134244224"));
		model.addElement(new Elemento("pikachu.png","Emoji","24242545"));
		lista.setModel(model);
		lista.setCellRenderer(new ElementoListRenderer());
		
		lista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2)
				  personaje.setIcon(imagenes.get(lista.getSelectedIndex()));	
			}
		});
		
		JScrollPane scroll=new JScrollPane(lista);
		//cajaArriba.setBorder(new CompoundBorder( new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK, 2)));
		scroll.setBorder(new TitledBorder(new CompoundBorder( new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK, 2)), "Contactos"));
		fixSize(scroll, 350, 500);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		cajaIzquierda.add(scroll);
	}
	
	private void fixSize(JComponent c, int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}

	
	private void actionBotonBuscar() {
		botonBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
			controlador.cambiarVentanaBuscar(ventana);
			}
		});
	}
	
	
	/*
	private void inicializarChat(JPanel panelChat) {
		ventana.setLayout(new BoxLayout(ventana, BoxLayout.Y_AXIS));
		
		BubbleText burbuja = new BubbleText(panelChat, "Hola grupo!!", Color.GREEN, "J.Ramón", BubbleText.SENT);
		panelChat.add(burbuja);

		BubbleText burbuja2 = new BubbleText(panelChat, "Hola!", Color.LIGHT_GRAY, "Jesús", BubbleText.RECEIVED);
		panelChat.add(burbuja2);

	}
	 */

}


