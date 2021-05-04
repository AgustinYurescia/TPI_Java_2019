package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.Conexion;

public class TardanzaPedidoDAO {
	
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
			}
		}
	}

}
