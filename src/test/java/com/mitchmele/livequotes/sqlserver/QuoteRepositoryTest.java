package com.mitchmele.livequotes.sqlserver;

import com.mitchmele.livequotes.models.Bid;
import com.mitchmele.livequotes.models.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuoteRepositoryTest {


    @Autowired
    QuoteRepository quoteRepository;

    @BeforeEach
    void setUp() {
        Quote quote1 = new Quote("EEE", 90.00, 90.25);
        Quote quote2 = new Quote("PPO", 141.05, 141.55);
        Quote quote3 = new Quote("MKL", 56.05, 57.10);

        quoteRepository.save(quote1);
        quoteRepository.save(quote2);
        quoteRepository.save(quote3);
    }

    @Test
    public void contextLoads() {
    }
}
/*
 * [database Name].[Schema Name].[Table Name]
 * quote_db.dbo.quote
 * */