package interfaz;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

	public FotoPerfil(String imagePath, int size) {
		this(loadImageFromPath(imagePath), size);
	}

	public FotoPerfil(File imageFile, int size) {
		this(loadImageFromFile(imageFile), size);
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

	private static BufferedImage loadImageFromPath(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			return null;
		}
	}

	private static BufferedImage loadImageFromFile(File file) {
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			return null;
		}
	}

	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		return bimage;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setColor(Color.WHITE);
		g2.fillOval(0, 0, diametro, diametro);
		if (image != null) {
			Shape oldClip = g2.getClip();
			g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, diametro, diametro));
			g2.drawImage(image, 0, 0, diametro, diametro, null);
			g2.setClip(oldClip);
		}
		g2.setColor(Color.LIGHT_GRAY);
		g2.setStroke(new BasicStroke(1f));
		g2.drawOval(0, 0, diametro - 1, diametro - 1);
		g2.dispose();
	}
}
