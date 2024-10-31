package com.nitp.club.management.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfig{
	
	@Value("${mysql.datasource.url}")
	private String url;
	
	@Value("${mysql.datasource.username}")
	private String username;
	
	@Value("${mysql.datasource.password}")
	private String password;

	@Bean
	DataSource dataSource() {
		HikariDataSource datasource = new HikariDataSource();
		datasource.setJdbcUrl(url);
		datasource.setPassword(password);
		datasource.setUsername(username);
		datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		datasource.setConnectionTestQuery("select 1");
		return datasource;
	}
}
