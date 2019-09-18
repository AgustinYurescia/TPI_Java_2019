package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.Conexion;
import modelo.Descuento_Socio;

public class Descuento_SocioDAO {
	public void alta(Descuento_Socio ds) {
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO descuento_socio(fecha_desde,porcentaje_desc)VALUES(?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setDate(1, ds.getFecha_desde());
			st.setDouble(2, ds.getPorcentaje_desc());
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
