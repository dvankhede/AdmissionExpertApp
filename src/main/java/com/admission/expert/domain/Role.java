package com.admission.expert.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private List<UserRole> usersRoles;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private List<RolePrivilegeComponent> rolesPrivilegesComponents;

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

	public List<UserRole> getUsersRoles() {
		return usersRoles;
	}

	public void setUsersRoles(List<UserRole> usersRoles) {
		this.usersRoles = usersRoles;
	}

	public List<RolePrivilegeComponent> getRolesPrivilegesComponents() {
		return rolesPrivilegesComponents;
	}

	public void setRolesPrivilegesComponents(List<RolePrivilegeComponent> rolesPrivilegesComponents) {
		this.rolesPrivilegesComponents = rolesPrivilegesComponents;
	}


}
