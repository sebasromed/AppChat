package interfaz;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modelo.Contacto;

public class ContactoPanelSimple extends ContactoPanel {

	public ContactoPanelSimple(Contacto contacto) {
		super(contacto);
		construirPanel(contacto);
	}

	@Override
	protected void construirPanel(Contacto contacto) {
		JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        info.add(new JLabel(contacto.getNombre()));
        add(info, BorderLayout.CENTER);
	}

}
