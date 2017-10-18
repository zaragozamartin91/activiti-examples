package mz.ex.activiti;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.activiti.engine.ProcessEngine;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Configuration
public class SpringConfig {
	// @Bean
	// public ProcessEngine processEngine() {
	// ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	// return processEngine;
	// }

	/*
	 * <property name="jdbcUrl" value="jdbc:h2:mem:activiti;DB_CLOSE_DELAY=1000" /> <property name="jdbcDriver" value="org.h2.Driver" /> <property
	 * name="jdbcUsername" value="sa" /> <property name="jdbcPassword" value="" />
	 */

	// xpath.compile("/beans/bean/property[@name='jdbcUrl']/@value").evaluate(doc,XPathConstants.STRING)

	@Bean
	public Document activitiCfgDoc() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new ClassPathResource("activiti.cfg.xml").getInputStream());
	}

	@Bean
	public XPath xpath() {
		XPathFactory xPathfactory = XPathFactory.newInstance();
		return xPathfactory.newXPath();
	}

	@Bean
	public SimpleDriverDataSource dataSource(Document activitiCfgDoc, XPath xpath) throws XPathExpressionException, ClassNotFoundException {
		/* Se leen y se cargan las configuraciones directamente desde activiti.cfg.xml */
		Document doc = activitiCfgDoc;
		String jdbcUrl = (String) xpath.compile("/beans/bean/property[@name='jdbcUrl']/@value").evaluate(doc, XPathConstants.STRING);
		String username = (String) xpath.compile("/beans/bean/property[@name='jdbcUsername']/@value").evaluate(doc, XPathConstants.STRING);
		String password = (String) xpath.compile("/beans/bean/property[@name='jdbcPassword']/@value").evaluate(doc, XPathConstants.STRING);
		String driverClassname = (String) xpath.compile("/beans/bean/property[@name='jdbcDriver']/@value").evaluate(doc, XPathConstants.STRING);

		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass((Class<? extends java.sql.Driver>) Class.forName(driverClassname));
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean
	public DataSourceTransactionManager transactionManager(SimpleDriverDataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public SpringProcessEngineConfiguration processEngineConfiguration(SimpleDriverDataSource dataSource, DataSourceTransactionManager transactionManager) {
		SpringProcessEngineConfiguration springProcessEngineConfiguration = new SpringProcessEngineConfiguration();

		springProcessEngineConfiguration.setDataSource(dataSource);
		springProcessEngineConfiguration.setTransactionManager(transactionManager);
		springProcessEngineConfiguration.setDatabaseSchemaUpdate("true");
		springProcessEngineConfiguration.setAsyncExecutorActivate(false);

		return springProcessEngineConfiguration;
	}

	/**
	 * Define el factory bean para {@link ProcessEngine}. Si se solicita este bean al contexto, se obtendra un {@link ProcessEngine} y no un
	 * {@link ProcessEngineFactoryBean}.
	 * 
	 * @param processEngineConfiguration
	 *            Configuracion a inyectar en el processEngine.
	 * @return Nuevo ProcessEngine.
	 */
	@Bean
	public ProcessEngineFactoryBean processEngine(SpringProcessEngineConfiguration processEngineConfiguration) {
		ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
		processEngineFactoryBean.setProcessEngineConfiguration(processEngineConfiguration);
		return processEngineFactoryBean;
	}

	// @Bean
	// public StandaloneProcessEngineConfiguration processEngineConfiguration() {
	// StandaloneProcessEngineConfiguration standaloneProcessEngineConfiguration = new StandaloneProcessEngineConfiguration();
	//
	// standaloneProcessEngineConfiguration.setJdbcUrl("jdbc:postgresql://192.168.99.100/activiti?user=root&password=root");
	// standaloneProcessEngineConfiguration.setJdbcDriver("org.postgresql.Driver");
	// standaloneProcessEngineConfiguration.setJdbcUsername("root");
	// standaloneProcessEngineConfiguration.setJdbcPassword("root");
	// standaloneProcessEngineConfiguration.setDatabaseSchemaUpdate("true");
	//
	// return standaloneProcessEngineConfiguration;
	// }
}
