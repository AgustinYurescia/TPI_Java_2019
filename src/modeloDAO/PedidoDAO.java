package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import config.Conexion;
import modelo.LineaPedido;
import modelo.Pedido;



public class PedidoDAO {

	public int alta(Pedido ped, ArrayList<LineaPedido> lin) { 
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO pedido(fecha_pedido,monto,dni_cliente)values(current_date,?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setDouble(1, ped.getMonto());
			st.setString(2, ped.getDni_cliente());
			st.executeUpdate();
			keyResultSet=st.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()) {
				ped.setNro_pedido(keyResultSet.getInt(1));
				setear_fecha_entrega_estimada(ped.getNro_pedido());
				generar_pedido_productos(ped.getNro_pedido(), lin);
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
		
		return ped.getNro_pedido();
	}

	public void generar_pedido_productos(int nroPedido, ArrayList<LineaPedido> lin ) { 
		ProductoDAO proDAO = new ProductoDAO();
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO pedido_productos(codigo_producto,nro_pedido,cantidad)values(?,?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ArrayList<LineaPedido> linea = lin; 
			Iterator<LineaPedido>iter = linea.iterator();
			LineaPedido l;
			while(iter.hasNext()){
				l=iter.next();
				st.setInt(1, l.getCodigo_producto());
				st.setInt(2, nroPedido);
				st.setInt(3, l.getCantidad());
				st.executeUpdate();
				proDAO.descontar_stock(l.getCodigo_producto(), l.getCantidad());
			}
		}catch (Exception e) {
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
	
	//CONTROLAR QUE ESTÉ BIEN
	public void calcular_monto_pedido(int nro_pedido) { 
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="UPDATE pedido SET monto = ("
				+ "SELECT SUM(pro.precio_venta * pp.cantidad)"
				+ "FROM pedido_productos AS pp"
				+ "INNER JOIN producto AS pro"
				+ "ON pp.codigo_producto = pro.codigo"
				+ "WHERE pp.nro_pedido = "+nro_pedido+""
				+ "GROUP BY pp.nro_pedido)"
				+ "WHERE nro_pedido = "+nro_pedido+"";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			st.executeUpdate();
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
	
	//CONTROLAR QUE ESTÉ BIEN
	public void setear_fecha_entrega_estimada(int nro_pedido) { 
		java.sql.Date fecha_ent_est = null;
		int cantidad_de_dias;
		Statement st = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		ResultSet keyResultSet=null;
		String sentenciaParaDias="SELECT cantidad_de_dias "
				+ "FROM tardanza_preparacion_pedido "
				+ "WHERE fecha_desde = ("
				+ "SELECT MAX(fecha_desde) "
				+ "FROM tardanza_preparacion_pedido "
				+ "WHERE fecha_desde <= current_date)";
		try {
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaParaDias);
			if (rs.next()) {
				cantidad_de_dias=rs.getInt(1);
				String sentenciaParaUpdate="UPDATE pedido SET fecha_entrega_est = ADDDATE(fecha_pedido, interval "+cantidad_de_dias+" DAY) WHERE nro_pedido="+nro_pedido+"";
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
	
	public ArrayList<Pedido> listar(java.sql.Date fecha_ini, java.sql.Date fecha_fin ) {
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Pedido>lista = new ArrayList<>();
		String sentenciaSQL = "SELECT * FROM pedido WHERE fecha_pedido >='"+fecha_ini+"' AND fecha <= '"+fecha_fin+"'";
		try {
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaSQL);
			if(rs!=null) {
				while(rs.next()) {
					Pedido ped = new Pedido();
					ped.setNro_pedido(rs.getInt("nro_pedido"));
					ped.setFecha_pedido(rs.getDate("fecha_pedido"));
					ped.setFecha_entrega_est(rs.getDate("fecha_entrega_est"));
					ped.setMonto(rs.getDouble("monto"));
					ped.setFecha_cancelacion(rs.getDate("fecha_cancelacion"));
					ped.setFecha_entrega_real(rs.getDate("fecha_entrega_real"));
					ped.setDni_cliente(rs.getString("dni_cliente"));
					lista.add(ped);
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
	
	public ArrayList<Pedido> listar()  {
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Pedido>lista = new ArrayList<>();
		String sentenciaSQL = "SELECT * FROM pedido ";
		try {
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaSQL);
			if(rs!=null) {
				while(rs.next()) {
					Pedido ped = new Pedido();
					ped.setNro_pedido(rs.getInt("nro_pedido"));
					ped.setFecha_pedido(rs.getDate("fecha_pedido"));
					ped.setFecha_entrega_est(rs.getDate("fecha_entrega_est"));
					ped.setMonto(rs.getDouble("monto"));
					ped.setFecha_cancelacion(rs.getDate("fecha_cancelacion"));
					ped.setFecha_entrega_real(rs.getDate("fecha_entrega_real"));
					ped.setDni_cliente(rs.getString("dni_cliente"));
					lista.add(ped);
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
}
