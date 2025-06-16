package interfaz;

import java.awt.Color;
import java.awt.Dimension;

import javax.imageio.ImageTypeSpecifier;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/*
 * El elmento se creará en función del tipo de chat
 * Si el chat es indivuial y esta agregado
 * o si no lo esta
 */
public class Elemento extends JPanel{
	
	public Elemento(String fileName, String nombre, String telefono) {
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		fixSize(this,250,70);
		this.setBackground(Color.WHITE);
		this.setBorder(new LineBorder(Color.BLACK, 2));
		
		JLabel lblimagen=new JLabel();
		lblimagen.setIcon(new ImageIcon(getClass().getResource("/"+fileName)));
		fixSize(lblimagen,45,54);
	
		JPanel info = new JPanel();
		info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
		info.setBackground(getBackground().white);
			
		JLabel info_nombre = new JLabel(nombre);
		fixSize(info_nombre, 200, 30);
		
		JLabel info_msg = new JLabel("mensaje....");
		fixSize(info_msg, 200, 25);
		info_msg.setBorder(new LineBorder(Color.BLACK, 2));
		
		info.add(info_nombre);
		info.add(info_msg);
		
		this.add(lblimagen);
		this.add(Box.createRigidArea(new Dimension(5,5)));
		this.add(info);
	}
	
	private void fixSize(JComponent c, int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}
}
