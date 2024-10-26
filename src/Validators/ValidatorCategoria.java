package Validators;

import exceptions.AppException;
import exceptions.ValidatorsException;
import java.util.regex.Pattern;
import java.io.IOException;
import java.util.regex.Matcher;

public class ValidatorCategoria {
		
	public void validacion_categoria(String nombre) throws AppException, IOException{
		if (nombre != null) {
			Pattern nombre_pattern = Pattern.compile("/^\\s+$/");
			Matcher nombre_matcher = nombre_pattern.matcher(nombre);
			if (nombre.isEmpty()) {
				throw new ValidatorsException("Nombre de la categoría vacío");
			}
			else if(nombre_matcher.matches()){
				throw new ValidatorsException("Nombre de la categoría formado por espacios en blanco");
			}
			else if (nombre.length() > 45) {
				throw new ValidatorsException("El nombre de la categoría debe tener una longitud menor a 45");
			}
		}
	}
}
