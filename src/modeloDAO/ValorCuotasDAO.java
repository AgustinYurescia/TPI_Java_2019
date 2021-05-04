package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.Conexion;

public class ValorCuotasDAO {
	private PreparedStatement ps;
	private ResultSet rs;
	private String sentenciaSQL = null;
	
	private static Logger _logger = LogManager.getLogger(ValorCuotasDAO.class);
	
	
	public void Alta(double valorCuota) throws Exception {
		sentenciaSQL="INSERT INTO valor_cuotas(fecha_desde,valor_cuota)VALUES(current_date,?)";
		try 
		{
			ps=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setDouble(1, valorCuota);
			ps.executeUpdate();
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
	
	public void Modificacion(double valorCuota) throws Exception
	{	
		sentenciaSQL = "UPDATE valor_cuotas SET valor_cuota=? WHERE fecha_desde=current_date";
		try
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setDouble(1, valorCuota);
			ps.executeUpdate();
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
	
	public boolean YaExiste() throws Exception
	{	
		sentenciaSQL = "SELECT * FROM valor_cuotas WHERE fecha_desde=current_date";
		try
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				return true;
			}
			else
			{
				return false;
			}
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
				if(rs!=null) {rs.close();}
				if(ps!=null) {ps.close();}
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
	}

}
