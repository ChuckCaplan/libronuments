package com.chuckcaplan.libronuments.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.chuckcaplan.libronuments.entity.Library;
import com.chuckcaplan.libronuments.entity.Monument;
import com.chuckcaplan.libronuments.entity.Neighborhood;

/**
 * @author Chuck Caplan
 * 
 *         Class to read the DB config from a file and initialize Hibernate.
 *
 */
@Configuration
@EnableTransactionManagement
public class DBConfig {

	private final static Logger logger = LoggerFactory.getLogger(DBConfig.class);

	// DB Data Source - null until initialized
	private static BasicDataSource dataSource = null;

	/**
	 * @return The Hibernate template
	 */
	@Bean
	public HibernateTemplate hibernateTemplate() {
		return new HibernateTemplate(sessionFactory());
	}

	/**
	 * @return a SessionFactory of each entity we are using with Hibernate
	 */
	@Bean
	public SessionFactory sessionFactory() {
		// REMEMBER - Each entity needs to be manually added to this method to
		// get it to work with Hibernate.
		return new LocalSessionFactoryBuilder(getDataSource()).addAnnotatedClasses(Library.class)
				.addAnnotatedClasses(Monument.class).addAnnotatedClasses(Neighborhood.class).buildSessionFactory();
	}

	/**
	 * Creates the data source. Make sure there is a db.properties file in your
	 * classpath with the following properties: db.class db.url db.username
	 * db.password
	 * 
	 * @return the data source based on the DB properties in the file.
	 */
	@Bean
	public DataSource getDataSource() {
		// initialize the Data Source once using properties in db.properties
		if (dataSource == null) {
			try {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				// load db.properties from classpath
				InputStream input = classLoader.getResourceAsStream("db.properties");
				Properties dbProperties = new Properties();
				dbProperties.load(input);
				dataSource = new BasicDataSource();
				dataSource.setDriverClassName(dbProperties.getProperty("db.class"));
				dataSource.setUrl(dbProperties.getProperty("db.url"));
				dataSource.setUsername(dbProperties.getProperty("db.user"));
				dataSource.setPassword(dbProperties.getProperty("db.password"));
			} catch (IOException e) {
				logger.error("Error reading db.properties", e);
			}
		}
		return dataSource;
	}

	/**
	 * @return The Hibernate Transaction Manager. For commits and rollbacks.
	 */
	@Bean
	public HibernateTransactionManager hibTransMan() {
		return new HibernateTransactionManager(sessionFactory());
	}
}