package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.Conexion;
import modelo.Porc_Ganancia;

public class PorcentajeGananciaDAO {
	
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
