package com.mitchmele.livequotes;

import com.mitchmele.livequotes.mongo.AskDORepository;
import com.mitchmele.livequotes.mongo.BidDORepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {BidDORepository.class, AskDORepository.class})
public class LivequotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LivequotesApplication.class, args);
	}

}
