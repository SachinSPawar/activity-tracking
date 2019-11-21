package com.userexpirior.activitytracking.to;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class EmployeeActivitiesTO {

	@JsonProperty("employee_id")
	@SerializedName("employee_id")
	private Long employeeId;

	private List<ActivityTO> activities;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public List<ActivityTO> getActivities() {
		return activities;
	}

	public void setActivities(List<ActivityTO> activities) {
		this.activities = activities;
	}

}
