package com.springboot.api.service;

import java.util.List;

import com.springboot.api.domain.User;
import com.springboot.api.vo.AssignRoleToUserDTO;
import com.springboot.api.vo.RolesByUserDTO;

public interface RoleService {

	public User assignRoleToUser(Long userId, AssignRoleToUserDTO assignRoleToUserDTO);

	public List<RolesByUserDTO> getRolesByUser(Long userId);

}
