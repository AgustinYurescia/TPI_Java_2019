package modelo;

public class Admin {
	private String admin_usuario;
	private String admin_contrasena;
	
	public String getAdmin_usuario() {
		return admin_usuario;
	}
	public void setAdmin_usuario(String admin_usuario) {
		this.admin_usuario = admin_usuario;
	}
	public String getAdmin_contrasena() {
		return admin_contrasena;
	}
	public void setAdmin_contrasena(String admin_contrasena) {
		this.admin_contrasena = admin_contrasena;
	}
	
	public static boolean isValid(String contrasena) {
		boolean valid = true;
		
		if ((contrasena == "") || (contrasena.length() > 45) || (contrasena.length() < 4)) {
			valid=false;
		}
		
		return valid;
	}
}
