package interfaz;

import javax.swing.*;

import controlador.Controlador;
import modelo.Contacto;
import modelo.ContactoGrupo;
import modelo.ContactoIndividual;
import modelo.Usuario;

import java.awt.*;

public abstract class ContactoPanel extends JPanel {
    
	private Usuario userDeContacto;
	
	public ContactoPanel(Contacto contacto) {
        setLayout(new BorderLayout(10, 0));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        setMaximumSize(new Dimension(300, 80));
        setPreferredSize(new Dimension(300, 80));
			
        userDeContacto = Controlador.INSTANCE.getUsuarioDeContacto(contacto);
        
        ImageIcon fotoPerfil;
        if (userDeContacto == null) {
			fotoPerfil = new ImageIcon(((ContactoGrupo) contacto).getImagen());
		} else {
			fotoPerfil = new ImageIcon(userDeContacto.getImagen());
		}

        // Profile photo (circular)
        JLabel foto = new JLabel();
        foto.setPreferredSize(new Dimension(60, 60));
        foto.setIcon(fotoPerfil);
        add(foto, BorderLayout.WEST);
        
        construirPanel(contacto);

        // Info panel
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        info.add(new JLabel("Nombre: " + contacto.getNombre()));
        if (contacto.getNombre().startsWith("+")) {
        	// No mostrar numero de telefono en el campo telefono, ya que se muestra en el nombre
        }
        else {
        	info.add(new JLabel("Teléfono: " + userDeContacto.getTelefono()));
        } 
        info.add(new JLabel("Saludo: " + userDeContacto.getSaludo()));
        add(info, BorderLayout.CENTER);
    }
	
	public Usuario getUserDeContacto() {
		return userDeContacto;
	}

	public void setUserDeContacto(Usuario userDeContacto) {
		this.userDeContacto = userDeContacto;
	}

	protected abstract void construirPanel(Contacto contacto);
}
