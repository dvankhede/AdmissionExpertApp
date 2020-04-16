package com.admission.expert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admission.expert.domain.State;

public interface StateRepository extends JpaRepository<State, Long> {

}
