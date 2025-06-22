package modelo;

import java.util.List;
import java.util.stream.Collectors;

public class FiltroPorTelefono implements FiltroBusqueda {
	private String telefono;
	private FiltroBusqueda filtro;

	public FiltroPorTelefono(FiltroBusqueda filtro, String telefono) {
		this.filtro = filtro;
		this.telefono = telefono;
	}

	@Override
	public List<Mensaje> filtrar(List<Mensaje> mensajes) {
		List<Mensaje> mensajesFiltrados = filtro.filtrar(mensajes);

		if (telefono == null || telefono.isEmpty()) {
			return mensajesFiltrados;
		}

		return mensajesFiltrados.stream().filter(m -> {
			if (telefono.equals(m.getEmisor().getTelefono())) {
				return true;
			}
			if (m.getReceptor() instanceof ContactoIndividual) {
				ContactoIndividual receptor = (ContactoIndividual) m.getReceptor();
				return telefono.equals(receptor.getTelefono());
			}
			return false;
		}).collect(Collectors.toList());
	}
}
