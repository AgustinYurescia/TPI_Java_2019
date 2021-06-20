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

	public void enviar_mail_confirmacion(String destinatario, int nro_pedido) throws IOException, MessagingException {
		
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
			

		    message.setSubject("CONFIRMACIÓN DEL PEDIDO");

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("GRACIAS POR SU COMPRA. RECUERDE QUE DEBE PODRÁ PASAR A RETIRAR SU PEDIDO CUANDO SE LO NOTIFIQUE POR EMAIL. SU NÚMERO DE PEDIDO ES " + nro_pedido);

			Multipart multipart = new MimeMultipart();

			//Setting email text message
			multipart.addBodyPart(messageBodyPart);

			//set the attachments to the email
	        message.setContent(multipart);

			Transport.send(message);


		} catch (MessagingException e) {
			throw e;
		}
	}
	
	public void enviar_mail_cancelacion(String destinatario) throws IOException, MessagingException {
		
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
			
	
		    message.setSubject("CONFIRMACIÓN DE CANCELACIÓN");
	
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("SU PEDIDO SE CANCELÓ SATISFACTORIAMENTE, MUCHAS GRACIAS.");
			
			Multipart multipart = new MimeMultipart();
			
			//Setting email text message
			multipart.addBodyPart(messageBodyPart);
	
			//set the attachments to the email
	        message.setContent(multipart);
	
			Transport.send(message);
	
			System.out.println("Correo enviado");
	
		} catch (MessagingException e) {
			throw e;
		}
	}

	public void enviar_mail_confirmacion_preparacion(String destinatario, int nro_pedido) throws IOException, MessagingException {
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
	
	
			message.setSubject("CONFIRMACION DE PREPARACION");
	
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("ESTIMADO CLIENTE, SU PEDIDO NÚMERO " + nro_pedido + " YA ESTÁ LISTO Y PUEDE PASAR A RETIRARLO");
			
			Multipart multipart = new MimeMultipart();
	
			//Setting email text message
			multipart.addBodyPart(messageBodyPart);
	
			//set the attachments to the email
	        message.setContent(multipart);
	
			Transport.send(message);
	
	
		} catch (MessagingException e) {
			throw e;
		}
	}
}
