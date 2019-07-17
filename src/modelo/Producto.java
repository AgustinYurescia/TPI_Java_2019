package modelo;

public class Producto {
	
	private int idProducto;
	private String nombre;
	private String categoria;
	private String descripcion;
	private Double precioCosto;
	private Double precioVenta;
	private int stock;
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Double getPrecioCosto() {
		return precioCosto;
	}
	public void setPrecioCosto(Double precioCosto) {
		this.precioCosto = precioCosto;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public void setPrecioVenta() {
		this.precioVenta = this.precioCosto*1.40;
	}
	public Double getPrecioVenta() {
		return precioVenta;
	}
}

