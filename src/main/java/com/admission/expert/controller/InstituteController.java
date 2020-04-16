package com.admission.expert.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.admission.expert.service.InstituteService;

import io.swagger.annotations.Api;

@RestController
@Api(value = "Institute operations")
public class InstituteController {

	private final Logger logger = LoggerFactory.getLogger(InstituteController.class);

	@Autowired
	private InstituteService instituteService;

}
