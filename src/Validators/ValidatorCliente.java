package Validators;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.AppException;
import exceptions.ValidatorsException;

public class ValidatorCliente {
	 public static void ValidarAlta(String dni, String cliente_usuario, String cliente_contrasena, String nombre, String apellido,
				String mail, String telefono, String direccion) throws AppException {
		 
		 Pattern whitespacesPattern = Pattern.compile("/^\\s+$/");
		 Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
		 Pattern integerPattern = Pattern.compile("^\\d+$");
		 
		 Matcher dniWhitespaceMatcher = whitespacesPattern.matcher(dni);
		 Matcher userNameWhitespaceMatcher = whitespacesPattern.matcher(cliente_usuario);
		 Matcher contrasenaWhitespaceMatcher = whitespacesPattern.matcher(cliente_contrasena);
		 Matcher mailWhitespaceMatcher = whitespacesPattern.matcher(mail);
		 Matcher nombreWhitespaceMatcher = whitespacesPattern.matcher(nombre);
		 Matcher apellidoWhitespaceMatcher = whitespacesPattern.matcher(apellido);
		 Matcher telefonoWhitespaceMatcher = whitespacesPattern.matcher(telefono);
		 Matcher direccionWhitespaceMatcher = whitespacesPattern.matcher(direccion);
		 
		 Matcher mailFormatMatcher = emailPattern.matcher(mail);
		 
		 Matcher telefonoMatcher = integerPattern.matcher(telefono);
		 
		 ArrayList<String> errores = new ArrayList<String>();
		
		 //Validaciones para dni
		 if(dniWhitespaceMatcher.matches() || dni.isEmpty()) {
			 errores.add("el dni no puede contener espacios en blanco o estar vacio.");
		 }
		 else if(IsInteger(dni)) {
			 if(dni.length() != 7 && dni.length() != 8) {
				 errores.add("El dni no posee la longitud requerida.");
			 }
		 }else {
			 char first =dni.charAt(0);
			 int result = "fmFM".indexOf(dni.charAt(0));
			 if(0 < "fmFM".indexOf(dni.charAt(0)) || (dni.length() != 8 && dni.length() != 9)) {
				 errores.add("dni no cumple con la longitud especificada para aquellos que empiezan con letras.");
			 }
		 }
		 
		 //validaciones para usuario
		 if(userNameWhitespaceMatcher.matches() || cliente_usuario.isEmpty() || cliente_usuario.contains(" ")) {
			 errores.add("el usuario no puede contener espacios en blanco o estar vacio.");
		 }
		 if(cliente_usuario.length() < 4) {
			 errores.add("el usuario no puede tener menos de cuatro caracteres de longitud.");
		 }
		 
		 //validaciones para contrasena
		 if(contrasenaWhitespaceMatcher.matches() || cliente_contrasena.isEmpty()) {
			 errores.add("la contrasena no puede contener espacios en blanco o estar vacia.");
		 }
		 if(cliente_contrasena.length() > 16 || cliente_contrasena.length() < 4) {
			 errores.add("La contrasena debe tener entre 4 y 16 caracteres.");
		 }
		 
		 //validaciones para nombre
		 if(nombreWhitespaceMatcher.matches() || nombre.isEmpty()) {
			 errores.add("El nombre no puede estar vacio o formado por espacios en blanco.");
		 }
		 //validaciones para apellido
		 if(apellidoWhitespaceMatcher.matches() || apellido.isEmpty()) {
			 errores.add("El apellido no puede estar vacio o formado por espacios en blanco.");
		 }
		 
		//validaciones para mail
		 if(!mailFormatMatcher.matches()) {
			 errores.add("revise el formato del email.");
		 }
		 if(mailWhitespaceMatcher.matches() || mail.isEmpty() || mail.contains(" ")) {
			 errores.add("El mail no puede contener espacios en blanco o estar vacio.");
		 }
		 
		 //validaciones para telefono
		 if(telefonoWhitespaceMatcher.matches() || telefono.isEmpty() || telefono.contains(" ")) {
			 errores.add("El telefono no puede contener espacios en blanco o estar vacio.");
		 }else if(!telefonoMatcher.matches()) {
			 errores.add("El telefono debe estar formado unicamente por numeros.");
		 }
		 
		 //Validaciones para direccion
		 if(direccionWhitespaceMatcher.matches() || nombre.isEmpty()) {
			 errores.add("La direccion no puede estar vacia o formada por espacios en blanco.");
		 }
		 
		 //Chequeo final
		 String MensajeError = "";
		 for(String e : errores) {
			 MensajeError = MensajeError + e + " <br>";
		 }
		 if(!MensajeError.equals("")) {
			 throw new ValidatorsException(MensajeError);
		 }
	 }
	 
	 private static boolean IsInteger(String param) {
		 try {
			 Integer.parseInt(param);
			 return true;
		 }catch(Exception ex) {
			 return false;
		 }
	 }
}
