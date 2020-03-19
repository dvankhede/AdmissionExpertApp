package com.admission.expert.service;

import java.util.List;

import com.admission.expert.domain.User;
import com.admission.expert.dto.AssignRoleToUserDTO;
import com.admission.expert.dto.RolesByUserDTO;

public interface RoleService {

	public User assignRoleToUser(Long userId, AssignRoleToUserDTO assignRoleToUserDTO);

	public List<RolesByUserDTO> getRolesByUser(Long userId);

}
