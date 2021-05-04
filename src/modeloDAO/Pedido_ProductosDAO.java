package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.Conexion;
import modelo.Pedido_Productos;

public class Pedido_ProductosDAO {
	private static Logger _logger = LogManager.getLogger(Pedido_ProductosDAO.class);
	//_logger.error(e.getMessage());
	
	public void alta(Pedido_Productos pp) {
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO pedido_productos(codigo_producto,nro_pedido,cantidad)values(?,?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setInt(1, pp.getCodigo_producto());
			st.setInt(2, pp.getNro_pedido());
			st.setInt(3, pp.getCantidad());
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

}
