package com.nexera.common.enums;

public enum Milestones {
	App1003(1, "1003"), AUSUW(2, "AUSUW"), QC(3, "QC"), LM_DECISION(4,
	        "LM_DECISION"), DISCLOSURE(5, "DISCLOSURE"), APPRAISAL(6,
	        "APPRAISAL"), UW(7, "UW"), LOAN_CLOSURE(8, "LOAN_CLOSURE");

	private int milestoneID;

	private String milestoneKey;

	public String getMilestoneKey() {
		return milestoneKey;
	}

	private Milestones(int milestoneId, String milestoneKey) {
		this.milestoneID = milestoneId;
		this.milestoneKey = milestoneKey;
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