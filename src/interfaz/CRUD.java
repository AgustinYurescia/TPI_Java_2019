package interfaz;

import java.util.List;

import modelo.Producto;

public interface CRUD {
	public List<?> listar();
	public Boolean alta(Producto prod);
	public Boolean baja(int id);
	public Boolean modificar(Producto prod);

}
