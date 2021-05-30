package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.Conexion;


public class PorcentajeGananciaDAO {
	
	private static Logger _logger = LogManager.getLogger(PorcentajeGananciaDAO.class);
	
	private PreparedStatement ps = null;
	private String sentenciaSQL = null;
	private ResultSet rs = null;
	
	public void Alta(double porcentaje) throws Exception {
		sentenciaSQL="INSERT INTO porc_ganancia(fecha_desde,porcentaje)VALUES(current_date,?)";
		try 
		{
			ps=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setDouble(1,porcentaje);
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
	
	public void Modificacion(double porcentaje) throws Exception
	{	
		sentenciaSQL = "UPDATE porc_ganancia SET porcentaje=? WHERE fecha_desde=current_date";
		try
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setDouble(1, porcentaje);
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
		sentenciaSQL = "SELECT * FROM porc_ganancia WHERE fecha_desde=current_date";
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
	
	public float ObtenerPorcentajeActual() throws Exception
	{	
		sentenciaSQL = "SELECT porcentaje FROM porc_ganancia as pg WHERE pg.fecha_desde = (SELECT MAX(fecha_desde) FROM porc_ganancia)";
		float porcentaje = 0;
		try
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				porcentaje = rs.getFloat(1) / 100;
			}
			return porcentaje;
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
