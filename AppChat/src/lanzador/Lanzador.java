package lanzador;

import java.awt.EventQueue;

import javax.swing.UIManager;

import controlador.Controlador;
import interfaz.VentanaLogin;


public class Lanzador {
	private static Controlador controlador;
	public static void main(final String[] args){
			controlador = Controlador.getUnicaInstancia();
			VentanaLogin window = new VentanaLogin(controlador);
			//controlador.setInterface(window);
	}
}