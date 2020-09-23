package modelo;

public class TardanzaPedido {
	private java.sql.Date fechaDesde;
	private int cantidadDias;
	
	public TardanzaPedido(java.sql.Date fechaDesde, int cantidadDias)
	{
		this.fechaDesde = fechaDesde;
		this.cantidadDias = cantidadDias;
	}

	public java.sql.Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(java.sql.Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public int getCantidadDias() {
		return cantidadDias;
	}

	public void setCantidadDias(int cantidadDias) {
		this.cantidadDias = cantidadDias;
	}
	
}
