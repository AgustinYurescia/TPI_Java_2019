package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.Conexion;
import modelo.Precio;

public class PrecioDAO {
	
	public void alta(Precio pre) {
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="insert into precio(codigo_producto,cuil_proveedor,fecha_desde,precio)values(?,?,?,?)";
		try {
				st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
				st.setInt(1, pre.getCodigo_producto());
				st.setString(2, pre.getCuil_proveedor());
				st.setDate(3, pre.getFecha_desde());
				st.setDouble(4, pre.getPrecio());
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
