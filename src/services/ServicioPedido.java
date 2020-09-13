package services;

import modeloDAO.PedidoDAO;
import modeloDAO.ProductoDAO;

public class ServicioPedido {
	
	private PedidoDAO _pedidoDao;
	private ProductoDAO _productoDao;
	public ServicioPedido()
	{
		this._pedidoDao = new PedidoDAO();
		this._productoDao = new ProductoDAO();
	}
	
}
