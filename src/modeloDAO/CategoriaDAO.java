package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import config.Conexion;
import modelo.Categoria;

public class CategoriaDAO {
	
	public List<Categoria> obtenerTodas() 
	{
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Categoria>lista = new ArrayList<>();
		String sentenciaSQL = "SELECT * FROM categoria order by descripcion asc";
		try 
		{
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaSQL);
			if(rs!=null) 
			{
				while(rs.next()) 
				{
					Categoria cat = new Categoria();
					cat.setCodigo(rs.getInt("codigo"));
					cat.setDescripcion(rs.getString("descripcion"));
					lista.add(cat);
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if(rs!=null) {rs.close();}
				if(st!=null) {st.close();}
				Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return lista;
	}
	
	public void alta(String categoria) throws Exception 
	{
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO categoria(descripcion)VALUES(?)";
		try 
		{
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setString(1, categoria);
			st.executeUpdate();
			keyResultSet=st.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()) 
			{
			
			}
		} 
		catch (Exception e) 
		{
			throw e;
		}
		finally 
		{
			try 
			{
				if(keyResultSet!=null) {keyResultSet.close();}
                if(st!=null) {st.close();}
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
	}
	public void baja(int codigoCategoria) throws Exception 
	{
		PreparedStatement st = null;
		String sentenciaSQL="DELETE FROM categoria WHERE codigo=?";
		try 
		{
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			st.setInt(1, codigoCategoria);
			st.executeUpdate();		
		} 
		catch (Exception e) 
		{
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
				e.printStackTrace();
			}
		}
		
	}
	public boolean existeCategoria(String descripcion_categoria) throws Exception 
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		String sentenciaSQL="SELECT codigo FROM categoria WHERE descripcion = ?";
		try 
		{
			ps=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setString(1, descripcion_categoria);
			rs=ps.executeQuery();
			if(rs!=null && rs.next()) 
			{
				return true;
			}
		} 
		catch (Exception e) 
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
		return false;
	}
	
	public boolean existeCategoria(int codigoCategoria) throws Exception 
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		String sentenciaSQL="SELECT * FROM categoria WHERE codigo = ?";
		try 
		{
			ps=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setInt(1, codigoCategoria);
			rs=ps.executeQuery();
			if(rs!=null && rs.next()) 
			{
				return true;
			}
		} 
		catch (Exception e) 
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
		return false;
	}
	
}
