package interfaz;

import controlador.Controlador;
import modelo.ContactoIndividual;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.*;

public class VentanaAddGrupo extends JDialog implements ActionListener {

    private JTextField textNombreGrupo;
    private JPanel panelCheckList;
    private JButton botonElegirImagen, botonCrearGrupo;
    private FotoPerfil iconoImagen;
    private String rutaImagenSeleccionada = null;
    private List<ContactoIndividual> contactosIndividuales;
    private List<JCheckBox> checkBoxes;

    public VentanaAddGrupo(JFrame parent) {
        super(parent, "Crear Grupo", true); // modal dialog
        setSize(700, 420);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // Left: Contactos con checkboxes
        contactosIndividuales = Controlador.getContactosIndividuales(Controlador.INSTANCE.getContactosOrdenadosAlfabeticamente());

        panelCheckList = new JPanel();
        panelCheckList.setLayout(new BoxLayout(panelCheckList, BoxLayout.Y_AXIS));
        checkBoxes = new ArrayList<>();
        for (ContactoIndividual ci : contactosIndividuales) {
        	JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            row.setOpaque(false);

            JCheckBox cb = new JCheckBox();
            cb.setBackground(Color.WHITE);
            checkBoxes.add(cb);

            ContactoPanelSimple panelSimple = new ContactoPanelSimple(ci);
            panelSimple.setOpaque(false);

            row.add(cb);
            row.add(Box.createHorizontalStrut(8));
            row.add(panelSimple);
            row.add(Box.createHorizontalGlue());

            panelCheckList.add(row);
            panelCheckList.add(Box.createVerticalStrut(4));
        }
        JScrollPane scroll = new JScrollPane(panelCheckList);
        scroll.setPreferredSize(new Dimension(220, 350));
        scroll.getViewport().setBackground(Color.WHITE);
        add(scroll, BorderLayout.WEST);

        // Right: Panel nombre grupo y foto
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBackground(Color.WHITE);
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Nombre grupo
        JPanel panelNombre = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNombre.setOpaque(false);
        JLabel labelNombre = new JLabel("Nombre del grupo:");
        textNombreGrupo = new JTextField(18);
        panelNombre.add(labelNombre);
        panelNombre.add(textNombreGrupo);
        panelDerecho.add(panelNombre);

        // Espacio
        panelDerecho.add(Box.createVerticalStrut(20));

        // Imagen grupo
        JPanel panelImagen = new JPanel();
        panelImagen.setOpaque(false);
        panelImagen.setLayout(new BoxLayout(panelImagen, BoxLayout.Y_AXIS));
        panelImagen.setBorder(BorderFactory.createTitledBorder("Imagen del grupo"));

        int diametro = 100;
        BufferedImage defaultImage = null;
        try {
            defaultImage = ImageIO.read(getClass().getResource("/blank-profile-circle.png"));
        } catch (Exception ex) {}
        iconoImagen = new FotoPerfil(defaultImage, diametro);
        iconoImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelImagen.add(iconoImagen);

        panelImagen.add(Box.createVerticalStrut(10));
        botonElegirImagen = new JButton("Elegir imagen");
        botonElegirImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonElegirImagen.addActionListener(this);
        panelImagen.add(botonElegirImagen);

        panelDerecho.add(panelImagen);

        // Espacio
        panelDerecho.add(Box.createVerticalGlue());

        add(panelDerecho, BorderLayout.CENTER);

        // Bottom: Botón crear grupo
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(Color.WHITE);
        botonCrearGrupo = new JButton("Crear grupo");
        botonCrearGrupo.addActionListener(this);
        panelBoton.add(botonCrearGrupo);
        add(panelBoton, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == botonElegirImagen) {
            PanelArrastraImagen dialog = new PanelArrastraImagen((JFrame) getParent());
            List<File> files = dialog.showDialog();
            if (!files.isEmpty()) {
                try {
                    BufferedImage image = ImageIO.read(files.get(0));
                    if (image != null) {
                        iconoImagen.setImage(image);
                        iconoImagen.revalidate();
                        iconoImagen.repaint();
                        rutaImagenSeleccionada = files.get(0).getPath();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (src == botonCrearGrupo) {
            String nombreGrupo = textNombreGrupo.getText().trim();
            if (nombreGrupo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe introducir un nombre para el grupo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<ContactoIndividual> seleccionados = new ArrayList<>();
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    seleccionados.add(contactosIndividuales.get(i));
                }
            }
            if (seleccionados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar al menos un contacto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (rutaImagenSeleccionada == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una imagen para el grupo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String rutaImagen = rutaImagenSeleccionada;
            try {
                rutaImagen = Controlador.INSTANCE.guardarImagenPerfil(rutaImagenSeleccionada, "grupo_" + nombreGrupo);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No se pudo guardar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(Controlador.INSTANCE.registrarGrupo(nombreGrupo, rutaImagen, seleccionados)) {
                JOptionPane.showMessageDialog(this, "Grupo creado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Ya existe un grupo con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dispose();
        }
    }
}