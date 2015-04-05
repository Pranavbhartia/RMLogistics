package com.nexera.common.enums;



import java.io.Serializable;

import com.nexera.common.enums.helperinterface.PersistentEnum;

public enum ActiveInternalEnum implements PersistentEnum {

	ACTIVE (1),
    INACTIVE (0),
    DELETED (-1);
    

        
    private int userStatusId;

    private ActiveInternalEnum(int userStatusId) {
        
        this.userStatusId = userStatusId;
    }

    
    @Override
    	public int getId()
    {
    	return userStatusId;
    }

	
}

