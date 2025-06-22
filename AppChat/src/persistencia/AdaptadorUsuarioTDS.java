package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import modelo.Contacto;
import modelo.Usuario;
import beans.Entidad;
import beans.Propiedad;

/**
 * 
 * Clase que implementa el Adaptador DAO concreto de Usuario para el tipo H2.
 * 
 */
public enum AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO {
	INSTANCE;

	// Constantes para facilitar el manejo de entidades
	private static final String USUARIO = "usuario";
	private static final String CODIGO = "codigo";
	private static final String NOMBRE = "nombre";
	private static final String APELLIDOS = "apellidos";
	private static final String TELEFONO = "telefono";
	private static final String PASSWORD = "password";
	private static final String FECHA_NACIMIENTO = "fechaNacimiento";
	private static final String IMAGEN = "imagen";
	private static final String PREMIUM = "premium";
	private static final String SALUDO = "saludo";
	private static final String CONTACTOS = "contactos";
	private static final String FECHA_REGISTRO = "fehcaRegistro";

	private ServicioPersistencia servPersistencia;
	private DateTimeFormatter dateFormat;

	AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	}

	@Override
	public void addUsuario(Usuario usuario) {
		Entidad eUsuario = null;

		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		} catch (NullPointerException e) {
		}
		if (eUsuario != null)
			return;

		// registrar primero los atributos que son objetos
		AdaptadorContactoTDS adaptadorC = AdaptadorContactoTDS.INSTANCE;
		for (Contacto c : usuario.getContactos())
			adaptadorC.addContacto(c);

		// Crear entidad usuario
		eUsuario = usuarioToEntidad(usuario);

		// registrar entidad usuario
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		usuario.setCodigo(eUsuario.getId());
	}

	@Override
	public boolean deleteUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());

		return servPersistencia.borrarEntidad(eUsuario);
	}

	@Override
	public void updateUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());

		for (Propiedad prop : eUsuario.getPropiedades()) {
			if (prop.getNombre().equals(CODIGO)) {
				prop.setValor(String.valueOf(usuario.getCodigo()));
			} else if (prop.getNombre().equals(NOMBRE)) {
				prop.setValor(usuario.getNombre());
			} else if (prop.getNombre().equals(APELLIDOS)) {
				prop.setValor(usuario.getApellidos());
			} else if (prop.getNombre().equals(TELEFONO)) {
				prop.setValor(usuario.getTelefono());
			} else if (prop.getNombre().equals(PASSWORD)) {
				prop.setValor(usuario.getPassword());
			} else if (prop.getNombre().equals(FECHA_NACIMIENTO)) {
				prop.setValor(usuario.getFechaNacimiento().format(dateFormat));
			} else if (prop.getNombre().equals(SALUDO)) {
				prop.setValor(usuario.getSaludo());
			} else if (prop.getNombre().equals(IMAGEN)) {
				prop.setValor(usuario.getImagen());
			} else if (prop.getNombre().equals(PREMIUM)) {
				prop.setValor(String.valueOf(usuario.isPremium()));
			} else if (prop.getNombre().equals(CONTACTOS)) {
				prop.setValor(obtenerCodigosContactos(usuario.getContactos()));
			} else if (prop.getNombre().equals(FECHA_REGISTRO)) {
				prop.setValor(usuario.getFechaRegistro().format(dateFormat));
			}

			servPersistencia.modificarPropiedad(prop);
		}
	}

	@Override
	public Usuario getUsuario(int id) {
		if (PoolDAO.getUnicaInstancia().contiene(id)) {
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(id);
		}

		Entidad eUsuario = servPersistencia.recuperarEntidad(id);
		Usuario usuario = entidadToUsuario(eUsuario);
		usuario.setCodigo(id);

		PoolDAO.getUnicaInstancia().addObjeto(id, usuario);

		List<Contacto> contactos = new LinkedList<Contacto>();
		contactos = obtenerContactosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "contactos"));

		for (Contacto c : contactos) {
			usuario.addContacto(c);
		}

		return usuario;
	}

	@Override
	public List<Usuario> getAllUsuarios() {
		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");
		List<Usuario> usuarios = new LinkedList<Usuario>();

		for (Entidad eUsuario : eUsuarios) {
			usuarios.add(getUsuario(eUsuario.getId()));
		}
		return usuarios;
	}

	// -------------------Funciones auxiliares-----------------------------
	private Usuario entidadToUsuario(Entidad eUsuario) {

		String nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE);
		String apellidos = servPersistencia.recuperarPropiedadEntidad(eUsuario, APELLIDOS);
		String telefono = servPersistencia.recuperarPropiedadEntidad(eUsuario, TELEFONO);
		String password = servPersistencia.recuperarPropiedadEntidad(eUsuario, PASSWORD);
		LocalDate fechaNacimiento = LocalDate
				.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, FECHA_NACIMIENTO), dateFormat);
		String saludo = servPersistencia.recuperarPropiedadEntidad(eUsuario, SALUDO);
		String imagen = servPersistencia.recuperarPropiedadEntidad(eUsuario, IMAGEN);
		boolean premium = Boolean.valueOf(servPersistencia.recuperarPropiedadEntidad(eUsuario, PREMIUM));
		LocalDate fechaRegistro = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, FECHA_REGISTRO),
				dateFormat);

		Usuario usuario = new Usuario(nombre, apellidos, telefono, password, fechaNacimiento, saludo, imagen,
				fechaRegistro);
		usuario.setPremium(premium);

		return usuario;
	}

	private Entidad usuarioToEntidad(Usuario usuario) {
		Entidad eUsuario = new Entidad();
		eUsuario.setNombre(USUARIO);
		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad(NOMBRE, usuario.getNombre()),
				new Propiedad(APELLIDOS, usuario.getApellidos()), new Propiedad(TELEFONO, usuario.getTelefono()),
				new Propiedad(PASSWORD, usuario.getPassword()),
				new Propiedad(FECHA_NACIMIENTO, usuario.getFechaNacimiento().format(dateFormat)),
				new Propiedad(SALUDO, usuario.getSaludo()), new Propiedad(IMAGEN, usuario.getImagen()),
				new Propiedad(PREMIUM, String.valueOf(usuario.isPremium())),
				new Propiedad(CONTACTOS, obtenerCodigosContactos(usuario.getContactos())),
				new Propiedad(FECHA_REGISTRO, usuario.getFechaRegistro().format(dateFormat)))));
		return eUsuario;
	}

	private String obtenerCodigosContactos(List<Contacto> contactos) {
		String lineas = "";
		for (Contacto contacto : contactos) {
			lineas += contacto.getCodigo() + " ";
		}
		return lineas.trim();
	}

	private List<Contacto> obtenerContactosDesdeCodigos(String lineas) {
		List<Contacto> contactos = new LinkedList<Contacto>();
		StringTokenizer strTok = new StringTokenizer(lineas, " ");
		AdaptadorContactoTDS adaptadorC = AdaptadorContactoTDS.INSTANCE;
		while (strTok.hasMoreTokens()) {
			contactos.add(adaptadorC.getContacto(Integer.valueOf((String) strTok.nextElement())));
		}
		return contactos;
	}

}
