package modelo;
import modelo.Producto;
import modeloDAO.ProductoDAO;
public class LineaPedido {
	
	private int codigo_producto;
	private int cantidad;
	private double subtotal;
	
	public LineaPedido(int codigo_producto, int cantidad, double subtotal){
		this.setCantidad(cantidad);
		this.setCodigo_producto(codigo_producto);
		this.setSubtotal(subtotal);
	}
	
	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	
	public int getCodigo_producto() {
		return codigo_producto;
	}
	public void setCodigo_producto(int codigo_producto) {
		this.codigo_producto = codigo_producto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public Producto getProducto(int codigo_producto) {
		ProductoDAO prodDAO = new ProductoDAO();
		Producto prod = null;
		try
		{
			prod = prodDAO.buscarProducto(codigo_producto);
			return prod;
		}
		catch (Exception e)
		{
			
		}
		return prod;
	}
	
	
	
}
