package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoDAO;

/**
 * Enum que implementa el catálogo de contactos como un Singleton, gestionando una caché en memoria
 * de objetos Contacto (ContactoIndividual y ContactoGrupo).
 */
public enum CatalogoContactos {
    INSTANCE;

    private Map<Integer, Contacto> contactos;
    private FactoriaDAO dao;
    private IAdaptadorContactoDAO adaptadorContacto;

    private CatalogoContactos() {
        try {
            dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
            adaptadorContacto = dao.getContactoDAO();
            contactos = new HashMap<Integer, Contacto>();
            this.cargarCatalogo();
        } catch (DAOException eDAO) {
            eDAO.printStackTrace();
        }
    }

    /**
     * Devuelve una lista con todos los contactos almacenados.
     * @return Lista de contactos.
     */
    public List<Contacto> getContactos() {
        ArrayList<Contacto> lista = new ArrayList<Contacto>();
        for (Contacto c : contactos.values()) {
            lista.add(c);
        }
        return lista;
    }

    /**
     * Devuelve el contacto correspondiente al código especificado.
     * @param codigo Identificador único del contacto.
     * @return Contacto si existe, null en caso contrario.
     */
    public Contacto getContacto(int codigo) {
        return contactos.get(codigo);
    }

    /**
     * Añade un contacto al catálogo y lo persiste en la base de datos.
     * @param contacto Contacto a añadir.
     */
    public void addContacto(Contacto contacto) {
        contactos.put(contacto.getCodigo(), contacto);
        adaptadorContacto.addContacto(contacto);
    }

    /**
     * Elimina un contacto del catálogo y de la base de datos.
     * @param contacto Contacto a eliminar.
     */
    public void removeContacto(Contacto contacto) {
        contactos.remove(contacto.getCodigo());
        adaptadorContacto.deleteContacto(contacto);
    }

    /**
     * Carga todos los contactos desde la base de datos al inicializar el catálogo.
     */
    private void cargarCatalogo() {
        List<Contacto> contactosBD = adaptadorContacto.getAllContactos();
        for (Contacto contacto : contactosBD) {
            contactos.put(contacto.getCodigo(), contacto);
        }
    }
}
