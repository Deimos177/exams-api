package com.exams.utils;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component(value = "emailService")
public class EmailService {

	private JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

	public void sendEmail(SimpleMailMessage email) {
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("remetente email");
	    mailSender.setPassword("remetente senha");
	    
	    Properties props = mailSender.getJavaMailProperties();
	    
	    props.put("mail.smtp.host", "smtp.gmail.com");  
	    props.put("mail.smtp.auth", "true"); 
	    props.put("mail.smtp.port", "465");  
	    props.put("mail.smtp.starttls.enable", "true");  
	    props.put("mail.smtp.socketFactory.port", "465");  
	    props.put("mail.smtp.socketFactory.fallback", "false");  
	    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    
		mailSender.send(email);
	}

	public SimpleMailMessage generatePasswordEmail(String email, String token) {

		SimpleMailMessage passwordEmail = new SimpleMailMessage();

		passwordEmail.setFrom("support@pbc.com");
		passwordEmail.setTo(email);
		passwordEmail.setSubject("Password Reset Request");
		passwordEmail
				.setText("To reset your password, click the link below:\n localhost:8080/user/resetpassword?token=" + token);

		return passwordEmail;
	}
}