package persistencia;

import java.util.List;

import modelo.Mensaje;

/**
 * Interfaz que define las operaciones de persistencia para la entidad Mensaje en el sistema AppChat.
 */
public interface IAdaptadorMensajeDAO {

    /**
     * Añade un nuevo mensaje a la persistencia.
     * @param mensaje El mensaje a añadir.
     */
    void addMensaje(Mensaje mensaje);

    /**
     * Elimina un mensaje de la persistencia.
     * @param mensaje El mensaje a eliminar.
     * @return true si el mensaje fue eliminado, false si no existe.
     */
    boolean deleteMensaje(Mensaje mensaje);

    /**
     * Actualiza un mensaje existente en la persistencia.
     * @param mensaje El mensaje con los datos actualizados.
     */
    void updateMensaje(Mensaje mensaje);

    /**
     * Recupera un mensaje por su identificador único.
     * @param id El código del mensaje.
     * @return El mensaje correspondiente o null si no existe.
     */
    Mensaje getMensaje(int id);

    /**
     * Recupera todos los mensajes almacenados.
     * @return Lista de todos los mensajes.
     */
    List<Mensaje> getAllMensajes();
}
