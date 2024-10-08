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
		
        // Configuración de propiedades para la conexión SMTP de Brevo
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp-relay.brevo.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2"); 

        // Autenticación con tu cuenta Brevo
        final String brevoUsername = "7d394a001@smtp-brevo.com";
        final String brevoPassword = "5PN6C2wcLA7dm8zE"; 

        // Sesión de autenticación SMTP
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(brevoUsername, brevoPassword);
            }
        });

        try {
            // Creación del mensaje de correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("vinotecagatti@gmail.com")); // Remitente
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Destinatario
            message.setSubject("Confirmación del pedido"); // Asunto

            // Cuerpo del mensaje
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Gracias por tu compra. Tu número de pedido es: " + nro_pedido);

            // Agregar contenido al correo
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            // Enviar el correo
            Transport.send(message);
            System.out.println("Correo enviado correctamente!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
	}
	
	public void enviar_mail_cancelacion(String destinatario) throws IOException, MessagingException {
		  // Configuración de propiedades para la conexión SMTP de Brevo
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp-relay.brevo.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2"); 

        // Autenticación con tu cuenta Brevo
        final String brevoUsername = "7d394a001@smtp-brevo.com";
        final String brevoPassword = "5PN6C2wcLA7dm8zE"; 

        // Sesión de autenticación SMTP
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(brevoUsername, brevoPassword);
            }
        });

		try {
	
            // Creación del mensaje de correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("vinotecagatti@gmail.com")); // Remitente
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Destinatario
			
	
		    message.setSubject("CONFIRMACI�N DE CANCELACI�N");
	
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("SU PEDIDO SE CANCEL� SATISFACTORIAMENTE, MUCHAS GRACIAS.");
			
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
		  // Configuración de propiedades para la conexión SMTP de Brevo
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp-relay.brevo.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2"); 

        // Autenticación con tu cuenta Brevo
        final String brevoUsername = "7d394a001@smtp-brevo.com";
        final String brevoPassword = "5PN6C2wcLA7dm8zE"; 

        // Sesión de autenticación SMTP
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(brevoUsername, brevoPassword);
            }
        });
	
		try {
			 // Creación del mensaje de correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("vinotecagatti@gmail.com")); // Remitente
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Destinatario
	
	
			message.setSubject("CONFIRMACION DE PREPARACION");
	
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("ESTIMADO CLIENTE, SU PEDIDO N�MERO " + nro_pedido + " YA EST� LISTO Y PUEDE PASAR A RETIRARLO");
			
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
