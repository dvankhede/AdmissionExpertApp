package com.springboot.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.api.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
