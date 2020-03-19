package com.admission.expert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admission.expert.domain.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

}
