package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.Template;



public interface TemplateDao extends GenericDao {
	
	
	public void saveOrUpdate(List<Template> templates);

	public Template getTemplateByKey(String key);

}
