package controlador;

import java.io.IOException;

public class ProbarCorreos {

	public static void main(String[] args)  {
		Correo correo = new Correo();
		try {
			correo.enviar_mail_confirmacion("yurex96@gmail.com", 4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
