package com.admission.expert.service;

import java.util.List;

import com.admission.expert.domain.CasteCategory;
import com.admission.expert.domain.City;
import com.admission.expert.domain.State;

public interface AppMetaDataService {

	public List<CasteCategory> findAllCategories();
	
	public CasteCategory findCategoryById(Long categoryId);

	public List<State> findAllState();

	public State findStateById(Long stateId);

	public List<City> findAllCity();

	public City findCityById(Long cityId);

}
