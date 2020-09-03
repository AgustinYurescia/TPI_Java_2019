package modeloDAO;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;
import config.Conexion;
import modelo.Producto;

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
					prod.set_imagen(rs.getBinaryStream("imagen"));
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
	
	
	public void listar_imagen(int codigo, HttpServletResponse response) {
		Statement st = null;
		ResultSet rs = null;
		String sentenciaSQL = "SELECT * FROM producto WHERE codigo="+codigo;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		response.setContentType("image/*");
		try {
			outputStream = response.getOutputStream();
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaSQL);
			if(rs.next()) {
				inputStream = rs.getBinaryStream("imagen");
			}
			bufferedInputStream = new BufferedInputStream(inputStream);
			bufferedOutputStream = new BufferedOutputStream(outputStream);
			int i = 0;
			while ((i=bufferedInputStream.read()) != -1) {
				bufferedOutputStream.write(i);
			}
		}catch(Exception e){
			
		}
		
	}

	
	public void alta(Producto prod, Double precio) {
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO producto(nombre,imagen,stock,codigo_categoria)VALUES(?,?,?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setString(1, prod.getNombre());
			st.setBlob(2, prod.get_imagen());
			st.setInt(3, prod.getStock());
			st.setInt(4, prod.getCodigo_categoria());
			st.executeUpdate();
			keyResultSet=st.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()) {
				prod.setCodigo(keyResultSet.getInt(1));
				calcular_precio_venta(keyResultSet.getInt(1),precio);
				
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
	
	
	public List<Producto> obtener_por_codigo_categoria(int codigo_categoria) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Producto>lista = new ArrayList<>();
		String sentenciaSQL = "SELECT * FROM producto WHERE fecha_baja is null and codigo_categoria = ? order by nombre asc";
		try {
			ps=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setInt(1, codigo_categoria);
			rs=ps.executeQuery();
			if(rs!=null) {
				while(rs.next()) {
					Producto prod = new Producto();
					prod.setCodigo(rs.getInt("codigo"));
					prod.setNombre(rs.getString("nombre"));
					prod.set_imagen(rs.getBinaryStream("imagen"));
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
				if(ps!=null) {ps.close();}
				Conexion.getInstancia().desconectar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lista;
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
	
	
	public void reponer_stock(int codigo_producto, int cantidad, double precio) {
		PreparedStatement st = null;
		String sentenciaSQL="UPDATE producto SET stock=stock+"+cantidad+" WHERE codigo="+codigo_producto+"";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			st.executeUpdate();
			calcular_precio_venta(codigo_producto, precio);
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
	
	public void descontar_stock(int codigo_producto, int cantidad) {
		PreparedStatement st = null;
		String sentenciaSQL="UPDATE producto SET stock=stock-"+cantidad+" WHERE codigo="+codigo_producto+"";
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
	
	
	public void editar_producto(Producto prod) {
		PreparedStatement st = null;
		String sentenciaSQL="UPDATE producto SET nombre=?,imagen=?,precio_venta=? WHERE codigo=?";
		try {
			if (prod.get_imagen() != null) 
			{
				st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
				st.setString(1, prod.getNombre());
				st.setBlob(2, prod.get_imagen());
				st.setDouble(3, prod.getPrecioVenta());
				st.setInt(4, prod.getCodigo());
			}
			else
			{
				sentenciaSQL="UPDATE producto SET nombre=?,precio_venta=? WHERE codigo=?";
				st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
				st.setString(1, prod.getNombre());
				st.setDouble(2, prod.getPrecioVenta());
				st.setInt(3, prod.getCodigo());
			}
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
	
	
	public void calcular_precio_venta(int codigo_producto, Double precio) {
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sentenciaParaPorc="SELECT * FROM producto WHERE codigo = "+codigo_producto+"";
		try {
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaParaPorc);
			if (rs.next()) {
				prod.setCodigo(rs.getInt(1));
				prod.setNombre(rs.getString(2));
				prod.set_imagen(rs.getBinaryStream(3));
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