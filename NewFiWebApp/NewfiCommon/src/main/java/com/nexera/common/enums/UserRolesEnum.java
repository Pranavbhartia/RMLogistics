package com.nexera.common.enums;

public enum UserRolesEnum {
	
	    CUSTOMER ("CUSTOMER"),
	    REALTOR ("REALTOR"),
	    INTERNAL ("INTERNAL"),
	    SYSTEM ("SYSTEM");

	    private final String name;       

	    private UserRolesEnum(String s) {
	        name = s;
	    }

	    public boolean equalsName(String otherName){
	        return (otherName == null)? false:name.equals(otherName);
	    }

	    public String toString(){
	       return name;
	    }

	
}
