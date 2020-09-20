package com.fitapp.logic.controller.emailutil;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.fitapp.logic.factory.alertfactory.AlertFactory;

public class EmailSender {
	private static final String SENDER = "fitappispw@gmail.com";
	private static final String PASSWORD = "ispw20192020";
	private static final String HOST = "mail.smtp.host";
	private static final String PORT = "mail.smtp.port";
	private static final String AUTH = "mail.smtp.auth";

	private Properties props;

	public EmailSender() {
		this.props = new Properties();
		props.put(HOST, "smtp.gmail.com");
		props.put(PORT, "465");
		props.put(AUTH, "true");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.ssl.checkserveridentity", true); // Compliant

	}

	public void sendEmails(String subject, String object, String email) throws MessagingException {
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER, PASSWORD);
			}
		});
		// Used to debug SMTP issues
		session.setDebug(true);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(SENDER));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setText(object);

			// Send message
			Transport.send(message);

		} catch (MessagingException mex) {
			AlertFactory.getInstance().createAlert(mex);
		}

	}

}
