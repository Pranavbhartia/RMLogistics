package com.nexera.common.enums;

public enum UserRolesEum {
	
	    CUSTOMER ("CUST"),
	    REALTOR ("REALTOR"),
	    INTERNALUSER ("INTERNAL"),
	    SYSTEMUSER ("SYSTEM");

	    private final String name;       

	    private UserRolesEum(String s) {
	        name = s;
	    }

	    public boolean equalsName(String otherName){
	        return (otherName == null)? false:name.equals(otherName);
	    }

	    public String toString(){
	       return name;
	    }

	
}
