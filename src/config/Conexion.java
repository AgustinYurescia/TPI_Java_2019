package config;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Conexion {
    private static Conexion instancia;
    private Connection con = null;
    private int conectados = 0;

    private String url;
    private String usuario;
    private String pass;

    private Conexion() {
        try {
            // Cargar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            String configFilePath = System.getProperty("catalina.base") + "/conf/config.properties";

            
            // Leer el archivo de configuración
            FileInputStream inputStream = new FileInputStream(configFilePath);
            loadConfig(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadConfig(FileInputStream inputStream) throws Exception {
        // Cargar las propiedades desde el archivo de configuración
        Properties config = new Properties();
        config.load(inputStream);

        // Obtener los valores de configuración
        url = config.getProperty("db.url");
        usuario = config.getProperty("db.user");
        pass = config.getProperty("db.password");

        if (url == null || usuario == null || pass == null) {
            throw new RuntimeException("Faltan parámetros en el archivo config.properties");
        }
    }

    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConexion() {
        try {
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(url, usuario, pass);
                conectados = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        conectados++;
        return con;
    }

    public void desconectar() {
        conectados--;
        try {
            if (conectados <= 0) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
