package modelo;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Usuario {
	
	private int codigo;
	private String nombre;
	private String apellidos;
	private String telefono;
	private String password;
	private LocalDate fechaNacimiento;
	private String saludo;
	private String imagen;
	private boolean premium;
	private List<Contacto> contactos;
	private LocalDate fechaRegistro;
	
	public Usuario(String nombre, String apellidos, String telefono, String password, LocalDate fechaNacimiento, String saludo,
			String imagen, LocalDate fechaRegistro) {
		
		this.codigo = 0;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.password = password;
		this.fechaNacimiento = fechaNacimiento;
		this.saludo = saludo;
		this.imagen = imagen;
		this.premium = false;
		this.contactos = new LinkedList<Contacto>();
		this.fechaRegistro = fechaRegistro;
	}

	public Usuario(String nombre, String apellidos, String telefono, String password, LocalDate fechaNacimiento, String imagen, LocalDate fechaRegistro) {
		this(nombre, apellidos, telefono, password, fechaNacimiento, "Saludo por defecto", imagen, fechaRegistro);
	}
	
	public void addContacto(Contacto contacto) {
		contactos.add(contacto);
	}
	
	public boolean eliminarContacto(int codigo) {
		Contacto c = getContacto(codigo);
		if (c==null) return false;
		contactos.remove(c);
		return true;
	}
	
	public Contacto modifyContacto(int codigo, String nick) {
		Contacto c = getContacto(codigo);
		contactos.remove(c);
		c.setNombre(nick);
		contactos.add(c);
		return c;
	}
	
	public Contacto getContacto(int codigo)
	{
		try {
		return contactos.stream()
				.filter(c -> c.getCodigo() == codigo).collect(Collectors.toList()).get(0);
		} catch (Exception e){
			return null;
		}
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public void setSaludo(String saludo) {
		this.saludo = saludo;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}
	
	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public int getCodigo() {
		return codigo;
	}
	
	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getPassword() {
		return password;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getSaludo() {
		return saludo;
	}

	public String getImagen() {
		return imagen;
	}
	
	public List<Contacto> getContactos() {
		return contactos;
	}

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}
	
	public boolean isPremium() {
		return premium;
	}
	
	
	
	
	
	
	

}
