package services;

import java.util.ArrayList;
import java.util.Map;

import exceptions.AppException;
import modelo.Cliente;
import modelo.GeneradorGrafico;
import modelo.LineaPedido;
import modelo.Pedido;
import modeloDAO.PedidoDAO;

public class ServicioPedido {
	
	private PedidoDAO _pedidoDAO;
	private GeneradorGrafico _graficador;
	public ServicioPedido()
	{
		this._pedidoDAO = new PedidoDAO();
		this._graficador = new GeneradorGrafico();
	}
	public int Alta(Pedido ped, ArrayList<LineaPedido> linea) throws Exception
	{
		try 
		{
			return _pedidoDAO.alta(ped, linea);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	public ArrayList<Pedido> Listar(String estado) throws Exception{
		return _pedidoDAO.listar(estado);
	}
	public ArrayList<Pedido> Listar(String fechaDesde,String fechaHasta, String estado) throws Exception{
		return _pedidoDAO.listar( fechaDesde, fechaHasta, estado);
	}
	public ArrayList<Pedido> ListarFinalizados() throws Exception{
		try
		{
			return _pedidoDAO.listarFinalizados();
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	public ArrayList<Pedido> ListarFinalizados(String fechaDesde,String fechaHasta) throws Exception{
		try
		{
			return _pedidoDAO.listarFinalizados( fechaDesde, fechaHasta);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	public Pedido BuscarPedido(int numeroPedido) throws Exception {
		return _pedidoDAO.buscar_pedido(numeroPedido);
	}
	public Pedido BuscarPedidoConProductos(int numeroPedido) throws Exception {
		try
		{
			Pedido pedido = _pedidoDAO.buscar_pedido(numeroPedido);
			pedido.setProductos(_pedidoDAO.buscar_productos_pedido(numeroPedido));
			return pedido;
		}
		catch(AppException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw e;
		}
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
	public ArrayList<String> obtenerTotalVentasPorMes(Integer anio) throws Exception 
	{
		Map<Integer, Float> ventas = null;
		ArrayList<String> imagesAsBase64 = new ArrayList<String>();
		try
		{
			ventas = _pedidoDAO.obtenerTotalVentasPorMes(anio);
			imagesAsBase64.add(_graficador.graficoBarrasVentasPorMes(ventas));
			imagesAsBase64.add(_graficador.graficoLinealVentasPorMes(ventas));
			return imagesAsBase64;  
		}
		catch(Exception e)
		{
			 throw e;
		}
	}
	public ArrayList<String> obtenerTotalVentasPorAnio() throws Exception 
	{
		Map<Integer, Float> ventas = null;
		ArrayList<String> imagesAsBase64 = new ArrayList<String>();
		try
		{
			ventas = _pedidoDAO.obtenerTotalVentasPorAnio();
			imagesAsBase64.add(_graficador.graficoBarrasVentasPorAnio(ventas));
			imagesAsBase64.add(_graficador.graficoLinealVentasPorAnio(ventas));
			return imagesAsBase64;
		}
		catch(Exception e)
		{
			 throw e;
		}
	}
	
	public ArrayList<Pedido> PedidosAEntregarManana() throws Exception
	{
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
		try
		{
			pedidos = _pedidoDAO.PedidosAEntregarManana();
			return pedidos;
		}
		catch(AppException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	public void SetEstadoPreparado(ArrayList<Pedido> pedidos) throws Exception
	{
		try
		{
			_pedidoDAO.setEstadoPreparado(pedidos);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	public void SetEstadoPreparado(String nro_pedido) throws Exception
	{
		try
		{
			_pedidoDAO.setEstadoPreparado(nro_pedido);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public ArrayList<Pedido> VentasDelDia() throws Exception
	{
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
		try
		{
			pedidos = _pedidoDAO.VentasDelDia();
			return pedidos;
		}
		catch(AppException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
}
