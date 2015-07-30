/**
 * @author Charu Joshi
 */
package com.nexera.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.nexera.common.entity.NeedsListMaster;

/**
 * @author charu
 *
 */
public class ManagerNeedVo{
	private Integer Id;
	private String desc;
	private String title;
	private String needCategory;
	private int loanId;
	private int needType;
	private Boolean isChecked;
	private String lqbDocumentType;
	
	public ManagerNeedVo(NeedsListMaster needsList){
		this.setDesc(needsList.getDescription());
		this.setTitle(needsList.getLabel());
		this.needCategory=needsList.getNeedCategory();
		this.needType=needsList.getId();
		this.isChecked=false;
		this.lqbDocumentType=needsList.getUploadedTo();
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		Id = id;
	}
	/**
	 * @return the needCategory
	 */
	public String getNeedCategory() {
		return needCategory;
	}
	/**
	 * @param needCategory the needCategory to set
	 */
	public void setNeedCategory(String needCategory) {
		this.needCategory = needCategory;
	}
	/**
	 * @return the loanId
	 */
	public int getLoanId() {
		return loanId;
	}
	/**
	 * @param loanId the loanId to set
	 */
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	
	/**
	 * @return the needType
	 */
	public int getNeedType() {
		return needType;
	}
	/**
	 * @param needType the needType to set
	 */
	public void setNeedType(int needType) {
		this.needType = needType;
	}

	/**
	 * @return the isChecked
	 */
	public Boolean getIsChecked() {
		return isChecked;
	}

	/**
	 * @param isChecked the isChecked to set
	 */
	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public String getLqbDocumentType() {
	    return lqbDocumentType;
    }

	public void setLqbDocumentType(String lqbDocumentType) {
	    this.lqbDocumentType = lqbDocumentType;
    }

	

}
