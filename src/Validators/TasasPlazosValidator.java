package Validators;

import exceptions.AppException;
import exceptions.ValidatorsException;
import java.util.regex.Pattern;
import java.io.IOException;
import java.util.regex.Matcher;

public class TasasPlazosValidator {
	
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
	
	public void validacion_descuento_socio(String porcDescuento) throws AppException, IOException{
		if (porcDescuento != null) {
			Pattern descuento_pattern = Pattern.compile("/^\\s+$/");
			Matcher descuento_matcher = descuento_pattern.matcher(porcDescuento);
			if (porcDescuento.isEmpty()) {
				throw new ValidatorsException("Porcentaje de descuento vacío");
			}
			else if(descuento_matcher.matches()){
				throw new ValidatorsException("Porcentaje de descuento formado por espacios en blanco");
			}
			else if (!isDoubleNumeric(porcDescuento)) {
				throw new ValidatorsException("El porcentaje de descuento debe ser un número");
			}
			else if (Double.parseDouble(porcDescuento) <= 0) {
				throw new ValidatorsException("El porcentaje de descuento debe ser mayor a cero");
			}
		}
	}
	
	public void validacion_plazo_entrega(String dias) throws AppException, IOException{
		if (dias != null) {
			Pattern dias_pattern = Pattern.compile("/^\\s+$/");
			Matcher dias_matcher = dias_pattern.matcher(dias);
			if (dias.isEmpty()) {
				throw new ValidatorsException("Plazo de entrega vacío");
			}
			else if(dias_matcher.matches()){
				throw new ValidatorsException("Plazo de entrega formado por espacios en blanco");
			}
			else if (!isIntNumeric(dias)) {
				throw new ValidatorsException("El plazo de entrega debe ser un número");
			}
			else if (Integer.parseInt(dias) <= 0) {
				throw new ValidatorsException("El plazo de entrega debe ser mayor a cero");
			}
		}
	}
	
	public void validacion_porcentaje_ganancia(String porcGanancia) throws AppException, IOException{
		if (porcGanancia != null) {
			Pattern porcGanancia_pattern = Pattern.compile("/^\\s+$/");
			Matcher porcGanancia_matcher = porcGanancia_pattern.matcher(porcGanancia);
			if (porcGanancia.isEmpty()) {
				throw new ValidatorsException("Porcentaje de ganancia vacío");
			}
			else if(porcGanancia_matcher.matches()){
				throw new ValidatorsException("Porcentaje de ganancia formado por espacios en blanco");
			}
			else if (!isDoubleNumeric(porcGanancia)) {
				throw new ValidatorsException("El porcentaje de ganancia debe ser un número");
			}
			else if (Double.parseDouble(porcGanancia) <= 0) {
				throw new ValidatorsException("El porcentaje de ganancia debe ser mayor a cero");
			}
		}
	}
	
	public void validacion_valor_cuotas(String valor) throws AppException, IOException{
		if (valor != null) {
			Pattern valor_pattern = Pattern.compile("/^\\s+$/");
			Matcher valor_matcher = valor_pattern.matcher(valor);
			if (valor.isEmpty()) {
				throw new ValidatorsException("Valor de cuotas vacío");
			}
			else if(valor_matcher.matches()){
				throw new ValidatorsException("Valor de cuotas formado por espacios en blanco");
			}
			else if (!isDoubleNumeric(valor)) {
				throw new ValidatorsException("El valor de cuotas debe ser un número");
			}
			else if (Double.parseDouble(valor) <= 0) {
				throw new ValidatorsException("El valor de cuotas debe ser mayor a cero");
			}
		}
	}
	
}
