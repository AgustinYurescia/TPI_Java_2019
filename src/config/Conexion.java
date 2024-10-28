package config;

import java.sql.*;

public class Conexion {
	private static Conexion instancia;
	private Connection con=null;
	private int conectados=0;
//	private String url="jdbc:mysql://node71920-elviejotonel.jelastic.saveincloud.net:3306/vinoteca_gatti?useTimezone=true&serverTimezone=UTC";
	// private String url="jdbc:mysql://localhost:3306/vinoteca_gatti_2024?useTimezone=true&serverTimezone=America/Argentina/Buenos_Aires";
	private String url="mysql://root:EqkXsWVVRSuLrMQdCIaZsVshjOtxqjbh@mysql.railway.internal:3306/railway";
	// private String usuario="root";
	// private String pass="root";
	private String usuario="root";
	private String pass="EqkXsWVVRSuLrMQdCIaZsVshjOtxqjbh";
	
	
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


