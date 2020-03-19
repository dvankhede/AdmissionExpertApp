package com.admission.expert.service;

import java.util.Optional;

import com.admission.expert.domain.User;

public interface UserService {
	
	public Optional<User> findUserByResetToken(String resetToken);
}
