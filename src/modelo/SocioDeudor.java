package modelo;

import java.util.ArrayList;

public class SocioDeudor extends Cliente{
	
	private int CantidadCuotasAdeudadas;
	private ArrayList<Cuota> cuotas;

	public int getCantidadCuotasAdeudadas() {
		return CantidadCuotasAdeudadas;
	}

	public void setCantidadCuotasAdeudadas(int cantidadCuotasAdeudadas) {
		CantidadCuotasAdeudadas = cantidadCuotasAdeudadas;
	}
	public ArrayList<Cuota> getCuotas()
	{
		return cuotas;
	}
	public void setCuotas(ArrayList<Cuota>cuotas)
	{
		this.cuotas = cuotas;
	}
	
}
