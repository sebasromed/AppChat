package modelo;

import java.util.List;

public class ContactoGrupo extends Contacto{
	
	private Usuario administrador;
	private String imagen; 
	private List<ContactoIndividual> miembros;
	
	// Con foto de grupo
	public ContactoGrupo(Usuario administrador, String nombre, String imagen, List<ContactoIndividual> miembros) {
		super(nombre);
		this.administrador = administrador;
		this.imagen = imagen;
		this.miembros = miembros;
	}
	// Sin foto de grupo
	public ContactoGrupo(Usuario administrador, String nombre, List<ContactoIndividual> miembros) {
		this(administrador, nombre, "ImagenDefecto.png", miembros);
	}
	
	public void añadirMiembro(ContactoIndividual contacto) {
		miembros.add(contacto);
	}
	
	public void eliminarMiembro(Contacto contacto) {
		miembros.remove(contacto);
	}
	
	// ----------------------- Getters and Setters -------------------
	
	public Usuario getAdministrador() {
		return administrador;
	}
	
	public String getImagen() {
		return imagen;
	}
	
	public List<ContactoIndividual> getMiembros() {
		return miembros;
	}
	
	public void setAdministrador(Usuario administrador) {
		this.administrador = administrador;
	}
	
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	public void setMiembros(List<ContactoIndividual> miembros) {
		this.miembros = miembros;
	}

	
	

}
