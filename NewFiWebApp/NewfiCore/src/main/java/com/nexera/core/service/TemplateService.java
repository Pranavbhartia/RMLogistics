package com.nexera.core.service;

import java.util.List;

import com.nexera.common.entity.Template;
import com.nexera.common.vo.TemplateVO;

public interface TemplateService {

	public void saveOrUpdate(List<TemplateVO> templateVOs);
	
	
	public List<TemplateVO> getListsTemplate();

	Template getTemplateByKey(String key);
}

