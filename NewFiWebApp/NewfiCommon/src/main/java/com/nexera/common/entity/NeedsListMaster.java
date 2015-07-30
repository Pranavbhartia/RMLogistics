package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the needslistmaster database table.
 * 
 */
@Entity
@Table(name = "needslistmaster")
@NamedQuery(name = "NeedsListMaster.findAll", query = "SELECT n FROM NeedsListMaster n")
public class NeedsListMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String description;
	private String shortDescription;
	private String label;
	private Date modifiedDate;
	private String needCategory;
	private Boolean isCustom;
	private List<LoanNeedsList> loanNeedsList;
	private User modifiedBy;
	private String uploadedTo;

	public NeedsListMaster() {
	}
	@Transient
	public static NeedsListMaster getCustomNeed(String label,String category,String desc,User user,String lqbDocumentType){
		NeedsListMaster need=new NeedsListMaster();
		need.setDescription(desc);
		need.setIsCustom(true);
		need.setLabel(label);
		need.setNeedCategory(category);
		need.setModifiedDate(new Date());
		need.setModifiedBy(user);
		//to be chnaged
		need.setUploadedTo(lqbDocumentType);
		return need;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "need_category")
	public String getNeedCategory() {
		return this.needCategory;
	}

	public void setNeedCategory(String needCategory) {
		this.needCategory = needCategory;
	}
	@Column(name = "short_description")
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	// bi-directional many-to-one association to LoanNeedsList
	@OneToMany(mappedBy = "needsListMaster")
	public List<LoanNeedsList> getLoanNeedsList() {
		return this.loanNeedsList;
	}

	public void setLoanNeedsList(List<LoanNeedsList> loanNeedsList) {
		this.loanNeedsList = loanNeedsList;
	}

	public LoanNeedsList addLoanNeedsList(LoanNeedsList loanneedslist) {
		getLoanNeedsList().add(loanneedslist);
		loanneedslist.setNeedsListMaster(this);

		return loanneedslist;
	}

	public LoanNeedsList removeLoanNeedsList(LoanNeedsList loanneedslist) {
		getLoanNeedsList().remove(loanneedslist);
		loanneedslist.setNeedsListMaster(null);

		return loanneedslist;
	}

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")
	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "is_custom", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsCustom() {
		return isCustom;
	}

	public void setIsCustom(Boolean isCustom) {
		this.isCustom = isCustom;
	}
	@Column(name = "uploaded_to")
	public String getUploadedTo() {
	    return uploadedTo;
    }
	public void setUploadedTo(String uploadedTo) {
	    this.uploadedTo = uploadedTo;
    }

}