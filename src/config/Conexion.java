package config;

import java.sql.*;

public class Conexion {
	Connection con;
	public Conexion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/java?useTimezone=true&serverTimezone=UTC","root", "java2019");
		} catch (Exception e){
			System.err.println("Error"+e);
		}
	}
	public Connection getConexion() {
		return con;
	}
	public void desconectar() throws SQLException {
		if (con != null && !con.isClosed()) {
            con.close();
        }
	}
}
