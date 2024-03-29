package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.Conexion;
import modelo.Categoria;

public class CategoriaDAO {
	
	private static Logger _logger = LogManager.getLogger(CategoriaDAO.class);
	
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
			_logger.error(e.getMessage());
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
				_logger.error(e.getMessage());
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
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
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
	
	public void modificacion(int codigoCategoria, String descripcion) throws Exception 
	{
		PreparedStatement ps = null;
		String sentenciaSQL="UPDATE categoria SET descripcion=? WHERE codigo=?";
		try 
		{
			ps=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setString(1, descripcion);
			ps.setInt(2, codigoCategoria);
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
		return false;
	}
	
	public Categoria buscarCategoria(int codigoCategoria) throws Exception 
	{
		Categoria categoria = null;
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
				categoria = new Categoria();
				categoria.setCodigo(rs.getInt(1));
				categoria.setDescripcion(rs.getString(2));
			}
			return categoria;
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
