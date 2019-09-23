package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import config.Conexion;
import modelo.Pedido;



public class PedidoDAO {

	public void alta(Pedido ped) { 
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO producto(fecha_pedido,fecha_entrega_est,monto,fecha_cancelacion,fecha_entrega_real,dni_cliente)values(?,?,?,?,?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setDate(1, ped.getFecha_pedido());
			st.setDate(2, ped.getFecha_entrega_est());
			st.setDouble(3, ped.getMonto());
			st.setDate(4, ped.getFecha_cancelacion());
			st.setDate(5, ped.getFecha_entrega_real());
			st.setString(6, ped.getDni_cliente());
			st.executeUpdate();
			keyResultSet=st.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()) {
				ped.setNro_pedido(keyResultSet.getInt(1));
				calcular_monto_pedido(ped.getNro_pedido());
				setear_fecha_entrega_estimada(ped.getNro_pedido());
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
		int cantidad_de_dias;
		Statement st = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		String sentenciaParaPorc="SELECT cantidad_de_dias "
				+ "FROM tardanza_preparacion_pedido "
				+ "WHERE fecha_desde = ("
				+ "SELECT MAX(fecha_desde) "
				+ "FROM tardanza_preparacion_pedido "
				+ "WHERE fecha_desde <= current_date)";
		try {
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaParaPorc);
			if (rs.next()) {
				cantidad_de_dias=rs.getInt("cantidad_de_dias");
				String sentenciaParaUpdate="UPDATE pedido SET precio_venta = ADDDATE(fecha_pedido, interval "+cantidad_de_dias+" DAY) WHERE nro_pedido="+nro_pedido+"";
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
	
	public List<Pedido> listar(java.sql.Date fecha_ini, java.sql.Date fecha_fin ) {
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
					ped.setFecha_entrega_est(rs.getDate("fecha_entraga_est"));
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
