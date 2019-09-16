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
					prod.setNombre(rs.getString("nombre"));
					prod.setUrl_imagen(rs.getString("url_imagen"));
					prod.setStock(rs.getInt("stock"));
					prod.setPrecioVenta();
					prod.setCodigo_categoria(Integer.parseInt(rs.getString("codigoCategoria")));
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
		String sentenciaSQL="insert into producto(nombre,url_imagen,stock,precio_venta,codigo_categoria)values(?,?,?,?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setString(1,prod.getNombre());
			st.setString(2, prod.getUrl_imagen());
			st.setInt(3, prod.getStock());
			st.setDouble(4, prod.getPrecioVenta());
			st.setInt(5, prod.getCodigo_categoria());
			st.executeUpdate();
			keyResultSet=st.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()) {
				prod.setCodigo(keyResultSet.getInt(1));
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
	
	public Boolean baja(int idProd) {
		PreparedStatement st = null;
		String sentenciaSQL="DELETE FROM productos WHERE idProducto=?";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			st.setInt(1, idProd);
			st.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
                Conexion.getInstancia().desconectar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public void update(Producto prod) {
		PreparedStatement st = null;
		
		String sentenciaSQL="update productos set(nombre=?,url_imagen=?,stock=?,precio_venta=?,codigo_categoria=?) where idProducto=? values(?,?,?,?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			st.setString(1,prod.getNombre());
			st.setString(2, prod.getUrl_imagen());
			st.setInt(3, prod.getStock());
			st.setDouble(4, prod.getPrecioVenta());
			st.setInt(5, prod.getCodigo_categoria());
			st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
                Conexion.getInstancia().desconectar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
