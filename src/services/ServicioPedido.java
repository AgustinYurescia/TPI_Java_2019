package services;

import java.util.ArrayList;

import modelo.Cliente;
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
	public Pedido BuscarPedido(int numeroPedido) throws Exception {
		return _pedidoDAO.buscar_pedido(numeroPedido);
	}
	public Pedido BuscarPedidoConProductos(int numeroPedido) throws Exception {		
		Pedido pedido = _pedidoDAO.buscar_pedido(numeroPedido);
		pedido.setProductos(_pedidoDAO.buscar_productos_pedido(numeroPedido));
		return pedido;
	}
	public void CancelarPedido(int numeroPedido) {
		_pedidoDAO.cancelar_pedido(numeroPedido);
	}
	public ArrayList<Pedido> ListarPedidosCliente(Cliente cli, String estado) throws Exception{
		return _pedidoDAO.listar_pedidos_cliente(cli.getDni(), estado);
	}
	public void RegistrarEntrega(int numeroPedido) throws Exception {
		_pedidoDAO.RegistrarEntrega(numeroPedido);
	}
	
}
