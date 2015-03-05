package com.nexera.common.enums;

public enum InternalUserRolesEum {
	
	    LM ("Loan Manager"),
	    SM ("Sales Manager"),
	    PC ("Processor");

	    private final String name;       

	    private InternalUserRolesEum(String s) {
	        name = s;
	    }

	    public boolean equalsName(String otherName){
	        return (otherName == null)? false:name.equals(otherName);
	    }

	    public String getName(){
	       return name;
	    }

	
}
