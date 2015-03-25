package com.nexera.workflow.enums;

public enum Milestones {
	App1003("1003"), AUSUW("AUSUW"), QC("QC"), LM_DECISION("LM_DECISION"), DISCLOSURE(
	        "DISCLOSURE"), APPRAISAL("APPRAISAL"), UW("UW"), LOAN_CLOSURE(
	        "LOAN_CLOSURE");

	private final String statusValue;

	private Milestones(String s) {
		statusValue = s;
	}

	public Milestones getMileStone(String strStatus) {
		Milestones mileStone = Milestones.App1003;
		for (Milestones ms : Milestones.values()) {
			if (strStatus != null && ms.statusValue.equals(strStatus)) {
				mileStone = ms;
				break;
			}
		}
		return mileStone;
	}

	public String getStatusValue() {
		return statusValue;
	}

}
