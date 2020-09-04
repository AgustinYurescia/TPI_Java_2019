package services;

import modeloDAO.ProductoDAO;
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
	
	public void EditarProducto(Producto prod) throws Exception {
		
		try
		{
			_productoDAO.editar_producto(prod);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
}
