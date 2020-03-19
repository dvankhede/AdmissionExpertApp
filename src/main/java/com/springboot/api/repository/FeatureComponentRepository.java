package com.springboot.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.api.domain.FeatureComponent;

public interface FeatureComponentRepository extends JpaRepository<FeatureComponent, Long> {

}
