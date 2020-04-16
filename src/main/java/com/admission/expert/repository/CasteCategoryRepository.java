package com.admission.expert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admission.expert.domain.CasteCategory;

public interface CasteCategoryRepository extends JpaRepository<CasteCategory, Long> {

}
