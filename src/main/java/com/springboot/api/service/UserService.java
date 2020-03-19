package com.springboot.api.service;

import java.util.Optional;

import com.springboot.api.domain.User;

public interface UserService {
	
	public Optional<User> findUserByResetToken(String resetToken);
}
