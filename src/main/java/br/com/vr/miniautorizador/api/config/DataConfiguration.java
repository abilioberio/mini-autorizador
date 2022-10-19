package br.com.vr.miniautorizador.api.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Profile("docker")
public class DataConfiguration {
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("root");
		dataSource.setPassword("");
		dataSource.setUrl(
				"jdbc:mysql://localhost:3306/miniautorizador?createDatabaseIfNotExist=true");
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		return dataSource;
	}

	@Bean
	public Properties aditionalProperties() {
		Properties props = new Properties();
		props.put("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
		props.put("hibernate.show_sql", false);
		props.put("hibernate.format_sql", true);
		props.put("hibernate.hbm2ddl.auto", "update");
//		props.put("hibernate.jdbc.time_zone", "spring.jpa.properties.hibernate.jdbc.time_zone");

		return props;
	}
}
