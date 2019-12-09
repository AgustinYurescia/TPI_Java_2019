package modelo;

import java.io.InputStream;

public class Producto {
	
	private int codigo;
	private String nombre;
	private InputStream imagen;
	private int stock;
	private Double precioVenta;
	private int codigo_categoria;
	private java.sql.Date fecha_baja;
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
	public InputStream get_imagen() {
		return imagen;
	}
	public void set_imagen(InputStream imagen) {
		this.imagen = imagen;
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
	public void setPrecioVenta(Double precio_venta) {
		this.precioVenta = precio_venta;
		 
	}
	public int getCodigo_categoria() {
		return codigo_categoria;
	}
	public void setCodigo_categoria(int codigo_categoria) {
		this.codigo_categoria = codigo_categoria;
	}
	public java.sql.Date getFecha_baja() {
		return fecha_baja;
	}
	public void setFecha_baja(java.sql.Date fecha_baja) {
		this.fecha_baja = fecha_baja;
	}
}

