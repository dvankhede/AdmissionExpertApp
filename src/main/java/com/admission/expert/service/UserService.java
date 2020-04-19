package com.admission.expert.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.admission.expert.domain.User;
import com.admission.expert.dto.UserVO;

public interface UserService {
	
	public Optional<User> findUserByResetToken(String resetToken);

	public User addUser(UserVO userVO, User loggedInUser);

	public Page<UserVO> findAllUser(Pageable pageable);

	public User findById(Long userId);

	public User update(UserVO userVO, User user);

	public void delete(Long userId);

}
