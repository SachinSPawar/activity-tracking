package com.userexpirior.activitytracking.to;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActivityOccurenceTO {

	@JsonProperty("activity_name")
	private String activityName;

	private Long occurrences;

	public ActivityOccurenceTO(String activityName, Long occurrences) {
		super();
		this.activityName = activityName;
		this.occurrences = occurrences;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Long getOccurrences() {
		return occurrences;
	}

	public void setOccurrences(Long occurrences) {
		this.occurrences = occurrences;
	}

}
