package interfaz;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modelo.Contacto;
import modelo.ContactoGrupo;
import modelo.ContactoIndividual;

public class ContactoPanelDetallado extends ContactoPanel {
		public ContactoPanelDetallado(Contacto contacto) {
			super(contacto);
			construirPanel(contacto);
		}
		
		public void construirPanel(Contacto contacto) {
			JPanel info = new JPanel();
	        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
	        info.setOpaque(false);
	        
	        JPanel labelsPanel = new JPanel();
	        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
	        labelsPanel.setOpaque(false);
	        
	        JLabel nombreLabel = new JLabel(contacto.getNombre());
	        nombreLabel.setFont(new Font(nombreLabel.getFont().getName(), Font.BOLD, 18));
	        nombreLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
	        labelsPanel.add(nombreLabel);
	        if (contacto.getNombre().startsWith("+")) {
	        	// No mostrar numero de telefono en el campo telefono, ya que se muestra en el nombre
	        }
	        else if (contacto instanceof ContactoIndividual) {
	        	JLabel telefonoLabel = new JLabel("Teléfono: " + ((ContactoIndividual) contacto).getTelefono());
	            telefonoLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
	            labelsPanel.add(telefonoLabel);
	            JLabel saludoLabel = new JLabel("Saludo: " + getUserDeContacto().getSaludo());
		        saludoLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		        labelsPanel.add(saludoLabel);
	        }
	        
	        info.add(Box.createVerticalGlue());
	        info.add(labelsPanel);
	        info.add(Box.createVerticalGlue());
	        
	        add(info, BorderLayout.CENTER);
		}

}
//No mostrar numero de telefono en el campo telefono, ya que se muestra en el nombre