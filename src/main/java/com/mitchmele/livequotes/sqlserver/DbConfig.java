package com.mitchmele.livequotes.sqlserver;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = {"com.mitchmele.livequotes"})
@EnableJpaRepositories(basePackages = {"com.mitchmele"})
@ComponentScan("com.mitchmele")
@EnableTransactionManagement
public class DbConfig {
}
