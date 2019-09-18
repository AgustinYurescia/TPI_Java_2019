package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.Conexion;
import modelo.Porc_Ganancia;

public class Porc_GananciaDAO {
	
	public void alta(Porc_Ganancia pg) {
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO porc_ganancia(fecha_desde,porcentaje)VALUES(?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setDate(1, pg.getFecha_desde());
			st.setDouble(2, pg.getPorcentaje());
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
