package modelo;
import modelo.Producto;
public class LineaPedido {
	
	private Producto producto;
	private int cantidad;
	private double subtotal;
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	
	public LineaPedido(Producto prod,int cantidad) {
		this.setProducto(prod);
		this.setCantidad(cantidad);
		double subtotal = this.getProducto().getPrecioVenta() * this.getCantidad();
		this.setSubtotal(subtotal);	}
}
