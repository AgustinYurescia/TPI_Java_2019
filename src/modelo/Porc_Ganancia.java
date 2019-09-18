package modelo;

public class Porc_Ganancia {
	private java.sql.Date fecha_desde;
	private Double porcentaje;
	
	public java.sql.Date getFecha_desde() {
		return fecha_desde;
	}
	public void setFecha_desde(java.sql.Date fecha_desde) {
		this.fecha_desde = fecha_desde;
	}
	public Double getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}
}
