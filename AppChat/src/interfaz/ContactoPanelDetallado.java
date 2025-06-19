package interfaz;

import java.awt.BorderLayout;

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
	        info.add(new JLabel("Nombre: " + contacto.getNombre()));
	        if (contacto instanceof ContactoGrupo) {
	            // No se muestra nada mas, los grupos no tienen teléfono ni saludo
	        } else {
	            if (!contacto.getNombre().startsWith("+") && contacto instanceof ContactoIndividual) {
	                info.add(new JLabel("Teléfono: " + ((ContactoIndividual) contacto).getTelefono()));
	            }
	            info.add(new JLabel("Saludo: " + getUserDeContacto().getSaludo()));
	        }
	        info.add(new JLabel("Saludo: " + getUserDeContacto().getSaludo()));
	        add(info, BorderLayout.CENTER);
		}

}
//No mostrar numero de telefono en el campo telefono, ya que se muestra en el nombre