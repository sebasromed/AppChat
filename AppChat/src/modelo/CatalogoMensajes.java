package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorMensajeDAO;

/**
 * Enum que implementa el catálogo de mensajes como un Singleton, gestionando una caché en memoria
 * de objetos Mensaje.
 */
public enum CatalogoMensajes {
    INSTANCE;

    private Map<Integer, Mensaje> mensajes;
    private FactoriaDAO dao;
    private IAdaptadorMensajeDAO adaptadorMensaje;

    private CatalogoMensajes() {
        try {
            dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
            adaptadorMensaje = dao.getMensajeDAO();
            mensajes = new HashMap<Integer, Mensaje>();
            this.cargarCatalogo();
        } catch (DAOException eDAO) {
            eDAO.printStackTrace();
        }
    }

    /**
     * Devuelve una lista con todos los mensajes almacenados.
     * @return Lista de mensajes.
     */
    public List<Mensaje> getMensajes() {
        ArrayList<Mensaje> lista = new ArrayList<Mensaje>();
        for (Mensaje m : mensajes.values()) {
            lista.add(m);
        }
        return lista;
    }

    /**
     * Devuelve el mensaje correspondiente al código especificado.
     * @param codigo Identificador único del mensaje.
     * @return Mensaje si existe, null en caso contrario.
     */
    public Mensaje getMensaje(int codigo) {
        return mensajes.get(codigo);
    }

    /**
     * Añade un mensaje al catálogo y lo persiste en la base de datos.
     * @param mensaje Mensaje a añadir.
     */
    public void addMensaje(Mensaje mensaje) {
        mensajes.put(mensaje.getCodigo(), mensaje);
        adaptadorMensaje.addMensaje(mensaje);
    }

    /**
     * Elimina un mensaje del catálogo y de la base de datos.
     * @param mensaje Mensaje a eliminar.
     */
    public void removeMensaje(Mensaje mensaje) {
        mensajes.remove(mensaje.getCodigo());
        adaptadorMensaje.deleteMensaje(mensaje);
    }

    /**
     * Carga todos los mensajes desde la base de datos al inicializar el catálogo.
     */
    private void cargarCatalogo() {
        List<Mensaje> mensajesBD = adaptadorMensaje.getAllMensajes();
        for (Mensaje mensaje : mensajesBD) {
            mensajes.put(mensaje.getCodigo(), mensaje);
        }
    }
}