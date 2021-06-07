package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Pedido {
	private int nro_pedido;
	private LocalDate fecha_pedido;
	private LocalDate fecha_entrega_est;
	private Double monto;
	private LocalDate fecha_cancelacion;
	private LocalDate fecha_entrega_real;
	private String dni_cliente;
	private ArrayList<LineaPedido> productos;
	private String estado;
	
	public int getNro_pedido() {
		return nro_pedido;
	}
	public ArrayList<LineaPedido> getProductos() {
		return productos;
	}
	public void setProductos(ArrayList<LineaPedido> productos) {
		this.productos = productos;
	}
	public void setNro_pedido(int nro_pedido) {
		this.nro_pedido = nro_pedido;
	}
	public LocalDate getFecha_pedido() {
		return fecha_pedido;
	}
	public void setFecha_pedido(LocalDate fecha_pedido) {
		this.fecha_pedido = fecha_pedido;
	}
	public LocalDate getFecha_entrega_est() {
		return fecha_entrega_est;
	}
	public void setFecha_entrega_est(LocalDate fecha_entrega_est) {
		this.fecha_entrega_est = fecha_entrega_est;
	}
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	public LocalDate getFecha_cancelacion() {
		return fecha_cancelacion;
	}
	public void setFecha_cancelacion(LocalDate fecha_cancelacion) {
		this.fecha_cancelacion = fecha_cancelacion;
	}
	public LocalDate getFecha_entrega_real() {
		return fecha_entrega_real;
	}
	public void setFecha_entrega_real(LocalDate fecha_entrega_real) {
		this.fecha_entrega_real = fecha_entrega_real;
	}
	public String getDni_cliente() {
		return dni_cliente;
	}
	public void setDni_cliente(String dni_cliente) {
		this.dni_cliente = dni_cliente;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
}
