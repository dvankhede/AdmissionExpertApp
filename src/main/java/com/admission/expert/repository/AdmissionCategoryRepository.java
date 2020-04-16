package com.admission.expert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.admission.expert.domain.AdmissionCategory;

public interface AdmissionCategoryRepository extends JpaRepository<AdmissionCategory, Long> {

	@Query("select ac from AdmissionCategory ac where ac.status='A'")
	public List<AdmissionCategory> findAllActive();

}
