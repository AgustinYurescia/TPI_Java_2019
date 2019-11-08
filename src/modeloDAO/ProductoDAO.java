package modeloDAO;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import config.Conexion;
import modelo.Producto;
import modelo.Producto_Proveedor;
import modeloDAO.Producto_ProveedorDAO;
import modelo.Precio;
import modeloDAO.PrecioDAO;

public class ProductoDAO {
		
	public List<Producto> obtener_todos() {
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Producto>lista = new ArrayList<>();
		String sentenciaSQL = "SELECT * FROM producto WHERE fecha_baja is null order by nombre asc";
		try {
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaSQL);
			if(rs!=null) {
				while(rs.next()) {
					Producto prod = new Producto();
					prod.setCodigo(rs.getInt("codigo"));
					prod.setNombre(rs.getString("nombre"));
					prod.setUrl_imagen(rs.getString("url_imagen"));
					prod.setStock(rs.getInt("stock"));
					prod.setPrecioVenta(rs.getDouble("precio_venta"));
					prod.setCodigo_categoria(Integer.parseInt(rs.getString("codigo_categoria")));
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

	public void alta(Producto prod, String cuil_proveedor, Double precio) {
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO producto(nombre,url_imagen,stock,codigo_categoria)VALUES(?,?,?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setString(1, prod.getNombre());
			st.setString(2, prod.getUrl_imagen());
			st.setInt(3, prod.getStock());
			st.setInt(4, prod.getCodigo_categoria());
			st.executeUpdate();
			keyResultSet=st.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()) {
				prod.setCodigo(keyResultSet.getInt(1));
				Producto_Proveedor pp = new Producto_Proveedor();
				Producto_ProveedorDAO ppDAO = new Producto_ProveedorDAO();
				Precio pre = new Precio();
				PrecioDAO preDAO = new PrecioDAO();
				pp.setCodigo_producto(keyResultSet.getInt(1));
				pp.setCuil_proveedor(cuil_proveedor);
				pre.setCodigo_producto(prod.getCodigo());
				pre.setCuil_proveedor(cuil_proveedor);
				java.util.Date utilDate = new java.util.Date();
				long lnMilisegundos = utilDate.getTime();
				java.sql.Date fecha = new java.sql.Date(lnMilisegundos);
				pre.setFecha_desde(fecha);
				pre.setPrecio(precio);
				ppDAO.alta(pp);
				preDAO.alta(pre);
				calcular_precio_venta_nuevo_producto(keyResultSet.getInt(1),precio);
				
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

	public void baja(int codigo_producto_baja) {
		PreparedStatement st = null;
		String sentenciaSQL="UPDATE producto SET fecha_baja = current_date WHERE codigo="+codigo_producto_baja+"";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
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
	
	public void actualizar_stock(int codigo_producto, int cantidad) {
		PreparedStatement st = null;
		String sentenciaSQL="UPDATE producto SET stock=stock+"+cantidad+" WHERE codigo="+codigo_producto+"";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
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
	public void calcular_precio_venta_nuevo_producto(int codigo_producto, Double precio) {
		Double porcGan;
		Double precio_venta;
		Statement st = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		String sentenciaParaPorc="SELECT porcentaje FROM porc_ganancia WHERE fecha_desde = (SELECT MAX(fecha_desde) FROM porc_ganancia WHERE fecha_desde <= current_date)";
		try {
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaParaPorc);
			if (rs.next()) {
				porcGan=rs.getDouble("porcentaje");
				precio_venta = (precio*(1+(porcGan/100)));
				String sentenciaParaUpdate="UPDATE producto SET precio_venta="+precio_venta+" WHERE codigo="+codigo_producto+"";
				st2=Conexion.getInstancia().getConexion().prepareStatement(sentenciaParaUpdate);
				st2.executeUpdate(); 
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public Producto buscar_producto(int codigo_producto) {
		Producto prod = new Producto();
		Statement st = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		String sentenciaParaPorc="SELECT * FROM producto WHERE codigo = "+codigo_producto+"";
		try {
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaParaPorc);
			if (rs.next()) {
				prod.setCodigo(rs.getInt(1));
				prod.setNombre(rs.getString(2));
				prod.setUrl_imagen(rs.getString(3));
				prod.setStock(rs.getInt(4));
				prod.setPrecioVenta(rs.getDouble(5));
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return prod;
	}
}