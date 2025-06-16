package persistencia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import modelo.Usuario;
import beans.Entidad;
import beans.Propiedad;

/**
 * 
 * Clase que implementa el Adaptador DAO concreto de Usuario para el tipo H2.
 * 
 */
public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO {

	private ServicioPersistencia servPersistencia;
	private DateTimeFormatter dateFormat;

	public AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	}

	private Usuario entidadToUsuario(Entidad eUsuario) {

		String nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre");
		String apellidos = servPersistencia.recuperarPropiedadEntidad(eUsuario, "apellidos");
		String telefono = servPersistencia.recuperarPropiedadEntidad(eUsuario, "telefono");
		String password = servPersistencia.recuperarPropiedadEntidad(eUsuario, "contraseña");
		LocalDate fechaNacimiento = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento"), dateFormat);
		String saludo = servPersistencia.recuperarPropiedadEntidad(eUsuario, "saludo");
		String imagen = servPersistencia.recuperarPropiedadEntidad(eUsuario, "imagen");
		boolean premium = Boolean.valueOf(servPersistencia.recuperarPropiedadEntidad(eUsuario, "premium"));
		LocalDate fechaRegistro = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaRegistro"), dateFormat);
		

		Usuario usuario = new Usuario(nombre, apellidos, telefono, password, fechaNacimiento, saludo, imagen, fechaRegistro);
		usuario.setCodigo(eUsuario.getCodigo());

		return usuario;
	}

	private Entidad usuarioToEntidad(Usuario usuario) {
		Entidad eUsuario = new Entidad();
		eUsuario.setNombre(USUARIO);

		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad(NOMBRE, usuario.getNombre()),
				new Propiedad(APELLIDOS, usuario.getApellidos()), new Propiedad(EMAIL, usuario.getEmail()),
				new Propiedad(LOGIN, usuario.getLogin()), new Propiedad(PASSWORD, usuario.getPassword()),
				new Propiedad(FECHA_NACIMIENTO, usuario.getFechaNacimiento()))));
		return eUsuario;
	}

	public void create(Usuario usuario) {
		Entidad eUsuario = this.usuarioToEntidad(usuario);
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		usuario.setCodigo(eUsuario.getId());
	}
	*/
	/*
	public void registrarUsuario(Usuario usuario) {
		Entidad eUsuario;
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true;
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe)
			return;

		// registrar primero los atributos que son objetos
		// registrar contactos
		//AdaptadorContactoIndividualTDS adaptadorCI = AdaptadorContactoIndividualTDS.getUnicaInstancia();
		//for (ContactoIndividual ci : usuario.getContactos())
			//adaptadorCI.registrarContactoIndividual(ci);

		// registrar grupos
		//AdaptadorContactoGrupoTDS adaptadorCG = AdaptadorContactoGrupoTDS.getUnicaInstancia();
		//for (ContactoGrupo cg : usuario.getGrupos())
			//adaptadorCG.registrarContactoGrupo(cg);

		// Crear entidad usuario
		eUsuario = new Entidad();
		eUsuario.setNombre(USUARIO);
		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(NOMBRE, usuario.getNombre()),
				new Propiedad(FECHA_NACIMIENTO, dateFormat.format(usuario.getFecha())),
				new Propiedad(TELEFONO, usuario.getTlf()),
				new Propiedad(NOMBRE, usuario.getNombre()),
				new Propiedad(PASSWORD, usuario.getContraseña()),
				new Propiedad(IMAGEN, usuario.getImagen()),
				new Propiedad(SALUDO, usuario.getSaludo()),
				new Propiedad(PREMIUM, String.valueOf(usuario.isPremium())),
				new Propiedad(CONTACTOS, Auxiliar.obtenerCodigos(usuario.getContactos())),
				new Propiedad(GRUPOS, Auxiliar.obtenerCodigos(usuario.getGrupos())))));

		// registrar entidad usuario
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		usuario.setCodigo(eUsuario.getId());
	}
	public boolean delete(Usuario usuario) {
		Entidad eUsuario;
		eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());

		return servPersistencia.borrarEntidad(eUsuario);
	}

	/**
	 * Permite que un Usuario modifique su perfil: password y email
	 
	public void update(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());

		for (Propiedad prop : eUsuario.getPropiedades()) {
			if (prop.getNombre().equals(PASSWORD)) {
				prop.setValor(usuario.getPassword());
			} else if (prop.getNombre().equals(EMAIL)) {
				prop.setValor(usuario.getEmail());
			} else if (prop.getNombre().equals(NOMBRE)) {
				prop.setValor(usuario.getNombre());
			} else if (prop.getNombre().equals(APELLIDOS)) {
				prop.setValor(usuario.getApellidos());
			} else if (prop.getNombre().equals(LOGIN)) {
				prop.setValor(usuario.getLogin());
			} else if (prop.getNombre().equals(FECHA_NACIMIENTO)) {
				prop.setValor(dateFormat.format(usuario.getFechaNacimiento()));
			}
			servPersistencia.modificarPropiedad(prop);
		}
	}

	public Usuario get(int id) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(id);

		return entidadToUsuario(eUsuario);
	}
*/
	/*
	public List<Usuario> getAll() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades(USUARIO);

		List<Usuario> usuarios = new LinkedList<Usuario>();
		for (Entidad eUsuario : entidades) {
			usuarios.add(get(eUsuario.getId()));
		}

		return usuarios;
	}
	*/
	@Override
	public void addUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deleteUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario getUsuario(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> getAllUsuarios() {
		// TODO Auto-generated method stub
		return null;
	}

}
