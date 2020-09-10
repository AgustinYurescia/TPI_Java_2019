package services;

import java.util.ArrayList;

import exceptions.NonExistentFeeValueException;
import exceptions.NonExistentPartnerException;
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

}
