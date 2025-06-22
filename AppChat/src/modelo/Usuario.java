package modelo;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import modelo.Mensaje.TipoMensaje;

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

	private static final double PRECIO_PREMIUM = 100.0;

	public Usuario(String nombre, String apellidos, String telefono, String password, LocalDate fechaNacimiento,
			String saludo, String imagen, LocalDate fechaRegistro) {

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

	public Usuario(String nombre, String apellidos, String telefono, String password, LocalDate fechaNacimiento,
			String imagen, LocalDate fechaRegistro) {
		this(nombre, apellidos, telefono, password, fechaNacimiento, "Saludo por defecto", imagen, fechaRegistro);
	}

	public void addContacto(Contacto contacto) {
		contactos.add(contacto);
	}

	public boolean eliminarContacto(int codigo) {
		Contacto c = getContacto(codigo);
		if (c == null)
			return false;
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

	public Contacto getContacto(int codigo) {
		try {
			return contactos.stream().filter(c -> c.getCodigo() == codigo).collect(Collectors.toList()).get(0);
		} catch (Exception e) {
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

	public String getNombreCompleto() {
		return nombre + " " + apellidos;
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

	public List<Contacto> getContactosOrdenadosPorNombre() {
		return contactos.stream().sorted((c1, c2) -> c1.getNombre().compareToIgnoreCase(c2.getNombre()))
				.collect(Collectors.toList());
	}

	public List<Contacto> getContactosOrdenadosPorChatReciente() {
		return contactos.stream().sorted((c1, c2) -> {
			// If both contacts have no messages, they are considered equal
			if (c1.getMensajes().isEmpty() && c2.getMensajes().isEmpty())
				return 0;
			// If one contact has no messages, it is considered less recent
			if (c1.getMensajes().isEmpty())
				return 1;
			if (c2.getMensajes().isEmpty())
				return -1;
			// Compare the timestamps of the last messages
			return c2.getMensajes().get(c2.getMensajes().size() - 1).getFechaEnvio()
					.compareTo(c1.getMensajes().get(c1.getMensajes().size() - 1).getFechaEnvio());
		}).collect(Collectors.toList());
	}

	public int getMensajesUltimoMes() {
		LocalDate fechaActual = LocalDate.now();
		LocalDate mesActual = fechaActual.withDayOfMonth(1);
		LocalDate primerDiaUltimoMes = mesActual.minusMonths(1);
		LocalDate ultimoDiaUltimoMes = mesActual.minusDays(1);

		return (int) contactos.stream().filter(contacto -> contacto != null && contacto.getMensajes() != null) // Filtrar
																												// contactos
																												// válidos
				.flatMap(contacto -> contacto.getMensajes().stream()) // Convertir lista de contactos a stream de
																		// mensajes
				.filter(mensaje -> mensaje != null && mensaje.getTipoMensaje() == TipoMensaje.ENVIADO) // Filtrar
																										// mensajes
																										// válidos con
																										// tipo 0
				.map(mensaje -> mensaje.getFechaEnvio().toLocalDate()) // Obtener fechas de los mensajes
				.filter(fecha -> !fecha.isBefore(primerDiaUltimoMes) && !fecha.isAfter(ultimoDiaUltimoMes)) // Filtrar
																											// por rango
																											// de fechas
				.count(); // Contar los mensajes que cumplen con los criterios
	}

	public double getPrecioPremium(Descuento descuento) {
		return descuento.getDescuento(PRECIO_PREMIUM);
	}

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public boolean isPremium() {
		return premium;
	}

}
