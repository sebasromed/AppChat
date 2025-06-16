package modelo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;

public enum RepositorioUsuarios {
	INSTANCE;
	private Map<String, Usuario> usuarios;
	
	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	
	
	private RepositorioUsuarios() {
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorUsuario = dao.getUsuarioDAO();
			usuarios = new HashMap<String, Usuario>();
			this.cargarRepo();
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}
	
	private void cargarRepo() {
		List<Usuario> usuariosBD = adaptadorUsuario.getAllUsuarios();
		for (Usuario usuario : usuariosBD)
			usuarios.put(usuario.getTelefono(), usuario);
	}
	
	
	
	public List<Usuario> findUsuarios() throws DAOException {
		return new LinkedList<Usuario>(usuarios.values());
	}
	
	public Usuario findUsuario(String telefono) {
		return usuarios.get(telefono);
	}

	public void addUsuario(Usuario usuario) {
		usuarios.put(usuario.getTelefono(), usuario);
	}
	
	public void removeUsuario(Usuario usuario) {
		usuarios.remove(usuario.getTelefono());
	}
	
	
}
