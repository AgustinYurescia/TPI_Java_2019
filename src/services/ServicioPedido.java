package services;

import exceptions.NonExistentOrderException;
import modelo.Pedido;
import modeloDAO.PedidoDAO;

public class ServicioPedido {
	
	private PedidoDAO _pedidoDAO;
	
	public ServicioPedido()
	{
		_pedidoDAO = new PedidoDAO();
	}

	public Pedido BuscarPedido(int nroPedido) throws Exception
	{
		try
		{
			return _pedidoDAO.buscar_pedido(nroPedido);
		}
		catch(NonExistentOrderException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public void RegistrarEntrega(int nroPedido) throws Exception
	{
		try
		{
			_pedidoDAO.RegistrarEntrega(nroPedido);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
}
