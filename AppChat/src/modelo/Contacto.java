package modelo;
import java.util.List;
import java.util.LinkedList;

public abstract class Contacto {
	
	private int codigo;
	private String nombre;
	private List<Mensaje> mensajes;
	
	protected Contacto(String nombre) {
		this.codigo = 0;
		this.nombre = nombre;
		this.mensajes = new LinkedList<Mensaje>();
	}

	public int getCodigo() {
		return codigo;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
	
	
	
}
