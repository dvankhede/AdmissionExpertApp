package com.springboot.api.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "roles_privileges_components")
public class RolePrivilegeComponent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "roleId")
	private Role role;

	@ManyToOne
	@JoinColumn(name = "privilegeId")
	private Privilege privilege;

	@ManyToOne
	@JoinColumn(name = "featureComponentId")
	private FeatureComponent featureComponent;

	private boolean isPrivilegeActive;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	public FeatureComponent getFeatureComponent() {
		return featureComponent;
	}

	public void setFeatureComponent(FeatureComponent featureComponent) {
		this.featureComponent = featureComponent;
	}

	public boolean isPrivilegeActive() {
		return isPrivilegeActive;
	}

	public void setPrivilegeActive(boolean isPrivilegeActive) {
		this.isPrivilegeActive = isPrivilegeActive;
	}

}
