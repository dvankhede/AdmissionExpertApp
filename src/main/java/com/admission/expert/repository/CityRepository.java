package com.admission.expert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admission.expert.domain.City;

public interface CityRepository extends JpaRepository<City, Long> {

}
