package com.nexera.common.enums;

public enum UserRolesEnum {
	
	    CUSTOMER ("CUSTOMER",1),
	    REALTOR ("REALTOR",2),
	    INTERNAL ("INTERNAL",3),
	    SYSTEM ("SYSTEM",4),
	    SM ("SM",2),
	    PC ("PC",3),
	    LOANMANAGER ("LM",1);

	    private final String name;       
	    private final int roleId;

	    private UserRolesEnum(String s,int roleId) {
	        this.name = s;
	        this.roleId = roleId;
	    }

	    public boolean equalsName(String otherName){
	        return (otherName == null)? false:name.equals(otherName);
	    }
	    
	    public int getRoleId(){
	    	return roleId;
	    }

	    public String toString(){
	       return name;
	    }

	
}
