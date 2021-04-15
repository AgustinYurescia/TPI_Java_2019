package Validators;

import exceptions.AppException;
import exceptions.ValidatorsException;
import java.util.regex.Pattern;

import javax.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;


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
	
	public static boolean isIntNumeric(String cadena) {
        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException e) {
            resultado = false;
        }

        return resultado;
    }
	
	public static boolean isDoubleNumeric(String cadena) {

        boolean resultado;

        try {
            Double.parseDouble(cadena);
            resultado = true;
        } catch (NumberFormatException e) {
            resultado = false;
        }
        return resultado;
    }
	
	public void validacion_producto(String nombre, String cod_cat, String cantidad, String precio, Part imagen, String cod_prod) throws AppException, IOException{
		if (nombre != null) {
			Pattern nombre_pattern = Pattern.compile("/^\\s+$/");
			Matcher nombre_matcher = nombre_pattern.matcher(nombre);
			if (nombre.isBlank() || nombre.isEmpty()) {
				throw new ValidatorsException("Nombre vacío");
			}
			else if(nombre_matcher.matches()){
				throw new ValidatorsException("Nombre formado por espacios en blanco");
			}
			else if (nombre.length() > 45) {
				throw new ValidatorsException("El nombre del producto debe tener una longitud menor a 45");
			}
		}
		if (cod_cat != null) {
			if(cod_cat.isBlank() || cod_cat.isEmpty()) {
				throw new ValidatorsException("Categoria vacía");
			}
		}
		if (cantidad != null) {
			Pattern cantidad_pattern = Pattern.compile("/^\\s+$/");
			Matcher cantidad_matcher = cantidad_pattern.matcher(cantidad);
			if(cantidad.isBlank() || cantidad.isEmpty())
			{
				throw new ValidatorsException("Cantidad vacía");
			}
			else if(cantidad_matcher.matches()) {
				throw new ValidatorsException("Cantidad formada por espacios en blanco");
			}
			else if(!isIntNumeric(cantidad)) {
				throw new ValidatorsException("La cantidad debe ser un número");
			}
			else if(Integer.parseInt(cantidad) <= 0) {
				throw new ValidatorsException("La cantidad debe ser mayor a cero");
			}
		}
		if (precio != null) {
			Pattern precio_pattern = Pattern.compile("/^\\s+$/");
			Matcher precio_matcher = precio_pattern.matcher(precio);
			if(precio.isBlank() || precio.isEmpty())
			{
				throw new ValidatorsException("Precio vacío");
			}
			else if(precio_matcher.matches()) {
				throw new ValidatorsException("Precio formado por espacios en blanco");
			}
			else if(!isDoubleNumeric(precio)) {
				throw new ValidatorsException("El precio debe ser un número");
			}
			else if(Double.parseDouble(precio) <= 0) {
				throw new ValidatorsException("El precio debe ser mayor a cero");
			}
		}
		if(imagen != null) {
			if (imagen.getSize() == 0) {
				throw new ValidatorsException("No se cargó la imagen del producto");
			}
		}
		if(cod_prod != null) {
			if(cod_prod.isBlank() || cod_prod.isEmpty()) {
				throw new ValidatorsException("No se seleccionó ningún producto");
			}
		}
	}
}
