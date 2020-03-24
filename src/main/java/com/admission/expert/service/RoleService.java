package com.admission.expert.service;

import java.util.List;
import java.util.Optional;

import com.admission.expert.domain.Role;
import com.admission.expert.domain.User;
import com.admission.expert.dto.AssignRoleToUserDTO;
import com.admission.expert.dto.RolesByUserDTO;
import com.admission.expert.dto.UserRolesDto;

public interface RoleService {

	public User assignRoleToUser(Long userId, AssignRoleToUserDTO assignRoleToUserDTO);

	public List<RolesByUserDTO> getRolesByUser(Long userId);
	
	public List<Role> getAllUserRoles();
	
	public Optional<Role> getRoleByRoleId(Long roleId);

}
