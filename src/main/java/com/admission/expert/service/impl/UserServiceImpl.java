package com.admission.expert.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admission.expert.domain.User;
import com.admission.expert.repository.UserRepository;
import com.admission.expert.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<User> findUserByResetToken(String resetToken) {
		return userRepository.findByResetToken(resetToken);
	}

}
