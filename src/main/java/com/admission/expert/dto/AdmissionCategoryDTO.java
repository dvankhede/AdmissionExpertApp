package com.admission.expert.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class AdmissionCategoryDTO {

	@JsonProperty(access = JsonProperty.Access.AUTO)
	@ApiModelProperty(hidden = true)
	private Long id;

	private String name;

	private String description;

	private String iconName;

	private String colorCode;

	@JsonProperty(access = JsonProperty.Access.AUTO)
	@ApiModelProperty(hidden = true)
	private char status;

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

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

}
