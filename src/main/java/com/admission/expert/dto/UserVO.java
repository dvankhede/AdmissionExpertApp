package com.admission.expert.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class UserVO {

	@JsonProperty(access = JsonProperty.Access.AUTO)
	@ApiModelProperty(hidden = true)
	private long id;
	private String fullName;
	private Float pcbPercentage;
	private Float neetScore;
	private Integer allIndiaRank;
	private Integer stateRank;
	private String gender;
	@JsonFormat(pattern = "mm-dd-yyyy'T'HH:mm:ss")
	private Date dateOfBirth;
	private String mobile;
	private String email;
	private Long casteCategoryId;
	private Long stateId;
	private Long cityId;

	@JsonProperty(access = JsonProperty.Access.AUTO)
	@ApiModelProperty(hidden = true)
	private Boolean isActive;

	@JsonIgnore
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	public UserVO() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Float getPcbPercentage() {
		return pcbPercentage;
	}

	public void setPcbPercentage(Float pcbPercentage) {
		this.pcbPercentage = pcbPercentage;
	}

	public Float getNeetScore() {
		return neetScore;
	}

	public void setNeetScore(Float neetScore) {
		this.neetScore = neetScore;
	}

	public Integer getAllIndiaRank() {
		return allIndiaRank;
	}

	public void setAllIndiaRank(Integer allIndiaRank) {
		this.allIndiaRank = allIndiaRank;
	}

	public Integer getStateRank() {
		return stateRank;
	}

	public void setStateRank(Integer stateRank) {
		this.stateRank = stateRank;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getCasteCategoryId() {
		return casteCategoryId;
	}

	public void setCasteCategoryId(Long casteCategoryId) {
		this.casteCategoryId = casteCategoryId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

}
