package modelo;

import java.util.List;
import java.util.stream.Collectors;

public class FiltroPorTexto implements FiltroBusqueda {

	private String texto;
	private FiltroBusqueda filtro;

	public FiltroPorTexto(FiltroBusqueda filtro, String texto) {
		this.filtro = filtro;
		this.texto = texto;
	}

	@Override
	public List<Mensaje> filtrar(List<Mensaje> mensajes) {
		List<Mensaje> mensajesFiltrados = filtro.filtrar(mensajes);
		if (texto == null || texto.isEmpty()) {
			return mensajesFiltrados;
		}
		return mensajesFiltrados.stream().filter(m -> m.getTexto().contains(texto)).collect(Collectors.toList());
	}

}
