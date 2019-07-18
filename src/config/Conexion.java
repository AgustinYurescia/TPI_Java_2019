package config;

import java.sql.*;

public class Conexion {
	private static Conexion instancia;
	private Connection con=null;
	private int conectados=0;
	private String url="jdbc:mysql://localhost:3306/java?useTimezone=true&serverTimezone=UTC";
	private String usuario="root";
	private String pass="java2019";
	
	private Conexion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Conexion getInstancia() {
		if(instancia==null) {
			instancia = new Conexion();
		}
		return instancia;
	}
	
	public Connection getConexion() {
		try {
			if (con==null || con.isClosed()) {
				con=DriverManager.getConnection(url,usuario,pass);
				conectados = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		conectados ++;
		return con;
	}
	
	public void desconectar() {
		conectados --;
		try {
			if(conectados <= 0) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
	/*
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
	*/

