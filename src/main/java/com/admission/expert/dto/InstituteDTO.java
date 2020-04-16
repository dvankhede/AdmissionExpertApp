package com.admission.expert.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class InstituteDTO {

	@JsonProperty(access = JsonProperty.Access.AUTO)
	@ApiModelProperty(hidden = true)
	private Long id;

	private String name;

	private String description;

	private char hostelFacility;

	private Double fees;

	private Double hostelFees;

	private Integer totalSeats;

	private String address;

	private String city;

	private String zipCode;

	private Date establishmentYear;

	private List<Long> courseIds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public char getHostelFacility() {
		return hostelFacility;
	}

	public void setHostelFacility(char hostelFacility) {
		this.hostelFacility = hostelFacility;
	}

	public Double getFees() {
		return fees;
	}

	public void setFees(Double fees) {
		this.fees = fees;
	}

	public Double getHostelFees() {
		return hostelFees;
	}

	public void setHostelFees(Double hostelFees) {
		this.hostelFees = hostelFees;
	}

	public Integer getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(Integer totalSeats) {
		this.totalSeats = totalSeats;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Date getEstablishmentYear() {
		return establishmentYear;
	}

	public void setEstablishmentYear(Date establishmentYear) {
		this.establishmentYear = establishmentYear;
	}

	public List<Long> getCourseIds() {
		return courseIds;
	}

	public void setCourseIds(List<Long> courseIds) {
		this.courseIds = courseIds;
	}

}
