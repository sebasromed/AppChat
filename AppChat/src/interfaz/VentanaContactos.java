package interfaz;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import controlador.Controlador;
import modelo.Contacto;
import modelo.ContactoGrupo;
import modelo.ContactoIndividual;

public class VentanaContactos extends JFrame implements ActionListener {
    private JPanel panelNorte, panelCentro, panelDetalle;
    private JButton botonAddContacto, botonAddGrupo, botonEliminar, botonAnadirMiembro, botonEliminarGrupo;
    private JList<ContactoPanel> listaContactos;
    private JList<ContactoPanel> listaMiembrosGrupo;
    private DefaultListModel<ContactoPanel> modeloContactos;
    private List<Contacto> contactos;

    private final Color colorFondo = new Color(76, 175, 80);

    public VentanaContactos() {
        inicializar();
        setVisible(true);
    }

    private void inicializar() {
        setResizable(false);
        setSize(new Dimension(900, 550));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Controlador.cambiarVentana(VentanaContactos.this, VentanaPrincipal::new);
            }
        });

        panelTitulo();
        panelContactos();
    }

    private void panelTitulo() {
        panelNorte = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panelNorte.setBackground(Color.WHITE);
        getContentPane().add(panelNorte, BorderLayout.NORTH);

        JLabel lblNorte = new JLabel("Contactos") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setFont(getFont());
                g2.setColor(new Color(0, 0, 0, 60));
                g2.drawString(getText(), 4, getBaseline(getWidth(), getHeight()) + 4);
                g2.setColor(colorFondo);
                g2.drawString(getText(), 0, getBaseline(getWidth(), getHeight()));
                g2.dispose();
            }
        };
        lblNorte.setFont(new Font("Comic Sans MS", Font.BOLD, 42));
        lblNorte.setOpaque(false);
        panelNorte.add(lblNorte);
    }

    private void panelContactos() {
        panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBorder(new TitledBorder(
            new CompoundBorder(
                new EmptyBorder(10, 20, 10, 20),
                new LineBorder(colorFondo, 2)
            ),
            "lista contactos",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Comic Sans MS", Font.BOLD, 16),
            colorFondo
        ));
        panelCentro.setBackground(Color.WHITE);
        getContentPane().add(panelCentro, BorderLayout.CENTER);

        contactos = Controlador.INSTANCE.getContactosOrdenadosAlfabeticamente();
        modeloContactos = new DefaultListModel<>();
        contactos.forEach(c -> modeloContactos.addElement(new ContactoPanelSimple(c)));
        listaContactos = new JList<>(modeloContactos);
        listaContactos.setCellRenderer(new ListCellRenderer<ContactoPanel>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends ContactoPanel> list, ContactoPanel value, int index, boolean isSelected, boolean cellHasFocus) {
                value.setBackground(isSelected ? new Color(220, 248, 198) : Color.WHITE);
                value.setBorder(BorderFactory.createLineBorder(isSelected ? new Color(76, 175, 80) : Color.LIGHT_GRAY, 2));
                return value;
            }
        });
        
        JScrollPane scrollContactos = new JScrollPane(listaContactos);

        // Panel for list and button
        JPanel panelListaYBoton = new JPanel();
        panelListaYBoton.setLayout(new BorderLayout());
        panelListaYBoton.setBackground(Color.WHITE);
        panelListaYBoton.add(scrollContactos, BorderLayout.CENTER);

        botonAddContacto = new JButton("Añadir contacto");
        botonAddContacto.addActionListener(this);
        panelListaYBoton.add(botonAddContacto, BorderLayout.SOUTH);

        panelDetalle = new JPanel(new BorderLayout());
        panelDetalle.setBackground(Color.WHITE);
        
        botonAddGrupo = new JButton("Añadir grupo");
        botonAddGrupo.addActionListener(this);
        panelDetalle.add(botonAddGrupo, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelListaYBoton, panelDetalle);
        splitPane.setDividerLocation(250);
        panelCentro.add(splitPane, BorderLayout.CENTER);

        listaContactos.addListSelectionListener(e -> actualizarDetalle());
    }

    private void actualizarDetalle() {
        panelDetalle.removeAll();
        if (listaContactos.getSelectedValue() == null) {
            panelDetalle.revalidate();
            panelDetalle.repaint();
            return;
        }
        Contacto seleccionado = listaContactos.getSelectedValue().getContacto();
        if (seleccionado instanceof ContactoGrupo) {
            ContactoGrupo grupo = (ContactoGrupo) seleccionado;
            JPanel panelGrupo = new JPanel(new BorderLayout());
            panelGrupo.setBorder(BorderFactory.createTitledBorder(grupo.getNombre()));
            panelGrupo.setBackground(Color.WHITE);

            DefaultListModel<ContactoPanel> modeloGrupo = new DefaultListModel<>();
            grupo.getMiembros().forEach(c -> modeloGrupo.addElement(new ContactoPanelSimple(c)));
            listaMiembrosGrupo = new JList<>(modeloGrupo);
            listaMiembrosGrupo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listaMiembrosGrupo.setCellRenderer(new ListCellRenderer<ContactoPanel>() {
                @Override
                public Component getListCellRendererComponent(JList<? extends ContactoPanel> list, ContactoPanel value, int index, boolean isSelected, boolean cellHasFocus) {
                    value.setBackground(isSelected ? new Color(220, 248, 198) : Color.WHITE);
                    value.setBorder(BorderFactory.createLineBorder(isSelected ? new Color(76, 175, 80) : Color.LIGHT_GRAY, 2));
                    return value;
                }
            });
            
            panelGrupo.add(new JScrollPane(listaMiembrosGrupo), BorderLayout.CENTER);
            
            botonEliminar = new JButton("Eliminar miembro");
            botonAnadirMiembro = new JButton("Añadir miembro");
            botonEliminarGrupo = new JButton("Eliminar grupo");
            
            botonEliminar.addActionListener(this);
            botonAnadirMiembro.addActionListener(this);
            botonEliminarGrupo.addActionListener(this);
            
            JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panelBoton.setBackground(Color.WHITE);
            panelBoton.add(botonEliminar);
            panelBoton.add(botonAnadirMiembro);
            panelBoton.add(botonEliminar);
            panelBoton.add(botonEliminarGrupo);

            panelGrupo.add(panelBoton, BorderLayout.SOUTH);
            panelDetalle.add(panelGrupo, BorderLayout.CENTER);
        }
        panelDetalle.revalidate();
        panelDetalle.repaint();
    }
    
    // Para actualizar la lista de contactos
    public void refrescarContactos() {
        contactos = Controlador.INSTANCE.getContactosOrdenadosAlfabeticamente();
        modeloContactos.clear();
        contactos.forEach(c -> modeloContactos.addElement(new ContactoPanelSimple(c)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == botonAddContacto) {
        	new VentanaAddContacto(this);
        	refrescarContactos();
        } else if (src == botonAddGrupo) {
			new VentanaAddGrupo(this);
			refrescarContactos();
		} else if (src == botonEliminar) {
			Contacto seleccionado = listaContactos.getSelectedValue().getContacto();
	        if (seleccionado instanceof ContactoGrupo) {
	            ContactoGrupo grupo = (ContactoGrupo) seleccionado;
	            // Find the JList of members
	            int idx = listaMiembrosGrupo.getSelectedIndex();
	            if (idx >= 0) {
	                ContactoIndividual miembro = (ContactoIndividual) grupo.getMiembros().get(idx);
	                Controlador.INSTANCE.borrarMiembroGrupo(miembro, grupo);
	                grupo.getMiembros().remove(miembro);
	                actualizarDetalle();
	            } else {
	                JOptionPane.showMessageDialog(this, "Seleccione un miembro para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
	            }
	        }
	    } else if (src == botonAnadirMiembro) {
	        // TODO: Implement logic to add a member to the group
	        JOptionPane.showMessageDialog(this, "Funcionalidad de añadir miembro no implementada.");
	    } else if (src == botonEliminarGrupo) {
	        Contacto seleccionado = listaContactos.getSelectedValue().getContacto();
	        if (seleccionado instanceof ContactoGrupo) {
	            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro que quiere eliminar el grupo?", "Confirmar", JOptionPane.YES_NO_OPTION);
	            if (confirm == JOptionPane.YES_OPTION) {
	                Controlador.INSTANCE.borrarGrupo((ContactoGrupo) seleccionado);
	                refrescarContactos();
	                panelDetalle.removeAll();
	                panelDetalle.revalidate();
	                panelDetalle.repaint();
	            }
	        }
	    }
    }
}
