package com.nexera.workflow.enums;

public enum Milestones {
	App1003(1), AUSUW(2), QC(3), LM_DECISION(4), DISCLOSURE(5), APPRAISAL(6), UW(
	        7), LOAN_CLOSURE(8);

	private int milestoneID;

	private String milestoneKey;

	public String getMilestoneKey() {
		return milestoneKey;
	}

	private Milestones(int milestoneId) {
		milestoneID = milestoneId;
	}

	public Milestones getMileStone(int inputID) {
		Milestones mileStone = Milestones.App1003;
		for (Milestones ms : Milestones.values()) {
			if (ms.milestoneID == inputID) {
				mileStone = ms;
				break;
			}
		}
		return mileStone;
	}

	public Milestones getMileStone(String milestoneKey) {
		Milestones mileStone = Milestones.App1003;
		for (Milestones ms : Milestones.values()) {
			if (milestoneKey != null
			        && milestoneKey.equalsIgnoreCase(ms.getMilestoneKey())) {
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
