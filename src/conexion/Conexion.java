package conexion;

import java.sql.*;

public class Conexion {
    private Connection jdbcConnection;
    private String jdbcURL;
    private String jdbcUsuario;
    private String jdbcContrasena;
    
    public Conexion(String jdbcURL, String jdbcUsuario, String jdbcContrasena) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsuario = jdbcUsuario;
		this.jdbcContrasena = jdbcContrasena;
	}

	public void conectar() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(
                                        jdbcURL, jdbcUsuario, jdbcContrasena);
        }
    }
     
    public void desconectar() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

	public Connection getJdbcConnection() {
		return jdbcConnection;
	}
}


