package com.admission.expert.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.admission.expert.domain.AdmissionCategory;
import com.admission.expert.domain.CasteCategory;
import com.admission.expert.domain.City;
import com.admission.expert.domain.State;
import com.admission.expert.dto.AdmissionCategoryDTO;
import com.admission.expert.dto.CasteCategoryDTO;
import com.admission.expert.dto.CityDTO;
import com.admission.expert.dto.StateDTO;
import com.admission.expert.service.AdmissionCategoryService;
import com.admission.expert.service.AppMetaDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "MetaData operations")
public class AppMetaDataController {

	private final Logger logger = LoggerFactory.getLogger(AppMetaDataController.class);

	@Autowired
	private AppMetaDataService appMetaDataService;

	@Autowired
	private AdmissionCategoryService admissionCategoryService;

	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@GetMapping(value = "/api/all/category")
	@ApiOperation(value = "Find All categories ", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched the all categories") })
	public ResponseEntity<List<CasteCategoryDTO>> getAllCategory(Authentication authentication) {
		logger.info("getUser called {}", authentication.getName());
		List<CasteCategory> casteCategories = appMetaDataService.findAllCategories();
		List<CasteCategoryDTO> casteCategoryDTOs = new ArrayList<CasteCategoryDTO>();
		for (CasteCategory casteCategory : casteCategories) {
			CasteCategoryDTO casteCategoryDTO = new CasteCategoryDTO();
			BeanUtils.copyProperties(casteCategory, casteCategoryDTO);
			casteCategoryDTOs.add(casteCategoryDTO);
		}
		return new ResponseEntity<List<CasteCategoryDTO>>(casteCategoryDTOs, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@GetMapping(value = "/api/all/state")
	@ApiOperation(value = "Find All state information ", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched the all states") })
	public ResponseEntity<List<StateDTO>> getAllState(Authentication authentication) {
		logger.info("getUser called {}", authentication.getName());
		List<State> states = appMetaDataService.findAllState();
		List<StateDTO> stateDTOs = new ArrayList<StateDTO>();
		for (State state : states) {
			StateDTO stateDTO = new StateDTO();
			BeanUtils.copyProperties(state, stateDTO);
			stateDTOs.add(stateDTO);
		}
		return new ResponseEntity<List<StateDTO>>(stateDTOs, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@GetMapping(value = "/api/all/city")
	@ApiOperation(value = "Find All Cities information ", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched the all cities") })
	public ResponseEntity<List<CityDTO>> getAllCity(Authentication authentication) {
		logger.info("getUser called {}", authentication.getName());
		List<City> cities = appMetaDataService.findAllCity();
		List<CityDTO> cityDTOs = new ArrayList<CityDTO>();
		for (City city : cities) {
			CityDTO cityDTO = new CityDTO();
			BeanUtils.copyProperties(city, cityDTO);
			cityDTOs.add(cityDTO);
		}
		return new ResponseEntity<List<CityDTO>>(cityDTOs, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@PostMapping(value = "/api/add/admissionCategory")
	@Transactional
	@ApiOperation(value = "Add Admission Category.", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created admission Category") })
	public ResponseEntity<HttpStatus> addAdmissionCategory(@RequestBody AdmissionCategoryDTO admissionCategoryDTO) {
		logger.debug("Adding a new admission category entry with information: {}", admissionCategoryDTO);
		admissionCategoryService.add(admissionCategoryDTO);
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@GetMapping(value = "/api/all/admissionCategory")
	@ApiOperation(value = "Find All admissionCategory information ", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched the all admissionCategory") })
	public ResponseEntity<List<AdmissionCategoryDTO>> getAllAdmissionCategory(Authentication authentication) {
		logger.info("getUser called {}", authentication.getName());
		List<AdmissionCategory> admissionCategories = admissionCategoryService.findAll();
		List<AdmissionCategoryDTO> admissionCategoryDTOs = new ArrayList<AdmissionCategoryDTO>();
		for (AdmissionCategory admissionCategory : admissionCategories) {
			AdmissionCategoryDTO admissionCategoryDTO = new AdmissionCategoryDTO();
			BeanUtils.copyProperties(admissionCategory, admissionCategoryDTO);
			admissionCategoryDTOs.add(admissionCategoryDTO);
		}
		return new ResponseEntity<List<AdmissionCategoryDTO>>(admissionCategoryDTOs, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@GetMapping(value = "/api/get/admissionCategory/{admissionCategoryId}")
	@ApiOperation(value = "Find  admissionCategory information ", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched the admissionCategory") })
	public ResponseEntity<AdmissionCategoryDTO> getAdmissionCategoryById(
			@PathVariable("admissionCategoryId") Long admissionCategoryId, Authentication authentication) {
		logger.info("getUser called {}", authentication.getName());
		AdmissionCategory admissionCategory = admissionCategoryService.findById(admissionCategoryId);
		AdmissionCategoryDTO admissionCategoryDTO = new AdmissionCategoryDTO();
		BeanUtils.copyProperties(admissionCategory, admissionCategoryDTO);
		return new ResponseEntity<AdmissionCategoryDTO>(admissionCategoryDTO, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@PutMapping(value = "/api/delete/admissionCategory/{admissionCategoryId}")
	@ApiOperation(value = "Delete  admissionCategory information ", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully delete the admissionCategory") })
	public ResponseEntity<HttpStatus> deleteAdmissionCategoryById(
			@PathVariable("admissionCategoryId") Long admissionCategoryId, Authentication authentication) {
		logger.info("getUser called {}", authentication.getName());
		admissionCategoryService.delete(admissionCategoryId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
