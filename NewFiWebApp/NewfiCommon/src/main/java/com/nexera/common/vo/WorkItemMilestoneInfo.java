package com.nexera.common.vo;

import java.util.List;

import com.nexera.common.enums.Milestones;

public class WorkItemMilestoneInfo {

	public WorkItemMilestoneInfo(Milestones milestone, List<String> workfItems) {
		this.milestone = milestone;

		this.workItems = workfItems;

	}

	private Milestones milestone;

	public Milestones getMilestone() {
		return milestone;
	}

	public void setMilestone(Milestones milestone) {
		this.milestone = milestone;
	}

	public List<String> getWorkItems() {
		return workItems;
	}

	public void setWorkItems(List<String> workfItems) {
		this.workItems = workfItems;
	}

	private List<String> workItems;
}
