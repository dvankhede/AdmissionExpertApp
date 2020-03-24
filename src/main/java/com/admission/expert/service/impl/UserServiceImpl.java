package com.admission.expert.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admission.expert.domain.User;
import com.admission.expert.dto.UserVO;
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

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return (List<User>) userRepository.findAll();
	}

	@Override
	public Optional<User> getUserById(Long userId) {
		// TODO Auto-generated method stub
		return userRepository.findById(userId);
	}

	@Override
	public Optional<User> updateUserById(Long userId, UserVO userDto) {
		// TODO Auto-generated method stub
		Optional<User> userAvail = userRepository.findById(userId);
		if(userAvail  == null) {
			return null;
		}else {
			userAvail.get().setName(userDto.getName());
			userAvail.get().setEmail(userDto.getEmail());
			userAvail.get().setCity(userDto.getCity());
			userAvail.get().setMobile(userDto.getMobile());
			userRepository.save(userAvail.get());
			return userAvail;
		}
		
	}

}
