package com.admission.expert.dto;

import java.util.List;

public class AssignRoleToUserDTO {

	private List<Long> assignRoleIds;

	private List<Long> revokeRoleIds;

	public List<Long> getAssignRoleIds() {
		return assignRoleIds;
	}

	public void setAssignRoleIds(List<Long> assignRoleIds) {
		this.assignRoleIds = assignRoleIds;
	}

	public List<Long> getRevokeRoleIds() {
		return revokeRoleIds;
	}

	public void setRevokeRoleIds(List<Long> revokeRoleIds) {
		this.revokeRoleIds = revokeRoleIds;
	}

}
