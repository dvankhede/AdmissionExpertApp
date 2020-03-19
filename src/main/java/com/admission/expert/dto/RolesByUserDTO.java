package com.admission.expert.dto;

import javax.persistence.Id;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RolesByUserDTO {

	@JsonIgnore
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Id
	private long id;

	private Long userRoleId;

	private Long roleId;

	private String roleName;

	private boolean defaultRole;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isDefaultRole() {
		return defaultRole;
	}

	public void setDefaultRole(boolean defaultRole) {
		this.defaultRole = defaultRole;
	}

}
