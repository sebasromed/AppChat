package controlador;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoGrupo;
import modelo.ContactoIndividual;
import modelo.Descuento;
import modelo.DescuentoComposite;
import modelo.DescuentoEnvioMensajes;
import modelo.DescuentoFechaRegistro;
import modelo.FiltroBusqueda;
import modelo.FiltroDefault;
import modelo.FiltroPorNombreContacto;
import modelo.FiltroPorTelefono;
import modelo.FiltroPorTexto;
import modelo.Mensaje;
import modelo.Mensaje.TipoMensaje;
import modelo.Usuario;
import persistencia.AdaptadorContactoTDS;
import persistencia.AdaptadorMensajeTDS;
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

	private Map<String, Supplier<Descuento>> supplierDescuento = new HashMap<>();

	public static final Color VERDE_MEDIO = new Color(76, 175, 80);
	public static final Color VERDE_CLARO = new Color(220, 248, 198);

	private CatalogoUsuarios catalogoUsuarios;

	private Controlador() {
		usuarioActual = null;
		inicializarAdaptadores();
		inicializarCatalogos();
		inicializarSuppliersDescuento();
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

	private void inicializarSuppliersDescuento() {
		supplierDescuento.put(DescuentoEnvioMensajes.class.getName(), DescuentoEnvioMensajes::new);
		supplierDescuento.put(DescuentoFechaRegistro.class.getName(), DescuentoFechaRegistro::new);
		supplierDescuento.put(DescuentoComposite.class.getName(), DescuentoComposite::new);
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

	public boolean registrarUsuario(String nombre, String apellidos, String tlf, String contraseña,
			LocalDate fechaNacimiento, String saludo, String imagen, LocalDate fechaRegistro) {
		if (esUsuarioRegistrado(tlf))
			return false;
		Usuario usuario;
		if (saludo.equals(""))
			usuario = new Usuario(nombre, apellidos, tlf, contraseña, fechaNacimiento, imagen, fechaRegistro);
		else
			usuario = new Usuario(nombre, apellidos, tlf, contraseña, fechaNacimiento, saludo, imagen, fechaRegistro);

		adaptadorUsuario.addUsuario(usuario);
		catalogoUsuarios.addUsuario(usuario);

		ContactoIndividual contactoUsuarioActual = new ContactoIndividual(
				usuario.getNombre() + " " + usuario.getApellidos(), usuario.getTelefono());
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
		return usuarioActual.getContactos().stream().filter(c -> c instanceof ContactoIndividual)
				.map(c -> (ContactoIndividual) c)
				.filter(ci -> ci.getNombre().equals(nombreCompleto) && ci.getTelefono().equals(telefono)).findFirst()
				.orElse(null);
	}

	public List<Contacto> getContactos() {
		return usuarioActual.getContactos();
	}

	// Devuelve solo los contactos que han sido agregados (excluye los que empiezan
	// por "+")
	public List<Contacto> getContactosAgregados() {
		return usuarioActual.getContactos().stream()
				.filter(c -> (c instanceof ContactoGrupo)
						|| (c instanceof ContactoIndividual && !c.getNombre().startsWith("+")))
				.collect(Collectors.toList());
	}

	public List<Contacto> getContactosOrdenadosAlfabeticamente() {
		return usuarioActual.getContactosOrdenadosPorNombre();
	}

	public List<Contacto> getContactosOrdenadosAlfabeticamente(Usuario usuario) {
		return usuario.getContactosOrdenadosPorNombre();
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

	public ContactoIndividual getContactoPorTelefono(String telefono, Usuario usuario) {
		return usuario.getContactos().stream().filter(c -> c instanceof ContactoIndividual)
				.map(c -> (ContactoIndividual) c).filter(ci -> ci.getTelefono().equals(telefono)).findFirst()
				.orElse(null);
	}

	public ContactoIndividual getContactoPorNombre(String nombre) {
		return usuarioActual.getContactos().stream().filter(c -> c instanceof ContactoIndividual)
				.map(c -> (ContactoIndividual) c).filter(ci -> ci.getTelefono().equals(nombre)).findFirst()
				.orElse(null);
	}

	public ContactoGrupo getGrupoPorNombre(String nombre) {
		return usuarioActual.getContactos().stream().filter(c -> c instanceof ContactoGrupo).map(c -> (ContactoGrupo) c)
				.filter(cg -> cg.getNombre().equals(nombre)).findFirst().orElse(null);
	}

	public List<Usuario> getUsuariosDeContactos(List<Contacto> contactos) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		for (Contacto c : contactos) {
			usuarios.add(catalogoUsuarios.getUsuario(c.getCodigo()));
		}
		return usuarios;
	}

	public static List<ContactoIndividual> getContactosIndividuales(List<Contacto> contactos) {
		return contactos.stream().filter(c -> c instanceof ContactoIndividual).map(c -> (ContactoIndividual) c)
				.collect(Collectors.toList());
	}

	public boolean registrarContacto(String nombre, String telefono, Usuario usuario) {
		ContactoIndividual existentePorTelefono = getContactoPorTelefono(telefono, usuario);
		ContactoIndividual existentePorNombre = getContactoPorNombre(nombre);

		// Si es un contacto añadido automaticamente por el sistema al recibir un
		// mensaje, permitir actualizar el nombre
		if (existentePorTelefono != null) {
			if (existentePorTelefono.getNombre().startsWith("+")) {
				usuario.getContactos().remove(existentePorTelefono);
				existentePorTelefono.setNombre(nombre);
				adaptadorContacto.updateContacto(existentePorTelefono);
				usuario.addContacto(existentePorTelefono);
				adaptadorUsuario.updateUsuario(usuario);
				return true;
			} else {
				return false; // Telefono en uso por un contacto normal
			}
		}
		if (existentePorNombre != null) {
			return false; // Nombre en uso por otro contacto
		}

		ContactoIndividual nuevoContacto = new ContactoIndividual(nombre, telefono);
		adaptadorContacto.addContacto(nuevoContacto);
		usuario.addContacto(nuevoContacto);
		adaptadorUsuario.updateUsuario(usuario);
		return true;
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

	public boolean addMiembroAGrupo(ContactoIndividual contacto, ContactoGrupo grupo) {
		if (grupo.getMiembros().contains(contacto)) {
			return false; // El contacto ya es miembro del grupo
		}
		ContactoGrupo grupoInicial = grupo;

		grupo.getMiembros().add(contacto);
		adaptadorContacto.updateContacto(grupo);

		List<Contacto> contactos = usuarioActual.getContactos();
		int idx = contactos.indexOf(grupoInicial);
		if (idx != -1) {
			contactos.set(idx, grupo);
		}
		adaptadorUsuario.updateUsuario(usuarioActual);
		return true;
	}

	public List<Mensaje> obtenerMensajes() {
		List<Mensaje> mensajes = new ArrayList<>();
		for (Contacto contacto : usuarioActual.getContactos()) {
			mensajes.addAll(contacto.getMensajes());
		}
		return mensajes.stream().sorted((m1, m2) -> m1.getFechaEnvio().compareTo(m2.getFechaEnvio()))
				.collect(Collectors.toList());
	}

	public List<Mensaje> getMensajesDeContacto(Contacto contacto) {
		return contacto.getMensajes().stream().sorted((m1, m2) -> m1.getFechaEnvio().compareTo(m2.getFechaEnvio()))
				.collect(Collectors.toList());
	}

	public List<Mensaje> buscarMensajes(String texto, String telefono, String nombreContacto) {
		List<Mensaje> mensajes = obtenerMensajes();

		FiltroBusqueda filtro = new FiltroDefault();
		if (telefono != null && !telefono.isEmpty()) {
			filtro = new FiltroPorTelefono(filtro, telefono);
		}
		if (texto != null && !texto.isEmpty()) {
			filtro = new FiltroPorTexto(filtro, texto);
		}
		if (nombreContacto != null && !nombreContacto.isEmpty()) {
			filtro = new FiltroPorNombreContacto(filtro, nombreContacto);
		}

		return filtro.filtrar(mensajes);
	}

	public boolean enviarMensaje(Contacto c1, String texto) {
		if (c1 == null || texto == null || texto.isEmpty()) {
			return false;
		}

		// 1. Se crea un mensaje de tipo ENVIADO que se añade a la lista de mensajes de
		// C1
		Mensaje enviado = new Mensaje(texto, LocalDateTime.now(), usuarioActual, c1, TipoMensaje.ENVIADO);
		if (c1 instanceof ContactoGrupo) {
			ContactoGrupo grupo = (ContactoGrupo) c1;
			// Si es un grupo, se envía a todos los miembros
			for (ContactoIndividual miembro : grupo.getMiembros()) {
				enviarMensaje(miembro, texto);
			}
			adaptadorMensaje.addMensaje(enviado);
			grupo.getMensajes().add(enviado);
			adaptadorContacto.updateContacto(grupo);
		} else {
			adaptadorMensaje.addMensaje(enviado);
			c1.getMensajes().add(enviado);
			adaptadorContacto.updateContacto(c1);

			// 2. Se busca UC1 (usuario asociado a C1)
			Usuario uc1 = catalogoUsuarios.getUsuario(((ContactoIndividual) c1).getTelefono());
			if (uc1 == null)
				return true; // No user found, nothing else to do

			// 3. Se busca o se crea C2 (contacto del UA en la lista de contactos de UC1)
			ContactoIndividual c2 = uc1.getContactos().stream().filter(c -> c instanceof ContactoIndividual)
					.map(c -> (ContactoIndividual) c).filter(ci -> ci.getTelefono().equals(usuarioActual.getTelefono()))
					.findFirst().orElse(null);

			if (c2 == null) {
				c2 = new ContactoIndividual("+" + usuarioActual.getTelefono(), usuarioActual.getTelefono());
				if (!registrarContacto(c2.getNombre(), c2.getTelefono(), uc1)) {
					return false; // Error al registrar el contacto
				}
			}

			// 4. Crear mensaje RECIBIDO para C2
			Mensaje recibido = new Mensaje(texto, LocalDateTime.now(), usuarioActual, c2, TipoMensaje.RECIBIDO);
			adaptadorMensaje.addMensaje(recibido);
			c2.getMensajes().add(recibido);
			adaptadorContacto.updateContacto(c2);

		}

		return true;
	}

	public boolean enviarMensaje(Contacto c1, int idEmoji) {
		if (c1 == null || idEmoji == -1) {
			return false;
		}

		// 1. Se crea un mensaje de tipo ENVIADO que se añade a la lista de mensajes de
		// C1
		Mensaje enviado = new Mensaje(idEmoji, LocalDateTime.now(), usuarioActual, c1, TipoMensaje.ENVIADO);
		if (c1 instanceof ContactoGrupo) {
			ContactoGrupo grupo = (ContactoGrupo) c1;
			// Si es un grupo, se envía a todos los miembros
			for (ContactoIndividual miembro : grupo.getMiembros()) {
				enviarMensaje(miembro, idEmoji);
			}
			adaptadorMensaje.addMensaje(enviado);
			grupo.getMensajes().add(enviado);
			adaptadorContacto.updateContacto(grupo);
		} else {
			adaptadorMensaje.addMensaje(enviado);
			c1.getMensajes().add(enviado);
			adaptadorContacto.updateContacto(c1);

			// 2. Se busca UC1 (usuario asociado a C1)
			Usuario uc1 = catalogoUsuarios.getUsuario(((ContactoIndividual) c1).getTelefono());
			if (uc1 == null)
				return true; // No user found, nothing else to do

			// 3. Se busca o se crea C2 (contacto del UA en la lista de contactos de UC1)
			ContactoIndividual c2 = uc1.getContactos().stream().filter(c -> c instanceof ContactoIndividual)
					.map(c -> (ContactoIndividual) c).filter(ci -> ci.getTelefono().equals(usuarioActual.getTelefono()))
					.findFirst().orElse(null);

			if (c2 == null) {
				c2 = new ContactoIndividual("+" + usuarioActual.getTelefono(), usuarioActual.getTelefono());
				if (!registrarContacto(c2.getNombre(), c2.getTelefono(), uc1)) {
					return false; // Error al registrar el contacto
				}
			}

			// 4. Crear mensaje RECIBIDO para C2
			Mensaje recibido = new Mensaje(idEmoji, LocalDateTime.now(), usuarioActual, c2, TipoMensaje.RECIBIDO);
			adaptadorMensaje.addMensaje(recibido);
			c2.getMensajes().add(recibido);
			adaptadorContacto.updateContacto(c2);

		}

		return true;
	}

	public double calcularPrecioPremium() {
		Descuento descuento = null;
		if (usuarioActual.getMensajesUltimoMes() >= 2
				&& usuarioActual.getFechaRegistro().isAfter(LocalDate.of(2025, 1, 1))) {
			Descuento descuentoCompuesto = supplierDescuento.get(DescuentoComposite.class.getName()).get();
			((DescuentoComposite) descuentoCompuesto)
					.addDescuento(supplierDescuento.get(DescuentoEnvioMensajes.class.getName()).get());
			((DescuentoComposite) descuentoCompuesto)
					.addDescuento(supplierDescuento.get(DescuentoFechaRegistro.class.getName()).get());
			usuarioActual.setPremium(true);
			adaptadorUsuario.updateUsuario(usuarioActual);

		} else if (usuarioActual.getMensajesUltimoMes() >= 5) {
			usuarioActual.setPremium(true);
			adaptadorUsuario.updateUsuario(usuarioActual);
			descuento = supplierDescuento.get(DescuentoEnvioMensajes.class.getName()).get();
		} else if (usuarioActual.getFechaRegistro().isAfter(LocalDate.of(2025, 1, 1))) {
			usuarioActual.setPremium(true);
			adaptadorUsuario.updateUsuario(usuarioActual);
			descuento = supplierDescuento.get(DescuentoFechaRegistro.class.getName()).get();
		}

		return usuarioActual.getPrecioPremium(descuento);
	}

	public void activarPremium() {
		usuarioActual.setPremium(true);
		adaptadorUsuario.updateUsuario(usuarioActual);
	}

	public void cancelarPremium() {
		usuarioActual.setPremium(false);
		adaptadorUsuario.updateUsuario(usuarioActual);
	}

	public String guardarImagenPerfil(String rutaOrigen, String nombre) throws IOException {
		File origen = new File(rutaOrigen);
		String extension = origen.getName().substring(origen.getName().lastIndexOf('.') + 1);
		String stringRandom = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
		File destino = new File("imagenes", nombre + "_" + stringRandom + "." + extension); // Asi se generan nombres
																							// unicos
		java.nio.file.Files.createDirectories(destino.getParentFile().toPath());
		java.nio.file.Files.copy(origen.toPath(), destino.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		return pathSinContrabarras(destino.getPath());
	}

	// Ya que las rutas de windows tienen \ y el metodo para leer imagenes de
	// recursos espera /
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
