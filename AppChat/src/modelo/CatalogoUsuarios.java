package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;

public enum CatalogoUsuarios {
	INSTANCE;
	private Map<String, Usuario> usuarios;
	
	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	
	
	private CatalogoUsuarios() {
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorUsuario = dao.getUsuarioDAO();
			usuarios = new HashMap<String, Usuario>();
			this.cargarCatalogo();
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}
	public List<Usuario> getUsuarios(){
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		for (Usuario u:usuarios.values()) 
			lista.add(u);
		return lista;
	}
	
	public Usuario getUsuario(int codigo) {
		for (Usuario u:usuarios.values()) {
			if (u.getCodigo()==codigo) return u;
		}
		return null;
	}
	public Usuario getUsuario(String telefono) {
		return usuarios.get(telefono); 
	}

	public void addUsuario(Usuario usuario) {
		usuarios.put(usuario.getTelefono(), usuario);
	}
	
	public void removeUsuario(Usuario usuario) {
		usuarios.remove(usuario.getTelefono());
	}
	
	private void cargarCatalogo() {
		List<Usuario> usuariosBD = adaptadorUsuario.getAllUsuarios();
		for (Usuario usuario : usuariosBD)
			usuarios.put(usuario.getTelefono(), usuario);
	}	
	
	
}
