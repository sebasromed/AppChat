package modelo;

import java.util.ArrayList;
import java.util.List;

public class DescuentoComposite implements Descuento {

	private List<Descuento> descuentos;

	public DescuentoComposite() {
		this.descuentos = new ArrayList<>();
	}

	public void addDescuento(Descuento descuento) {
		descuentos.add(descuento);
	}

	public void removeDescuento(Descuento descuento) {
		descuentos.remove(descuento);
	}

	@Override
	public double getDescuento(Double precioInicial) {
		double precioFinal = precioInicial;
		for (Descuento descuento : descuentos) {
			precioFinal = descuento.getDescuento(precioFinal);
		}
		return precioFinal;
	}

	public List<Descuento> getDescuentos() {
		return descuentos;
	}
}
