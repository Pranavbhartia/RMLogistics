package com.nexera.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.nexera.common.vo.ZipCodeLookupVO;

/**
 * The persistent class for the zipcodelookup database table.
 * 
 */
@Entity
@Table(name = "zipcodelookup")
@NamedQuery(name = "ZipCodeLookup.findAll", query = "SELECT z FROM ZipCodeLookup z")
public class ZipCodeLookup implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String zipcode;
	private String countyname;
	private String cityname;
	private StateLookup stateLookup;
	
	public ZipCodeLookup() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id")
	public StateLookup getStateLookup() {
	    return stateLookup;
    }
	
	
	public void setStateLookup(StateLookup stateLookup) {
	    this.stateLookup = stateLookup;
    }

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCountyname() {
		return countyname;
	}

	public void setCountyname(String countyname) {
		this.countyname = countyname;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public static ZipCodeLookupVO converToVo(ZipCodeLookup model) {
		if (model == null)
			return null;

		ZipCodeLookupVO vo = new ZipCodeLookupVO();
		vo.setCityName(model.getCityname());
		vo.setCountyName(model.getCountyname());
		vo.setId(model.getId());
		vo.setZipcode(model.getZipcode());

		return vo;

	}
	
	public static List<ZipCodeLookupVO> convertToVo(List<ZipCodeLookup> modelList){
		
		if(modelList==null || modelList.isEmpty())
		return null;
		List<ZipCodeLookupVO> voList=new ArrayList<ZipCodeLookupVO>();
		
		for(ZipCodeLookup model:modelList){
			voList.add(ZipCodeLookup.converToVo(model));
		}
		return voList;
		
	}

}