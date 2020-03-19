package com.springboot.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.api.domain.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

}
