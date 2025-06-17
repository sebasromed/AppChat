package interfaz;

import java.awt.*;
import javax.swing.*;

import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class FotoPerfil extends JPanel {
	private BufferedImage image;
	private int diametro;
	
	 public FotoPerfil(BufferedImage image, int size) {
		this.image = image;
		this.diametro = size;
		setPreferredSize(new Dimension(diametro, diametro));
		setMinimumSize(new Dimension(diametro, diametro));
	    setMaximumSize(new Dimension(diametro, diametro));
	    setOpaque(false);
	}


	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
		repaint();
		revalidate();
	}

	public int getDiametro() {
		return diametro;
	}

	public void setDiametro(int diametro) {
		this.diametro = diametro;
	}


	@Override
     protected void paintComponent(Graphics g) {
		super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        // Draw white circle as background
        g2.setColor(Color.WHITE);
        g2.fillOval(0, 0, diametro, diametro);
        // Draw the image clipped to a circle
        if (image != null) {
            Shape oldClip = g2.getClip();
            g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, diametro, diametro));
            g2.drawImage(image, 0, 0, diametro, diametro, null);
            g2.setClip(oldClip);
        }
        g2.dispose();
	 }

}
