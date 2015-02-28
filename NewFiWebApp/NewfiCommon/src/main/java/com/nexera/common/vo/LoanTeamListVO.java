package com.nexera.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author rohit
 *
 */
public class LoanTeamListVO implements Serializable
{
    private static final long serialVersionUID = 1L;
    private List<LoanTeamVO> leanTeamList;

    public List<LoanTeamVO> getLeanTeamList()
    {
        return leanTeamList;
    }

    public void setLeanTeamList( List<LoanTeamVO> leanTeamList )
    {
        this.leanTeamList = leanTeamList;
    }

}
