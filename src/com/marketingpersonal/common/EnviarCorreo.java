package com.marketingpersonal.common;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import javax.mail.*;
import javax.mail.internet.*;

import com.marketingpersonal.model.entity.Presupuesto;
import com.marketingpersonal.model.entity.Usuario;

import java.util.Properties;

public class EnviarCorreo {
	private String mailServer = "smtp.office365.com";
	private String from = "presupuesto@marketingpersonal.com";
	private String password = "Marketing2018";
	private String username = "presupuesto@marketingpersonal.com";
    
	
	public void enviaCorreoResponsable(Presupuesto presupuesto, Usuario aprobadorInicialFinal, EnumEstadosPresupuesto estado) {
		try {
			String estadotxt = "";
			if (aprobadorInicialFinal.getCorreo() == null || aprobadorInicialFinal.getCorreo().equals("")) {
				return;
			}
			
			InternetAddress[] myList = new InternetAddress[1];
			myList[0] = new InternetAddress(aprobadorInicialFinal.getCorreo());

			String subject = "Notificación de Presupuesto "+estado.getNombre().toUpperCase();
			String messageBody = "";

			// Formatos de numeros
			NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance();
			formatoMoneda.setMaximumFractionDigits(0);

			NumberFormat formatoPorcentaje = NumberFormat.getPercentInstance();
			formatoPorcentaje.setMaximumFractionDigits(2);

			String html = "";

			html = leerArchivoLocal(this.getClass(), "htmlResponsable.txt");

			html = html.replace(":responsable", presupuesto.getUsuario().getNombre());
			
			if(EnumEstadosPresupuesto.APROBADO.equals(estado) ||
					EnumEstadosPresupuesto.FINALIZADO.equals(estado)){
				estadotxt = "<span style='color:green'>"+estado.getNombre().toUpperCase()+"</span>";
			}else {
				estadotxt = "<span style='color:red'>"+estado.getNombre().toUpperCase()+"</span>";
			}
			
			html = html.replace(":estadoPresupuesto", estadotxt);
			
			html = html.replace(":nombrePresupuesto", presupuesto.getNombre());
			html = html.replace(":descripcionPresupuesto", presupuesto.getDescripcion());
			html = html.replace(":tipoPresupuesto", presupuesto.getTipo());
			html = html.replace(":clasificacionPresupuesto", presupuesto.getClasificacion());
			html = html.replace(":aprobadorInicialFinal", aprobadorInicialFinal.getNombre());
			

			messageBody = html;

			// Setup mail server
			Properties props = System.getProperties();
			 props.put("mail.smtp.auth",true);
		      props.put("mail.smtp.starttls.enable",true);
		      props.put("mail.smtp.host", mailServer);
		      props.put("mail.smtp.port", "587");
		      props.put("mail.transport.protocol", "smtp");
		      props.put("mail.smtp.connectiontimeout", "20000");
		      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		      // Get the Session object.
		      SimpleMailAuthenticator authenticator = new SimpleMailAuthenticator(username, password);
		      Session session = Session.getInstance(props, authenticator);

			// Define a new mail message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, myList);

			message.setSubject(subject);
			message.setSentDate(new Date());

			// Create a message part to represent the body text
			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setContent(messageBody, "text/html");

			// use a MimeMultipart as we need to handle the file attachments
			Multipart multipart = new MimeMultipart();

			// add the message body to the mime message
			multipart.addBodyPart(messageBodyPart);

			// Put all message parts in the message
			message.setContent(multipart);

			// Send the message
			Transport.send(message);
			System.out.println("Correo enviado");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void enviaCorreoAprobadorInicial(Presupuesto presupuesto, Usuario responsable, List<Usuario> listaAprobadorInicial, EnumEstadosPresupuesto estado) {
		try {
			if (responsable.getCorreo() == null || responsable.getCorreo().equals("")) {
				return;
			}
			
			InternetAddress[] myList = new InternetAddress[1];
			myList[0] = new InternetAddress(responsable.getCorreo());

			InternetAddress[] listaCorreosAprobadorInicial = null;

			if (listaAprobadorInicial.size() >= 1) {
				listaCorreosAprobadorInicial = new InternetAddress[listaAprobadorInicial.size()];

				int i = 0;
				for (Usuario usu : listaAprobadorInicial) {
					listaCorreosAprobadorInicial[i] = new InternetAddress(usu.getCorreo());
					i++;
				}
			}

			String subject = "Notificación de CREACION de Presupuesto";
			String messageBody = "";

			// Formatos de numeros
			NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance();
			formatoMoneda.setMaximumFractionDigits(0);

			NumberFormat formatoPorcentaje = NumberFormat.getPercentInstance();
			formatoPorcentaje.setMaximumFractionDigits(2);

			String html = "";

			html = leerArchivoLocal(this.getClass(), "htmlAprobadorInicial.txt");

			html = html.replace(":aprobadorInicial", presupuesto.getUsuario().getNombre());
			
			html = html.replace(":nombrePresupuesto", presupuesto.getNombre());
			html = html.replace(":descripcionPresupuesto", presupuesto.getDescripcion());
			html = html.replace(":tipoPresupuesto", presupuesto.getTipo());
			html = html.replace(":clasificacionPresupuesto", presupuesto.getClasificacion());
			html = html.replace(":responsablePresupuesto", responsable.getNombre());

			messageBody = html;

			// Setup mail server
			Properties props = System.getProperties();
			props.put("mail.smtp.auth",true);
		      props.put("mail.smtp.starttls.enable",true);
		      props.put("mail.smtp.host", mailServer);
		      props.put("mail.smtp.port", "587");
		      props.put("mail.transport.protocol", "smtp");
		      props.put("mail.smtp.connectiontimeout", "20000");
		      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		      // Get the Session object.
		      SimpleMailAuthenticator authenticator = new SimpleMailAuthenticator(username, password);
		      Session session = Session.getInstance(props, authenticator);

			// Define a new mail message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, myList);

			if (listaAprobadorInicial.size() >= 1) {
				message.setRecipients(Message.RecipientType.CC, listaCorreosAprobadorInicial);
			}

			message.setSubject(subject);
			message.setSentDate(new Date());

			// Create a message part to represent the body text
			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setContent(messageBody, "text/html");

			// use a MimeMultipart as we need to handle the file attachments
			Multipart multipart = new MimeMultipart();

			// add the message body to the mime message
			multipart.addBodyPart(messageBodyPart);

			// Put all message parts in the message
			message.setContent(multipart);

			// Send the message
			Transport.send(message);
			System.out.println("Correo enviado");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void enviaCorreoAprobadorFinal(Presupuesto presupuesto, Usuario aprobadorInicial, List<Usuario> listaAprobadorFinal, EnumEstadosPresupuesto estado) {
		try {
			if (aprobadorInicial.getCorreo() == null || aprobadorInicial.getCorreo().equals("")) {
				return;
			}
			
			InternetAddress[] myList = new InternetAddress[1];
			myList[0] = new InternetAddress(aprobadorInicial.getCorreo());

			InternetAddress[] listaCorreosAprobadorFinal = null;

			if (listaAprobadorFinal.size() >= 1) {
				listaCorreosAprobadorFinal = new InternetAddress[listaAprobadorFinal.size()];

				int i = 0;
				for (Usuario usu : listaAprobadorFinal) {
					listaCorreosAprobadorFinal[i] = new InternetAddress(usu.getCorreo());
					i++;
				}
			}

			String subject = "Notificación de Presupuesto";
			String messageBody = "";

			// Formatos de numeros
			NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance();
			formatoMoneda.setMaximumFractionDigits(0);

			NumberFormat formatoPorcentaje = NumberFormat.getPercentInstance();
			formatoPorcentaje.setMaximumFractionDigits(2);

			String html = "";

			html = leerArchivoLocal(this.getClass(), "htmlAprobadorFinal.txt");

			html = html.replace(":aprobadorFinal", presupuesto.getUsuario().getNombre());
			
			html = html.replace(":nombrePresupuesto", presupuesto.getNombre());
			html = html.replace(":descripcionPresupuesto", presupuesto.getDescripcion());
			html = html.replace(":tipoPresupuesto", presupuesto.getTipo());
			html = html.replace(":clasificacionPresupuesto", presupuesto.getClasificacion());
			html = html.replace(":aprobadorInicial", aprobadorInicial.getNombre());

			messageBody = html;

			// Setup mail server
			Properties props = System.getProperties();
			props.put("mail.smtp.auth",true);
		      props.put("mail.smtp.starttls.enable",true);
		      props.put("mail.smtp.host", mailServer);
		      props.put("mail.smtp.port", "587");
		      props.put("mail.transport.protocol", "smtp");
		      props.put("mail.smtp.connectiontimeout", "20000");
		      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		      // Get the Session object.
		      SimpleMailAuthenticator authenticator = new SimpleMailAuthenticator(username, password);
		      Session session = Session.getInstance(props, authenticator);

			// Define a new mail message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, myList);

			if (listaAprobadorFinal.size() >= 1) {
				message.setRecipients(Message.RecipientType.CC, listaCorreosAprobadorFinal);
			}

			message.setSubject(subject);
			message.setSentDate(new Date());

			// Create a message part to represent the body text
			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setContent(messageBody, "text/html");

			// use a MimeMultipart as we need to handle the file attachments
			Multipart multipart = new MimeMultipart();

			// add the message body to the mime message
			multipart.addBodyPart(messageBodyPart);

			// Put all message parts in the message
			message.setContent(multipart);

			// Send the message
			Transport.send(message);
			System.out.println("Correo enviado");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	public void enviaCorreoMP() {
		try {
			InternetAddress[] myList = new InternetAddress[1];
			myList[0] = new InternetAddress("camel8160@gmail.com");

			InternetAddress[] listaCorreosAprobadorFinal = null;

			String subject = "Notificación de Presupuesto";
			String messageBody = "";

			// Formatos de numeros
			NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance();
			formatoMoneda.setMaximumFractionDigits(0);

			NumberFormat formatoPorcentaje = NumberFormat.getPercentInstance();
			formatoPorcentaje.setMaximumFractionDigits(2);

			String html = "";

			html = leerArchivoLocal(this.getClass(), "htmlAprobadorFinal.txt");


			messageBody = html;

			// Setup mail server
			Properties props = System.getProperties();
			props.put("mail.smtp.auth",true);
		      props.put("mail.smtp.starttls.enable",true);
		      props.put("mail.smtp.host", mailServer);
		      props.put("mail.smtp.port", "587");
		      props.put("mail.transport.protocol", "smtp");
		      props.put("mail.smtp.connectiontimeout", "20000");
		      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			// Get the Session object.
		      SimpleMailAuthenticator authenticator = new SimpleMailAuthenticator(username, password);
		      Session session = Session.getInstance(props, authenticator);

			// Define a new mail message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, myList);

			
			message.setSubject(subject);
			message.setSentDate(new Date());

			// Create a message part to represent the body text
			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setContent(messageBody, "text/html");

			// use a MimeMultipart as we need to handle the file attachments
			Multipart multipart = new MimeMultipart();

			// add the message body to the mime message
			multipart.addBodyPart(messageBodyPart);

			// Put all message parts in the message
			message.setContent(multipart);

			// Send the message
			Transport.send(message);
			System.out.println("Correo enviado");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	

	public static String leerArchivoLocal(Class<?> claseBase, String nombreArchivo) throws IOException {
		String resultado = "";
		InputStream is = claseBase.getResourceAsStream(nombreArchivo);

		while (is.available() > 0) {
			char a = (char) is.read();
			resultado += a;
		}
		is.close();
		return resultado;
	}
	
	
	public static void main(String[] args) {
		EnviarCorreo a =new EnviarCorreo();
		a.sendEmail();		
	}
	
	 public static boolean sendEmail() {
	      String to = "camel8160@gmail.com";

	      // Sender's email ID needs to be mentioned
	      String from = "presupuesto@marketingpersonal.com";
	      final String username = "presupuesto@marketingpersonal.com";
	      final String password = "Marketing2018";
	      
	      // Assuming you are sending email through relay.jangosmtp.net
	      String host = "smtp.office365.com";

	      Properties props = new Properties();

	      props.put("mail.smtp.auth",true);
	      props.put("mail.smtp.starttls.enable",true);
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "587");
	      props.put("mail.transport.protocol", "smtp");
	      props.put("mail.smtp.connectiontimeout", "20000");
	      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

	      // Get the Session object.
	      SimpleMailAuthenticator authenticator = new SimpleMailAuthenticator(username, password);
	      Session session = Session.getInstance(props, authenticator);


	      try {
	         // Create a default MimeMessage object.
	         Message message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.setRecipients(Message.RecipientType.TO,
	         InternetAddress.parse(to));

	         // Set Subject: header field
	         message.setSubject("test");

	         // Now set the actual message
	         message.setText("test");

	         // Send message
	         Transport.send(message);

	         System.out.println("Sent message successfully....");

	      } catch (MessagingException e) {
	            throw new RuntimeException(e);
	      }
	    return true;
	   }

}
