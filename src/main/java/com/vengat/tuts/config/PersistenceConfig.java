package com.vengat.tuts.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = { "com.vengat.tuts.repository" })
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class PersistenceConfig {

	private static final String[] ENTITY_PACKAGES = { "com.vengat.tuts.model" };

//    private static final String PROPERTY_NAME_DB_DRIVER_CLASS = "spring.datasource.dbcp2.driver-class-name";
//    private static final String PROPERTY_NAME_DB_PASSWORD = "spring.datasource.password";
//    private static final String PROPERTY_NAME_DB_URL = "spring.datasource.url";
//    private static final String PROPERTY_NAME_DB_USER = "spring.datasource.username";
//    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "spring.jpa.properties.hibernate.dialect";
//    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "spring.jpa.properties.hibernate.format_sql";
//    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "spring.jpa.hibernate.ddl-auto";
//    private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "spring.jpa.hibernate.naming-stratgey";
//    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "spring.jpa.hibernate.show-sql";

	@Autowired
	private Environment env;

	/**
	 * Creates the transaction manager bean that integrates the used JPA provider
	 * with the Spring transaction mechanism.
	 * 
	 * @param entityManagerFactory The used JPA entity manager factory.
	 * @return
	 */
	@Bean
	JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

}
