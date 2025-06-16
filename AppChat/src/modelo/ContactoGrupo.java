package modelo;

import java.util.List;

public class ContactoGrupo extends Contacto{
	
	private Usuario administrador;
	private String imagen; 
	private List<Contacto> miembros;
	
	// Con foto de grupo
	public ContactoGrupo(Usuario administrador, String nombre, String imagen, List<Contacto> miembros) {
		super(nombre);
		this.administrador = administrador;
		this.imagen = imagen;
		this.miembros = miembros;
	}
	// Sin foto de grupo
	public ContactoGrupo(Usuario administrador, String nombre, List<Contacto> miembros) {
		this(administrador, nombre, "ImagenDefecto.png", miembros);
	}
	
	public void añadirMiembro(Contacto contacto) {
		miembros.add(contacto);
	}
	
	public void eliminarMiembro(Contacto contacto) {
		miembros.remove(contacto);
	}
	
	// ----------------------- Getters and Setters -------------------
	
	public Usuario getAdministrador() {
		return getAdministrador();
	}
	
	public String getImagen() {
		return imagen;
	}
	
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public List<Contacto> getMiembros() {
		return miembros;
	}
	
	public void setMiembros(List<Contacto> miembros) {
		this.miembros = miembros;
	}

	
	

}
