package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import config.Conexion;
import modelo.Admin;
import modelo.Cliente;

public class AdminDAO {
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
			e.printStackTrace();
		}finally {
			try {
				if(keyResultSet!=null) {keyResultSet.close();}
                if(st!=null) {st.close();}
                Conexion.getInstancia().desconectar();
			} catch (Exception e) {
				e.printStackTrace();
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
			e.printStackTrace();
		} finally {
			try {
				if(st!=null) {st.close();}
				Conexion.getInstancia().desconectar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public ArrayList<String> cambioContrasena(String usuario, String contrasena_actual, String contrasena_nueva, String contrasena_nueva_rep) {
		ArrayList<String>mensajes = new ArrayList<>();
		String ok_mensaje = "Contraseña cambiada exitosamente";
		String error_mensaje = null;
		String sentenciaSQL="UPDATE admin SET admin_contrasena=? WHERE admin_usuario=? AND admin_contrasena=?";
		PreparedStatement st = null;
		if (existe(usuario, contrasena_actual)) {
			if (Admin.isValid(contrasena_nueva)) {
				if(contrasena_nueva.equals(contrasena_nueva_rep)) {
					try {
						st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
						st.setString(1, contrasena_nueva);
						st.setString(2, usuario);
						st.setString(3, contrasena_actual);
						st.executeUpdate();				
					}catch(Exception e){
						e.printStackTrace();
					}finally {
						try {
							if(st!=null) {st.close();}
							Conexion.getInstancia().desconectar();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				else {
					error_mensaje = "Las contraseñas ingresadas (contraseña nueva y contraseña nueva repetida) no coinciden";
					ok_mensaje = null;
					mensajes.add(0, ok_mensaje);
					mensajes.add(1, error_mensaje);
					return mensajes;
				}
			}else {
				error_mensaje = "La contraseña nueva debe tener longitud mayor o igual a 4 y menor o igual a 45";
				ok_mensaje = null;
				mensajes.add(0, ok_mensaje);
				mensajes.add(1, error_mensaje);
				return mensajes;
			}
		}else {
			error_mensaje = "La contraseña actual es incorrecta";
			ok_mensaje = null;
			mensajes.add(0, ok_mensaje);
			mensajes.add(1, error_mensaje);
			return mensajes;
		}
		mensajes.add(0, ok_mensaje);
		mensajes.add(1, error_mensaje);
		return mensajes;
	}
}
