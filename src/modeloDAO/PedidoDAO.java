package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.Conexion;
import modelo.Pedido;


public class PedidoDAO {

	public void alta(Pedido ped) {
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO producto(fecha_pedido,fecha_entrega_est,monto,fecha_cancelacion,fecha_entrega_real,dni_cliente)values(?,?,?,?,?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setDate(1, ped.getFecha_pedido());
			st.setDate(2, ped.getFecha_entrega_est());
			st.setDouble(3, ped.getMonto());
			st.setDate(4, ped.getFecha_cancelacion());
			st.setDate(5, ped.getFecha_entrega_real());
			st.setString(6, ped.getDni_cliente());
			st.executeUpdate();
			keyResultSet=st.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()) {
				ped.setNro_pedido(keyResultSet.getInt(1));	
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
