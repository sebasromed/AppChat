package interfaz;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controlador.Controlador;
import modelo.Contacto;
import modelo.ContactoGrupo;
import modelo.ContactoIndividual;

public class VentanaAddMiembro extends JDialog implements ActionListener {

	private JPanel panelCampos, panelBoton;
	private JLabel lblTitulo;
	private JButton botonAceptar;
	private final Color colorFondo = new Color(220, 248, 198);
	private JFrame parent;

	private JList<CheckListItem> listaContactos;
	private DefaultListModel<CheckListItem> modeloContactos;
	private ContactoGrupo grupo;

	public VentanaAddMiembro(JFrame parent, ContactoGrupo grupo) {
		super(parent, "Añadir Miembros al Grupo", true);
		this.parent = parent;
		this.grupo = grupo;
		getContentPane().setBackground(colorFondo);
		inicializar(parent);
		setVisible(true);
	}

	public VentanaAddMiembro(JFrame parent) {
		super(parent, "Añadir Contacto", true);
		this.parent = parent;
		getContentPane().setBackground(colorFondo);
		inicializar(parent);
		setVisible(true);
	}

	private void inicializar(JFrame parent) {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		lblTitulo = new JLabel("Seleccione los contactos a añadir al grupo");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
		add(lblTitulo, BorderLayout.NORTH);

		// Obtener contactos menos los que ya están en el grupo o son grupos
		List<Contacto> posibles = Controlador.INSTANCE.getContactosOrdenadosAlfabeticamente().stream()
				.filter(c -> c instanceof ContactoIndividual).filter(c -> !grupo.getMiembros().contains(c))
				.collect(Collectors.toList());

		modeloContactos = new DefaultListModel<>();
		for (Contacto c : posibles) {
			modeloContactos.addElement(new CheckListItem((ContactoIndividual) c));
		}

		listaContactos = new JList<>(modeloContactos);
		listaContactos.setCellRenderer(new CheckListRenderer());
		listaContactos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listaContactos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = listaContactos.locationToIndex(e.getPoint());
				if (index != -1) {
					CheckListItem item = modeloContactos.getElementAt(index);
					item.setSelected(!item.isSelected());
					listaContactos.repaint(listaContactos.getCellBounds(index, index));
				}
			}
		});

		JScrollPane scroll = new JScrollPane(listaContactos);
		scroll.setBorder(new EmptyBorder(10, 15, 10, 15));
		add(scroll, BorderLayout.CENTER);

		panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(this);
		panelBoton.add(botonAceptar);
		panelBoton.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		panelBoton.setBackground(colorFondo);
		add(panelBoton, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(parent);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == botonAceptar && grupo != null) {
			boolean contactoSeleccionado = false;
			for (int i = 0; i < modeloContactos.size(); i++) {
				CheckListItem item = modeloContactos.get(i);
				if (item.isSelected()) {
					Controlador.INSTANCE.addMiembroAGrupo(item.getContacto(), grupo);
					contactoSeleccionado = true;
				}
			}
			if (contactoSeleccionado) {
				JOptionPane.showMessageDialog(this, "Miembros añadidos correctamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				if (parent instanceof VentanaContactos) {
					((VentanaContactos) parent).refrescarContactos();
				}
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Seleccione al menos un contacto.", "Aviso",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private static class CheckListItem {
		private final ContactoIndividual contacto;
		private boolean selected;

		public CheckListItem(ContactoIndividual contacto) {
			this.contacto = contacto;
			this.selected = false;
		}

		public ContactoIndividual getContacto() {
			return contacto;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		@Override
		public String toString() {
			return contacto.getNombre() + " (" + contacto.getTelefono() + ")";
		}
	}

	private static class CheckListRenderer extends JCheckBox implements ListCellRenderer<CheckListItem> {
		@Override
		public Component getListCellRendererComponent(JList<? extends CheckListItem> list, CheckListItem value,
				int index, boolean isSelected, boolean cellHasFocus) {
			setEnabled(list.isEnabled());
			setSelected(value.isSelected());
			setFont(list.getFont());
			setBackground(isSelected ? new Color(220, 248, 198) : Color.WHITE);
			setText(value.toString());
			return this;
		}
	}
}
