package interfaz;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.Box;
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
        
        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
        labelsPanel.setOpaque(false);
        labelsPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        JLabel nombreLabel = new JLabel(contacto.getNombre());
        nombreLabel.setFont(new Font(nombreLabel.getFont().getName(), Font.BOLD, 18));
        nombreLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        labelsPanel.add(nombreLabel);

        info.add(Box.createVerticalGlue());
        info.add(labelsPanel);
        info.add(Box.createVerticalGlue());

        add(info, BorderLayout.CENTER);
        add(info, BorderLayout.CENTER);
	}

}
