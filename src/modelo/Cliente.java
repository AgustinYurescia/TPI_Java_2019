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
	
}
