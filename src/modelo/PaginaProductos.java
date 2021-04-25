package modelo;

import java.util.ArrayList;

public class PaginaProductos {
	ArrayList<Producto> Productos;
	int CantidadRegistros;
	
	public ArrayList<Producto> getProductos() {
		return Productos;
	}
	public void setProductos(ArrayList<Producto> productos) {
		Productos = productos;
	}
	public int getCantidadRegistros() {
		return CantidadRegistros;
	}
	public void setCantidadRegistros(int cantidadRegistros) {
		CantidadRegistros = cantidadRegistros;
	}
}
