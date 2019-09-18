package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.Conexion;
import modelo.Pedprov_producto;

public class Pedprov_productoDAO {
	
	public void alta(Pedprov_producto ppp) {
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO pedprov_producto(codigo_producto,cuil_proveedor,nro_pedido_prov,cantidad)values(?,?,?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setInt(1, ppp.getCodigo_producto());
			st.setString(2, ppp.getCuil_proveedor());
			st.setInt(3, ppp.getNro_pedido_prov());
			st.setInt(4, ppp.getCantidad());
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
