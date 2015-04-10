package com.nexera.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.nexera.common.vo.TemplateVO;

@Entity
@Table(name = "template")
public class Template implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String key;
	private String value;
	private String description;
	private Date modifiedDate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(updatable=false)
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	@Column(updatable=false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "modified_date",updatable=false)
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public static TemplateVO convertEntityToVO(Template template) {
		TemplateVO templateVO = new TemplateVO();
		templateVO.setId(template.getId());
		templateVO.setDesc(template.getDescription());
		templateVO.setKey(template.getKey());
		templateVO.setValue(template.getValue());
		return templateVO;
	}

	public static Template convertVOToEntity(TemplateVO templateVO) {
		Template template = new Template();
		template.setId(templateVO.getId());
		template.setDescription(templateVO.getDesc());
		template.setKey(templateVO.getKey());
		template.setValue(templateVO.getValue());
		template.setModifiedDate(new Date());
		return template;
	}
}
