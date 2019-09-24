package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import config.Conexion;
import modelo.Categoria;

public class CategoriaDAO {
	
	public List<Categoria> obtener_todos() {
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Categoria>lista = new ArrayList<>();
		String sentenciaSQL = "SELECT * FROM categoria order by descripcion asc";
		try {
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaSQL);
			if(rs!=null) {
				while(rs.next()) {
					Categoria cat = new Categoria();
					cat.setCodigo(rs.getInt("codigo"));
					cat.setDescripcion(rs.getString("descripcion"));
					lista.add(cat);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(st!=null) {st.close();}
				Conexion.getInstancia().desconectar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lista;
	}
	
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
