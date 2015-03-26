package com.nexera.workflow.enums;

public enum Milestones {
	App1003(1), AUSUW(2), QC(3), LM_DECISION(4), DISCLOSURE(5), APPRAISAL(6), UW(
	        7), LOAN_CLOSURE(8);

	private final int milestoneID;

	private Milestones(int milestoneId) {
		milestoneID = milestoneId;
	}

	public Milestones getMileStone(int statusValue) {
		Milestones mileStone = Milestones.App1003;
		for (Milestones ms : Milestones.values()) {
			if (ms.milestoneID == statusValue) {
				mileStone = ms;
				break;
			}
		}
		return mileStone;
	}

	public int getMilestoneID() {
		return milestoneID;
	}

}
