package services;

import modeloDAO.ClienteDAO;
import modelo.Cliente;

public class CustomerService {
	
	private ClienteDAO _clienteDAO;
	//comentario de pruebas
	public CustomerService() 
	{
		_clienteDAO = new ClienteDAO();
	}
	
	public boolean ValidateEqualPasswords(String firstPassword, String secondPassword)
	{
		return(firstPassword.equals(secondPassword));
	}
	
	public void RegisterCustomer(Cliente cliente) throws Exception 
	{
		try
		{
			_clienteDAO.alta(cliente);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	public void CambioContrasena(String usuario, String contrasena_actual, String contrasena_nueva, String contrasena_nueva_rep) throws Exception 
	{
		try
		{
			_clienteDAO.cambioContrasena(usuario, contrasena_actual,contrasena_nueva,contrasena_nueva_rep);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	public void Baja(String usuario, String contrasena) throws Exception 
	{
		try
		{
			_clienteDAO.baja(usuario, contrasena);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
}
