package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.Conexion;
import exceptions.ExistentUserException;
import exceptions.NoRowsAffectedException;
import exceptions.NonExistentPartnerException;
import exceptions.NonExistentUserException;
import modelo.Cliente;
import modelo.Cuota;
import modelo.SocioDeudor;

public class ClienteDAO {
	
	private static Logger _logger = LogManager.getLogger(ClienteDAO.class);
			
	public void alta(Cliente cli, int esSocio) throws Exception {
		
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL= null;
		if (esSocio == 1)
		{
			sentenciaSQL="INSERT INTO cliente(dni,cliente_usuario,cliente_contrasena,nombre,apellido,mail,telefono,direccion,fecha_baja_socio,fecha_baja)VALUES(?,?,?,?,?,?,?,?,NULL,NULL)";
		}
		else
		{
			sentenciaSQL="INSERT INTO cliente(dni,cliente_usuario,cliente_contrasena,nombre,apellido,mail,telefono,direccion,fecha_baja_socio,fecha_baja)VALUES(?,?,?,?,?,?,?,?,current_date,NULL)";
		}

		if (yaExisteUsuario(cli.getCliente_usuario(), cli.getMail()))
		{
			throw new ExistentUserException("El usuario y/o el mail ingresados ya existen");
		}
		else 
		{
			try {
				st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
				st.setString(1, cli.getDni());
				st.setString(2, cli.getCliente_usuario());
				st.setString(3, cli.getCliente_contrasena());
				st.setString(4, cli.getNombre());
				st.setString(5, cli.getApellido());
				st.setString(6, cli.getMail());
				st.setString(7, cli.getTelefono());
				st.setString(8, cli.getDireccion());
				st.executeUpdate();
				keyResultSet=st.getGeneratedKeys();
			}catch (Exception e) 
			{
				_logger.error(e.getMessage());
				throw e;
			}
			finally 
			{
				try 
				{
					if(keyResultSet!=null) {keyResultSet.close();}
	                if(st!=null) {st.close();}
	                Conexion.getInstancia().desconectar();
				} catch (Exception e) {
					_logger.error(e.getMessage());
					throw e;
				}
			}
		}

	}
	
