package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.Conexion;
import modelo.Categoria;

public class CategoriaDAO {
	public void alta(Categoria cat) {
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO categoria(descripcion)VALUES(?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setString(1, cat.getDescripcion());
			st.executeUpdate();
			keyResultSet=st.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()) {
				cat.setCodigo(keyResultSet.getInt(1));
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
	public int getCodigoCategoria(String descripcion_categoria) {
		PreparedStatement st = null;
		ResultSet rs=null;
		String sentenciaSQL="SELECT codigo FROM categoria WHERE descripcion = '"+descripcion_categoria+"' ";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			rs=st.executeQuery();
			if(rs!=null && rs.next()) {
				return rs.getInt("codigo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {rs.close();}
                if(st!=null) {st.close();}
                Conexion.getInstancia().desconectar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
}
