package com.admission.expert.controller;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.admission.expert.domain.User;
import com.admission.expert.dto.UserVO;
import com.admission.expert.exception.InvalidInputException;
import com.admission.expert.exception.ResourceConflictException;
import com.admission.expert.exception.ResourceNotFoundException;
import com.admission.expert.repository.UserRepository;
import com.admission.expert.service.EmailService;
import com.admission.expert.service.UserService;
import com.admission.expert.util.CommonUtils;
import com.admission.expert.util.SecurityUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value="User operations")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SecurityUtil secUtils;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/api/v1/user/signup")
	@Transactional
	@ApiOperation(value = "Register to app.",response = HttpStatus.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created account"),
            @ApiResponse(code = 409, message = "Conflict: User with given email id already exists"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
	public ResponseEntity<HttpStatus> register(@RequestBody UserVO userVO) {
		logger.info("register called {}", userVO.toString());
		if(!this.mandatoryValuesPresent(userVO)) {
			throw new InvalidInputException("Mandatory fields are missing");
		}
		
		if(StringUtils.isEmpty(userVO.getEmail()) || !CommonUtils.validateEmail(userVO.getEmail())) {
			throw new InvalidInputException("Enter valid email address");
		}
		
		User existingUser = userRepository.findByEmail(userVO.getEmail());
		if (existingUser!= null) {
			throw new ResourceConflictException("User with given email id already exist");
		}
		
		if(StringUtils.isEmpty(userVO.getPassword()) || !this.validPassword(userVO.getPassword())) {
			throw new InvalidInputException("Password does not meet minimum complexity. Min chars should be 8 & max chars should be 16");
		}
		
		User user=new User();
		BeanUtils.copyProperties(userVO, user);
		try {
			user.setPassword(this.secUtils.encryptPass(userVO.getPassword()));
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
				| NoSuchPaddingException | InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.userRepository.save(user);
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value = "/api/getAllUser", method = RequestMethod.GET)
	@ResponseBody
	public List<UserVO> getUser(){
		 List<User> listOfUser = userService.getAllUser();
		 List<UserVO> userListDto = new ArrayList<>();
		 for (User user : listOfUser) {
			 UserVO userDto= mapEntityListToDto(user);
			 userListDto.add(userDto);	 
		} 
		 return userListDto;	
	}
	
	@RequestMapping(value="/api/getUserById/{userId}", method =  RequestMethod.GET)
	@ResponseBody
	public UserVO getUserByID(@PathVariable("userId") Long userId) {
		Optional<User> userById = userService.getUserById(userId); 
		UserVO userDto = mapEnitityToDto(userById);
		return userDto;
	}
	
	@RequestMapping( value= "/api/updateUserById/{userId}", method = RequestMethod.PUT)
	@ResponseBody
	public UserVO updateUserById(@PathVariable ("userId")Long userId, @RequestBody UserVO userReqDto)
	{
		Optional<User> updateUserId = userService.updateUserById(userId, userReqDto);
		UserVO userDto = mapEnitityToDto(updateUserId);
		return userDto;
	}
	
	private UserVO mapEntityListToDto(User userall) {
		UserVO userDto = new UserVO();
		userDto.setId(userall.getId());
		userDto.setName(userall.getName());
		userDto.setEmail(userall.getName());
		userDto.setEmail(userall.getEmail());
		userDto.setCity(userall.getCity());
		userDto.setMobile(userall.getMobile());
		return userDto;		
	}
	
	public UserVO  mapEnitityToDto(Optional<User> userById) {
		UserVO userDto = new UserVO();
		userDto.setId(userById.get().getId());
		userDto.setName(userById.get().getName());
		userDto.setEmail(userById.get().getName());
		userDto.setEmail(userById.get().getEmail());
		userDto.setCity(userById.get().getCity());
		userDto.setMobile(userById.get().getMobile());
		return userDto;		
	}
	
	
	@PostMapping(value = "/api/forgot/password")
	@Transactional
	@ApiOperation(value = "forgot password.", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "A password reset link has been sent to"),
			@ApiResponse(code = 404, message = "We didn't find an account for that e-mail address.") })
	public ResponseEntity<HttpStatus> processForgotPasswordForm(@RequestParam("email") String userEmail,
			HttpServletRequest request) {

		User user = this.userRepository.findByEmail(userEmail);

		if (user != null) {
			user.setResetToken(UUID.randomUUID().toString());

			userRepository.save(user);

			String origin = request.getHeader("origin");

			emailService.sendEmail(origin, user);

			return new ResponseEntity<HttpStatus>(HttpStatus.OK);

		} else
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);

	}

	@PostMapping(value = "/reset/password")
	@Transactional
	@ApiOperation(value = "reset password.", response = HttpStatus.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "You have successfully reset your password.  You may now login."),
			@ApiResponse(code = 404, message = "Oops!  This is an invalid password reset link.") })
	public ResponseEntity<HttpStatus> setNewPassword(@RequestParam String token, @RequestParam String password) {

		Optional<User> user = userService.findUserByResetToken(token);

		if (user.isPresent()) {

			User resetUser = user.get();

			try {
				resetUser.setPassword(this.secUtils.encryptPass(password));
			} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
					| NoSuchPaddingException | InvalidAlgorithmParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			resetUser.setResetToken(null);

			userRepository.save(resetUser);

			return new ResponseEntity<HttpStatus>(HttpStatus.OK);

		} else {

			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}

	}

	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@GetMapping(value = "/api/v1/user")
	@ApiOperation(value = "Find user profile information for logged in user",response = HttpStatus.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched the profile"),
            @ApiResponse(code = 404, message = "User not found for given email id")
    })
	public ResponseEntity<UserVO> getUser(Authentication authentication) {
		logger.info("getUser called {}", authentication.getName());
		if(!authentication.isAuthenticated()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		User user=this.userRepository.findByEmail(authentication.getName());
		if (user == null) {
			throw new ResourceNotFoundException("User not found with give email id");
		}
		UserVO userVO=new UserVO();
		BeanUtils.copyProperties(user, userVO);
		userVO.setPassword(null);
		return new ResponseEntity<UserVO>(userVO, HttpStatus.OK);
	}
	
	private boolean mandatoryValuesPresent(UserVO userVO) {
		if(StringUtils.isEmpty(userVO.getName()) || StringUtils.isEmpty(userVO.getEmail()) || StringUtils.isEmpty(userVO.getPassword()) ) {
			return false;
		}
		return true;
	}


	private boolean validPassword(String pass) {
		if(pass.length()>=8 && pass.length()<=16) {
			return true;
		}
		return false;
	}
 
	
}
