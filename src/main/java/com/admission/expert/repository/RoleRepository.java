package com.admission.expert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admission.expert.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
