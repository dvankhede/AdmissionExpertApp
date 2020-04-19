package com.admission.expert.service;

import java.util.List;

import com.admission.expert.domain.AdmissionCategory;
import com.admission.expert.dto.AdmissionCategoryDTO;

public interface AdmissionCategoryService {

	public AdmissionCategory add(AdmissionCategoryDTO admissionCategoryDTO);

	public List<AdmissionCategory> findAll();

	public AdmissionCategory findById(Long admissionCategoryId);

	public void delete(Long admissionCategoryId);

}
