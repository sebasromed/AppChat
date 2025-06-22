package modelo;

public class DescuentoEnvioMensajes implements Descuento {

	@Override
	public double getDescuento(Double precioInicial) {
		return precioInicial - (precioInicial * 0.15);

	}

}
