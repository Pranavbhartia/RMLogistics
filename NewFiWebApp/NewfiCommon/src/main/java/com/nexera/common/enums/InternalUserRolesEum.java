package com.nexera.common.enums;

public enum InternalUserRolesEum {
	
	    LM ("Loan Advisor",1),
	    SM ("Sales Manager",2),
	    PC ("Processor",3);

	    private final String name;       
	    private final int roleId;
	    
	    private InternalUserRolesEum(String s,int id) {
	        name = s;
	        roleId=id;
	    }
	    
	    public int getRoleId() {
	        return roleId;
        }

	    public boolean equalsName(String otherName){
	        return (otherName == null)? false:name.equals(otherName);
	    }

	    public String getName(){
	       return name;
	    }

	
}
