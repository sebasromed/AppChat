package interfaz;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PanelArrastraImagen extends JDialog {

    private static final long serialVersionUID = 1L;
    private final List<File> archivosSubidos = new ArrayList<>();
    private final FotoPerfil fotoPerfil;
    private final JButton botonElegir = new JButton("Seleccionar de tu ordenador");

    public PanelArrastraImagen(JFrame owner) {
        super(owner, "Agregar fotos", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(450, 350);
        setResizable(false);
        setLocationRelativeTo(owner);

        JPanel contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);

        // Title and instructions
        JLabel titulo = new JLabel("<html><h2>Agregar Foto</h2><div>Puedes arrastrar la imagen aquí o seleccionarla de tu ordenador.</div></html>");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(titulo);
        contentPane.add(Box.createVerticalStrut(10));

        // Circular image preview using FotoPerfil
        
        int diametro = 120;
        BufferedImage defaultImage = null;
        try {
            defaultImage = ImageIO.read(getClass().getResource("/blank-profile-circle.png"));
        } catch (IOException ex) {
            // If not found, defaultImage remains null
        }
        fotoPerfil = new FotoPerfil(defaultImage, diametro);
        fotoPerfil.setAlignmentX(Component.CENTER_ALIGNMENT);
        fotoPerfil.setBackground(Color.WHITE);
        fotoPerfil.setOpaque(false);
        fotoPerfil.setPreferredSize(new Dimension(diametro, diametro));
        contentPane.add(fotoPerfil);
        contentPane.add(Box.createVerticalStrut(10));

        // Drag & drop support
        fotoPerfil.setTransferHandler(new TransferHandler() {
            public boolean canImport(TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }
            @SuppressWarnings("unchecked")
            public boolean importData(TransferSupport support) {
                try {
                    List<File> files = (List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    if (!files.isEmpty()) {
                        setImage(files.get(0));
                    }
                } catch (Exception ex) {
                    showError("No se pudo cargar la imagen.");
                }
                return true;
            }
        });
        new DropTarget(fotoPerfil, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
            public void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> files = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    if (!files.isEmpty()) {
                        setImage(files.get(0));
                    }
                } catch (Exception ex) {
                    showError("No se pudo cargar la imagen.");
                }
            }
        }, true);

        // File chooser button (green)
        botonElegir.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonElegir.setBackground(new Color(46, 204, 113));
        botonElegir.setForeground(Color.WHITE);
        botonElegir.setFocusPainted(false);
        botonElegir.setOpaque(true);
        botonElegir.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        botonElegir.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                setImage(chooser.getSelectedFile());
            }
        });
        contentPane.add(botonElegir);
        contentPane.add(Box.createVerticalStrut(10));

        // Accept/Cancel buttons (always visible)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setOpaque(false);
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");
        btnAceptar.addActionListener(ev -> dispose());
        btnCancelar.addActionListener(ev -> {
            archivosSubidos.clear();
            dispose();
        });
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        contentPane.add(panelBotones);
    }

    private void setImage(File file) {
        try {
            Image img = ImageIO.read(file);
            if (img != null) {
            	BufferedImage bufferedImage = FotoPerfil.toBufferedImage(img);
                fotoPerfil.setImage(bufferedImage);
                archivosSubidos.clear();
                archivosSubidos.add(file);
            } else {
                showError("El archivo seleccionado no es una imagen válida.");
            }
        } catch (Exception ex) {
            showError("No se pudo cargar la imagen.");
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public List<File> showDialog() {
        setVisible(true);
        return archivosSubidos;
    }
}