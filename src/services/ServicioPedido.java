package services;

import java.util.ArrayList;

import modelo.LineaPedido;
import modelo.Pedido;
import modeloDAO.PedidoDAO;
import modeloDAO.ProductoDAO;

public class ServicioPedido {
	
	private PedidoDAO _pedidoDAO;
	private ProductoDAO _productoDao;
	public ServicioPedido()
	{
		this._pedidoDAO = new PedidoDAO();
		this._productoDao = new ProductoDAO();
	}
	public int Alta(Pedido ped, ArrayList<LineaPedido> linea)
	{
		return _pedidoDAO.alta(ped, linea);
	}
}
