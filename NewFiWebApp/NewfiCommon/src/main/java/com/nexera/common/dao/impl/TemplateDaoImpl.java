package com.nexera.common.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
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

	@Override
	public Template getTemplateByKey(String key) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Template.class);
		criteria.add(Restrictions.eq("key", key));
		Template template = (Template) criteria.uniqueResult();
		return template;

	}

}
