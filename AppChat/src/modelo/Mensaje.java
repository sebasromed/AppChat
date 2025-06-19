package modelo;

import java.time.LocalDateTime;

public class Mensaje {
	
	public enum TipoMensaje {
		ENVIADO,
		RECIBIDO;
	}
	
	private int codigo;
	private String texto;
	private LocalDateTime horaEnvio;
	private Usuario emisor;
	private Contacto receptor;
	private TipoMensaje tipoMensaje;
	
	public Mensaje(String texto, LocalDateTime horaEnvio, Usuario emisor, Contacto receptor, TipoMensaje tipoMensaje) {
		this.codigo = 0;
		this.texto = texto;
		this.horaEnvio = horaEnvio;
		this.emisor = emisor;
		this.receptor = receptor;
		this.tipoMensaje = tipoMensaje;
	}

	//--------------- Getters and Setters ------------------
	
	public int getCodigo() {
		return codigo;
	}
	
	public String getTexto() {
		return texto;
	}

	public LocalDateTime getHoraEnvio() {
		return horaEnvio;
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

	public void setHoraEnvio(LocalDateTime horaEnvio) {
		this.horaEnvio = horaEnvio;
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
