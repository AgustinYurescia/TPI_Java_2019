package modelo;

import java.time.LocalDate;

public class Cuota 
{
	private String dniCliente;
	private int mes;
	private int anio;
	private double valor;
	private LocalDate fechaPago;
	private Cliente cliente;
	
	public Cuota(String dniCliente, int mes, int anio, double valor, LocalDate localDate) 
	{
		super();
		this.dniCliente = dniCliente;
		this.mes = mes;
		this.anio = anio;
		this.valor = valor;
		this.fechaPago = localDate;
	}

	public String getDniCliente() 
	{
		return dniCliente;
	}

	public void setDniCliente(String dniCliente) 
	{
		this.dniCliente = dniCliente;
	}

	public int getMes() 
	{
		return mes;
	}

	public void setMes(int mes) 
	{
		this.mes = mes;
	}

	public int getAnio()
	{
		return anio;
	}

	public void setAnio(int anio) 
	{
		this.anio = anio;
	}
	
	public double getValor()
	{
		return valor;
	}

	public void setValor(double valor) 
	{
		this.valor = valor;
	}

	public LocalDate getFechaPago() 
	{
		return fechaPago;
	}

	public void setFechaPago(LocalDate fechaPago) 
	{
		this.fechaPago = fechaPago;
	}

	public Cliente getCliente()
	{
		return cliente;
	}

	public void setCliente(Cliente cliente)
	{
		this.cliente = cliente;
	}

}
