package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.Conexion;
import exceptions.NonExistentOrderException;
import modelo.Cliente;
import modelo.LineaPedido;
import modelo.Pedido;
import modelo.Producto;

public class PedidoDAO {
	
	private static Logger _logger = LogManager.getLogger(PedidoDAO.class);

	public int alta(Pedido ped, ArrayList<LineaPedido> lin) throws Exception
	{ 
		PreparedStatement st = null;
		ResultSet keyResultSet = null;
		String sentenciaSQL="INSERT INTO pedido(fecha_pedido,monto,dni_cliente,estado)values(current_date,?,?,?)";
		try 
		{
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setDouble(1, ped.getMonto());
			st.setString(2, ped.getDni_cliente());
			st.setString(3, "pendiente");
			st.executeUpdate();
			keyResultSet=st.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()) 
			{
				ped.setNro_pedido(keyResultSet.getInt(1));
				setear_fecha_entrega_estimada(ped.getNro_pedido());
				generar_pedido_productos(ped.getNro_pedido(), lin);
			}
		} 
		catch (Exception e) 
		{
			_logger.error(e.getMessage());
			throw e;
		}
		finally 
		{
			try 
			{
				if(keyResultSet!=null) {keyResultSet.close();}
                if(st!=null) {st.close();}
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
		
		return ped.getNro_pedido();
	}

	public void generar_pedido_productos(int nroPedido, ArrayList<LineaPedido> lin )
	{ 
		ProductoDAO prodDAO = new ProductoDAO();
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="INSERT INTO pedido_productos(codigo_producto,nro_pedido,cantidad)values(?,?,?)";
		try 
		{
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ArrayList<LineaPedido> linea = lin; 
			Iterator<LineaPedido>iter = linea.iterator();
			LineaPedido l;
			while(iter.hasNext())
			{
				l=iter.next();
				st.setInt(1, l.getCodigo_producto());
				st.setInt(2, nroPedido);
				st.setInt(3, l.getCantidad());
				st.executeUpdate();
				prodDAO.descontarStock(l.getCodigo_producto(), l.getCantidad());
			}
		}
		catch (Exception e) 
		{
			_logger.error(e.getMessage());
		}
		finally 
		{
			try 
			{
				if(keyResultSet!=null) {keyResultSet.close();}
                if(st!=null) {st.close();}
                Conexion.getInstancia().desconectar();
			} catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
	}
	
	//CONTROLAR QUE ESTÉ BIEN ====> este metodo no deberia usarse con las nuevas reglas de negocio y el descuento para el socio
	public void calcular_monto_pedido(int nro_pedido) 
	{ 
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
		try 
		{
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			st.executeUpdate();
		} catch (Exception e) 
		{
			_logger.error(e.getMessage());
		}
		finally 
		{
			try 
			{
				if(keyResultSet!=null) {keyResultSet.close();}
                if(st!=null) {st.close();}
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
		
	}
	
	//CONTROLAR QUE ESTÉ BIEN
	public void setear_fecha_entrega_estimada(int nro_pedido) 
	{ 
		int cantidad_de_dias;
		Statement st = null;
		PreparedStatement st2 = null;
		ResultSet rs = null;
		String sentenciaParaDias="SELECT cantidad_de_dias "
				+ "FROM tardanza_preparacion_pedido "
				+ "WHERE fecha_desde = ("
				+ "SELECT MAX(fecha_desde) "
				+ "FROM tardanza_preparacion_pedido "
				+ "WHERE fecha_desde <= current_date)";
		try 
		{
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaParaDias);
			if (rs.next()) 
			{
				cantidad_de_dias=rs.getInt(1);
				String sentenciaParaUpdate="UPDATE pedido SET fecha_entrega_est = ADDDATE(fecha_pedido, interval "+cantidad_de_dias+" DAY) WHERE nro_pedido="+nro_pedido+"";
				st2=Conexion.getInstancia().getConexion().prepareStatement(sentenciaParaUpdate);
				st2.executeUpdate(); 
			}
		} 
		catch (Exception e) 
		{
			_logger.error(e.getMessage());
		}
		finally 
		{
			try 
			{
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
	}
	
	public ArrayList<Pedido> listar(String fecha_ini, String fecha_fin, String estado ) 
	{
		Statement st = null;
		ResultSet rs = null;
		String sentenciaSQL = "";
		ArrayList<Pedido>lista = new ArrayList<>();
		if(fecha_ini != "" && fecha_fin != "") 
		{
			sentenciaSQL = "SELECT * FROM pedido as ped INNER JOIN cliente as cli WHERE ped.dni_cliente = cli.dni AND fecha_pedido >= '"+fecha_ini+"' AND fecha_pedido <= '"+fecha_fin+"'";
		}
		else if(fecha_ini != "" && fecha_fin == "") 
		{
			sentenciaSQL = "SELECT * FROM pedido as ped INNER JOIN cliente as cli WHERE ped.dni_cliente = cli.dni AND fecha_pedido >= '"+fecha_ini+"'";
		}
		else if(fecha_ini == "" && fecha_fin != "") 
		{
			sentenciaSQL = "SELECT * FROM pedido as ped INNER JOIN cliente as cli WHERE ped.dni_cliente = cli.dni AND fecha_pedido <= '"+fecha_fin+"'";
		}
		if (estado.equalsIgnoreCase("pendiente")) 
		{
			sentenciaSQL = sentenciaSQL + " AND fecha_entrega_real is null AND fecha_cancelacion is null AND estado='pendiente' order by fecha_pedido desc";
		}
		else if (estado.equalsIgnoreCase("entregado")) 
		{
			sentenciaSQL = sentenciaSQL + " AND fecha_cancelacion is null AND fecha_entrega_real is not null AND estado='finalizado' order by fecha_pedido desc";
		}
		else if (estado.equalsIgnoreCase("cancelado")) 
		{
			sentenciaSQL = sentenciaSQL + " AND fecha_cancelacion is not null AND estado='cancelado' order by fecha_pedido desc";
		}
		else if (estado.equalsIgnoreCase("preparado")) 
		{
			sentenciaSQL = sentenciaSQL + "AND estado='preparado' order by fecha_pedido desc";
		}
		try 
		{
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaSQL);
			if(rs!=null) 
			{
				while(rs.next()) 
				{
					Pedido ped = new Pedido();
					Cliente cli = new Cliente();
					ArrayList<LineaPedido> lineasPedido;
					ped.setNro_pedido(rs.getInt("nro_pedido"));
					lineasPedido = this.buscar_productos_pedido(ped.getNro_pedido());
					ped.setProductos(lineasPedido);
					ped.setFecha_pedido(rs.getDate("fecha_pedido"));
					ped.setFecha_entrega_est(rs.getDate("fecha_entrega_est"));
					ped.setMonto(rs.getDouble("monto"));
					ped.setFecha_cancelacion(rs.getDate("fecha_cancelacion"));
					ped.setFecha_entrega_real(rs.getDate("fecha_entrega_real"));
					ped.setDni_cliente(rs.getString("dni_cliente"));
					ped.setEstado(rs.getString("estado"));
					cli.setApellido(rs.getString("apellido"));
					cli.setNombre(rs.getString("nombre"));
					cli.setTelefono(rs.getNString("telefono"));
					cli.setDireccion(rs.getString("direccion"));
					cli.setMail(rs.getString("mail"));
					ped.setCliente(cli);
					lista.add(ped);
				}
			}
		} 
		catch (Exception e) 
		{
			_logger.error(e.getMessage());
		} 
		finally 
		{
			try 
			{
				if(rs!=null) {rs.close();}
				if(st!=null) {st.close();}
				Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
		return lista;
	}
	
	public ArrayList<Pedido> listar(String estado) throws Exception  
	{
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Pedido>lista = new ArrayList<>();
		String sentenciaSQL = "SELECT * FROM pedido";
		if (estado.equalsIgnoreCase("pendiente")) 
		{
			sentenciaSQL = sentenciaSQL + " as ped INNER JOIN cliente as cli WHERE ped.dni_cliente = cli.dni AND fecha_entrega_real is null AND fecha_cancelacion is null  AND estado='pendiente' order by fecha_pedido desc";
		}
		else if (estado.equalsIgnoreCase("preparado")) 
		{
			sentenciaSQL = "SELECT * FROM pedido as ped INNER JOIN cliente as cli WHERE ped.dni_cliente = cli.dni AND estado='preparado' order by fecha_pedido desc";
		}
		else if (estado.equalsIgnoreCase("entregado")) 
		{
			sentenciaSQL = sentenciaSQL + " as ped INNER JOIN cliente as cli WHERE ped.dni_cliente = cli.dni AND fecha_cancelacion is null AND fecha_entrega_real is not null AND estado='finalizado' order by fecha_pedido desc";
		}
		else if (estado.equalsIgnoreCase("cancelado")) 
		{
			sentenciaSQL = sentenciaSQL + " as ped INNER JOIN cliente as cli WHERE ped.dni_cliente = cli.dni AND fecha_cancelacion is not null AND estado = 'cancelado' order by fecha_pedido desc";
		}
		try 
		{
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaSQL);
			if(rs!=null) 
			{
				while(rs.next()) 
				{
					Pedido ped = new Pedido();
					Cliente cli = new Cliente();
					ArrayList<LineaPedido> lineasPedido;
					ped.setNro_pedido(rs.getInt("nro_pedido"));
					lineasPedido = this.buscar_productos_pedido(ped.getNro_pedido());
					ped.setProductos(lineasPedido);
					ped.setFecha_pedido(rs.getDate("fecha_pedido"));
					ped.setFecha_entrega_est(rs.getDate("fecha_entrega_est"));
					ped.setMonto(rs.getDouble("monto"));
					ped.setFecha_cancelacion(rs.getDate("fecha_cancelacion"));
					ped.setFecha_entrega_real(rs.getDate("fecha_entrega_real"));
					ped.setDni_cliente(rs.getString("dni_cliente"));
					ped.setEstado(rs.getString("estado"));
					cli.setApellido(rs.getString("apellido"));
					cli.setNombre(rs.getString("nombre"));
					cli.setTelefono(rs.getNString("telefono"));
					cli.setDireccion(rs.getString("direccion"));
					cli.setMail(rs.getString("mail"));
					ped.setCliente(cli);
					lista.add(ped);
				}
			}
		} 
		catch (Exception e) 
		{
			_logger.error(e.getMessage());
			throw e;
		} 
		finally 
		{
			try 
			{
				if(rs!=null) {rs.close();}
				if(st!=null) {st.close();}
				Conexion.getInstancia().desconectar();
			} 
			catch (Exception e)
			{
				_logger.error(e.getMessage());
			}
		}
		return lista;
	}
	
	public ArrayList<Pedido> listarFinalizados(String fecha_ini, String fecha_fin) 
	{
		Statement st = null;
		ResultSet rs = null;
		String sentenciaSQL = "";
		ArrayList<Pedido>lista = new ArrayList<>();
		if(fecha_ini != "" && fecha_fin != "") 
		{
			sentenciaSQL = "SELECT * FROM pedido as ped INNER JOIN cliente as cli WHERE ped.dni_cliente = cli.dni AND fecha_entrega_real >= '"+fecha_ini+"' AND fecha_entrega_real <= '"+fecha_fin+"'";
		}
		else if(fecha_ini != "" && fecha_fin == "") 
		{
			sentenciaSQL = "SELECT * FROM pedido as ped INNER JOIN cliente as cli WHERE ped.dni_cliente = cli.dni AND fecha_entrega_real >= '"+fecha_ini+"'";
		}
		else if(fecha_ini == "" && fecha_fin != "") 
		{
			sentenciaSQL = "SELECT * FROM pedido as ped INNER JOIN cliente as cli WHERE ped.dni_cliente = cli.dni AND fecha_entrega_real <= '"+fecha_fin+"'";
		}
		try 
		{
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaSQL);
			if(rs!=null) 
			{
				while(rs.next()) 
				{
					Pedido ped = new Pedido();
					Cliente cli = new Cliente();
					ArrayList<LineaPedido> lineasPedido;
					ped.setNro_pedido(rs.getInt("nro_pedido"));
					lineasPedido = this.buscar_productos_pedido(ped.getNro_pedido());
					ped.setProductos(lineasPedido);
					ped.setFecha_pedido(rs.getDate("fecha_pedido"));
					ped.setFecha_entrega_est(rs.getDate("fecha_entrega_est"));
					ped.setMonto(rs.getDouble("monto"));
					ped.setFecha_cancelacion(rs.getDate("fecha_cancelacion"));
					ped.setFecha_entrega_real(rs.getDate("fecha_entrega_real"));
					ped.setDni_cliente(rs.getString("dni_cliente"));
					ped.setEstado(rs.getString("estado"));
					cli.setApellido(rs.getString("apellido"));
					cli.setNombre(rs.getString("nombre"));
					cli.setTelefono(rs.getNString("telefono"));
					cli.setDireccion(rs.getString("direccion"));
					cli.setMail(rs.getString("mail"));
					ped.setCliente(cli);
					lista.add(ped);
				}
			}
		} 
		catch (Exception e) 
		{
			_logger.error(e.getMessage());
		} 
		finally 
		{
			try 
			{
				if(rs!=null) {rs.close();}
				if(st!=null) {st.close();}
				Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
		return lista;
	}
	
	public ArrayList<Pedido> listarFinalizados() throws Exception  
	{
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Pedido>lista = new ArrayList<>();
		String sentenciaSQL = "SELECT * FROM pedido as ped INNER JOIN cliente as cli WHERE ped.dni_cliente = cli.dni AND fecha_entrega_real is not null";
		try 
		{
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaSQL);
			if(rs!=null) 
			{
				while(rs.next()) 
				{
					Pedido ped = new Pedido();
					Cliente cli = new Cliente();
					ArrayList<LineaPedido> lineasPedido;
					ped.setNro_pedido(rs.getInt("nro_pedido"));
					lineasPedido = this.buscar_productos_pedido(ped.getNro_pedido());
					ped.setProductos(lineasPedido);
					ped.setFecha_pedido(rs.getDate("fecha_pedido"));
					ped.setFecha_entrega_est(rs.getDate("fecha_entrega_est"));
					ped.setMonto(rs.getDouble("monto"));
					ped.setFecha_cancelacion(rs.getDate("fecha_cancelacion"));
					ped.setFecha_entrega_real(rs.getDate("fecha_entrega_real"));
					ped.setDni_cliente(rs.getString("dni_cliente"));
					ped.setEstado(rs.getString("estado"));
					cli.setApellido(rs.getString("apellido"));
					cli.setNombre(rs.getString("nombre"));
					cli.setTelefono(rs.getNString("telefono"));
					cli.setDireccion(rs.getString("direccion"));
					cli.setMail(rs.getString("mail"));
					ped.setCliente(cli);
					lista.add(ped);
				}
			}
		} 
		catch (Exception e) 
		{
			_logger.error(e.getMessage());
			throw e;
		} 
		finally 
		{
			try 
			{
				if(rs!=null) {rs.close();}
				if(st!=null) {st.close();}
				Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
		return lista;
	}
	
	public Pedido buscar_pedido(int nro_pedido) throws Exception 
	{
		Pedido ped = null;
		Cliente cli = new Cliente();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sentenciaSQL="SELECT * FROM pedido as ped INNER JOIN cliente as cli WHERE ped.dni_cliente = cli.dni AND nro_pedido = ?";
		try 
		{
			ps=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setInt(1, nro_pedido);
			rs=ps.executeQuery();
			if (rs.next()) 
			{
				ped = new Pedido();
				ped.setNro_pedido(rs.getInt("nro_pedido"));
				ped.setFecha_pedido(rs.getDate("fecha_pedido"));
				ped.setFecha_entrega_est(rs.getDate("fecha_entrega_est"));
				ped.setMonto(rs.getDouble("monto"));
				ped.setFecha_cancelacion(rs.getDate("fecha_cancelacion"));
				ped.setFecha_entrega_real(rs.getDate("fecha_entrega_real"));
				ped.setDni_cliente(rs.getString("dni_cliente"));
				ped.setEstado(rs.getString("estado"));
				cli.setApellido(rs.getString("apellido"));
				cli.setNombre(rs.getString("nombre"));
				cli.setTelefono(rs.getNString("telefono"));
				cli.setDireccion(rs.getString("direccion"));
				cli.setMail(rs.getString("mail"));
				ped.setCliente(cli);
				
			}
		} 
		catch (Exception e)
		{
			_logger.error(e.getMessage());
			throw e;
		}
		finally 
		{
			try 
			{
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
		if (ped == null)
		{
			throw new NonExistentOrderException("El pedido solicitado no existe");	
		}
		return ped;
	}
	
	public ArrayList<LineaPedido> buscar_productos_pedido(int nro_pedido) 
	{
		Statement st = null;
		ResultSet rs = null;
		ArrayList<LineaPedido> pedido_productos = new ArrayList<LineaPedido>();
		String sentenciaSQL="SELECT * FROM pedido_productos as pp INNER JOIN producto as pr WHERE pp.codigo_producto = pr.codigo AND nro_pedido = "+nro_pedido+"";
		try 
		{
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaSQL);
			if (rs != null) 
			{
				while(rs.next()) 
				{
					Producto producto = new Producto();
					producto.setCodigo(rs.getInt("codigo"));
					producto.setNombre(rs.getString("nombre"));
					producto.setPrecioVenta(rs.getDouble("precio_venta"));
					LineaPedido linea = new LineaPedido(rs.getInt(1),rs.getInt(3),0);
					linea.setProducto(producto);
					pedido_productos.add(linea);
				}
			}
		} 
		catch (Exception e) 
		{
			_logger.error(e.getMessage());
		}
		finally 
		{
			try 
			{
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
		return pedido_productos;
	}
	
	//TODO throw exception on record not updated
	public void cancelar_pedido(int nro_pedido) 
	{
		PreparedStatement st = null;
		String sentenciaSQL="UPDATE pedido SET fecha_cancelacion = current_date, estado = 'cancelado' WHERE nro_pedido="+nro_pedido+"";
		try 
		{
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			st.executeUpdate();
		} 
		catch (Exception e) 
		{
			_logger.error(e.getMessage());
		}
		finally 
		{
			try 
			{
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
	}
	
	public ArrayList<Pedido> listar_pedidos_cliente(String dni_cliente, String estado) throws Exception  
	{
		Statement st = null;
		ResultSet rs = null;
		ArrayList<Pedido>lista = new ArrayList<>();
		String sentenciaSQL = "";
		if (estado.equalsIgnoreCase("pendiente")) 
		{
			sentenciaSQL = "SELECT * FROM pedido WHERE dni_cliente="+dni_cliente+" AND estado='pendiente' order by fecha_pedido desc";
		}
		else if (estado.equalsIgnoreCase("listo para retirar")) 
		{
			sentenciaSQL = "SELECT * FROM pedido WHERE dni_cliente="+dni_cliente+" AND estado='preparado' order by fecha_pedido desc";
		}
		else if (estado.equalsIgnoreCase("entregado")) 
		{
			sentenciaSQL = "SELECT * FROM pedido WHERE dni_cliente="+dni_cliente+" AND estado='finalizado' order by fecha_pedido desc";
		}
		else if (estado.equalsIgnoreCase("cancelado")) 
		{
			sentenciaSQL = "SELECT * FROM pedido WHERE dni_cliente="+dni_cliente+" AND estado='cancelado' order by fecha_pedido desc";
		}
		else if (estado.equalsIgnoreCase("-")) 
		{
			sentenciaSQL = "SELECT * FROM pedido WHERE dni_cliente="+dni_cliente+" order by fecha_pedido desc";
		}
		try 
		{
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaSQL);
			if(rs!=null) 
			{
				while(rs.next()) 
				{
					Pedido ped = new Pedido();
					ped.setNro_pedido(rs.getInt("nro_pedido"));
					ped.setFecha_pedido(rs.getDate("fecha_pedido"));
					ped.setFecha_entrega_est(rs.getDate("fecha_entrega_est"));
					ped.setMonto(rs.getDouble("monto"));
					ped.setFecha_cancelacion(rs.getDate("fecha_cancelacion"));
					ped.setFecha_entrega_real(rs.getDate("fecha_entrega_real"));
					ped.setDni_cliente(rs.getString("dni_cliente"));
					ped.setEstado(rs.getString("estado"));
					lista.add(ped);
				}
			}
		} 
		catch (Exception e) 
		{
			_logger.error(e.getMessage());
			throw e;
		}
		finally 
		{
			try 
			{
				if(rs!=null) {rs.close();}
				if(st!=null) {st.close();}
				Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
		return lista;
	}
	
	
	public void RegistrarEntrega(int numeroPedido) throws Exception 
	{
		PreparedStatement ps= null;
		String sentenciaSQL="UPDATE pedido SET fecha_entrega_real = current_date, estado = 'finalizado' WHERE nro_pedido = ?";
		try 
		{
			ps=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setInt(1, numeroPedido);
			ps.executeUpdate();
		} 
		catch (Exception e) 
		{
			_logger.error(e.getMessage());
			throw e;
		}
		finally 
		{
			try 
			{
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
	}
	
	public Map<Integer,Float> obtenerTotalVentasPorMes(Integer anio) throws Exception 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String consulta = "SELECT month(fecha_pedido), sum(monto) FROM pedido\r\n" + 
						  "WHERE fecha_cancelacion is null AND year(fecha_pedido)=?\r\n" + 
						  "GROUP BY month(fecha_pedido)\r\n" + 
						  "ORDER BY month(fecha_pedido) asc";
		Map<Integer, Float> result = new HashMap<Integer,Float>();
		try 
		{
			ps=Conexion.getInstancia().getConexion().prepareStatement(consulta);
			ps.setInt(1, anio);
		    rs = ps.executeQuery();
		    if(rs != null)
		    {
			    while(rs.next()) 
			    {
			    	result.put(rs.getInt(1), rs.getFloat(2));
			    }
		    }
		}
		catch(SQLException e) 
		{
			_logger.error(e.getMessage());
		}
		finally 
		{
			try 
			{
				if(rs!=null) {rs.close();}
				if(ps!=null) {ps.close();}
				Conexion.getInstancia().desconectar();
			}
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
		return result;
	}
	public ArrayList<Pedido> PedidosAEntregarManana() throws Exception 
	{
		PreparedStatement ps= null;
		ResultSet rs = null;
		ArrayList<Pedido>pedidos = new ArrayList<>();
		ArrayList<LineaPedido>lineasPedido = null;
		String sentenciaSQL="SELECT * FROM pedido as ped INNER JOIN cliente as cli WHERE ped.dni_cliente = cli.dni AND fecha_entrega_est = date_add(current_date, interval 1 day) AND estado != 'cancelado';";
		try 
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			rs = ps.executeQuery(sentenciaSQL);
			if(rs!=null) 
			{
				while(rs.next()) 
				{
					Pedido ped = new Pedido();
					Cliente cli = new Cliente();
					ped.setNro_pedido(rs.getInt("nro_pedido"));
					lineasPedido = this.buscar_productos_pedido(ped.getNro_pedido());
					ped.setProductos(lineasPedido);
					ped.setFecha_pedido(rs.getDate("fecha_pedido"));
					ped.setFecha_entrega_est(rs.getDate("fecha_entrega_est"));
					ped.setMonto(rs.getDouble("monto"));
					ped.setFecha_cancelacion(rs.getDate("fecha_cancelacion"));
					ped.setFecha_entrega_real(rs.getDate("fecha_entrega_real"));
					ped.setDni_cliente(rs.getString("dni_cliente"));
					ped.setEstado(rs.getString("estado"));
					cli.setApellido(rs.getString("apellido"));
					cli.setNombre(rs.getString("nombre"));
					cli.setTelefono(rs.getNString("telefono"));
					cli.setDireccion(rs.getString("direccion"));
					cli.setMail(rs.getString("mail"));
					ped.setCliente(cli);
					pedidos.add(ped);
				}
			}
		} 
		catch (Exception e) 
		{
			_logger.error(e.getMessage());
			throw e;
		}
		finally 
		{
			try 
			{
				if(rs!=null) {rs.close();}
				if(ps!=null) {ps.close();}
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
		if (pedidos.size() == 0)
		{
			throw new NonExistentOrderException("No existen pedidos con fecha de entrega el día de mañana");
		}
		return pedidos;
	}
	public void setEstadoPreparado(ArrayList<Pedido> pedidos) throws Exception 
	{
		PreparedStatement ps= null;
		String sentenciaSQL="UPDATE pedido SET estado = 'preparado' WHERE nro_pedido = ?";
		try 
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			for (Pedido p : pedidos)
			{
				ps.setInt(1, p.getNro_pedido());
				ps.executeUpdate();
			}
		} 
		catch (Exception e) 
		{
			_logger.error(e.getMessage());
			throw e;
		}
		finally 
		{
			try 
			{
				if(ps!=null) {ps.close();}
				Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
	}
	
	public Map<Integer,Float> obtenerTotalVentasPorAnio() throws Exception 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String consulta = "SELECT year(fecha_pedido), sum(monto) FROM pedido\r\n" + 
						  "WHERE fecha_cancelacion is null\r\n" + 
						  "GROUP BY year(fecha_pedido)";
		Map<Integer, Float> result = new HashMap<Integer,Float>();
		try 
		{
			ps=Conexion.getInstancia().getConexion().prepareStatement(consulta);
		    rs = ps.executeQuery();
		    if(rs != null)
		    {
			    while(rs.next()) 
			    {
			    	result.put(rs.getInt(1), rs.getFloat(2));
			    }
		    }
		}
		catch(SQLException e) 
		{
			_logger.error(e.getMessage());
		}
		finally 
		{
			try 
			{
				if(rs!=null) {rs.close();}
				if(ps!=null) {ps.close();}
				Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
		return result;
	}
	public void setEstadoPreparado(String nro_pedido) throws Exception 
	{
		PreparedStatement ps= null;
		String sentenciaSQL="UPDATE pedido SET estado = 'preparado' WHERE nro_pedido = ?";
		try 
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			ps.setInt(1, Integer.parseInt(nro_pedido));
			ps.executeUpdate();
		} 
		catch (Exception e) 
		{
			_logger.error(e.getMessage());
			throw e;
		}
		finally 
		{
			try 
			{
				if(ps!=null) {ps.close();}
				Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
	}
	public ArrayList<Pedido> VentasDelDia() throws Exception 
	{
		PreparedStatement ps= null;
		ResultSet rs = null;
		ArrayList<Pedido>pedidos = new ArrayList<>();
		ArrayList<LineaPedido>lineasPedido = null;
		String sentenciaSQL="SELECT * FROM pedido as ped INNER JOIN cliente as cli WHERE ped.dni_cliente = cli.dni AND fecha_entrega_real = current_date;";
		try 
		{
			ps = Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL);
			rs = ps.executeQuery(sentenciaSQL);
			if(rs!=null) 
			{
				while(rs.next()) 
				{
					Pedido ped = new Pedido();
					Cliente cli = new Cliente();
					ped.setNro_pedido(rs.getInt("nro_pedido"));
					lineasPedido = this.buscar_productos_pedido(ped.getNro_pedido());
					ped.setProductos(lineasPedido);
					ped.setFecha_pedido(rs.getDate("fecha_pedido"));
					ped.setFecha_entrega_est(rs.getDate("fecha_entrega_est"));
					ped.setMonto(rs.getDouble("monto"));
					ped.setFecha_cancelacion(rs.getDate("fecha_cancelacion"));
					ped.setFecha_entrega_real(rs.getDate("fecha_entrega_real"));
					ped.setDni_cliente(rs.getString("dni_cliente"));
					ped.setEstado(rs.getString("estado"));
					cli.setApellido(rs.getString("apellido"));
					cli.setNombre(rs.getString("nombre"));
					cli.setTelefono(rs.getNString("telefono"));
					cli.setDireccion(rs.getString("direccion"));
					cli.setMail(rs.getString("mail"));
					ped.setCliente(cli);
					pedidos.add(ped);
				}
			}
		} 
		catch (Exception e) 
		{
			_logger.error(e.getMessage());
			throw e;
		}
		finally 
		{
			try 
			{
				if(rs!=null) {rs.close();}
				if(ps!=null) {ps.close();}
                Conexion.getInstancia().desconectar();
			} 
			catch (Exception e) 
			{
				_logger.error(e.getMessage());
			}
		}
		if (pedidos.size() == 0)
		{
			throw new NonExistentOrderException("Hoy no hubo ventas");
		}
		return pedidos;
	}
}
