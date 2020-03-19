package com.springboot.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.springboot.api.domain.User;
import com.springboot.api.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromUserName;

	@Autowired
	private MailProperties mailProperties;

	@Override
	@Async
	public void sendEmail(String origin, User user) {
		// Email message
		SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
		passwordResetEmail.setFrom(mailProperties.getUsername());
		passwordResetEmail.setTo(user.getEmail());
		passwordResetEmail.setSubject("Password Reset Request");
		passwordResetEmail.setText(
				"To reset your password, click the link below:\n" + origin + "/reset/password?token="
						+ user.getResetToken());

		mailSender.send(passwordResetEmail);
	}

}