	public Boolean existe(String usuario, String contrasena) {
		PreparedStatement st = null;
		ResultSet rs=null;
		String sentenciaSQL="SELECT * FROM cliente WHERE cliente_usuario='"+usuario+"' AND cliente_contrasena='"+contrasena+"' and fecha_baja is null";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			rs = st.executeQuery();
			if(rs.next()) {
				return true;
			}else {
				return false;
			}

		} catch (Exception e) {
			_logger.error(e.getMessage());
		} finally {
			try {
				if(st!=null) {st.close();}
				Conexion.getInstancia().desconectar();
			} catch (Exception e) {
				_logger.error(e.getMessage());
			}
		}
		return false;
	}
	
	public void baja(String usuario, String contrasena) throws Exception {
		PreparedStatement st = null;
		if (!existe(usuario, contrasena))
		{
			throw new NonExistentUserException("Usuario y/o contraseña incorrectos");
		}
		else
		{
			String sentenciaSQL="UPDATE cliente SET fecha_baja = current_date WHERE cliente_usuario=? AND cliente_contrasena=? and fecha_baja is null";
			try {
				st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
				st.setString(1, usuario);
				st.setString(2, contrasena);
				st.executeUpdate();
			} catch (Exception e) {
				throw e;
			} finally {
				try {
					if(st!=null) {st.close();}
					Conexion.getInstancia().desconectar();
				} catch (Exception e) {
					_logger.error(e.getMessage());
				}
			}
		}
	}
	
	public Boolean yaExisteUsuario(String usuario, String mail) {
		PreparedStatement st = null;
		ResultSet rs=null;
		String sentenciaSQL="SELECT * FROM cliente WHERE cliente_usuario='"+usuario+"' or mail='"+mail+"' and fecha_baja is null";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			rs = st.executeQuery();
			
			//True if any result exist
			if(rs.next()) {
				return true;
			}else {
				return false;
			}

		} catch (Exception e) {
			_logger.error(e.getMessage());
		} finally {
			try {
				if(st!=null) {st.close();}
				Conexion.getInstancia().desconectar();
			} catch (Exception e) {
				_logger.error(e.getMessage());
			}
		}
		return false;
	}
	
	public Cliente buscar_cliente(String usuario) throws Exception {
		Cliente cli = new Cliente();
		PreparedStatement st = null;
		ResultSet rs=null;
		String sentenciaSQL="SELECT * FROM cliente WHERE cliente_usuario='"+usuario+"' and fecha_baja is null";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			rs = st.executeQuery();
			if(rs.next()) {
				cli.setDni(rs.getString(1));
				cli.setCliente_usuario(rs.getString(2));
				cli.setCliente_contrasena(rs.getString(3));
				cli.setNombre(rs.getString(4));
				cli.setApellido(rs.getString(5));
				cli.setMail(rs.getString(6));
				cli.setTelefono(rs.getString(7));
				cli.setDireccion(rs.getString(8));
				cli.setFecha_baja_socio(rs.getDate(9));
				return cli;
			}else {
				throw new NonExistentUserException();
			}

		}catch(NonExistentUserException e){
			throw e;	
		}catch (Exception e) {
			_logger.error(e.getMessage());
			throw e;
			
		} finally {
			try {
				if(st!=null) {st.close();}
				Conexion.getInstancia().desconectar();
			} catch (Exception e) {
				_logger.error(e.getMessage());
			}
		}
	}
	public Cliente buscar_cliente_por_usuario (String usuario) throws Exception{
		PreparedStatement ps = null;
		String sentencia = "SELECT dni, nombre, apellido, mail, telefono, direccion FROM cliente WHERE cliente_usuario = ? and fecha_baja is null";
		ResultSet rs = null;
		Cliente cli = new Cliente();
		try{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentencia);
			ps.setString(1, usuario);
			rs = ps.executeQuery();
			if (rs.next()) {
				cli.setDni(rs.getString(1));
				cli.setNombre(rs.getString(2));
				cli.setApellido(rs.getString(3));
				cli.setMail(rs.getString(4));
				cli.setTelefono(rs.getString(5));
				cli.setDireccion(rs.getString(6));
			}
			else {
				throw new NonExistentUserException("No existe el usuario ingresado");
			}
		}catch(NonExistentUserException e){
			throw e;	
		} catch (Exception e) {
			_logger.error(e.getMessage());
		}finally {
			try {
				if(ps!=null) {ps.close();}
				Conexion.getInstancia().desconectar();
			} catch (Exception e) {
				_logger.error(e.getMessage());
			}
		}
		return cli;     
	}
	
	public void modificacion_cliente(Cliente cli) throws Exception {
		PreparedStatement ps = null;
		String sentencia = "UPDATE cliente SET nombre = ?, apellido = ?, mail = ?, direccion = ?, telefono = ? WHERE dni = ? and fecha_baja is null";
		try {
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentencia); //PreparedStatement.SUCCESS_NO_INFO
			ps.setString(1,cli.getNombre());
			ps.setString(2,cli.getApellido());
			ps.setString(3,cli.getMail());
			ps.setString(4,cli.getDireccion());
			ps.setString(5,cli.getTelefono());
			ps.setString(6,cli.getDni());
			int result = ps.executeUpdate(); //esto no funciona, todavia no se por que  
			if (result == 0 ) {
				throw new NoRowsAffectedException("No se ha editado ningun usuario");
			}
		}catch(Exception e) {
			_logger.error(e.getMessage());
			throw e;
		}finally {
			try {
				if(ps!=null) {ps.close();}
				Conexion.getInstancia().desconectar();
			} catch (Exception e) {
				_logger.error(e.getMessage());
			}
		}
	}
	
	public Cliente buscar_cliente_por_dni(String dni) throws Exception {
		Cliente cli = null;
		PreparedStatement st = null;
		ResultSet rs=null;
		String sentenciaSQL="SELECT * FROM cliente WHERE dni=?";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			st.setString(1, dni);
			rs = st.executeQuery();
			if(rs.next()) {
				cli = new Cliente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9), rs.getDate(10));
				return cli;
			}
			else 
			{
				throw new NonExistentUserException("No existe usuario con el dni ingresado");
			}
		} 
		catch (Exception e) 
		{
			_logger.error(e.getMessage());
			throw e;
		} 
		finally 
		{
			try 
			{
				if(st!=null) {st.close();}
				Conexion.getInstancia().desconectar();
			}
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
	}
	
	public void registrar_socio(String dni) throws Exception {
		PreparedStatement st = null;
		String sentenciaSQL="UPDATE cliente SET fecha_baja_socio=NULL WHERE dni=?";
		try 
		{
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			st.setString(1, dni);
			st.executeUpdate();
		} 
		catch (Exception e) 
		{
			_logger.error(e.getMessage());
			throw e;
		}
		finally 
		{
			try 
			{
				if(st!=null) {st.close();}
				Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
	}
	
	public void cambioContrasena(String usuario, String contrasena_actual, String contrasena_nueva) throws Exception {
		String sentenciaSQL="UPDATE cliente SET cliente_contrasena=? WHERE cliente_usuario=? AND cliente_contrasena=? and fecha_baja is null";
		PreparedStatement st = null;
		if (!existe(usuario, contrasena_actual)) 
		{
			throw new NonExistentUserException("La contrasena actual es incorrecta");
		}
		else
		{	
			try {
				st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
				st.setString(1, contrasena_nueva);
				st.setString(2, usuario);
				st.setString(3, contrasena_actual);
				st.executeUpdate();				
			}catch(Exception e){
				_logger.error(e.getMessage());
				throw e;
			}finally {
				try {
					if(st!=null) {st.close();}
					Conexion.getInstancia().desconectar();
				} catch (Exception e) {
					_logger.error(e.getMessage());
				}
			}
		}

	}
	
	public ArrayList<String> ObtenerDniSociosActivos() throws Exception {
		ArrayList <String> dniSocios = new ArrayList<String>();
		String sentenciaSQL="SELECT dni FROM cliente WHERE fecha_baja is null AND fecha_baja_socio is null AND dni not in (SELECT dni_cliente FROM cuota WHERE mes=MONTH(current_date) and anio=YEAR(current_date))";
		Statement st = null;
		ResultSet rs = null;
		try 
		{
			st=Conexion.getInstancia().getConexion().createStatement();
			rs = st.executeQuery(sentenciaSQL);
			if (rs.next())
			{
				dniSocios.add(rs.getString("dni"));
				while (rs.next())
				{
					dniSocios.add(rs.getString("dni"));
				}
				return dniSocios;
			}
			else
			{
				throw new NonExistentPartnerException("No existen socios activos sin cuotas generadas");
			}
		} catch(NonExistentPartnerException e) {
			throw e;
		}catch(Exception e)
		{
			_logger.error(e.getMessage());
			throw e;
		}
		finally 
		{
			try 
			{
				if(st!=null) {st.close();}
				Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
	}
	
	public int BajaSociosDeudores() throws Exception
	{
		int nroBajas = 0;
		PreparedStatement ps = null;
		String sentenciaSQL = "UPDATE cliente SET fecha_baja_socio=current_date WHERE dni in (SELECT dni_cliente FROM cuota WHERE fecha_pago is null GROUP BY dni_cliente HAVING COUNT(dni_cliente) > 3) AND fecha_baja_socio is null";
		try
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL, Statement.RETURN_GENERATED_KEYS);
			nroBajas = ps.executeUpdate();
			return nroBajas;
		}
		catch(Exception e)
		{
			_logger.error(e.getMessage());
			throw e;
		}
		finally 
		{
			try 
			{
				if(ps!=null) {ps.close();}
				Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
	}
	public boolean EsSocio(String nombreUsuario) {
		PreparedStatement ps = null;
		String sentenciaSQL = "SELECT fecha_baja_socio FROM cliente WHERE cliente_usuario = ? AND fecha_baja IS NULL";
		try
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setString(1, nombreUsuario);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				throw new NonExistentUserException("no se encontro el usuario "+nombreUsuario + " para verificar si es socio"); 
			}
			if (rs.getDate(1) == null)
			{
				return true;
			}
			return false;
		}
		catch(Exception e)
		{
			_logger.error(e.getMessage());
			return false;
		}
		finally 
		{
			try 
			{
				if(ps!=null) {ps.close();}
				Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
	}
	
	public ArrayList<Cliente> ObtenerSocios() throws Exception
	{
		ArrayList<Cliente> socios = new ArrayList<Cliente>();
		Cliente cliente = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sentenciaSQL = "SELECT * FROM cliente WHERE fecha_baja_socio is null";
		try
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				cliente = new Cliente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9), rs.getDate(10));
				socios.add(cliente);
				while(rs.next()) 
				{
					cliente = new Cliente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9), rs.getDate(10));
					socios.add(cliente);
				}
				return socios;
			}
			else
			{
				throw new NonExistentPartnerException("No existen socios");
			}
		}
		catch (Exception e)
		{
			_logger.error(e.getMessage());
			throw e;
		}
		finally 
		{
			try 
			{
				if(ps!=null) {ps.close();}
				Conexion.getInstancia().desconectar();
			}
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}

	}
	public ArrayList<SocioDeudor> GetSociosDeudores() throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		String sentenciaSQL = "SELECT cli.dni, cli.nombre, cli.apellido,  cli.cliente_usuario , cli.mail, cli.telefono, COUNT(cu.dni_cliente) FROM Cliente cli \r\n"
				+ "INNER JOIN Cuota cu ON cli.dni = cu.dni_cliente \r\n"
				+ "WHERE cu.fecha_pago IS NULL AND cli.fecha_baja_socio IS NULL AND cli.fecha_baja IS NULL \r\n"
				+ "GROUP BY cli.dni, cli.nombre, cli.apellido,  cli.cliente_usuario , cli.mail, cli.telefono";
		String sentenciaSQLCuotas = "SELECT cu.mes, cu.anio, cu.valor FROM Cliente as cli \r\n"
				+ "INNER JOIN Cuota as cu ON cli.dni = cu.dni_cliente \r\n"
				+ "WHERE cu.fecha_pago IS NULL AND cli.fecha_baja_socio IS NULL AND cli.fecha_baja IS NULL AND cli.dni = ?";
		ArrayList<SocioDeudor> sociosDeudores = new ArrayList<SocioDeudor>(); 
		try {
			Cuota cuota = new Cuota();
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			rs = ps.executeQuery();
			while(rs.next()) {
				ArrayList<Cuota> cuotas = new ArrayList<Cuota>();
				SocioDeudor socio = new SocioDeudor();
				socio.setDni(rs.getString(1));
				socio.setNombre(rs.getString(2));
				socio.setApellido(rs.getString(3));
				socio.setCliente_usuario(rs.getString(4));
				socio.setMail(rs.getString(5));
				socio.setTelefono(rs.getString(6));
				socio.setCantidadCuotasAdeudadas(rs.getInt(7));
				ps2 = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQLCuotas);
				ps2.setString(1, socio.getDni());
				rs2 = ps2.executeQuery();
				while(rs2.next())
				{
					cuota.setMes(rs2.getInt(1));
					cuota.setAnio(rs2.getInt(2));
					cuota.setValor(rs2.getDouble(3));
					cuotas.add(cuota);
				}
				socio.setCuotas(cuotas);
				sociosDeudores.add(socio);
			}
		}catch(SQLException ex) {
			_logger.error(ex.getMessage());
			throw ex;
		}
		finally{
			try 
			{
				if(ps!=null) {ps.close();}
				Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
		return sociosDeudores;
		
	}
}
	

