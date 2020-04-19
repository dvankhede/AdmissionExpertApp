package com.admission.expert.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name="user")
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = "email") })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String fullName;
	private Float pcbPercentage;
	private Float neetScore;
	private Integer allIndiaRank;
	private Integer stateRank;
	private String gender;
	private Date dateOfBirth;
    private String mobile;
    private String email;
	private Boolean isActive;
    private String password;
	private String resetToken;
	private Date createdOn;
	private Long createdBy;
	private Date updatedOn;
	private Long updatedBy;

	@ManyToOne
	@JoinColumn(name = "casteCategoryId")
	private CasteCategory casteCategory;

	@ManyToOne
	@JoinColumn(name = "stateId")
	private State state;

	@ManyToOne
	@JoinColumn(name = "cityId")
	private City city;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserRole> usersRoles;
    
    public User() {
  		super();
  	}
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public List<UserRole> getUsersRoles() {
		return usersRoles;
	}

	public void setUsersRoles(List<UserRole> usersRoles) {
		this.usersRoles = usersRoles;
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

	public CasteCategory getCasteCategory() {
		return casteCategory;
	}

	public void setCasteCategory(CasteCategory casteCategory) {
		this.casteCategory = casteCategory;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public CasteCategory getCastCategory() {
		return casteCategory;
	}

	public void setCastCategory(CasteCategory casteCategory) {
		this.casteCategory = casteCategory;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

}
