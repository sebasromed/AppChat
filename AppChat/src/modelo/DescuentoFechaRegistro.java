package modelo;

public class DescuentoFechaRegistro implements Descuento {

	@Override
	public double getDescuento(Double precioInicial) {
		return precioInicial - (precioInicial * 0.05);
	}

}
