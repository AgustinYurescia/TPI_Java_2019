package services;

import modeloDAO.ClienteDAO;

import java.util.ArrayList;

import exceptions.NonExistentPartnerException;
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
	public void ActualizarCliente(String nombre,String apellido,String telefono,String direccion,String mail,String usuario) throws Exception 
	{
		Cliente cli = new Cliente();
		try {
			cli = _clienteDAO.buscar_cliente_por_usuario(usuario);
			cli.setNombre(nombre);
			cli.setApellido(apellido);
			cli.setTelefono(telefono);
			cli.setDireccion(direccion);
			cli.setMail(mail);
		}
		catch(Exception ex) {
			throw ex;
		}
		if(_clienteDAO.yaExisteUsuario(cli.getCliente_usuario(), cli.getMail())) {
			try {
				_clienteDAO.modificacion_cliente(cli);
			}catch(Exception ex){
				throw ex;
			}
		}else {
			throw new NonExistentUserException("El susario ingresado no existe");
		}
		
	}
	
	public void IniciarSesion(String usuario, String contrasena)  throws Exception
	{
		if (!(_clienteDAO.existe(usuario, contrasena)))
		{
			throw new NonExistentUserException("Usuario y/o contraseña incorrectos"); 
		}
	}
	
	public ArrayList<String> ObtenerDniSociosActivos()  throws Exception
	{
		ArrayList<String> dniSocios = null;
		try
		{
			dniSocios = _clienteDAO.ObtenerDniSociosActivos();
			return dniSocios;
		}
		catch(NonExistentPartnerException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public int BajaSociosDeudores()  throws Exception
	{
		int nroBajas = 0;
		try
		{
			nroBajas = _clienteDAO.BajaSociosDeudores();
			return nroBajas;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
}
