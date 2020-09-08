package services;

import modeloDAO.ProductoDAO;

import java.util.ArrayList;

import modelo.Producto;

public class ServicioProducto {
	
	private ProductoDAO _productoDAO;
	
	public ServicioProducto()
	{
		_productoDAO = new ProductoDAO();
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

}
