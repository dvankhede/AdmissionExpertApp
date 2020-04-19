package com.admission.expert.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.admission.expert.domain.Institute;
import com.admission.expert.domain.User;
import com.admission.expert.dto.InstituteDTO;

public interface InstituteService {

	public Institute add(InstituteDTO instituteDTO, User loggedInUser);

	public Institute update(InstituteDTO instituteDTO, Long instituteId, User loggedInUser);

	public Page<InstituteDTO> findAll(Pageable pageable);

	public Institute findById(Long instituteId);

	public void delete(Long instituteId);

}
