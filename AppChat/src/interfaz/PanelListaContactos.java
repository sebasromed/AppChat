package interfaz;

import javax.swing.*;

import controlador.Controlador;
import modelo.Contacto;
import modelo.Usuario;

import java.awt.*;
import java.util.List;

public class PanelListaContactos extends JPanel {

    public PanelListaContactos(List<Contacto> contactos, boolean detallado) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(200, 100, 100));
        setPreferredSize(new Dimension(320, 480));
        setMinimumSize(new Dimension(320, 480));
        setMaximumSize(new Dimension(320, 480));

        for (Contacto c : contactos) {
        	if (detallado) {
        		add(new ContactoPanelDetallado(c));
        	}
        	else {
        		add(new ContactoPanelSimple(c));
        	}
            add(Box.createVerticalStrut(1));
        }
    }
}
