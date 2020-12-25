package com.mitchmele.livequotes.config;

import com.mitchmele.livequotes.sqlserver.AskRepository;
import com.mitchmele.livequotes.sqlserver.BidRepository;
import com.mitchmele.livequotes.sqlserver.QuoteRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = {"com.mitchmele.livequotes"})
@EnableJpaRepositories(basePackageClasses = {BidRepository.class, AskRepository.class, QuoteRepository.class})
@ComponentScan("com.mitchmele")
@EnableTransactionManagement
public class DbConfig {
}
