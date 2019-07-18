package modeloDAO;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import config.Conexion;
import modelo.Producto;

public class ProductoDAO {
		
	public List<Producto> listar() {
		Statement st = null;
		ResultSet rs=null;
		ArrayList<Producto>lista = new ArrayList<>();
		String sentenciaSQL="select * from productos";
		try {
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaSQL);
			if(rs!=null) {
				while(rs.next()) {
					Producto prod = new Producto();
					prod.setIdProducto(rs.getInt("idProducto"));
					prod.setNombre(rs.getString("nombre"));
					prod.setCategoria(rs.getString("categoria"));
					prod.setDescripcion(rs.getString("descripcion"));
					prod.setPrecioCosto(rs.getDouble("precioCosto"));
					prod.setPrecioVenta();
					prod.setStock(rs.getInt("stock"));
					lista.add(prod);
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

	public void alta(Producto prod) {
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="insert into productos(nombre,categoria,descripcion,precioCosto,precioVenta,stock)values(?,?,?,?,?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setString(1,prod.getNombre());
			st.setString(2, prod.getCategoria());
			st.setString(3, prod.getDescripcion());
			st.setDouble(4, prod.getPrecioCosto());
			st.setDouble(5, prod.getPrecioVenta());
			st.setInt(6, prod.getStock());
			st.executeUpdate();
			keyResultSet=st.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()) {
				prod.setIdProducto(keyResultSet.getInt(1));
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

	
	public Boolean baja(int id) {
		return null;
	}

	
	public Boolean modificar(Producto prod) {
		return null;
	}
	
}
