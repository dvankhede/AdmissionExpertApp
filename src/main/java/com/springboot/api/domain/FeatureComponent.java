package com.springboot.api.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "feature_component")
public class FeatureComponent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "featureComponent", cascade = CascadeType.ALL)
	private List<RolePrivilegeComponent> rolesPrivilegesComponents;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "components_Privileges", joinColumns = @JoinColumn(name = "component_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
	private List<Privilege> privileges;

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

	public List<RolePrivilegeComponent> getRolePrivilegeComponent() {
		return rolesPrivilegesComponents;
	}

	public void setRolePrivilegeComponent(List<RolePrivilegeComponent> rolePrivilegeComponent) {
		this.rolesPrivilegesComponents = rolePrivilegeComponent;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

}
