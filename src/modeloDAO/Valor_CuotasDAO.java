package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.Conexion;
import modelo.Valor_Cuotas;

public class Valor_CuotasDAO {
	
	public void alta(Valor_Cuotas vc) {
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO valor_cuotas(fecha_desde,valor_cuota)VALUES(?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setDate(1, vc.getFecha_desde());
			st.setDouble(2, vc.getValor_cuota());
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
