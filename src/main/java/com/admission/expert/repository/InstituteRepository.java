package com.admission.expert.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.admission.expert.domain.Institute;

public interface InstituteRepository extends JpaRepository<Institute, Long> {

	@Query("select i from Institute i where i.status='A'")
	public Page<Institute> findAllActive(Pageable pageable);

}
