package controlador;

import java.sql.Date;

import javax.swing.JFrame;

import interfaz.VentanaBuscar;
import interfaz.VentanaContactos;
import interfaz.VentanaLogin;
import interfaz.VentanaPrincipal;
import interfaz.VentanaRegistro;
import modelo.RepositorioUsuarios;
import modelo.Usuario;
import persistencia.AdaptadorUsuarioTDS;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class Controlador {

	private static Controlador unicaInstancia;
	
	private Usuario usuarioActual;
	private FactoriaDAO factoria;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	//Resto de adaptadores...
	
	private Controlador() {
		usuarioActual = null;
		try {
			factoria = FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}
	
	
	
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public boolean esUsuarioRegistrado(String tlf) {
		return RepositorioUsuarios.INSTANCE.findUsuario(tlf) != null;
	}

	public boolean loginUsuario(String tlf, String password) {
		Usuario usuario = RepositorioUsuarios.INSTANCE.findUsuario(tlf);
		if (usuario != null && usuario.getContraseña().equals(password)) {
			this.usuarioActual = usuario;
			return true;
		}
		return false;
	}
	
	public boolean registrarUsuario(String nombre, String apellidos, String tlf, String contraseña, Date fecha, String saludo, String imagen) {
		if (esUsuarioRegistrado(tlf))
			return false;
		Usuario usuario;
		if(saludo.equals(""))
			 usuario = new Usuario(nombre, apellidos, tlf, contraseña, fecha, imagen);
		else
			 usuario = new Usuario(nombre, apellidos, tlf, contraseña, fecha, saludo, imagen);
		
		adaptadorUsuario = factoria.getUsuarioDAO(); /* IAdaptadorDAO para almacenar el nuevo Usuario en la BD */
		adaptadorUsuario.addUsuario(usuario);
		RepositorioUsuarios.INSTANCE.addUsuario(usuario);
		this.usuarioActual = usuario;
		return true;
	}

	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getTlf()))
			return false;

		
		adaptadorUsuario = factoria.getUsuarioDAO(); /* IAdaptadorDAO para borrar el Usuario de la BD */
		adaptadorUsuario.deleteUsuario(usuario);
		RepositorioUsuarios.INSTANCE.removeUsuario(usuario);
		return true;
	}
	
	public void cambiarVentanaRegistrar(JFrame log) {
		VentanaRegistro interfaz = new VentanaRegistro(this);
		log.dispose();
	}
	
	public void cambiarVentanaLogin(JFrame log) {
		VentanaLogin interfaz = new VentanaLogin(this);
		log.dispose();
	}
	
	public void cambiarVentanaPrincipal(JFrame log) {
		VentanaPrincipal interfaz = new VentanaPrincipal(this);
		log.dispose();
	}
	
	
	public void cambiarVentanaBuscar(JFrame log) {
		VentanaBuscar interfaz = new VentanaBuscar();
		log.dispose();
	}
	
	public void cambiarVentanaAñadirContacto() {
		VentanaContactos interfaz = new VentanaContactos(this);
		
	}
	
	public void cambiarVenanaContactos() {
		VentanaContactos interfaz = new VentanaContactos(this);
	}
	
	
}
