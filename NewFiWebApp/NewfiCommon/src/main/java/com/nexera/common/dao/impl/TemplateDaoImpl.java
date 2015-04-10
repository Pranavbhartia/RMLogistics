package com.nexera.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.nexera.common.dao.TemplateDao;
import com.nexera.common.entity.Template;

@Component
public class TemplateDaoImpl extends GenericDaoImpl implements TemplateDao {

	@Override
	public void saveOrUpdate(List<Template> templates) {
		for (Template template : templates) {
			this.saveOrUpdate(template);
		}

	}

}
