package com.admission.expert.service;

import java.util.List;

import com.admission.expert.domain.Institute;
import com.admission.expert.domain.User;
import com.admission.expert.dto.InstituteDTO;

public interface InstituteService {

	public Institute add(InstituteDTO instituteDTO, User loggedInUser);

	public Institute update(InstituteDTO instituteDTO, Long instituteId, User loggedInUser);

	public List<Institute> findAll();

	public Institute findById(Long instituteId);

	public void delete(Long instituteId);

}
