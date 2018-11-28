package com.marketingpersonal.common;

/**
 * Clase para envio de correos electronicos
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
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
    
	/**
     * Método que realiza el envío de correo electronico a usuario Responsable
     * @param presupuesto: Variable de tipo Presupuesto que contiene la información del presupuesto que se enviará en el correo
     * @param aprobadorInicialFinal: usuario destinatario del correo
     * @param estado: estado del presupuesto
     */
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

			//Almacenamos en variable tipo string codigo html para dar formato al correo a enviar
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

			// Datos de conexion al servidor SMTP
			Properties props = System.getProperties();
			 props.put("mail.smtp.auth",true);
		      props.put("mail.smtp.starttls.enable",true);
		      props.put("mail.smtp.host", mailServer);
		      props.put("mail.smtp.port", "587");
		      props.put("mail.transport.protocol", "smtp");
		      props.put("mail.smtp.connectiontimeout", "20000");
		      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		      // Obtenemos el objeto Session
		      SimpleMailAuthenticator authenticator = new SimpleMailAuthenticator(username, password);
		      Session session = Session.getInstance(props, authenticator);

			// Definicion del mensaje de correo
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, myList);

			message.setSubject(subject);
			message.setSentDate(new Date());

			// Cuerpo del correo
			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setContent(messageBody, "text/html");

			// Variable para manejo de adjuntos
			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			// Envío de mensaje
			Transport.send(message);
			System.out.println("Correo enviado");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
     * Método que realiza el envío de correo electronico a usuario Aprobador Inicial
     * @param presupuesto: Variable de tipo Presupuesto que contiene la información del presupuesto que se enviará en el correo
     * @param responsable: usuario remitente del correo
     * @param listaAprobadorInicial: usuarios destinatarios del correo
     * @param estado: estado del presupuesto
     */
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

			// Datos de conexion al servidor SMTP
			Properties props = System.getProperties();
			props.put("mail.smtp.auth",true);
		      props.put("mail.smtp.starttls.enable",true);
		      props.put("mail.smtp.host", mailServer);
		      props.put("mail.smtp.port", "587");
		      props.put("mail.transport.protocol", "smtp");
		      props.put("mail.smtp.connectiontimeout", "20000");
		      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		   // Obtenemos el objeto Session
		      SimpleMailAuthenticator authenticator = new SimpleMailAuthenticator(username, password);
		      Session session = Session.getInstance(props, authenticator);

		   // Definicion del mensaje de correo
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, myList);

			if (listaAprobadorInicial.size() >= 1) {
				message.setRecipients(Message.RecipientType.CC, listaCorreosAprobadorInicial);
			}

			message.setSubject(subject);
			message.setSentDate(new Date());

			//Variable que respresenta el cuerpo del correo
			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setContent(messageBody, "text/html");

			// Variable para manejo de adjuntos
			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			// Envío de mensaje
			Transport.send(message);
			System.out.println("Correo enviado");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
     * Método que realiza el envío de correo electronico a usuario Aprobador Final
     * @param presupuesto: Variable de tipo Presupuesto que contiene la información del presupuesto que se enviará en el correo
     * @param aprobadorInicial: usuario remitente del correo
     * @param listaAprobadorFinal: usuarios destinatarios del correo
     * @param estado: estado del presupuesto
     */
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

			// Datos de conexion al servidor SMTP
			Properties props = System.getProperties();
			props.put("mail.smtp.auth",true);
		      props.put("mail.smtp.starttls.enable",true);
		      props.put("mail.smtp.host", mailServer);
		      props.put("mail.smtp.port", "587");
		      props.put("mail.transport.protocol", "smtp");
		      props.put("mail.smtp.connectiontimeout", "20000");
		      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		   // Obtenemos el objeto Session
		      SimpleMailAuthenticator authenticator = new SimpleMailAuthenticator(username, password);
		      Session session = Session.getInstance(props, authenticator);

		   // Definicion del mensaje de correo
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, myList);

			if (listaAprobadorFinal.size() >= 1) {
				message.setRecipients(Message.RecipientType.CC, listaCorreosAprobadorFinal);
			}

			message.setSubject(subject);
			message.setSentDate(new Date());

			//Variable que respresenta el cuerpo del correo
			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setContent(messageBody, "text/html");

			// Variable para manejo de adjuntos
			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			// Envío de correo
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
	
	//Metodo MAin para realizar pruebas de envio de correos a traves del metodo sendEmail
	public static void main(String[] args) {
		EnviarCorreo a =new EnviarCorreo();
		a.sendEmail();		
	}
	
	public static boolean sendEmail() {
	      //Correo remitente
		  String to = "presupuesto@marketingpersonal.com";

	      // Correo destinatario
	      String from = "presupuesto@marketingpersonal.com";
	      final String username = "presupuesto@marketingpersonal.com";
	      final String password = "Marketing2018";
	      	      
	      // Servidor SMTP
	      String host = "smtp.office365.com";

	      Properties props = new Properties();

	      props.put("mail.smtp.auth",true);
	      props.put("mail.smtp.starttls.enable",true);
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "587");
	      props.put("mail.transport.protocol", "smtp");
	      props.put("mail.smtp.connectiontimeout", "20000");
	      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

	      SimpleMailAuthenticator authenticator = new SimpleMailAuthenticator(username, password);
	      Session session = Session.getInstance(props, authenticator);


	      try {
	         // Create a default MimeMessage object.
	         Message message = new MimeMessage(session);

	         message.setFrom(new InternetAddress(from));

	         message.setRecipients(Message.RecipientType.TO,
	         InternetAddress.parse(to));

	         // Titulo del correo
	         message.setSubject("test");

	         // Mensaje del coreeo
	         message.setText("test");

	         // Envío de correo
	         Transport.send(message);

	         System.out.println("Mensaje Enviado....");

	      } catch (MessagingException e) {
	            throw new RuntimeException(e);
	      }
	    return true;
	   }

}
