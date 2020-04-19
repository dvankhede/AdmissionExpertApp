package com.admission.expert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admission.expert.domain.InstituteHasCourse;

public interface InstituteHasCourseRepository extends JpaRepository<InstituteHasCourse, Long> {

}
