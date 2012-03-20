package ch.ralscha.e4ds.config;

import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.db.DBAppender;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.db.DataSourceConnectionSource;
import ch.qos.logback.core.util.StatusPrinter;

import com.google.common.collect.Maps;

@Configuration
@EnableTransactionManagement
@ImportResource("classpath:ch/ralscha/e4ds/config/data.xml")
public class DataConfig {

	@Autowired
	private Environment environment;

	@Bean
	public DataSource dataSource() throws NamingException {
		Context ctx = new InitialContext();
		
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ds");
		setupLog(ds);
		
		return ds;
	}

	private void setupLog(DataSource dataSource) {
		boolean development = environment.acceptsProfiles("development");

		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		lc.reset();

		ConsoleAppender<ILoggingEvent> consoleAppender = null;
		if (development) {
			PatternLayoutEncoder encoder = new PatternLayoutEncoder();
			encoder.setContext(lc);
			encoder.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
			encoder.start();

			consoleAppender = new ConsoleAppender<ILoggingEvent>();
			consoleAppender.setContext(lc);
			consoleAppender.setEncoder(encoder);
			consoleAppender.start();
		}

		DataSourceConnectionSource source = new DataSourceConnectionSource();
		source.setContext(lc);
		source.setDataSource(dataSource);
		source.start();

		DBAppender dbAppender = new DBAppender();
		dbAppender.setContext(lc);
		dbAppender.setConnectionSource(source);
		dbAppender.start();

		Logger appLogger = lc.getLogger("ch.ralscha.e4ds");
		appLogger.setLevel(Level.WARN);

		Logger rootLogger = lc.getLogger("root");
		rootLogger.setLevel(Level.WARN);
		if (development) {
			rootLogger.addAppender(consoleAppender);
		}
		rootLogger.addAppender(dbAppender);
		lc.start();

		StatusPrinter.printInCaseOfErrorsOrWarnings(lc);

	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource());
		emf.setPersistenceProvider(new org.hibernate.ejb.HibernatePersistence());
		emf.setPackagesToScan("ch.ralscha.e4ds.entity");

		Map<String, String> properties = Maps.newHashMap();
		//properties.put("hibernate.show_sql", "true");

		String dialect = environment.getProperty("hibernate.dialect");
		if (dialect != null) {
			properties.put("hibernate.dialect", dialect);
		}

		emf.setJpaPropertyMap(properties);

		return emf;
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws NamingException {
		return new JpaTransactionManager(entityManagerFactory().getObject());
	}

	@Bean
	public SpringLiquibase liquibaseBean() throws NamingException {
		SpringLiquibase bean = new SpringLiquibase();
		bean.setDataSource(dataSource());
		bean.setChangeLog("classpath:db/changelog.xml");
		return bean;
	}

}
