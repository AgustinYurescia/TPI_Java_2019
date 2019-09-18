package modelo;

public class Descuento_Socio {
	private java.sql.Date fecha_desde;
	private Double porcentaje_desc;
	
	public java.sql.Date getFecha_desde() {
		return fecha_desde;
	}
	public void setFecha_desde(java.sql.Date fecha_desde) {
		this.fecha_desde = fecha_desde;
	}
	public Double getPorcentaje_desc() {
		return porcentaje_desc;
	}
	public void setPorcentaje_desc(Double porcentaje_desc) {
		this.porcentaje_desc = porcentaje_desc;
	}

}
