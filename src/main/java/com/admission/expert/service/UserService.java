package com.admission.expert.service;

import java.util.List;
import java.util.Optional;

import com.admission.expert.domain.User;
import com.admission.expert.dto.UserVO;

public interface UserService {	
	public Optional<User> findUserByResetToken(String resetToken);
	public List<User> getAllUser();
	public Optional<User> getUserById(Long userId);
	public Optional<User> updateUserById(Long userId, UserVO userDto);
}
