package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.Conexion;

public class TardanzaPedidoDAO {
	
	private static Logger _logger = LogManager.getLogger(TardanzaPedidoDAO.class);
	
	private PreparedStatement ps = null;
	private String sentenciaSQL = null;
	private ResultSet rs = null;
	
	public void Alta(int cantidadDias) throws Exception
	{
		sentenciaSQL = "INSERT INTO tardanza_preparacion_pedido(fecha_desde, cantidad_de_dias)VALUES(current_date,?)";
		try
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setInt(1, cantidadDias);
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
	
	public void Modificacion(int cantidadDias) throws Exception
	{	
		sentenciaSQL = "UPDATE tardanza_preparacion_pedido SET cantidad_de_dias=? WHERE fecha_desde=current_date";
		try
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setInt(1, cantidadDias);
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
		sentenciaSQL = "SELECT * FROM tardanza_preparacion_pedido WHERE fecha_desde=current_date";
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
	public Integer obtenerPlazoActual() throws Exception
	{	
		Integer plazo = 0;
		sentenciaSQL = "SELECT cantidad_de_dias "
						+ "FROM tardanza_preparacion_pedido "
						+ "WHERE fecha_desde = ("
						+ "SELECT MAX(fecha_desde) "
						+ "FROM tardanza_preparacion_pedido "
						+ "WHERE fecha_desde <= current_date)";
		try
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				plazo = rs.getInt(1);
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
		return plazo;
	}
}
