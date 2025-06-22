package persistencia;

import modelo.Usuario;
import java.util.List;

public interface IAdaptadorUsuarioDAO {

	void addUsuario(Usuario usuario);

	boolean deleteUsuario(Usuario usuario);

	void updateUsuario(Usuario usuario);

	Usuario getUsuario(int id);

	List<Usuario> getAllUsuarios();

}
