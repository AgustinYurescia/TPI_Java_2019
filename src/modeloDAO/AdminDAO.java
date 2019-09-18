package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.Conexion;
import modelo.Admin;

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
}
