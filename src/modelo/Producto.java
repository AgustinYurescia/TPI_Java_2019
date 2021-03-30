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
	private String imagenString;
	

	public String getImagenString() {
		return imagenString;
	}

	public void setImagenString(String imagenString) {
		this.imagenString = imagenString;
	}

	public Producto() 
	{
		super();
	}
	
	public Producto(String nombre, InputStream imagen, int stock, int codigo_categoria) 
	{
		super();
		this.nombre = nombre;
		this.imagen = imagen;
		this.stock = stock;
		this.codigo_categoria = codigo_categoria;
	}
	
	public Producto(int codigo, String nombre, InputStream imagen, Double precioVenta) 
	{
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.imagen = imagen;
		this.precioVenta = precioVenta;
	}
	
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
	public static boolean es_valido(String nombre, int categoria, InputStream imagen, int stock, Double precio) {
		boolean es_valido = true;
		if (nombre.length() <= 6 ){
			es_valido = false;
			}
			else{
				if(nombre.length() > 45) {
					es_valido = false;
				}
			}
		if (categoria == 0) {
			es_valido = false;
		}
		if (imagen == null) {
			es_valido = false;
		}
		if (stock == 0) {
			es_valido = false;
		}
		if (precio == 0) {
			es_valido = false;
		}
		return es_valido;
	}
}

