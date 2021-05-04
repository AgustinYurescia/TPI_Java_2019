package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.Conexion;
import exceptions.NonExistentUserException;
import modelo.Admin;


public class AdminDAO {
	private static Logger _logger = LogManager.getLogger(AdminDAO.class);
	public void alta(Admin adm) {
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO admin(admin_usuario,admin_contrasena)VALUES(?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setString(1, adm.getAdmin_usuario());
			st.setString(2, adm.getAdmin_contrasena());
			st.executeUpdate();
			keyResultSet=st.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()) {
			}
		} catch (Exception e) {
			_logger.error(e.getMessage());
		}finally {
			try {
				if(keyResultSet!=null) {keyResultSet.close();}
                if(st!=null) {st.close();}
                Conexion.getInstancia().desconectar();
			} catch (Exception e) {
				_logger.error(e.getMessage());
			}
		}
	}
	
	public Boolean existe(String usuario, String contrasena) {
		PreparedStatement st = null;
		ResultSet rs=null;
		String sentenciaSQL="SELECT * FROM admin WHERE admin_usuario='"+usuario+"' AND admin_contrasena='"+contrasena+"'";
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
	
	public void cambioContrasena(String usuario, String contrasena_actual, String contrasena_nueva) throws Exception {
		String sentenciaSQL="UPDATE admin SET admin_contrasena=? WHERE admin_usuario=? AND admin_contrasena=?";
		PreparedStatement st = null;
		if (!existe(usuario, contrasena_actual)) 
		{
			throw new NonExistentUserException("La contrasena actual es incorrecta");
		}
		else
		{
			try
			{
				st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
				st.setString(1, contrasena_nueva);
				st.setString(2, usuario);
				st.setString(3, contrasena_actual);
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
				} catch (Exception e) {
					_logger.error(e.getMessage());
				}
			}
		}
	}
}
