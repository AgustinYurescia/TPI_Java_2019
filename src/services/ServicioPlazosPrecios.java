package services;

import modeloDAO.TardanzaPedidoDAO;

public class ServicioPlazosPrecios {
	
	private TardanzaPedidoDAO _tardanzaPedidoDAO;

	public ServicioPlazosPrecios()
	{
		_tardanzaPedidoDAO = new TardanzaPedidoDAO();
	}
	
	public void AltaPlazo(int cantidadDias) throws Exception
	{
		try 
		{
			if (_tardanzaPedidoDAO.YaExiste())
			{
				_tardanzaPedidoDAO.Modificacion(cantidadDias);
			}
			else
			{
				_tardanzaPedidoDAO.Alta(cantidadDias);
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	}
}
