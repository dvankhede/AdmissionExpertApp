package com.admission.expert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.admission.expert.domain.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

	@Query("select c from Course c where c.id in (:courseIds) ")
	public List<Course> findCoursesByIds(List<Long> courseIds);

}
