package modelo;

public class Pedido {
	private int nro_pedido;
	java.sql.Date fecha_pedido;
	java.sql.Date fecha_entrega_est;
	Double monto;
	java.sql.Date fecha_cancelacion;
	java.sql.Date fecha_entrega_real;
	String dni_cliente;
	
	public int getNro_pedido() {
		return nro_pedido;
	}
	public void setNro_pedido(int nro_pedido) {
		this.nro_pedido = nro_pedido;
	}
	public java.sql.Date getFecha_pedido() {
		return fecha_pedido;
	}
	public void setFecha_pedido(java.sql.Date fecha_pedido) {
		this.fecha_pedido = fecha_pedido;
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
	public java.sql.Date getFecha_cancelacion() {
		return fecha_cancelacion;
	}
	public void setFecha_cancelacion(java.sql.Date fecha_cancelacion) {
		this.fecha_cancelacion = fecha_cancelacion;
	}
	public java.sql.Date getFecha_entrega_real() {
		return fecha_entrega_real;
	}
	public void setFecha_entrega_real(java.sql.Date fecha_entrega_real) {
		this.fecha_entrega_real = fecha_entrega_real;
	}
	public String getDni_cliente() {
		return dni_cliente;
	}
	public void setDni_cliente(String dni_cliente) {
		this.dni_cliente = dni_cliente;
	}

	

}
