package controlador;

import java.io.FileInputStream;
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

    private String mailSmtpHost;
    private String mailSmtpPort; 
    private String mailSmtpAuth; 
    private String mailSmtpStartTlsEnable; 
    private String mailSmtpSslProtocols; 
    private String mailUserName;   
    private String mailPassword;   

    Correo() {
        try {
            String configFilePath = System.getProperty("catalina.base") + "/conf/config.properties";
                        
            // Leer el archivo de configuraci�n
            FileInputStream inputStream = new FileInputStream(configFilePath);
            loadConfig(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadConfig(FileInputStream inputStream) throws Exception {
        // Cargar las propiedades desde el archivo de configuraci�n
        Properties config = new Properties();
        config.load(inputStream);

        // Obtener los valores de configuraci�n
        mailSmtpHost = config.getProperty("mail.smtp.host");
        mailSmtpPort = config.getProperty("mail.smtp.port");
        mailSmtpAuth = config.getProperty("mail.smtp.auth");
        mailSmtpStartTlsEnable = config.getProperty("mail.smtp.starttls.enable");
        mailSmtpSslProtocols = config.getProperty("mail.smtp.ssl.protocols");

        mailUserName = config.getProperty("mail.username");
        mailPassword = config.getProperty("mail.password");

        if (mailSmtpHost == null || 
        mailSmtpPort == null || 
        mailSmtpAuth == null ||
        mailSmtpStartTlsEnable == null || 
        mailSmtpSslProtocols == null||
        mailUserName == null || 
        mailPassword == null) {
            System.out.println("Faltan par�metros en el archivo config.properties para enviar email");
        }
    }

	public void enviar_mail_confirmacion(String destinatario, int nro_pedido) throws IOException, MessagingException {
		
        // Configuraci�n de propiedades para la conexi�n SMTP de Brevo
        Properties props = new Properties();
        props.put("mail.smtp.host", mailSmtpHost);
        props.put("mail.smtp.port", mailSmtpPort);
        props.put("mail.smtp.auth", mailSmtpAuth);
        props.put("mail.smtp.starttls.enable", mailSmtpStartTlsEnable);
        props.put("mail.smtp.ssl.protocols", mailSmtpSslProtocols); 

        // Autenticaci�n con tu cuenta Brevo
        final String brevoUsername = mailUserName;
        final String brevoPassword = mailPassword; 

        // Sesi�n de autenticaci�n SMTP
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(brevoUsername, brevoPassword);
            }
        });

        try {
            // Creaci�n del mensaje de correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("vinotecagatti@gmail.com")); // Remitente
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Destinatario
            message.setSubject("Confirmaci�n del pedido"); // Asunto

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
        // Configuraci�n de propiedades para la conexi�n SMTP de Brevo
        Properties props = new Properties();
        props.put("mail.smtp.host", mailSmtpHost);
        props.put("mail.smtp.port", mailSmtpPort);
        props.put("mail.smtp.auth", mailSmtpAuth);
        props.put("mail.smtp.starttls.enable", mailSmtpStartTlsEnable);
        props.put("mail.smtp.ssl.protocols", mailSmtpSslProtocols); 

        // Autenticaci�n con tu cuenta Brevo
        final String brevoUsername = mailUserName;
        final String brevoPassword = mailPassword; 

        // Sesi�n de autenticaci�n SMTP
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(brevoUsername, brevoPassword);
            }
        });

		try {
	
            // Creaci�n del mensaje de correo
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
        // Configuraci�n de propiedades para la conexi�n SMTP de Brevo
        Properties props = new Properties();
        props.put("mail.smtp.host", mailSmtpHost);
        props.put("mail.smtp.port", mailSmtpPort);
        props.put("mail.smtp.auth", mailSmtpAuth);
        props.put("mail.smtp.starttls.enable", mailSmtpStartTlsEnable);
        props.put("mail.smtp.ssl.protocols", mailSmtpSslProtocols); 

        // Autenticaci�n con tu cuenta Brevo
        final String brevoUsername = mailUserName;
        final String brevoPassword = mailPassword; 

        // Sesi�n de autenticaci�n SMTP
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(brevoUsername, brevoPassword);
            }
        });
	
		try {
			 // Creaci�n del mensaje de correo
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
