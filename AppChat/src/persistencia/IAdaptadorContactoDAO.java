package persistencia;

import modelo.Contacto;
import java.util.List;

public interface IAdaptadorContactoDAO {
	
	/**
	 * Añade un nuevo contacto a la persistencia.
	 * @param contacto El contacto (ContactoIndividual o ContactoGrupo) a añadir.
	 */
	void addContacto(Contacto contacto);
	
	/**
	 * Elimina un contacto de la persistencia.
	 * @param contacto El contacto a eliminar.
	 * @return true si el contacto fue eliminado, false si no existe.
	 */
	boolean deleteContacto(Contacto contacto);
	
	/**
	 * Actualiza un contacto existente en la persistencia.
	 * @param contacto El contacto con los datos actualizados.
	 */
	void updateContacto(Contacto contacto);
	
	/**
	 * Recupera un contacto por su código.
	 * @param codigo El código único del contacto.
	 * @return El contacto (ContactoUsuario o Grupo) o null si no existe.
	 */
	Contacto getContacto(int codigo);
	
	/**
	 * Recupera todos los contactos almacenados.
	 * @return Lista de todos los contactos (ContactoUsuario y Grupo).
	 */
	List<Contacto> getAllContactos();
}