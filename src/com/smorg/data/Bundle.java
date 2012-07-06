package com.smorg.data;

import java.io.Serializable;

public class Bundle implements Serializable {

	private Long goalId;
	private String eventId;


	public Long getGoalId() {
		return goalId;
	}
	public void setGoalId(Long goalId) {
		this.goalId = goalId;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}



}