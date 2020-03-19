package com.admission.expert.service;

import com.admission.expert.domain.User;

public interface EmailService {
	public void sendEmail(String appUrl, User user);

}
