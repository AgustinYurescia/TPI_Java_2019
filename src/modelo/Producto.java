package modelo;

public class Producto {
	
	private int codigo;
	private String nombre;
	private String url_imagen;
	private int stock;
	private Double precioVenta;
	private int codigo_categoria;
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getUrl_imagen() {
		return url_imagen;
	}
	public void setUrl_imagen(String url_imagen) {
		this.url_imagen = url_imagen;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public Double getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta() {
		
	}
	public int getCodigo_categoria() {
		return codigo_categoria;
	}
	public void setCodigo_categoria(int codigo_categoria) {
		this.codigo_categoria = codigo_categoria;
	}
	
}

