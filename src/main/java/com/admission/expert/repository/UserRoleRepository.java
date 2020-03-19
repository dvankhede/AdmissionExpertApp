package com.admission.expert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admission.expert.domain.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

	public UserRole findByUserIdAndRoleId(Long userId, Long roleId);
	
	public List<UserRole> findByUserIdAndIsRoleActiveIsTrue(Long userId);

}
