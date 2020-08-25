package modelo;

public class Cliente {
	private String dni;
	private String cliente_usuario;
	private String cliente_contrasena;
	private String nombre;
	private String apellido;
	private String mail;
	private String telefono;
	private String direccion;
	private java.sql.Date fecha_baja_socio;
	private java.sql.Date fecha_baja;
	
	public Cliente(String dni, String cliente_usuario, String cliente_contrasena, String nombre, String apellido,
			String mail, String telefono, String direccion){
		this.dni = dni;
		this.cliente_usuario = cliente_usuario;
		this.cliente_contrasena = cliente_contrasena;
		this.nombre = nombre;
		this.apellido = apellido;
		this.mail = mail;
		this.telefono = telefono;
		this.direccion = direccion;
	}
	
	public static boolean isValid(String dni ,String nombre ,String apellido ,String telefono ,String direccion ,String mail ,String contrasena) {
		boolean valid = true;
		
		if (dni.length() != 8) {
			valid=false;
			}else {
				for(int i = 0; i < dni.length(); i++) {
					try {
					Integer.parseInt(dni.substring(i,i+1));
					}catch(Exception e){
						valid = false;
						break;
					}
				}
		}
		
		if ((nombre == "") || (nombre.length() > 25)) {
			valid=false;
		}
		
		if ((apellido == "") || (apellido.length() > 25)) {
			valid=false;
		}
		
		if ((telefono.length() < 5) || (nombre.length() > 30)) {
			valid=false;
			}else {
				for(int i = 0; i < telefono.length(); i++) {
					try {
					Integer.parseInt(telefono.substring(i,i+1));
					}catch(Exception e){
						valid = false;
						break;
					}
				}
		}
		
		if ((direccion == "") || (direccion.length() > 25)) {
			valid=false;
		}
		
		if ((mail == "") || (mail.length() > 25)) {
			valid=false;
		}
		
		if ((contrasena == "") || (contrasena.length() > 45)) {
			valid=false;
		}
		
		return valid;
	}

	public static boolean isValid(String nombre ,String apellido ,String telefono ,String direccion ,String mail ) {
		boolean valid = true;
			
		if ((nombre == "") || (nombre.length() > 25)) {
			valid=false;
		}
		
		if ((apellido == "") || (apellido.length() > 25)) {
			valid=false;
		}
		
		if ((telefono.length() < 5) || (nombre.length() > 30)) {
			valid=false;
			}else {
				for(int i = 0; i < telefono.length(); i++) {
					try {
					Integer.parseInt(telefono.substring(i,i+1));
					}catch(Exception e){
						valid = false;
						break;
					}
				}
		}
		
		if ((direccion == "") || (direccion.length() > 25)) {
			valid=false;
		}
		
		if ((mail == "") || (mail.length() > 25)) {
			valid=false;
		}
		
		return valid;
	}
	
	public static boolean isValid(String contrasena) {
		boolean valid = true;
		
		if ((contrasena == "") || (contrasena.length() > 45) || (contrasena.length() < 4)) {
			valid=false;
		}
		
		return valid;
	}

	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getCliente_usuario() {
		return cliente_usuario;
	}
	public void setCliente_usuario(String cliente_usuario) {
		this.cliente_usuario = cliente_usuario;
	}
	public String getCliente_contrasena() {
		return cliente_contrasena;
	}
	public void setCliente_contrasena(String cliente_contrasena) {
		this.cliente_contrasena = cliente_contrasena;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public java.sql.Date getFecha_baja_socio() {
		return fecha_baja_socio;
	}
	public void setFecha_baja_socio(java.sql.Date fecha_baja_socio) {
		this.fecha_baja_socio = fecha_baja_socio;
	}
	public java.sql.Date getFecha_baja() {
		return fecha_baja;
	}
	public void setFecha_baja(java.sql.Date fecha_baja) {
		this.fecha_baja = fecha_baja;
	}
}
