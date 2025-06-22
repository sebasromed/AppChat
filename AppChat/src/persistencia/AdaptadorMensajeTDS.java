package persistencia;

import modelo.Contacto;
import modelo.Mensaje;
import modelo.Mensaje.TipoMensaje;
import modelo.Usuario;
import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Enum que implementa el Adaptador DAO concreto de Mensaje para el tipo H2
 * usando el patrón Singleton.
 */
public enum AdaptadorMensajeTDS implements IAdaptadorMensajeDAO {
	INSTANCE;

	// Constantes para facilitar el manejo de entidades
	private static final String MENSAJE = "mensaje";
	private static final String CODIGO = "codigo";
	private static final String TEXTO = "texto";
	private static final String ID_EMOJI = "idEmoji";
	private static final String FECHA_ENVIO = "fechaEnvio";
	private static final String EMISOR = "emisor";
	private static final String RECEPTOR = "receptor";
	private static final String TIPO_MENSAJE = "tipoMensaje";

	private final ServicioPersistencia servPersistencia;
	private final DateTimeFormatter dateTimeFormatter;

	// Constructor del enum
	AdaptadorMensajeTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	}

	@Override
	public void addMensaje(Mensaje mensaje) {
		Entidad eMensaje = null;

		// Verificar si el mensaje ya existe
		try {
			eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		} catch (NullPointerException e) {
		}
		if (eMensaje != null)
			return;

		// registrar primero los atributos que son objetos
		AdaptadorUsuarioTDS.INSTANCE.addUsuario(mensaje.getEmisor());
		AdaptadorContactoTDS.INSTANCE.addContacto(mensaje.getReceptor());

		// Crear entidad mensaje
		eMensaje = mensajeToEntidad(mensaje);

		// Registrar entidad mensaje
		eMensaje = servPersistencia.registrarEntidad(eMensaje);
		// Asignar identificador único
		mensaje.setCodigo(eMensaje.getId());
	}

	@Override
	public boolean deleteMensaje(Mensaje mensaje) {
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());

		return servPersistencia.borrarEntidad(eMensaje);
	}

	@Override
	public void updateMensaje(Mensaje mensaje) {
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());

		for (Propiedad prop : eMensaje.getPropiedades()) {
			if (prop.getNombre().equals(CODIGO)) {
				prop.setValor(String.valueOf(mensaje.getCodigo()));
			} else if (prop.getNombre().equals(TEXTO)) {
				prop.setValor(mensaje.getTexto());
			} else if (prop.getNombre().equals(ID_EMOJI)) {
				prop.setValor(String.valueOf(mensaje.getIdEmoji()));
			} else if (prop.getNombre().equals(FECHA_ENVIO)) {
				prop.setValor(mensaje.getFechaEnvio().format(dateTimeFormatter));
			} else if (prop.getNombre().equals(EMISOR)) {
				prop.setValor(String.valueOf(mensaje.getEmisor().getCodigo()));
			} else if (prop.getNombre().equals(RECEPTOR)) {
				prop.setValor(String.valueOf(mensaje.getReceptor().getCodigo()));
			}
			servPersistencia.modificarPropiedad(prop);
		}
	}

	@Override
	public Mensaje getMensaje(int id) {
		if (PoolDAO.getUnicaInstancia().contiene(id)) {
			return (Mensaje) PoolDAO.getUnicaInstancia().getObjeto(id);
		}

		Entidad eMensaje = servPersistencia.recuperarEntidad(id);
		Mensaje mensaje = entidadToMensaje(eMensaje);
		mensaje.setCodigo(id);

		PoolDAO.getUnicaInstancia().addObjeto(id, mensaje);

		return mensaje;
	}

	@Override
	public List<Mensaje> getAllMensajes() {
		List<Entidad> eMensajes = servPersistencia.recuperarEntidades(MENSAJE);
		List<Mensaje> mensajes = new LinkedList<>();

		for (Entidad eMensaje : eMensajes) {
			mensajes.add(getMensaje(eMensaje.getId()));
		}
		return mensajes;
	}

	// ------------------- Funciones auxiliares -----------------------------

	private Mensaje entidadToMensaje(Entidad eMensaje) {
		String texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, TEXTO);
		int idEmoji = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, ID_EMOJI));
		LocalDateTime horaEnvio = LocalDateTime.parse(servPersistencia.recuperarPropiedadEntidad(eMensaje, FECHA_ENVIO),
				dateTimeFormatter);
		TipoMensaje tipoMensaje = TipoMensaje
				.valueOf(servPersistencia.recuperarPropiedadEntidad(eMensaje, TIPO_MENSAJE));
		Usuario emisor = AdaptadorUsuarioTDS.INSTANCE
				.getUsuario(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "emisor")));
		Contacto receptor = AdaptadorContactoTDS.INSTANCE
				.getContacto(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "receptor")));

		return new Mensaje(texto, idEmoji, horaEnvio, emisor, receptor, tipoMensaje);
	}

	private Entidad mensajeToEntidad(Mensaje mensaje) {
		Entidad eMensaje = new Entidad();
		eMensaje.setNombre(MENSAJE);
		eMensaje.setPropiedades(new ArrayList<>(Arrays.asList(new Propiedad(TEXTO, mensaje.getTexto()),
				new Propiedad(ID_EMOJI, String.valueOf(mensaje.getIdEmoji())),
				new Propiedad(FECHA_ENVIO, mensaje.getFechaEnvio().format(dateTimeFormatter)),
				new Propiedad(EMISOR, String.valueOf(mensaje.getEmisor().getCodigo())),
				new Propiedad(RECEPTOR, String.valueOf(mensaje.getReceptor().getCodigo())),
				new Propiedad(TIPO_MENSAJE, String.valueOf(mensaje.getTipoMensaje())))));
		return eMensaje;
	}
}