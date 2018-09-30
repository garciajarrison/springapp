package com.marketingpersonal.common;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviarCorreoUtil {

	private Properties mailServerProperties;
	private Session getMailSession;
	private MimeMessage generateMailMessage;

	public EnviarCorreoUtil() {

		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		mailServerProperties.put("mail.smtp.host", "smtp.gmail.com");
		mailServerProperties.put("mail.smtp.port", "587");
	}

	public void generateAndSendEmail(String para, String copia, String asunto, String mensaje)
			throws AddressException, MessagingException {

		// Util util = Util.getInstance();
		// mailServerProperties.put("java.net.preferIPv4Stack", "true");
		// System.setProperty("java.net.preferIPv6Addresses" , "true");

		System.out.println("\n\n 2nd ===> get Mail Session..");
		getMailSession = Session.getDefaultInstance(mailServerProperties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("correo@correo.com.co", "clavecorreo");
			}
		});
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
		if (copia != null && !"".equals(copia))
			generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(copia));
		generateMailMessage.setSubject(asunto);
		generateMailMessage.setContent(mensaje, "text/html");
		System.out.println("Mail Session has been created successfully..");

		// Step3
		System.out.println("\n\n 3rd ===> Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtp");

		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		transport.connect("smtp.gmail.com", "<----- Your GMAIL ID ----->", "<----- Your GMAIL PASSWORD ----->");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}

}
