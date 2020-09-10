package modelo;

import java.sql.Date;

public class Cuota 
{
	private String dniCliente;
	private int mes;
	private int anio;
	private java.sql.Date fechaPago;
	
	public Cuota(String dniCliente, int mes, int anio, Date fechaPago) 
	{
		super();
		this.dniCliente = dniCliente;
		this.mes = mes;
		this.anio = anio;
		this.fechaPago = fechaPago;
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

	public java.sql.Date getFechaPago() 
	{
		return fechaPago;
	}

	public void setFechaPago(java.sql.Date fechaPago) 
	{
		this.fechaPago = fechaPago;
	}

}
