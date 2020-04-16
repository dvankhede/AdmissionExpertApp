package com.admission.expert.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admission.expert.domain.CasteCategory;
import com.admission.expert.domain.City;
import com.admission.expert.domain.State;
import com.admission.expert.domain.User;
import com.admission.expert.dto.UserVO;
import com.admission.expert.exception.ResourceNotFoundException;
import com.admission.expert.repository.UserRepository;
import com.admission.expert.service.AppMetaDataService;
import com.admission.expert.service.UserService;


@Service
public class UserServiceImpl implements UserService {

	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AppMetaDataService appMetaDataService;



	@Override
	public User addUser(UserVO userVO, User loggedInUser) {
		User user = new User();
		BeanUtils.copyProperties(userVO, user);
		user.setPassword(UUID.randomUUID().toString());
		user.setResetToken(UUID.randomUUID().toString());
		user.setIsActive(true);
		user.setCreatedOn(new Date());
		user.setCreatedBy(loggedInUser.getId());
		user.setUpdatedOn(new Date());
		user.setUpdatedBy(loggedInUser.getId());
		CasteCategory casteCategory = appMetaDataService.findCategoryById(userVO.getCasteCategoryId());
		user.setCasteCategory(casteCategory);
		State state = appMetaDataService.findStateById(userVO.getStateId());
		user.setState(state);
		City city = appMetaDataService.findCityById(userVO.getCityId());
		user.setCity(city);
		return userRepository.save(user);

	}

	@Override
	public Optional<User> findUserByResetToken(String resetToken) {
		return userRepository.findByResetToken(resetToken);
	}

	@Override
	public Page<UserVO> findAllUser(Pageable pageable) {
		Page<User> users = userRepository.findAll(pageable);
		List<UserVO> userVOs = new ArrayList<UserVO>();
		for (User user : users.getContent()) {
			UserVO userVO = new UserVO();
			BeanUtils.copyProperties(user, userVO);
			userVO.setCasteCategoryId(user.getCastCategory().getId());
			userVO.setCityId(user.getCity().getId());
			userVO.setStateId(user.getState().getId());
			userVOs.add(userVO);
		}
		return new PageImpl<>(userVOs, pageable, users.getTotalElements());
	}

	@Override
	public User findById(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new ResourceNotFoundException("User not found with give email id");
		}
		UserVO userVO = new UserVO();
		BeanUtils.copyProperties(user.get(), userVO);
		userVO.setCasteCategoryId(user.get().getCastCategory().getId());
		userVO.setCityId(user.get().getCity().getId());
		userVO.setStateId(user.get().getState().getId());
		return user.get();
	}

	@Override
	public User update(UserVO userVO, User user) {
		user.setFullName(userVO.getFullName());
		user.setEmail(userVO.getEmail());
		user.setDateOfBirth(userVO.getDateOfBirth());
		user.setMobile(userVO.getMobile());
		user.setGender(userVO.getGender());
	    user.setNeetScore(userVO.getNeetScore());
	    user.setPcbPercentage(userVO.getPcbPercentage());
	    user.setStateRank(userVO.getStateRank());
	    user.setAllIndiaRank(userVO.getAllIndiaRank());
		CasteCategory casteCategory = appMetaDataService.findCategoryById(userVO.getCasteCategoryId());
		user.setCasteCategory(casteCategory);
		State state = appMetaDataService.findStateById(userVO.getStateId());
		user.setState(state);
		City city = appMetaDataService.findCityById(userVO.getCityId());
		user.setCity(city);
		return userRepository.save(user);
	}

	@Transactional(readOnly = true, rollbackFor = { ResourceNotFoundException.class })
	@Override
	public void delete(Long userId) {
		logger.debug("Finding a User entry with id: {}", userId);
		Optional<User> foundUser = userRepository.findById(userId);
		logger.debug("Found User entry: {}", foundUser.get());
		if (!foundUser.isPresent()) {
			throw new ResourceNotFoundException("No User-entry found with id: " + userId);
		}
		User user = foundUser.get();
		user.setIsActive(false);
		userRepository.save(user);

	}
}
