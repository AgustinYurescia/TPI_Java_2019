package modeloDAO;

import java.util.List;
import java.sql.*;

import config.Conexion;
import interfaz.CRUD;
import modelo.Producto;

public class ProductoDAO implements CRUD{
	Conexion cn=new Conexion();
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;
	Producto prod=new Producto();
	Boolean agregado;
	
	@Override
	public List<?> listar() {
		return null;
	}

	@Override
	public Boolean alta(Producto prod) {
		String sentenciaSQL="insert into productos(nombre,categoria,descripcion,precioCosto,precioVenta,stock)values(?,?,?,?,?,?)";
		try {
			conn=cn.getConexion();
			ps=conn.prepareStatement(sentenciaSQL);
			ps.setString(1,prod.getNombre());
			ps.setString(2, prod.getCategoria());
			ps.setString(3, prod.getDescripcion());
			ps.setDouble(4, prod.getPrecioCosto());
			ps.setDouble(5, prod.getPrecioVenta());
			ps.setInt(6, prod.getStock());
			ps.executeUpdate();
			agregado=true;
			ps.close();
			cn.desconectar();
		} catch (Exception e) {
			agregado=false;
		}
		return agregado;
	}

	@Override
	public Boolean baja(int id) {
		return null;
	}

	@Override
	public Boolean modificar(Producto prod) {
		return null;
	}
	
}
