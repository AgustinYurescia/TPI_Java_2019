package services;

import java.util.ArrayList;

import exceptions.NonExistentFeeException;
import exceptions.NonExistentFeeValueException;
import exceptions.NonExistentPartnerException;
import modelo.Cuota;
import modeloDAO.CuotaDAO;

public class ServicioCuota {
	
	private CuotaDAO _cuotaDAO;
	private CustomerService _servicioCliente;
	
	public ServicioCuota()
	{
		_cuotaDAO = new CuotaDAO();
		_servicioCliente = new CustomerService();
	}
	
	public void GenerarCuotas() throws Exception
	{
		try
		{
			ArrayList <String> dniSocios = _servicioCliente.ObtenerDniSociosActivos();
			_cuotaDAO.GenerarCuotas(dniSocios);
		}
		catch(NonExistentPartnerException e)
		{
			throw e;
		}
		catch(NonExistentFeeValueException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public ArrayList<Cuota> ObtenerCuotasImpagas(String dniCliente) throws Exception
	{
		ArrayList<Cuota> cuotas = null;
		try
		{
			cuotas = _cuotaDAO.ObtenerCuotasImpagas(dniCliente);
			return cuotas;
		}
		catch(NonExistentFeeException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public void RegistrarPago(String dniCliente, int mes, int anio) throws Exception
	{
		try
		{
			_cuotaDAO.RegistrarPago(dniCliente, mes, anio);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public ArrayList<Cuota> ListadoCuotas(String mes, String anio) throws Exception
	{
		ArrayList<Cuota> cuotas = null;
		try
		{
			cuotas = _cuotaDAO.ListadoCuotas(Integer.parseInt(mes), Integer.parseInt(anio));
			return cuotas;
		}
		catch(NonExistentFeeException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public Integer getCantidadCuotasSinPago(String dni) throws Exception
	{
		try
		{ 
			Integer cantidadCuotasImpagas = _cuotaDAO.getCantidadCuotasSinPago(dni);
			return cantidadCuotasImpagas;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	public ArrayList<Cuota> ListadoCuotasPagasPorMes(int mes, int anio) throws Exception
	{
		try
		{ 
			ArrayList<Cuota> cuotasPagadas = _cuotaDAO.ListadoCuotasPagasPorMes(mes, anio);
			return cuotasPagadas;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
}
