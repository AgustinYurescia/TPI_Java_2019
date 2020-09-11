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
	
	public ArrayList<Cuota> ObtenerCuotasAnioActual(String dniCliente) throws Exception
	{
		ArrayList<Cuota> cuotas = null;
		try
		{
			cuotas = _cuotaDAO.ObtenerCuotasAnioActual(dniCliente);
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

}
