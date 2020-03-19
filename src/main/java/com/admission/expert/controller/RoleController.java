package com.admission.expert.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.admission.expert.domain.User;
import com.admission.expert.dto.AssignRoleToUserDTO;
import com.admission.expert.dto.RolesByUserDTO;
import com.admission.expert.service.RoleService;

import io.swagger.annotations.Api;

@RestController
@Api(value = "Role operations")
public class RoleController {

	private final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleService roleService;

	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	@RequestMapping(value = "api/assignRolesToUser/{userId}", method = RequestMethod.PUT)
	@ResponseBody
	public List<RolesByUserDTO> assignRolesToUser(@PathVariable("userId") Long userId,
			@Valid @RequestBody AssignRoleToUserDTO assignRoleToUserDTO) {
		User user = roleService.assignRoleToUser(userId, assignRoleToUserDTO);
		return roleService.getRolesByUser(user.getId());
	}

}
