package persistencia;

/**
 * Factoria concreta DAO para el Servidor de Persistencia de la asignatura TDS.
 * 
 */

public final class TDSFactoriaDAO extends FactoriaDAO {

	public TDSFactoriaDAO() {
	}

	@Override
	public AdaptadorUsuarioTDS getUsuarioDAO() {
		return AdaptadorUsuarioTDS.INSTANCE;
	}

	@Override
	public IAdaptadorContactoDAO getContactoDAO() {
		return AdaptadorContactoTDS.INSTANCE;
	}

	@Override
	public IAdaptadorMensajeDAO getMensajeDAO() {
		return AdaptadorMensajeTDS.INSTANCE;
	}

}
