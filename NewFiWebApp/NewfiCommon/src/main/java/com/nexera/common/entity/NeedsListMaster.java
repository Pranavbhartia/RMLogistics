package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the needslistmaster database table.
 * 
 */
@Entity
@NamedQuery(name="NeedsListMaster.findAll", query="SELECT n FROM NeedsListMaster n")
public class NeedsListMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String description;
	private String label;
	private Date modifiedDate;
	private String needCategory;
	private List<LoanNeedsList> loanneedslists;
	private User user;

	public NeedsListMaster() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	@Column(name="modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	@Column(name="need_category")
	public String getNeedCategory() {
		return this.needCategory;
	}

	public void setNeedCategory(String needCategory) {
		this.needCategory = needCategory;
	}


	//bi-directional many-to-one association to LoanNeedsList
	@OneToMany(mappedBy="needslistmaster")
	public List<LoanNeedsList> getLoanneedslists() {
		return this.loanneedslists;
	}

	public void setLoanneedslists(List<LoanNeedsList> loanneedslists) {
		this.loanneedslists = loanneedslists;
	}

	public LoanNeedsList addLoanneedslist(LoanNeedsList loanneedslist) {
		getLoanneedslists().add(loanneedslist);
		loanneedslist.setNeedslistmaster(this);

		return loanneedslist;
	}

	public LoanNeedsList removeLoanneedslist(LoanNeedsList loanneedslist) {
		getLoanneedslists().remove(loanneedslist);
		loanneedslist.setNeedslistmaster(null);

		return loanneedslist;
	}


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="modified_by")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}