package com.admission.expert.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admission.expert.domain.AdmissionCategory;
import com.admission.expert.dto.AdmissionCategoryDTO;
import com.admission.expert.exception.ResourceNotFoundException;
import com.admission.expert.repository.AdmissionCategoryRepository;
import com.admission.expert.service.AdmissionCategoryService;

@Service
public class AdmissionCategoryServiceImpl implements AdmissionCategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdmissionCategoryServiceImpl.class);

	@Autowired
	private AdmissionCategoryRepository admissionCategoryRepository;

	@Transactional
	@Override
	public AdmissionCategory add(AdmissionCategoryDTO admissionCategoryDTO) {
		LOGGER.debug("Adding a new AdmissionCategory  entry with information: {}", admissionCategoryDTO);
		AdmissionCategory admissionCategory = new AdmissionCategory();
		BeanUtils.copyProperties(admissionCategoryDTO, admissionCategory);
		admissionCategory.setStatus('A');
		return admissionCategoryRepository.save(admissionCategory);
	}

	@Transactional(readOnly = true)
	@Override
	public List<AdmissionCategory> findAll() {
		LOGGER.debug("Finding all admissionCategory entries");
		return admissionCategoryRepository.findAllActive();
	}

	@Transactional(readOnly = true)
	@Override
	public AdmissionCategory findById(Long admissionCategoryId) {
		LOGGER.debug("Finding a admissionCategory entry with id: {}", admissionCategoryId);
		Optional<AdmissionCategory> admissionCategory = admissionCategoryRepository.findById(admissionCategoryId);
		if (!admissionCategory.isPresent() && admissionCategory.get().getStatus() == 'A') {
			throw new ResourceNotFoundException("No admissionCategory found with id: " + admissionCategoryId);
		}
		return admissionCategory.get();
	}

	@Override
	public void delete(Long admissionCategoryId) {
		LOGGER.debug("Delete a admissionCategory entry with id: {}", admissionCategoryId);
		Optional<AdmissionCategory> admissionCategoryOptional = admissionCategoryRepository
				.findById(admissionCategoryId);
		if (!admissionCategoryOptional.isPresent() && admissionCategoryOptional.get().getStatus() == 'A') {
			throw new ResourceNotFoundException("No admissionCategory found with id: " + admissionCategoryId);
		}

		AdmissionCategory admissionCategory = admissionCategoryOptional.get();
		admissionCategory.setStatus('D');
		admissionCategoryRepository.save(admissionCategory);
	}

}
