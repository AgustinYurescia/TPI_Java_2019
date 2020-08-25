package config;

import java.sql.*;

public class Conexion {
	private static Conexion instancia;
	private Connection con=null;
	private int conectados=0;
	private String url="jdbc:mysql://localhost:3306/vinoteca_gatti?useTimezone=true&serverTimezone=UTC";
	private String usuario="Java";
	private String pass="Java2019";
	
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


