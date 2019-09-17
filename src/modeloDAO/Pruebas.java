package modeloDAO;
import modeloDAO.ClienteDAO;

public class Pruebas {

	public static void main(String[] args) {
		ClienteDAO cli = new ClienteDAO();
		Boolean rta;
		
		rta=cli.existe("agus_yur","39291780");
		System.out.println(rta);

	}

}
