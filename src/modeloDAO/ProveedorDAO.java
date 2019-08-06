package modeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import config.Conexion;
import modelo.Proveedor;

public class ProveedorDAO {
	
	public List<Proveedor> listar() {
		Statement st = null;
		ResultSet rs=null;
		ArrayList<Proveedor>listaProveedores = new ArrayList<>();
		String sentenciaSQL="select * from proveedores";
		try {
			st=Conexion.getInstancia().getConexion().createStatement();
			rs=st.executeQuery(sentenciaSQL);
			if(rs!=null) {
				while(rs.next()) {
					Proveedor prov = new Proveedor();
					prov.setCuit(rs.getString("cuit"));
					prov.setRazonSocial(rs.getString("razonSocial"));
					prov.setDireccion(rs.getString("direccion"));
					prov.setTelefono(rs.getString("telefono"));
					prov.setMail(rs.getString("mail"));
					listaProveedores.add(prov);
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
		return listaProveedores;
	}
	
	public void alta(Proveedor prov) {
		PreparedStatement st = null;
		ResultSet keyResultSet=null;
		String sentenciaSQL="insert into proveedores(cuit,razonSocial,direccion,telefono,mail)values(?,?,?,?,?)";
		try {
			st=Conexion.getInstancia().getConexion().prepareStatement(sentenciaSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			st.setString(1,prov.getCuit());
			st.setString(2, prov.getRazonSocial());
			st.setString(3, prov.getDireccion());
			st.setString(4, prov.getTelefono());
			st.setString(5, prov.getMail());
			st.executeUpdate();
			keyResultSet=st.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()) {
				prov.setCuit(keyResultSet.getString(1));
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

}
