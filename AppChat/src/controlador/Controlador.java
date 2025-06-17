package controlador;

import java.time.LocalDate;

import javax.swing.JFrame;

import interfaz.VentanaBuscar;
import interfaz.VentanaContactos;
import interfaz.VentanaLogin;
import interfaz.VentanaPrincipal;
import interfaz.VentanaRegistro;
import modelo.CatalogoContactos;
import modelo.CatalogoMensajes;
import modelo.CatalogoUsuarios;
import modelo.Usuario;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorUsuarioDAO;

public enum Controlador {
	INSTANCE;
	
	private Usuario usuarioActual;
	
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorContactoDAO adaptadorContacto;
	private IAdaptadorMensajeDAO adaptadorMensaje;
	
	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoContactos catalogoContactos;
	private CatalogoMensajes catalogoMensajes;
	
	private Controlador() {
		usuarioActual = null;
		inicializarAdaptadores();
		inicializarCatalogos();
	}
	
	
	
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorContacto = factoria.getContactoDAO();
		adaptadorMensaje = factoria.getMensajeDAO();
	}

	private void inicializarCatalogos() {
		catalogoUsuarios = CatalogoUsuarios.INSTANCE;
		catalogoContactos = CatalogoContactos.INSTANCE;
		catalogoMensajes = CatalogoMensajes.INSTANCE;
	}

	public boolean esUsuarioRegistrado(String tlf) {
		return CatalogoUsuarios.INSTANCE.getUsuario(tlf) != null;
	}

	public boolean loginUsuario(String tlf, String password) {
		Usuario usuario = CatalogoUsuarios.INSTANCE.getUsuario(tlf);
		if (usuario != null && usuario.getPassword().equals(password)) {
			this.usuarioActual = usuario;
			return true;
		}
		return false;
	}
	
	public boolean registrarUsuario(String nombre, String apellidos, String tlf, String contraseña, LocalDate fechaNacimiento, String saludo, String imagen, LocalDate fechaRegistro) {
		if (esUsuarioRegistrado(tlf))
			return false;
		Usuario usuario;
		if(saludo.equals(""))
			 usuario = new Usuario(nombre, apellidos, tlf, contraseña, fechaNacimiento, imagen, fechaRegistro);
		else
			 usuario = new Usuario(nombre, apellidos, tlf, contraseña, fechaNacimiento, saludo, imagen, fechaRegistro);
		
		adaptadorUsuario.addUsuario(usuario);
		catalogoUsuarios.addUsuario(usuario);
		return true;
	}

	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getTelefono()))
			return false;

		adaptadorUsuario.deleteUsuario(usuario);
		catalogoUsuarios.removeUsuario(usuario);
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
