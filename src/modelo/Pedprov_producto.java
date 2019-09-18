package modelo;

public class Pedprov_producto {
	private int codigo_producto;
	String cuil_proveedor;
	private int nro_pedido_prov;
	private int cantidad;
	
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
	public int getNro_pedido_prov() {
		return nro_pedido_prov;
	}
	public void setNro_pedido_prov(int nro_pedido_prov) {
		this.nro_pedido_prov = nro_pedido_prov;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
