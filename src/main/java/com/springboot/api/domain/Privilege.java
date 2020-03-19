package com.springboot.api.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "privilege")
public class Privilege {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "privilege", cascade = CascadeType.ALL)
	private List<RolePrivilegeComponent> rolesPrivilegesComponents;

	@ManyToMany(mappedBy = "privileges")
	private List<FeatureComponent> featureComponents;

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

	public List<RolePrivilegeComponent> getRolesPrivilegesComponents() {
		return rolesPrivilegesComponents;
	}

	public void setRolesPrivilegesComponents(List<RolePrivilegeComponent> rolesPrivilegesComponents) {
		this.rolesPrivilegesComponents = rolesPrivilegesComponents;
	}

	public List<FeatureComponent> getFeatureComponents() {
		return featureComponents;
	}

	public void setFeatureComponents(List<FeatureComponent> featureComponents) {
		this.featureComponents = featureComponents;
	}

}
