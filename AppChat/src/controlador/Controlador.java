package controlador;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.JFrame;

import interfaz.VentanaBuscar;
import interfaz.VentanaContactos;
import interfaz.VentanaLogin;
import interfaz.VentanaPrincipal;
import interfaz.VentanaRegistro;
import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoIndividual;
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
	
	private Controlador() {
		usuarioActual = null;
		inicializarAdaptadores();
		inicializarCatalogos();
	}
	
	public void setUsuarioActual(Usuario usuario) {
		this.usuarioActual = usuario;
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
		
		ContactoIndividual contactoUsuarioActual = new ContactoIndividual(usuario.getNombre() + " " + usuario.getApellidos(), usuario.getTelefono());
		adaptadorContacto.addContacto(contactoUsuarioActual);
		return true;
	}

	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getTelefono()))
			return false;

		adaptadorUsuario.deleteUsuario(usuario);
		catalogoUsuarios.removeUsuario(usuario);
		return true;
	}
	
	public List<Contacto> getContactos() {
		return usuarioActual.getContactos();
	}
	
	public List<Contacto> getContactosOrdenadosAlfabeticamente() {
		return usuarioActual.getContactosOrdenadosPorNombre();
	}
	
	public List<Contacto> getContactosOrdenadosCronologicamente() {
		return usuarioActual.getContactosOrdenadosPorChatReciente();
	}
	
	public Usuario getUsuarioDeContacto(Contacto contacto) {
		return catalogoUsuarios.getUsuario(contacto.getCodigo());
	}
	
	public List<Usuario> getUsuariosDeContactos(List<Contacto> contactos) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		for (Contacto c : contactos) {
			usuarios.add(catalogoUsuarios.getUsuario(c.getCodigo()));
		}
		return usuarios;
		
	}
	
	public String guardarImagenPerfil(File origen, String telefonoUsuario) throws IOException {
		String extension = origen.getName().substring(origen.getName().lastIndexOf('.') + 1);
	    File destino = new File("imagenes", telefonoUsuario + "." + extension);
	    java.nio.file.Files.createDirectories(destino.getParentFile().toPath());
	    java.nio.file.Files.copy(origen.toPath(), destino.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
	    return destino.getPath();
	}
	
	public static <T extends JFrame> void cambiarVentana(JFrame ventanaActual, Supplier<T> ventanaSupplier) {
        T nuevaVentana = ventanaSupplier.get(); // Crear la nueva ventana
        nuevaVentana.setVisible(true); // Asegurar que la ventana sea visible
        if (ventanaActual != null) {
            ventanaActual.dispose(); // Cerrar la ventana actual
        }
    }
}
