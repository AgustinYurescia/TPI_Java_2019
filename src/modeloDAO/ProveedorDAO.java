package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.Conexion;
import modelo.Proveedor;

public class ProveedorDAO {
	
	public void alta(Proveedor pro) {
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO proveedor(cuil,razon_social,telefono,direccion,mail)values(?,?,?,?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setString(1, pro.getCuil());
			st.setString(2, pro.getRazon_social());
			st.setString(3, pro.getTelefono());
			st.setString(4, pro.getDireccion());
			st.setString(5, pro.getMail());
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
