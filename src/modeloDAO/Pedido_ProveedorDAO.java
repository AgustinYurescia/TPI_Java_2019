package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.Conexion;
import modelo.Pedido_Proveedor;

public class Pedido_ProveedorDAO {

	public void alta(Pedido_Proveedor pedp) {
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO pedido_proveedor(fecha_pedido,fecha_entrega_real,fecha_entrega_est,monto)values(?,?,?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setDate(1, pedp.getFecha_pedido());
			st.setDate(2, pedp.getFecha_entrega_real());
			st.setDate(3, pedp.getFecha_entrega_est());
			st.setDouble(4, pedp.getMonto());
			st.executeUpdate();
			keyResultSet=st.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()) {
				pedp.setNro_pedido_prov(keyResultSet.getInt(1));	
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
