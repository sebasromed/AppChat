package modelo;

import java.time.LocalDateTime;

public class Mensaje {

	public enum TipoMensaje {
		ENVIADO, RECIBIDO;
	}

	private int codigo;
	private String texto;
	private int idEmoji;
	private LocalDateTime fechaEnvio;
	private Usuario emisor;
	private Contacto receptor;
	private TipoMensaje tipoMensaje;

	// Constructor con todos los atributos
	public Mensaje(int codigo, String texto, int idEmoji, LocalDateTime fechaEnvio, Usuario emisor, Contacto receptor,
			TipoMensaje tipoMensaje) {
		this.codigo = codigo;
		this.texto = texto;
		this.idEmoji = idEmoji;
		this.fechaEnvio = fechaEnvio;
		this.emisor = emisor;
		this.receptor = receptor;
		this.tipoMensaje = tipoMensaje;
	}

	// Constructor para mensajes con texto
	public Mensaje(String texto, LocalDateTime fechaEnvio, Usuario emisor, Contacto receptor, TipoMensaje tipoMensaje) {
		this(0, texto, -1, fechaEnvio, emisor, receptor, tipoMensaje);
	}

	// Constructor para mensajes con emoji
	public Mensaje(int idEmoji, LocalDateTime fechaEnvio, Usuario emisor, Contacto receptor, TipoMensaje tipoMensaje) {
		this(0, "", idEmoji, fechaEnvio, emisor, receptor, tipoMensaje);
	}

	// Constructor para mensajes en general
	public Mensaje(String texto, int idEmoji, LocalDateTime fechaEnvio, Usuario emisor, Contacto receptor,
			TipoMensaje tipoMensaje) {
		this(0, texto, idEmoji, fechaEnvio, emisor, receptor, tipoMensaje);
	}

	// --------------- Getters and Setters ------------------

	public int getCodigo() {
		return codigo;
	}

	public String getTexto() {
		return texto;
	}

	public int getIdEmoji() {
		return idEmoji;
	}

	public LocalDateTime getFechaEnvio() {
		return fechaEnvio;
	}

	public Usuario getEmisor() {
		return emisor;
	}

	public Contacto getReceptor() {
		return receptor;
	}

	public TipoMensaje getTipoMensaje() {
		return tipoMensaje;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public void setIdEmoji(int idEmoji) {
		this.idEmoji = idEmoji;
	}

	public void setFechaEnvio(LocalDateTime fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}

	public void setReceptor(Contacto receptor) {
		this.receptor = receptor;
	}

	public void setTipoMensaje(TipoMensaje tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

}
