package modeloDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.Conexion;
import modelo.Producto_Proveedor;

public class Producto_ProveedorDAO {
	
	public void alta(Producto_Proveedor pp) {
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="insert into producto_proveedor(cuil_proveedor,codigo_producto)values(?,?)";
		try {
				st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
				st.setString(1, pp.getCuil_proveedor());
				st.setInt(2, pp.getCodigo_producto());
				st.executeUpdate();
				keyResultSet=st.getGeneratedKeys();
			} 
			catch (Exception e) {
			e.printStackTrace();
			}
			finally {
				try {
					if(keyResultSet!=null) {keyResultSet.close();}
					if(st!=null) {st.close();}
					Conexion.getInstancia().desconectar();
					} 
					catch (Exception e) {
						e.printStackTrace();
					}
					}
	}
}



