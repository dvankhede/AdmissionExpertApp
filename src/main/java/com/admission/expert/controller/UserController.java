package com.admission.expert.controller;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@PostMapping(value = "/api/v1/user/signup")
	@Transactional
	@ApiOperation(value = "Register to app.",response = HttpStatus.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created account"),
            @ApiResponse(code = 409, message = "Conflict: User with given email id already exists"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
	public ResponseEntity<HttpStatus> register(@RequestBody UserVO userVO, HttpServletRequest request,
			Authentication authentication) {
		logger.info("getUser called {}", authentication.getName());
		if (!authentication.isAuthenticated()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		User loggedInUser = userRepository.findByEmail(authentication.getName());
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
		
//		if(StringUtils.isEmpty(userVO.getPassword()) || !this.validPassword(userVO.getPassword())) {
//			throw new InvalidInputException("Password does not meet minimum complexity. Min chars should be 8 & max chars should be 16");
//		}
		

//		try {
//			user.setPassword(this.secUtils.encryptPass(userVO.getPassword()));
//		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
//				| NoSuchPaddingException | InvalidAlgorithmParameterException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		User user = userService.addUser(userVO, loggedInUser);
		String origin = request.getHeader("origin");
		emailService.sendEmail(origin, user);

		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
		
	}
	
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@PutMapping(value = "/api/v1/user/update/{userId}")
	@Transactional
	@ApiOperation(value = "Upadte User.",response = HttpStatus.class)
	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Successfully Update user"),
            @ApiResponse(code = 409, message = "Conflict: User with given email id already exists"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
	public ResponseEntity<HttpStatus> update(@RequestBody UserVO userVO, @PathVariable("userId") Long userId) {
		logger.info("register called {}", userVO.toString());
		if(!this.mandatoryValuesPresent(userVO)) {
			throw new InvalidInputException("Mandatory fields are missing");
		}
		
		if(StringUtils.isEmpty(userVO.getEmail()) || !CommonUtils.validateEmail(userVO.getEmail())) {
			throw new InvalidInputException("Enter valid email address");
		}
		
		User existingUserByEmail = userRepository.findByEmail(userVO.getEmail());
		User existingUser = userService.findById(userId);
		if (existingUserByEmail != null) {
			if (!existingUser.getEmail().equalsIgnoreCase(userVO.getEmail())) {
			throw new ResourceConflictException("User with given email id already exist");
			}
		}
		userService.update(userVO, existingUser);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@PutMapping(value = "/api/v1/user/delet/{userId}")
	@Transactional
	@ApiOperation(value = "Delete User.", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully delete user"),
			@ApiResponse(code = 404, message = "Resource not found") })
	public ResponseEntity<HttpStatus> delete(@PathVariable("userId") Long userId) {
		logger.debug("Deleting a user entry with id: {}", userId);
		userService.delete(userId);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);

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
	
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@GetMapping(value = "/api/v1/user/{id}")
	@ApiOperation(value = "Find user profile information for logged in user",response = HttpStatus.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched the profile"),
            @ApiResponse(code = 404, message = "User not found for given email id")
    })
	public ResponseEntity<UserVO> getUserByid(@PathVariable("id") Long id) {

		User user = userService.findById(id);
		UserVO userVO = new UserVO();
		BeanUtils.copyProperties(user, userVO);
		return new ResponseEntity<UserVO>(userVO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@GetMapping(value = "/api/v1/all/users")
	@ApiOperation(value = "Find All users profile information ", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched the Users"),
			@ApiResponse(code = 404, message = "User not found for given email id") })
	public ResponseEntity<Page<UserVO>> getAllUser(Authentication authentication, Pageable pageable) {
		logger.info("getUser called {}", authentication.getName());
		if (!authentication.isAuthenticated()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<Page<UserVO>>(userService.findAllUser(pageable), HttpStatus.OK);
	}

	private boolean mandatoryValuesPresent(UserVO userVO) {
		if (StringUtils.isEmpty(userVO.getFullName()) || StringUtils.isEmpty(userVO.getEmail())
				|| userVO.getNeetScore() == null || userVO.getPcbPercentage() == null
				|| userVO.getAllIndiaRank() == null || userVO.getStateRank() == null
//				|| userVO.getCasteCategoryId() == null || userVO.getCityId() == null || userVO.getStateId() == null
		) {
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
