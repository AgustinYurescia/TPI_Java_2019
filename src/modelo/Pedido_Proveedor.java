package modelo;

public class Pedido_Proveedor {
	private int nro_pedido_prov;
	private java.sql.Date fecha_pedido;
	private java.sql.Date fecha_entrega_real;
	private java.sql.Date fecha_entrega_est;
	private Double monto;
	
	public int getNro_pedido_prov() {
		return nro_pedido_prov;
	}
	public void setNro_pedido_prov(int nro_pedido_prov) {
		this.nro_pedido_prov = nro_pedido_prov;
	}
	public java.sql.Date getFecha_pedido() {
		return fecha_pedido;
	}
	public void setFecha_pedido(java.sql.Date fecha_pedido) {
		this.fecha_pedido = fecha_pedido;
	}
	public java.sql.Date getFecha_entrega_real() {
		return fecha_entrega_real;
	}
	public void setFecha_entrega_real(java.sql.Date fecha_entrega_real) {
		this.fecha_entrega_real = fecha_entrega_real;
	}
	public java.sql.Date getFecha_entrega_est() {
		return fecha_entrega_est;
	}
	public void setFecha_entrega_est(java.sql.Date fecha_entrega_est) {
		this.fecha_entrega_est = fecha_entrega_est;
	}
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	
	

}
