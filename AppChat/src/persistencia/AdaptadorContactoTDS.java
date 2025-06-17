package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import modelo.Contacto;
import modelo.ContactoGrupo;
import modelo.ContactoIndividual;
import modelo.Mensaje;
import beans.Entidad;
import beans.Propiedad;

/**
 * 
 * Clase que implementa el Adaptador DAO concreto de Usuario para el tipo H2.
 * 
 */
public enum AdaptadorContactoTDS implements IAdaptadorContactoDAO {
	INSTANCE;
	
	// Constantes para facilitar el manejo de entidades
	private static final String CONTACTO = "contacto";
	private static final String TIPO = "tipo";
	private static final String CONTACTO_INDIVIDUAL = "contactoIndividual";
	private static final String CONTACTO_GRUPO = "contactoGrupo";
	private static final String CODIGO = "codigo";
	private static final String NOMBRE = "nombre";
	private static final String MENSAJES = "mensajes";
	private static final String TELEFONO = "telefono";
	private static final String ADMINISTRADOR = "administrador";
	private static final String IMAGEN = "imagen";
	private static final String MIEMBROS = "miembros";

	private ServicioPersistencia servPersistencia;

	AdaptadorContactoTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	 
	@Override
	public void addContacto(Contacto contacto) {
		Entidad eContacto = null;
		
		try {
			eContacto = servPersistencia.recuperarEntidad(contacto.getCodigo());
		} catch (NullPointerException e) {}
		if (eContacto != null) return;

		// registrar primero los atributos que son objetos
		if (contacto instanceof ContactoGrupo) {
			ContactoGrupo grupo = (ContactoGrupo) contacto;
			for (ContactoIndividual c : grupo.getMiembros())
				addContacto(c);
		}
		
		AdaptadorMensajeTDS adaptadorM = AdaptadorMensajeTDS.INSTANCE;
		for (Mensaje m : contacto.getMensajes())
			adaptadorM.addMensaje(m);

		// Crear entidad usuario
		eContacto = contactoToEntidad(contacto);

		// registrar entidad usuario
		eContacto = servPersistencia.registrarEntidad(eContacto);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		contacto.setCodigo(eContacto.getId());
	}

	@Override
	public boolean deleteContacto(Contacto contacto) {
		Entidad eContacto = servPersistencia.recuperarEntidad(contacto.getCodigo());
		
		return servPersistencia.borrarEntidad(eContacto);
	}

	@Override
	public void updateContacto(Contacto contacto) {
		Entidad eContacto = servPersistencia.recuperarEntidad(contacto.getCodigo());

		for (Propiedad prop : eContacto.getPropiedades()) {
			if (prop.getNombre().equals(CODIGO)) {
				prop.setValor(String.valueOf(contacto.getCodigo()));
			} else if (prop.getNombre().equals(NOMBRE)) {
				prop.setValor(contacto.getNombre());
			} else if (prop.getNombre().equals(TIPO)) {
				prop.setValor(contacto instanceof ContactoIndividual ? CONTACTO_INDIVIDUAL : CONTACTO_GRUPO);
			} else if (prop.getNombre().equals(TELEFONO) && contacto instanceof ContactoIndividual) {
				prop.setValor(((ContactoIndividual) contacto).getTelefono());
			} else if (prop.getNombre().equals(ADMINISTRADOR) && contacto instanceof ContactoGrupo) {
				prop.setValor(String.valueOf(((ContactoGrupo) contacto).getAdministrador().getCodigo()));
			} else if (prop.getNombre().equals(IMAGEN) && contacto instanceof ContactoGrupo) {
				prop.setValor(((ContactoGrupo) contacto).getImagen());
			} else if (prop.getNombre().equals(MIEMBROS) && contacto instanceof ContactoGrupo) {
				prop.setValor(obtenerCodigosMiembros(((ContactoGrupo) contacto).getMiembros()));
			} else if (prop.getNombre().equals(MENSAJES)) {
				prop.setValor(obtenerCodigosMensajes(contacto.getMensajes()));
			}
			servPersistencia.modificarPropiedad(prop);
		}
	}

	@Override
	public Contacto getContacto(int id) {
        if (PoolDAO.getUnicaInstancia().contiene(id)) {
            return (ContactoIndividual) PoolDAO.getUnicaInstancia().getObjeto(id);
        }

        Entidad eContacto = servPersistencia.recuperarEntidad(id);
        Contacto contacto = entidadToContacto(eContacto);
        contacto.setCodigo(id);

        PoolDAO.getUnicaInstancia().addObjeto(id, contacto);

        List<Mensaje> mensajes = obtenerMensajesDesdeCodigos(
            servPersistencia.recuperarPropiedadEntidad(eContacto, MENSAJES));
        contacto.setMensajes(mensajes);

        if (contacto instanceof ContactoGrupo) {
            List<ContactoIndividual> miembros = obtenerMiembrosDesdeCodigos(
                servPersistencia.recuperarPropiedadEntidad(eContacto, MIEMBROS));
            ((ContactoGrupo) contacto).setMiembros(miembros);
        }
        
        

        return contacto;
    }

