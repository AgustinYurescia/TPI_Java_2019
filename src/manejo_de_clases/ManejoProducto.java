package manejo_de_clases;

import java.sql.*;
import conexion.Conexion;
import entidades.Producto;

public class ManejoProducto {
	private Conexion con;
	private Connection connection;
	
	public ManejoProducto(String jdbcURL, String jdbcUsuario, String jdbcContrasena)  throws SQLException {
		con = new Conexion(jdbcURL,jdbcUsuario,jdbcContrasena);
	}
	
	public boolean insertar(Producto producto) throws SQLException {
		String sentenciaSQL="INSERT INTO productos (idProducto, nombre, categoria, descripcion, precioCosto, precioVenta,stock) VALUES (?, ?, ?,?,?,?,?)";
		con.conectar();
		connection=con.getJdbcConnection();
		PreparedStatement st = connection.prepareStatement(sentenciaSQL);
		st.setString(1,null);
		st.setString(2, producto.getNombre());
		st.setString(3, producto.getCategoria());
		st.setString(4, producto.getDescripcion());
		st.setDouble(5, producto.getPrecioCosto());
		st.setDouble(6, producto.getPrecioVenta());
		st.setInt(7, producto.getStock());
		
		boolean rowInserted = st.executeUpdate() > 0;
		st.close();
		con.desconectar();
		return rowInserted;
	}
}
