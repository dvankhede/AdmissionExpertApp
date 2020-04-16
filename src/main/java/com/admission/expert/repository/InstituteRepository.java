package com.admission.expert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.admission.expert.domain.Institute;

public interface InstituteRepository extends JpaRepository<Institute, Long> {

	@Query("select i from Institute i where i.status='A'")
	public List<Institute> findAllActive();
}
