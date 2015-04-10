package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.TemplateDao;
import com.nexera.common.entity.Template;
import com.nexera.common.vo.TemplateVO;
import com.nexera.core.service.TemplateService;

@Component
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	private TemplateDao templateDao;

	@Transactional
	@Override
	public void saveOrUpdate(List<TemplateVO> templateVOs) {
		List<Template> templates = new ArrayList<Template>();
		for (TemplateVO templateVO : templateVOs) {
			templates.add(Template.convertVOToEntity(templateVO));
		}
		templateDao.saveOrUpdate(templates);
	}

	@Transactional(readOnly = true)
	@Override
	public List<TemplateVO> getListsTemplate() {
		List<Template> templates = templateDao.loadAll(Template.class);
		List<TemplateVO> templateVOs = new ArrayList<TemplateVO>();
		for (Template template : templates) {
			templateVOs.add(Template.convertEntityToVO(template));
		}
		return templateVOs;
	}

	@Transactional
	@Override
	public Template getTemplateByKey(String key) {
		return templateDao.getTemplateByKey(key);
	}

}
