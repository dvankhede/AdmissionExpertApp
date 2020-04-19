package com.admission.expert.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "InstituteHasAdmissionCategory")
public class InstituteHasAdmissionCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "instituteId")
	private Institute institute;

	@ManyToOne
	@JoinColumn(name = "admissionCategoryId")
	private AdmissionCategory admissionCategory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	public AdmissionCategory getAdmissionCategory() {
		return admissionCategory;
	}

	public void setAdmissionCategory(AdmissionCategory admissionCategory) {
		this.admissionCategory = admissionCategory;
	}

}
