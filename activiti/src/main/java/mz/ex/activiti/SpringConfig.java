package mz.ex.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
	@Bean
	public ProcessEngine processEngine() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		return processEngine;
	}
	
//	@Bean
//	public StandaloneProcessEngineConfiguration processEngineConfiguration() {
//		StandaloneProcessEngineConfiguration standaloneProcessEngineConfiguration = new StandaloneProcessEngineConfiguration();
//
//		standaloneProcessEngineConfiguration.setJdbcUrl("jdbc:postgresql://192.168.99.100/activiti?user=root&password=root");
//		standaloneProcessEngineConfiguration.setJdbcDriver("org.postgresql.Driver");
//		standaloneProcessEngineConfiguration.setJdbcUsername("root");
//		standaloneProcessEngineConfiguration.setJdbcPassword("root");
//		standaloneProcessEngineConfiguration.setDatabaseSchemaUpdate("true");
//		
//		return standaloneProcessEngineConfiguration;
//	}
}
