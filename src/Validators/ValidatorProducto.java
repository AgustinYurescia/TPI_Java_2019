package Validators;

public class ValidatorProducto {
	public static Boolean ValidarListarPorPaginas(String numeroPorPagina,String numeroPagina,String codigoCategoria) {
		try {
			int parsedNumeroPorPagina = Integer.parseInt(numeroPorPagina);
			int parsedNumeroPagina = Integer.parseInt(numeroPorPagina);
			int parsedCodigoCategoria = Integer.parseInt(numeroPorPagina);
			if(parsedNumeroPorPagina < 5 || parsedNumeroPagina <= 0 || parsedCodigoCategoria < 0) {
				return false;
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}

}
