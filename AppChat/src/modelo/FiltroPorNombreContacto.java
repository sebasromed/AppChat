package modelo;

import java.util.List;
import java.util.stream.Collectors;

public class FiltroPorNombreContacto implements FiltroBusqueda {
	private String nombre;
	private FiltroBusqueda filtro;

	public FiltroPorNombreContacto(FiltroBusqueda filtro, String nombre) {
		this.filtro = filtro;
		this.nombre = nombre;
	}

	@Override
	public List<Mensaje> filtrar(List<Mensaje> mensajes) {
		List<Mensaje> mensajesFiltrados = filtro.filtrar(mensajes);
		if (nombre == null || nombre.isEmpty()) {
			return mensajesFiltrados;
		}
		return mensajesFiltrados.stream().filter(
				m -> m.getEmisor().getNombreCompleto().equals(nombre) || m.getReceptor().getNombre().equals(nombre))
				.collect(Collectors.toList());
	}
}
