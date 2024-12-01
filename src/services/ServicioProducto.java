package services;

import modeloDAO.PedidoDAO;
import modeloDAO.ProductoDAO;

import java.util.ArrayList;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import modelo.PaginaProductos;
import modelo.Producto;
import modelo.GeneradorGrafico;


public class ServicioProducto {
	
	private ProductoDAO _productoDAO;
	private static Logger _logger = LogManager.getLogger(PedidoDAO.class);
	private GeneradorGrafico _graficador;
	
	public ServicioProducto()
	{
		_productoDAO = new ProductoDAO();
		_graficador = new GeneradorGrafico();
	}
	
	public void AltaProducto(Producto prod, Double precio) throws Exception {
		
		try
		{
			_productoDAO.alta(prod, precio);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public void EliminarProducto(int codigoProducto) throws Exception {
		
		try
		{
			_productoDAO.baja(codigoProducto);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public void EditarProducto(Producto prod) throws Exception {
		
		try
		{
			_productoDAO.editarProducto(prod);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public Producto GetProducto(int codigo_producto) throws Exception {
		
		try
		{
			Producto prod = _productoDAO.buscarProducto(codigo_producto);
			return prod;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public Producto GetProductoSinImagen(int codigo_producto) throws Exception {
		
		try
		{
			Producto prod = _productoDAO.buscarProductoSinImagen(codigo_producto);
			return prod;
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	public void ReponerStock(int codigo_producto, int cantidad, double precio) throws Exception {
	
		try
		{
			_productoDAO.reponerStock(codigo_producto, cantidad, precio);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	public void ActualizarStock(int codigo_producto,int cantidad){
		try 
		{
			_productoDAO.actualizarStock(codigo_producto, cantidad);
		}
		catch(Exception ex)
		{
			//TODO: handle specific exception
			throw ex;
		}
	}
	
	public void DescontarStock(int codigo_producto,int cantidad) {
		try 
		{
			_productoDAO.descontarStock(codigo_producto, cantidad);
		}
		catch(Exception ex)
		{
			//TODO: handle specific exception
			throw ex;
		}
	}
		
	public ArrayList<Producto> ObtenerProductos(int codigoCategoria) throws Exception {
		
		if (codigoCategoria == 0) {
			try
			{
				ArrayList<Producto> productos = (ArrayList<Producto>) _productoDAO.obtenerTodos();
				return productos;
			}
			catch (Exception e)
			{
				throw e;
			}
		}
		else
		{
			try
			{
				ArrayList<Producto> productos = (ArrayList<Producto>) _productoDAO.obtenerPorCategoria(codigoCategoria);
				return productos;
			}
			catch (Exception e)
			{
				throw e;
			}
		}
	}
	
	public void BajaPorCategoria(int codigoCategoria) throws Exception {
		try
		{
			_productoDAO.bajaPorCategoria(codigoCategoria);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public PaginaProductos ObtenerPorPagina(int numeroPorPagina, int numeroPagina, int codigoCategoria, String textoBusqueda){
		PaginaProductos pagina = new PaginaProductos(); 
		try {
			ArrayList<Producto> productos = _productoDAO.obtenerPorPagina(numeroPorPagina, numeroPagina, codigoCategoria, textoBusqueda);
			int numeroRegistros = _productoDAO.obtenerNumeroDeRegistros(codigoCategoria);
			pagina.setProductos(productos);
			pagina.setCantidadRegistros(numeroRegistros);
			
		}catch(Exception e){
			_logger.error(e.getMessage());
		}
		return pagina;
	}
	public String obtenerVentasPorProducto(Integer anio) throws Exception 
	{
		Map<String, Integer> ventas = null;
		try
		{
			ventas = _productoDAO.obtenerVentasPorProducto(anio);
			String imageAsBase64 = _graficador.graficoBarrasVentasPorProducto(ventas);
			return imageAsBase64;
		}
		catch(Exception e)
		{
			 throw e;
		}
	}

}
