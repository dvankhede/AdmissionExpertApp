package com.admission.expert.controller;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.admission.expert.domain.User;
import com.admission.expert.dto.InstituteDTO;
import com.admission.expert.exception.InvalidInputException;
import com.admission.expert.service.InstituteService;
import com.admission.expert.util.SecurityUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Institute operations")
public class InstituteController {

	private final Logger LOGGER = LoggerFactory.getLogger(InstituteController.class);

	@Autowired
	private InstituteService instituteService;

	@Autowired
	private SecurityUtil secUtils;

	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@PostMapping(value = "/api/institute")
	@Transactional
	@ApiOperation(value = "Add New Institute.", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created institute"),
			@ApiResponse(code = 404, message = "Resource not found") })
	public ResponseEntity<HttpStatus> addInstitute(@RequestBody InstituteDTO instituteDTO,
			Authentication authentication) {

		LOGGER.debug("Adding a new Institute entry with information: {}", instituteDTO);
		User loggedInUser = secUtils.getLoggeedInUser(authentication);
		LOGGER.info("addInstitute called {}", instituteDTO.toString());
		if (!this.mandatoryValuesPresent(instituteDTO)) {
			throw new InvalidInputException("Mandatory fields are missing");
		}
		instituteService.add(instituteDTO, loggedInUser);
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);

	}

	private boolean mandatoryValuesPresent(InstituteDTO instituteDTO) {
		if (StringUtils.isEmpty(instituteDTO.getName()) || instituteDTO.getHostelFees() == null
				|| StringUtils.isEmpty(instituteDTO.getHostelFacility()) || instituteDTO.getFees() == null
				|| instituteDTO.getTotalSeats() == null || StringUtils.isEmpty(instituteDTO.getAddress())
				|| instituteDTO.getEstablishmentYear() == null || StringUtils.isEmpty(instituteDTO.getCity())
				|| StringUtils.isEmpty(instituteDTO.getZipCode())) {
			return false;
		}
		return true;
	}

	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@GetMapping(value = "/api/institute")
	@ApiOperation(value = "Find All institute information ", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched the institutes") })
	public ResponseEntity<Page<InstituteDTO>> getAllInstitute(Authentication authentication, Pageable pageable) {
		LOGGER.info("getUser called {}", authentication.getName());
		if (!authentication.isAuthenticated()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<Page<InstituteDTO>>(instituteService.findAll(pageable), HttpStatus.OK);
	}
}
