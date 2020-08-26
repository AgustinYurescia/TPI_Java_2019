package services;

import modeloDAO.ClienteDAO;
import exceptions.NonExistentUserException;
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
	
	public void RegisterCustomer(Cliente cliente, int esSocio) throws Exception 
	{
		try
		{
			_clienteDAO.alta(cliente,esSocio);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	public void CambioContrasena(String usuario, String contrasena_actual, String contrasena_nueva) throws Exception 
	{
		try
		{
			_clienteDAO.cambioContrasena(usuario, contrasena_actual, contrasena_nueva);
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
	public Cliente Buscar(String dni) throws Exception 
	{
		try
		{
			Cliente cli = _clienteDAO.buscar_cliente_por_dni(dni);
			return cli;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	public void RegistrarSocio(String dni) throws Exception 
	{
		try
		{
			_clienteDAO.registrar_socio(dni);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
}
