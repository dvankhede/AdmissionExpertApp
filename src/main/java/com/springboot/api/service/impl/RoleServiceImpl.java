package com.springboot.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.api.domain.Role;
import com.springboot.api.domain.User;
import com.springboot.api.domain.UserRole;
import com.springboot.api.repository.RoleRepository;
import com.springboot.api.repository.UserRepository;
import com.springboot.api.repository.UserRoleRepository;
import com.springboot.api.service.RoleService;
import com.springboot.api.vo.AssignRoleToUserDTO;
import com.springboot.api.vo.RolesByUserDTO;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public User assignRoleToUser(Long userId, AssignRoleToUserDTO assignRoleToUserDTO) {

		Optional<User> user = userRepository.findById(userId);
		for (Long roleId : assignRoleToUserDTO.getAssignRoleIds()) {
			Role role = roleRepository.getOne(roleId);
			UserRole existingUserRole = userRoleRepository.findByUserIdAndRoleId(userId, roleId);

			if (existingUserRole != null && existingUserRole.isRoleActive() == false) {
				existingUserRole.setRoleActive(true);
				userRoleRepository.save(existingUserRole);
			} else {
				UserRole existingUserRoleWithNoOperation = userRoleRepository.findByUserIdAndRoleId(userId, roleId);
				if (existingUserRole != null && existingUserRole.isRoleActive() == true) {
					userRoleRepository.save(existingUserRoleWithNoOperation);
				} else {
					UserRole userRole = new UserRole();
					userRole.setUser(user.get());
					userRole.setRole(role);
					userRole.setRoleActive(true);
					userRoleRepository.save(userRole);
				}
			}
		}
		revokeRolesOfUser(userId, assignRoleToUserDTO);
		return user.get();

	}

	private void revokeRolesOfUser(Long userId, AssignRoleToUserDTO assignRoleToUserDTO) {
		for (Long roleId : assignRoleToUserDTO.getRevokeRoleIds()) {
			UserRole existingUserRole = userRoleRepository.findByUserIdAndRoleId(userId, roleId);
			if (existingUserRole != null && existingUserRole.isRoleActive() == true) {
				existingUserRole.setRoleActive(false);
				userRoleRepository.save(existingUserRole);
			}

		}
	}

	@Override
	public List<RolesByUserDTO> getRolesByUser(Long userId) {
		List<UserRole> usersRoles = userRoleRepository.findByUserIdAndIsRoleActiveIsTrue(userId);
		List<RolesByUserDTO> rolesByUserDTOs=new ArrayList<RolesByUserDTO>();
		for (UserRole userRole : usersRoles) {
			RolesByUserDTO rolesByUserDTO =new RolesByUserDTO();
			rolesByUserDTO.setUserRoleId(userRole.getId());
			rolesByUserDTO.setRoleId(userRole.getRole().getId());
			rolesByUserDTO.setRoleName(userRole.getRole().getName());
			rolesByUserDTO.setDefaultRole(userRole.isDefaultRole());
			rolesByUserDTOs.add(rolesByUserDTO);
		}
		return rolesByUserDTOs;
	}

}