	@Override
	public List<Contacto> getAllContactos() {
		List<Entidad> eContactos = servPersistencia.recuperarEntidades(CONTACTO);
		List<Contacto> contactos = new LinkedList<>();

		for (Entidad eContacto : eContactos) {
			contactos.add(getContacto(eContacto.getId()));
		}
		return contactos;
	}
	
	
	// -------------------Funciones auxiliares-----------------------------
	private Contacto entidadToContacto(Entidad eContacto) {
        String nombre = servPersistencia.recuperarPropiedadEntidad(eContacto, NOMBRE);
        String tipo = servPersistencia.recuperarPropiedadEntidad(eContacto, TIPO);

        if (CONTACTO_INDIVIDUAL.equals(tipo)) {
            String numeroTelefono = servPersistencia.recuperarPropiedadEntidad(eContacto, TELEFONO);
            return new ContactoIndividual(nombre, numeroTelefono);
        } else {
        	ContactoIndividual administrador = obtenerMiembroDesdeCodigo(servPersistencia.recuperarPropiedadEntidad(eContacto, ADMINISTRADOR));
            String imagen = servPersistencia.recuperarPropiedadEntidad(eContacto, IMAGEN);
            List<ContactoIndividual> miembros = obtenerMiembrosDesdeCodigos(
                servPersistencia.recuperarPropiedadEntidad(eContacto, MIEMBROS));
            return new ContactoGrupo(administrador, nombre, imagen, miembros);
        }
    }

	private Entidad contactoToEntidad(Contacto contacto) {
		Entidad eContacto = new Entidad();
		eContacto.setNombre(CONTACTO);

		List<Propiedad> propiedades = new ArrayList<>(Arrays.asList(
			new Propiedad(NOMBRE, contacto.getNombre()),
			new Propiedad(TIPO, contacto instanceof ContactoIndividual ? CONTACTO_INDIVIDUAL : CONTACTO_GRUPO),
			new Propiedad(MENSAJES, obtenerCodigosMensajes(contacto.getMensajes()))
		));

		if (contacto instanceof ContactoIndividual) {
			propiedades.add(new Propiedad(TELEFONO, ((ContactoIndividual) contacto).getTelefono()));
			propiedades.add(new Propiedad(IMAGEN, ""));
			propiedades.add(new Propiedad(MIEMBROS, ""));
		} else if (contacto instanceof ContactoGrupo) {
			propiedades.add(new Propiedad(TELEFONO, ""));
			propiedades.add(new Propiedad(IMAGEN, ((ContactoGrupo) contacto).getImagen()));
			propiedades.add(new Propiedad(MIEMBROS, obtenerCodigosMiembros(((ContactoGrupo) contacto).getMiembros())));
		}

		eContacto.setPropiedades(propiedades);
		return eContacto;
	}
	
	private String obtenerCodigosMiembros(List<ContactoIndividual> miembros) {
		String lineas = "";
		for (ContactoIndividual miembro : miembros) {
			lineas += miembro.getCodigo() + " ";
		}
		return lineas.trim();
	}
	
	private List<ContactoIndividual> obtenerMiembrosDesdeCodigos(String lineas) {
		List<ContactoIndividual> miembros = new LinkedList<>();

		StringTokenizer strTok = new StringTokenizer(lineas, " ");
		while (strTok.hasMoreTokens()) {
			int codigo = Integer.valueOf((String) strTok.nextElement());
			Contacto contacto = getContacto(codigo);
			if (contacto instanceof ContactoIndividual) {
				miembros.add((ContactoIndividual) contacto);
			}
		}
		return miembros;
	}
	
	   private ContactoIndividual obtenerMiembroDesdeCodigo(String codigo) {
		   int id = Integer.parseInt(codigo);
		   Contacto contacto = getContacto(id);
		   if (contacto instanceof ContactoIndividual) {
			   return (ContactoIndividual) contacto;
		   }
	        return null;
	    }
	
	private String obtenerCodigosMensajes(List<Mensaje> mensajes) {
		String lineas = "";
		for (Mensaje mensaje : mensajes) {
			lineas += mensaje.getCodigo() + " ";
		}
		return lineas.trim();
	}
	
	private List<Mensaje> obtenerMensajesDesdeCodigos(String codigos) {
        List<Mensaje> mensajes = new LinkedList<>();
        
        StringTokenizer strTok = new StringTokenizer(codigos, " ");
        AdaptadorMensajeTDS adaptadorM = AdaptadorMensajeTDS.INSTANCE;
        while (strTok.hasMoreTokens()) {
        	mensajes.add(adaptadorM.getMensaje(Integer.valueOf((String) strTok.nextElement())));
        }
        return mensajes;
    }
}
