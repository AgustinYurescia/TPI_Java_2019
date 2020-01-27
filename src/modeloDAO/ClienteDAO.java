package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import config.Conexion;
import modelo.Cliente;

public class ClienteDAO {
	
	public void alta(Cliente cli) {
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO cliente(dni,cliente_usuario,cliente_contrasena,nombre,apellido,mail,telefono,direccion,fecha_baja_socio)VALUES(?,?,?,?,?,?,?,?,current_date)";
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
		String sentenciaSQL="SELECT * FROM cliente WHERE cliente_usuario='"+usuario+"' AND cliente_contrasena='"+contrasena+"'";
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
	
	public void baja(String usuario, String contrasena) {
		PreparedStatement st = null;
		String sentenciaSQL="DELETE FROM cliente WHERE cliente_usuario='"+usuario+"' AND cliente_contrasena='"+contrasena+"'";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			st.executeUpdate();
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
	}
	
	public void cambio_contrasena(String usuario, String contrasena_vieja, String contrasena_nueva) {
		PreparedStatement st = null;
		String sentenciaSQL="UPDATE cliente SET cliente_contrasena = '"+contrasena_nueva+"' WHERE cliente_usuario='"+usuario+"'"
				+ " AND cliente_contrasena='"+contrasena_vieja+"'";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			st.executeUpdate();
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
	}
	
	public Boolean yaExisteUsuario(String usuario) {
		PreparedStatement st = null;
		ResultSet rs=null;
		String sentenciaSQL="SELECT * FROM cliente WHERE cliente_usuario='"+usuario+"'";
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
	
	public Cliente buscar_cliente(String usuario) {
		Cliente cli = new Cliente();
		PreparedStatement st = null;
		ResultSet rs=null;
		String sentenciaSQL="SELECT * FROM cliente WHERE cliente_usuario='"+usuario+"'";
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
				return cli;
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
		return cli;
	}
	public Cliente buscar_cliente_2 (String usuario) {
		PreparedStatement ps = null;
		String sentencia = "SELECT dni, nombre, apellido, mail, telefono, direccion FROM cliente WHERE cliente_usuario = ?";
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
			return cli;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps!=null) {ps.close();}
				Conexion.getInstancia().desconectar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cli;     
	}
	
	public void modificacion_cliente(Cliente cli) {
		PreparedStatement ps = null;
		String sentencia = "UPDATE cliente SET nombre = ?, apellido = ?, mail = ?, direccion = ?, telefono = ? WHERE dni = ? ";
		try {
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentencia); //PreparedStatement.SUCCESS_NO_INFO
			ps.setString(1,cli.getNombre());
			ps.setString(2,cli.getApellido());
			ps.setString(3,cli.getMail());
			ps.setString(4,cli.getDireccion());
			ps.setString(5,cli.getTelefono());
			ps.setString(6,cli.getDni());
			ps.executeUpdate(); //esto no funciona, todavia no se por que  
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps!=null) {ps.close();}
				Conexion.getInstancia().desconectar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}


