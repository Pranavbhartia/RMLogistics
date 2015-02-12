package com.nexeracommon.newfi.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.nexeracommon.newfi.model.UserModel;

@Configuration
@ComponentScan("com.nexeracommon.newfi.dao")
@PropertySource("classpath:core-application.properties")
@EnableTransactionManagement
public class HibernateConfiguration {

	static{
		System.out.println("Reading hibernate configurations ");
	}
	
	@Value("${jdbc.driverClassName}")
	private String driverClassName;
	@Value("${jdbc.url}")
	private String url;
	@Value("${jdbc.username}")
	private String userName;
	@Value("${jdbc.password}")
	private String password;
	
	
	@Bean
	public DataSource getDataSource(){
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
			return dataSource;
	}
	
	
	@Bean
	public HibernateTransactionManager hibernateTransactionManager(){
		return new HibernateTransactionManager(sessionFactoryBean().getObject());
	} 
	/*
	@Bean
	public SessionFactory sessionFactory(){
		return new LocalSessionFactoryBuilder(getDataSource())
		   .addAnnotatedClasses(UserModel.class)
		   .buildSessionFactory();
	}*/
	
	@Bean
	public LocalSessionFactoryBean sessionFactoryBean() {
		
		Properties props = new Properties();
		props.put("hibernate.dialect", MySQL5Dialect.class.getName());
		props.put("hibernate.format_sql", "true");
		props.put("hibernate.show_sql", "true");
		

		LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
		bean.setAnnotatedClasses(new Class[] { 	
				UserModel.class
				});
		bean.setHibernateProperties(props);
		bean.setDataSource(this.getDataSource());

		return bean;
	}
	
	@Bean
	public HibernateTemplate hibernateTemplate() {
		return new HibernateTemplate(sessionFactoryBean().getObject());
	}
	
	@Bean
	 public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	      return new PropertySourcesPlaceholderConfigurer();
	 }
	
}
