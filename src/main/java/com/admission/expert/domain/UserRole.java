package com.admission.expert.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "users_roles")
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;  

	@ManyToOne
	@JoinColumn(name = "roleId")
	private Role role;

	private boolean isRoleActive;

	private boolean isDefaultRole;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isRoleActive() {
		return isRoleActive;
	}

	public void setRoleActive(boolean isRoleActive) {
		this.isRoleActive = isRoleActive;
	}

	public boolean isDefaultRole() {
		return isDefaultRole;
	}

	public void setDefaultRole(boolean isDefaultRole) {
		this.isDefaultRole = isDefaultRole;
	}


}
