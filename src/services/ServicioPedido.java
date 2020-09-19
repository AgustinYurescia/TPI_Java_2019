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
	public ArrayList<Pedido> Listar(String estado) throws Exception{
		return _pedidoDAO.listar(estado);
	}
	public ArrayList<Pedido> Listar(String fechaDesde,String fechaHasta, String estado) throws Exception{
		return _pedidoDAO.listar( fechaDesde, fechaHasta, estado);
	}
}
