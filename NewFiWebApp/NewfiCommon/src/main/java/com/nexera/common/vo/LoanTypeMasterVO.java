package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.nexera.common.entity.LoanTypeMaster;
import com.nexera.common.enums.LoanTypeMasterEnum;

public class LoanTypeMasterVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String description;
	private String loanTypeCd;
	private Date modifiedDate;
	private List<LoanVO> loans;
	private List<LoanAppFormVO> loanappforms;
	private List<LoanApplicationFeeMasterVO> loanApplicationFeeMasters;
	private List<LoanMilestoneMasterVO> loanmilestonemasters;
	private UserVO modifiedBy;
	private UploadedFilesListVO uploadedFiles;

	public LoanTypeMasterVO() {
	    // TODO Auto-generated constructor stub
    }
	public LoanTypeMasterVO(LoanTypeMasterEnum ref) {
		this.id = ref.getStatusId();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLoanTypeCd() {
		return loanTypeCd;
	}

	public void setLoanTypeCd(String loanTypeCd) {
		this.loanTypeCd = loanTypeCd;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<LoanVO> getLoans() {
		return loans;
	}

	public void setLoans(List<LoanVO> loans) {
		this.loans = loans;
	}

	public List<LoanAppFormVO> getLoanappforms() {
		return loanappforms;
	}

	public void setLoanappforms(List<LoanAppFormVO> loanappforms) {
		this.loanappforms = loanappforms;
	}

	public List<LoanApplicationFeeMasterVO> getLoanApplicationFeeMasters() {
		return loanApplicationFeeMasters;
	}

	public void setLoanApplicationFeeMasters(
	        List<LoanApplicationFeeMasterVO> loanApplicationFeeMasters) {
		this.loanApplicationFeeMasters = loanApplicationFeeMasters;
	}

	public List<LoanMilestoneMasterVO> getLoanmilestonemasters() {
		return loanmilestonemasters;
	}

	public void setLoanmilestonemasters(
	        List<LoanMilestoneMasterVO> loanmilestonemasters) {
		this.loanmilestonemasters = loanmilestonemasters;
	}

	public UserVO getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(UserVO modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public UploadedFilesListVO getUploadedFiles() {
	    return uploadedFiles;
    }
	public void setUploadedFiles(UploadedFilesListVO uploadedFiles) {
	    this.uploadedFiles = uploadedFiles;
    }

	
	public LoanTypeMaster convertToEntity(){
		
		LoanTypeMaster loanTypeMaster=null;
		if(null!= this.getLoanTypeCd() && this.getLoanTypeCd().equalsIgnoreCase("REF")){
			loanTypeMaster = new LoanTypeMaster(LoanTypeMasterEnum.REF);
			
		}else if(null!= this.getLoanTypeCd() && this.getLoanTypeCd().equalsIgnoreCase("PUR")) {
			loanTypeMaster = new LoanTypeMaster(LoanTypeMasterEnum.PUR);
		}else{
			loanTypeMaster = new LoanTypeMaster(LoanTypeMasterEnum.NONE);
		}
		
		loanTypeMaster.setDescription(this.getDescription());
		loanTypeMaster.setLoanTypeCd(this.getLoanTypeCd());
		
		return loanTypeMaster;
	}
}