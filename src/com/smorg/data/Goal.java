package com.smorg.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Goal implements Serializable {
    
	private Long goalId;
    
    private String userId;
    private List<Long> eventIds;
    private String title;
    private String description;
    private Double progress;
    private Date startDate;
    private Date deadline;
    
    public Goal(){
    	
    }
    
	public Long getGoalId() {
		return goalId;
	}
	public void setGoalId(Long goalId) {
		this.goalId = goalId;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public List<Long> getEventIds() {
		return eventIds;
	}
	public void setEventIds(List<Long> eventIds) {
		this.eventIds = eventIds;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getProgress() {
		return progress;
	}
	public void setProgress(Double progress) {
		this.progress = progress;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	@Override
	public String toString() {
		return "{Goal: id = " + getGoalId()
				+ ", userid = " + getUserId()
				+ "}";
	}
}
