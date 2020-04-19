package com.admission.expert.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admission.expert.domain.Course;
import com.admission.expert.domain.Institute;
import com.admission.expert.domain.InstituteHasCourse;
import com.admission.expert.domain.User;
import com.admission.expert.dto.InstituteDTO;
import com.admission.expert.exception.ResourceNotFoundException;
import com.admission.expert.repository.CourseRepository;
import com.admission.expert.repository.InstituteHasCourseRepository;
import com.admission.expert.repository.InstituteRepository;
import com.admission.expert.service.InstituteService;

@Service
public class InstituteServiceImpl implements InstituteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InstituteServiceImpl.class);

	@Autowired
	private InstituteRepository instituteRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private InstituteHasCourseRepository instituteHasCourseRepository;

	@Transactional
	@Override
	public Institute add(InstituteDTO instituteDTO, User loggedInUser) {
		LOGGER.debug("Adding a new institute  entry with information: {}", instituteDTO);
		Institute institute = new Institute();
		BeanUtils.copyProperties(instituteDTO, institute);
		institute.setStatus('A');
		setCoursesToInstitute(instituteDTO, institute);
		institute.setCreatedOn(new Date());
		institute.setCreatedBy(loggedInUser.getId());
		institute.setUpdatedBy(loggedInUser.getId());
		institute.setUpdatedOn(new Date());
		return instituteRepository.save(institute);
	}

	private void setCoursesToInstitute(InstituteDTO instituteDTO, Institute institute) {
		List<Course> courses = courseRepository.findCoursesByIds(instituteDTO.getCourseIds());
		for (Course course : courses) {
			InstituteHasCourse instituteHasCourse = new InstituteHasCourse();
			instituteHasCourse.setInstitute(institute);
			instituteHasCourse.setCourse(course);
			instituteHasCourseRepository.save(instituteHasCourse);

		}
	}

	@Transactional
	@Override
	public Institute update(InstituteDTO instituteDTO, Long instituteId, User loggedInUser) {

		LOGGER.debug("Adding a new Institute  entry with information: {}", instituteDTO);
		Institute institute = new Institute();
		BeanUtils.copyProperties(instituteDTO, institute);
		setCoursesToInstitute(instituteDTO, institute);
		setCoursesToInstitute(instituteDTO, institute);
		institute.setUpdatedBy(loggedInUser.getId());
		institute.setUpdatedOn(new Date());
		return instituteRepository.save(institute);

	}

	@Transactional(readOnly = true)
	@Override
	public Page<InstituteDTO> findAll(Pageable pageable) {
		Page<Institute> institutes = instituteRepository.findAllActive(pageable);
		List<InstituteDTO> instituteDTOs = new ArrayList<InstituteDTO>();
		for (Institute institute : institutes.getContent()) {
			InstituteDTO instituteDTO = new InstituteDTO();
			BeanUtils.copyProperties(institute, instituteDTO);
			instituteDTOs.add(instituteDTO);
		}
		return new PageImpl<>(instituteDTOs, pageable, institutes.getTotalElements());

	}

	@Transactional(readOnly = true)
	@Override
	public Institute findById(Long instituteId) {
		LOGGER.debug("Finding a Institute entry with id: {}", instituteId);
		Optional<Institute> institute = instituteRepository.findById(instituteId);
		if (!institute.isPresent() && institute.get().getStatus() == 'A') {
			throw new ResourceNotFoundException("No institute found with id: " + instituteId);
		}
		return institute.get();
	}

	@Override
	public void delete(Long instituteId) {
		LOGGER.debug("Delete a Institute entry with id: {}", instituteId);
		Optional<Institute> instituteOptional = instituteRepository.findById(instituteId);
		if (!instituteOptional.isPresent() && instituteOptional.get().getStatus() == 'A') {
			throw new ResourceNotFoundException("No Institute found with id: " + instituteId);
		}

		Institute institute = instituteOptional.get();
		institute.setStatus('D');
		instituteRepository.save(institute);
	}

}
