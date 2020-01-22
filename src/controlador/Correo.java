package controlador;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Correo {

	public void enviar_mail_confirmacion(String destinatario, int nro_pedido) throws IOException {
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		String resourceName = "config.properties";
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties config = new Properties();
		try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
		    config.load(resourceStream);
		}

		final String gmailAccount = config.getProperty("gmail.account");
		final String gmailPassword = config.getProperty("gmail.password");
		final String emailDestination = destinatario;

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(gmailAccount,gmailPassword);
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(gmailAccount));
			
			
		    message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestination));
			

			message.setSubject("Confirmación del pedido");

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("Gracias por su compra. Recuerde que debe pasar a retirar y abonar el pedido en tres dás hábiles por el local. Su número de pedido es "+nro_pedido);
			
			Multipart multipart = new MimeMultipart();
			
			//Setting email text message
			multipart.addBodyPart(messageBodyPart);

			//set the attachments to the email
	        message.setContent(multipart);

			Transport.send(message);

			System.out.println("Correo enviado");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
	
public void enviar_mail_cancelacion(String destinatario) throws IOException {
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		String resourceName = "config.properties";
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties config = new Properties();
		try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
		    config.load(resourceStream);
		}

		final String gmailAccount = config.getProperty("gmail.account");
		final String gmailPassword = config.getProperty("gmail.password");
		final String emailDestination = destinatario;

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(gmailAccount,gmailPassword);
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(gmailAccount));
			
			
		    message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestination));
			

			message.setSubject("Confirmación de cancelación");

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("Su pedido ha sido cancelado exitosamente");
			
			Multipart multipart = new MimeMultipart();
			
			//Setting email text message
			multipart.addBodyPart(messageBodyPart);

			//set the attachments to the email
	        message.setContent(multipart);

			Transport.send(message);

			System.out.println("Correo enviado");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

}
