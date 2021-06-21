package Validators;

import exceptions.AppException;
import exceptions.ValidatorsException;
import java.util.regex.Pattern;
import java.io.IOException;
import java.util.regex.Matcher;

public class ValidatorPedido {
	
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
	
	public void validacion_entrega(String nro) throws AppException, IOException{
		if (nro != null) {
			Pattern nro_pattern = Pattern.compile("/^\\s+$/");
			Matcher nro_matcher = nro_pattern.matcher(nro);
			if (nro.isEmpty()) {
				throw new ValidatorsException("Número de pedido vacío");
			}
			else if(nro_matcher.matches()){
				throw new ValidatorsException("Número de pedido formado por espacios en blanco");
			}
			else if(!isIntNumeric(nro)) {
				throw new ValidatorsException("El número de pedido debe ser un número");
			}
			else if(Integer.parseInt(nro) <= 0) {
				throw new ValidatorsException("El número de pedido debe ser mayor a cero");
			}
		}
	}
	
	public void validacion_agregar_al_carrito(String disponible, String cantidad) throws AppException, IOException{
		if (cantidad != null) {
			Pattern cantidad_pattern = Pattern.compile("/^\\s+$/");
			Matcher cantidad_matcher = cantidad_pattern.matcher(cantidad);
			if (cantidad.isEmpty()) {
				throw new ValidatorsException("No se ingresó la cantidad deseada");
			}
			else if(cantidad_matcher.matches()){
				throw new ValidatorsException("Cantidad formada por espacios en blanco");
			}
			else if(!isIntNumeric(cantidad)) {
				throw new ValidatorsException("La cantidad debe ser un número");
			}
			else if(Integer.parseInt(cantidad) <= 0) {
				throw new ValidatorsException("La cantidad debe ser mayor a cero");
			}
			else if(Integer.parseInt(cantidad) > Integer.parseInt(disponible)) {
				throw new ValidatorsException("No hay stock disponible para la cantidad ingresada");
			}
		}
	}
}
