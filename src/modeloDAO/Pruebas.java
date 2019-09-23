package modeloDAO;
import modeloDAO.ProductoDAO;

public class Pruebas {

	public static void main(String[] args) {
		ProductoDAO cli = new ProductoDAO();
		cli.calcular_precio_venta_nuevo_producto(7,120.0);
	}

}
