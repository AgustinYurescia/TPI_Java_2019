package modelo;

public class Precio {
	private int codigo_producto;
	private String cuil_proveedor;
	private java.sql.Date fecha_desde;
	private Double precio;
	
	public int getCodigo_producto() {
		return codigo_producto;
	}
	public void setCodigo_producto(int codigo_producto) {
		this.codigo_producto = codigo_producto;
	}
	public String getCuil_proveedor() {
		return cuil_proveedor;
	}
	public void setCuil_proveedor(String cuil_proveedor) {
		this.cuil_proveedor = cuil_proveedor;
	}
	public java.sql.Date getFecha_desde() {
		return fecha_desde;
	}
	public void setFecha_desde(java.sql.Date fecha_desde) {
		this.fecha_desde = fecha_desde;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
}
