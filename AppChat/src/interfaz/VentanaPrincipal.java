package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.List;
import modelo.Contacto;
import controlador.Controlador;

public class VentanaPrincipal extends JFrame implements ActionListener {

    private JPanel contenedor;
    private JButton contactos, premium, buscarMensajes, logout;

    public VentanaPrincipal() {
        initialize();
        setVisible(true);
    }

    private void initialize() {
        setSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel panel_superior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel_superior.setPreferredSize(new Dimension(900, 70));
        panel_superior.setBorder(new TitledBorder(null, null, TitledBorder.LEADING, TitledBorder.TOP, null, null));

        int diametro = 50;
        FotoPerfil fotoPerfil = new FotoPerfil(Controlador.INSTANCE.getUsuarioActual().getImagen(), diametro);
        panel_superior.add(fotoPerfil);

        // Single "Contactos" button
        contactos = new JButton("Contactos");
        contactos.setPreferredSize(new Dimension(150, 40));
        contactos.setMargin(new Insets(2, 2, 2, 2));
        contactos.addActionListener(this);
        panel_superior.add(contactos);

        premium = new JButton("Premium");
        premium.addActionListener(this);
        panel_superior.add(premium);

        buscarMensajes = new JButton("Buscar Mensajes");
        buscarMensajes.addActionListener(this);
        panel_superior.add(buscarMensajes);

        logout = new JButton("Logout");
        logout.addActionListener(this);
        panel_superior.add(logout);

        getContentPane().add(panel_superior, BorderLayout.NORTH);

        List<Contacto> contactosList = Controlador.INSTANCE.getContactos();
        PanelListaContactos panelContactos = new PanelListaContactos(contactosList, true);
        panelContactos.setBackground(new Color(220, 248, 198));
        getContentPane().add(panelContactos, BorderLayout.WEST);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == contactos) {
            Controlador.cambiarVentana(this, VentanaContactos::new);
        }
        if (src == logout) {
            Controlador.INSTANCE.setUsuarioActual(null);
            Controlador.cambiarVentana(this, VentanaLogin::new);
        }
    }
}