package interfaz;

import javax.imageio.ImageIO;
import javax.swing.*;

import controlador.Controlador;
import modelo.Contacto;
import modelo.ContactoGrupo;
import modelo.ContactoIndividual;
import modelo.Usuario;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class ContactoPanel extends JPanel {
    
	private Usuario userDeContacto;
	private Contacto contacto;
	
	public ContactoPanel(Contacto contacto) {
		this.contacto = contacto;
		
		if (contacto instanceof ContactoIndividual) {
			this.userDeContacto = Controlador.INSTANCE.getUsuarioDeContacto((ContactoIndividual) contacto); // Obtener el usuario de contacto
		} else {
			this.userDeContacto = null; // Los grupos no tienen un usuario de contacto individual
		}
		
        setLayout(new BorderLayout(10, 0));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        setMaximumSize(new Dimension(300, 80));
        setPreferredSize(new Dimension(300, 80));
        
        BufferedImage imagenPerfil = null;
        FotoPerfil fotoPerfil = null;
        int diametro = 80;
        
        if (userDeContacto == null) {
	        try {
	            imagenPerfil = ImageIO.read(new File(((ContactoGrupo) contacto).getImagen()));
	            
	        } catch (IOException ex) {
	        	 JOptionPane.showMessageDialog(this, "Error cargando foto de perfil", "Error", JOptionPane.ERROR_MESSAGE);
	             System.exit(1);
	        }
        } else {
        	try {
				imagenPerfil = ImageIO.read(new File(userDeContacto.getImagen()));
			} catch (IOException e) {
				 JOptionPane.showMessageDialog(this, "Error cargando foto de perfil", "Error", JOptionPane.ERROR_MESSAGE);
			     System.exit(1);
			}
        }
        fotoPerfil = new FotoPerfil(imagenPerfil, diametro);

        add(fotoPerfil, BorderLayout.WEST);
        
        construirPanel(contacto);
    }
	
	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public Usuario getUserDeContacto() {
		return userDeContacto;
	}

	public void setUserDeContacto(Usuario userDeContacto) {
		this.userDeContacto = userDeContacto;
	}

	protected abstract void construirPanel(Contacto contacto);
}
