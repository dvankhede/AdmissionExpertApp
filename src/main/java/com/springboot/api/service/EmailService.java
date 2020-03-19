package com.springboot.api.service;

import com.springboot.api.domain.User;

public interface EmailService {
	public void sendEmail(String appUrl, User user);

}
