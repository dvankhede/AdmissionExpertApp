package com.admission.expert.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admission.expert.domain.CasteCategory;
import com.admission.expert.domain.City;
import com.admission.expert.domain.State;
import com.admission.expert.exception.ResourceNotFoundException;
import com.admission.expert.repository.CasteCategoryRepository;
import com.admission.expert.repository.CityRepository;
import com.admission.expert.repository.StateRepository;
import com.admission.expert.service.AppMetaDataService;

@Service
public class AppMetaDataServiceImpl implements AppMetaDataService {

	@Autowired
	public CasteCategoryRepository casteCategoryRepository;

	@Autowired
	public StateRepository stateRepository;

	@Autowired
	public CityRepository cityRepository;

	@Override
	public List<CasteCategory> findAllCategories() {
		List<CasteCategory> casteCategories = casteCategoryRepository.findAll();
		return casteCategories;
	}

	@Override
	public CasteCategory findCategoryById(Long categoryId) {
		Optional<CasteCategory> casteCategory = casteCategoryRepository.findById(categoryId);
		if (!casteCategory.isPresent()) {
			throw new ResourceNotFoundException("Category not found of given id");
		}
		return casteCategory.get();
	}

	@Override
	public List<State> findAllState() {
		List<State> states = stateRepository.findAll();
		return states;
	}

	@Override
	public State findStateById(Long stateId) {
		Optional<State> state = stateRepository.findById(stateId);
		if (!state.isPresent()) {
			throw new ResourceNotFoundException("State not found of given id");
		}
		return state.get();
	}

	@Override
	public List<City> findAllCity() {
		List<City> cities = cityRepository.findAll();
		return cities;
	}

	@Override
	public City findCityById(Long cityId) {
		Optional<City> city = cityRepository.findById(cityId);
		if (!city.isPresent()) {
			throw new ResourceNotFoundException("State not found of given id");
		}
		return city.get();
	}

}
