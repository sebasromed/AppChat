package modelo;

import java.time.LocalDateTime;

public class Mensaje {
	
	private String texto;
	private LocalDateTime horaEnvio;
	private String emisor;
	private Contacto receptor;
	
	public Mensaje(String texto, LocalDateTime horaEnvio, String emisor, Contacto receptor) {
		this.texto = texto;
		this.horaEnvio = horaEnvio;
		this.emisor = emisor;
		this.receptor = receptor;
	}

	//--------------- Getters and Setters ------------------
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getHoraEnvio() {
		return horaEnvio;
	}

	public void setHoraEnvio(LocalDateTime horaEnvio) {
		this.horaEnvio = horaEnvio;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public Contacto getReceptor() {
		return receptor;
	}

	public void setReceptor(Contacto receptor) {
		this.receptor = receptor;
	}
	
	
	
}
