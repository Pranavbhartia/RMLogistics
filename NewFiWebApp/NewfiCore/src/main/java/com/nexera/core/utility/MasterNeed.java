/**
 * @author Charu Joshi
 */
package com.nexera.core.utility;

/**
 * @author charu
 *
 */
public enum MasterNeed {
	DSETTLEAGREE (1),
	CONTACTINFOLANDPROP (2),
	DSEPAGREESETTLEAGREE (3),
	CANCEL12CHECKS (4),
	EARNESTMONEYDEPOSIT (5)
	; // semicolon needed when fields / methods follow


    private final int levelCode;

    MasterNeed(int levelCode) {
        this.levelCode = levelCode;
    }
    
    public int getLevelCode() {
    	return this.levelCode;
    }
    
        
}
