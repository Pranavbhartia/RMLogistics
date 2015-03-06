package com.nexera.common.enums;

public enum MessageTypeEnum {
	
	    NOTE ("NOTE"),
	    EMAIL ("EMAIL"),
	    ALERT ("ALERT");
	    

	    private final String name;       

	    private MessageTypeEnum(String s) {
	        name = s;
	    }

	    public boolean equalsName(String otherName){
	        return (otherName == null)? false:name.equals(otherName);
	    }

	    public String toString(){
	       return name;
	    }

	
}
