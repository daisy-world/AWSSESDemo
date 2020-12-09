package com.app.AWSSESDemo;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
	
	@Value("${app.email.host}")
	private String SMTP_HOST_NAME;	
	
	@Value("${app.email.user}")
	private String SMTP_AUTH_USER;	
	
	@Value("${app.email.password}")
	private String SMTP_AUTH_PWD;	
	
	@Value("${app.email.port}")
	private int SMTP_HOST_PORT;	
	
	@Value("${app.email.from}")
	private String FROM;	
	
	
	
	@GetMapping("/sendEmail")
	
	public String sendEmailMethod() throws MessagingException {
		
		sendEmail();
		return "email sent successfully";
		
		
	}
		public  void sendEmail() throws MessagingException {
		
		
		final String TO = "example@gmail.com"; // {YOUR_RECIPIENT_EMAIL_ADDRESS}

			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.auth", "false");
			props.put("mail.smtp.starttls.enable", "true");

			Session mailSession = Session.getDefaultInstance(props);
			mailSession.setDebug(true);

			Transport transport = mailSession.getTransport("smtp");

			MimeMessage message = new MimeMessage(mailSession);

			message.setSubject("AWS SES DEMO");
			message.setContent("Hello AWS", "text/html");
			message.setSentDate(new Date());
			message.setFrom(new InternetAddress(FROM));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
																					
			transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
			System.out.println("email sent successfully........");
		
		
	}

}
