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
	}


	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
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
         Graphics2D g2d = (Graphics2D) g;

         // Hacer que los bordes sean suaves
        // g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

         // Crear un círculo
         Ellipse2D circle = new Ellipse2D.Double(0, 0, diametro, diametro);

         // Recortar la imagen dentro del círculo
         g2d.setClip(circle);

         // Escalar la imagen para que se ajuste al círculo
         g2d.drawImage(image, 0, 0, diametro, diametro, this);
	 }

}
