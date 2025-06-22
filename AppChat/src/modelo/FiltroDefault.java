package modelo;

import java.util.List;

public class FiltroDefault implements FiltroBusqueda {
	@Override
	public List<Mensaje> filtrar(List<Mensaje> mensajes) {
		return mensajes;
	}
}
