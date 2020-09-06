package services;

import exceptions.NonExistentUserException;
import modeloDAO.AdminDAO;

public class ServicioAdmin 
{
	private AdminDAO _adminDAO;
	
	public ServicioAdmin()
	{
		_adminDAO = new AdminDAO();
	}
	
	public boolean ValidateEqualPasswords(String firstPassword, String secondPassword)
	{
		return(firstPassword.equals(secondPassword));
	}
	
	public void CambioContrasena(String usuario, String contrasena_actual, String contrasena_nueva) throws Exception
	{
		try
		{
			_adminDAO.cambioContrasena(usuario, contrasena_actual, contrasena_nueva);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public void IniciarSesion(String usuario, String contrasena)  throws Exception
	{
		if (!(_adminDAO.existe(usuario, contrasena)))
		{
			throw new NonExistentUserException("Usuario y/o contraseña incorrectos"); 
		}
	}
}
