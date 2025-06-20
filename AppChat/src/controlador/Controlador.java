package controlador;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import interfaz.VentanaBuscar;
import interfaz.VentanaContactos;
import interfaz.VentanaLogin;
import interfaz.VentanaPrincipal;
import interfaz.VentanaRegistro;
import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoGrupo;
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
	
	public boolean borrarMiembroGrupo(ContactoIndividual contacto, ContactoGrupo grupo) {
		if (grupo.getMiembros().contains(contacto)) {
			Contacto grupoInicial = grupo;
			grupo.getMiembros().remove(contacto);
			adaptadorContacto.updateContacto(grupo);
			
			List<Contacto> contactos = usuarioActual.getContactos();
		    int idx = contactos.indexOf(grupoInicial);
		    if (idx != -1) {
		     	contactos.set(idx, grupo);
		     } 
		    adaptadorUsuario.updateUsuario(usuarioActual);
			return true;
		}
		return false;
	}
	
	public boolean borrarGrupo(ContactoGrupo grupo) {
		if (usuarioActual.getContactos().contains(grupo)) {
			usuarioActual.eliminarContacto(grupo.getCodigo());
			adaptadorContacto.deleteContacto(grupo);
			adaptadorUsuario.updateUsuario(usuarioActual);
			return true;
		}
		return false;
	}
	
	public ContactoIndividual getContactoDeUsuarioActual() {
	    String nombreCompleto = usuarioActual.getNombre() + " " + usuarioActual.getApellidos();
	    String telefono = usuarioActual.getTelefono();
	    return usuarioActual.getContactos().stream()
	        .filter(c -> c instanceof ContactoIndividual)
	        .map(c -> (ContactoIndividual) c)
	        .filter(ci -> ci.getNombre().equals(nombreCompleto) && ci.getTelefono().equals(telefono))
	        .findFirst()
	        .orElse(null);
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
	
	public Usuario getUsuarioDeContacto(ContactoIndividual contacto) {
		return catalogoUsuarios.getUsuario(contacto.getTelefono());
	}
	
	public Usuario getUsuarioPorTelefono(String telefono) {
		return catalogoUsuarios.getUsuario(telefono);
	}
	
	public ContactoIndividual getContactoPorTelefono(String telefono) {
		return usuarioActual.getContactos().stream()
			    .filter(c -> c instanceof ContactoIndividual)
			    .map(c -> (ContactoIndividual) c)
			    .filter(ci -> ci.getTelefono().equals(telefono))
			    .findFirst()
			    .orElse(null);
	}
	
	public ContactoIndividual getContactoPorNombre(String nombre) {
		return usuarioActual.getContactos().stream()
			    .filter(c -> c instanceof ContactoIndividual)
			    .map(c -> (ContactoIndividual) c)
			    .filter(ci -> ci.getTelefono().equals(nombre))
			    .findFirst()
			    .orElse(null);
	}
	
	public ContactoGrupo getGrupoPorNombre(String nombre) {
		return usuarioActual.getContactos().stream()
			    .filter(c -> c instanceof ContactoGrupo)
			    .map(c -> (ContactoGrupo) c)
			    .filter(cg -> cg.getNombre().equals(nombre))
			    .findFirst()
			    .orElse(null);
	}
	
	public List<Usuario> getUsuariosDeContactos(List<Contacto> contactos) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		for (Contacto c : contactos) {
			usuarios.add(catalogoUsuarios.getUsuario(c.getCodigo()));
		}
		return usuarios;
	}
	
	public static List<ContactoIndividual> getContactosIndividuales(List<Contacto> contactos) {
		return contactos.stream()
			    .filter(c -> c instanceof ContactoIndividual)
			    .map(c -> (ContactoIndividual) c)
			    .collect(Collectors.toList());
	}
	
	public boolean registrarContacto(String nombre, String telefono) {
		if (getContactoPorTelefono(telefono) != null  || getContactoPorNombre(nombre) != null) {
			return false; // El contacto ya existe o el nombre ya está en uso
		}
		else {
			ContactoIndividual nuevoContacto = new ContactoIndividual(nombre, telefono);
			adaptadorContacto.addContacto(nuevoContacto);
			
			usuarioActual.addContacto(nuevoContacto);
			adaptadorUsuario.updateUsuario(usuarioActual);
			return true;
		}
	}
	
	public boolean registrarGrupo(String nombre, String rutaImagen, List<ContactoIndividual> miembros) {
		if (getGrupoPorNombre(nombre) != null) {
			return false; // El grupo ya existe
		}
		
		ContactoGrupo contactoGrupo = new ContactoGrupo(usuarioActual, nombre, rutaImagen, miembros);
			
		adaptadorContacto.addContacto(contactoGrupo);
		usuarioActual.addContacto(contactoGrupo);
		adaptadorUsuario.updateUsuario(usuarioActual);
		return true;
	}
	
	

	public String guardarImagenPerfil(String rutaOrigen, String nombre) throws IOException {
	    File origen = new File(rutaOrigen);
	    String extension = origen.getName().substring(origen.getName().lastIndexOf('.') + 1);
	    String stringRandom = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
	    File destino = new File("imagenes", nombre + "_" + stringRandom + "." + extension); // Asi se generan nombres unicos
	    java.nio.file.Files.createDirectories(destino.getParentFile().toPath());
	    java.nio.file.Files.copy(origen.toPath(), destino.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
	    return pathSinContrabarras(destino.getPath());
	}
	
	// Ya que las rutas de windows tienen \ y el metodo para leer imagenes de recursos espera /
	private String pathSinContrabarras(String path) {
		if (path != null && !path.isEmpty()) {
			return path.replace("\\", "/");
		}
		return path;
	}
	
	public static <T extends JFrame> void cambiarVentana(JFrame ventanaActual, Supplier<T> ventanaSupplier) {
        T nuevaVentana = ventanaSupplier.get(); // Crear la nueva ventana
        nuevaVentana.setVisible(true); // Asegurar que la ventana sea visible
        if (ventanaActual != null) {
            ventanaActual.dispose(); // Cerrar la ventana actual
        }
    }
}
