package modelo;
import modelo.Producto;
public class LineaPedido {
	
	private int codigo_producto;
	private int cantidad;
	
	public LineaPedido(int codigo_producto, int cantidad){
		this.setCantidad(cantidad);
		this.setCodigo_producto(codigo_producto);
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
	
	
	
}
