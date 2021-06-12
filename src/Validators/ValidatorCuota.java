package Validators;

import exceptions.ValidatorsException;

public class ValidatorCuota {
	public static void  ValidarMesYAnio(String mes, String anio) throws ValidatorsException {
		try {
			int parseMes = Integer.parseInt(mes);
			int parseanio = Integer.parseInt(anio);
			if(parseMes < 1 || parseMes > 12)
			{
				throw new ValidatorsException("el mes no encuentra entre 1 y 12");
			}
		}catch(NumberFormatException e){
			throw new ValidatorsException("Alguno de los valores año y mes no es numerico");
		}
		
	}

}
