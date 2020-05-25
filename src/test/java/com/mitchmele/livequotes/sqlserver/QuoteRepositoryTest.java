package com.mitchmele.livequotes.sqlserver;

import com.mitchmele.livequotes.models.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuoteRepositoryTest {

    @Autowired
    QuoteRepository quoteRepository;

    Quote quote1 = new Quote("EEE", BigDecimal.valueOf(90.00),  BigDecimal.valueOf(90.25));
    Quote quote2 = new Quote("PPO",  BigDecimal.valueOf(114.05),  BigDecimal.valueOf(141.55));
    Quote quote3 = new Quote("MKL",  BigDecimal.valueOf(56.05),  BigDecimal.valueOf(56.11));
    Quote quote4 = new Quote("MKL",  BigDecimal.valueOf(56.15),  BigDecimal.valueOf(56.25));

    @BeforeEach
    void setUp() {
        quoteRepository.deleteAll();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void save_success_savesQuote_entity_toSqlServer() {

        assertThat(quote1.getId()).isNull();
        Quote actual = quoteRepository.save(quote1);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual).isEqualTo(quote1);
    }

    @Test
    public void findBySymbol_success_shouldReturnAllQuotesForSymbol() {
        quoteRepository.save(quote1);
        quoteRepository.save(quote2);
        quoteRepository.save(quote3);
        quoteRepository.save(quote4);

        List<Quote> actual = quoteRepository.findAllBySymbol("MKL");

        assertThat(actual).hasSize(2);
        assertThat(actual).containsExactly(quote3, quote4);
    }

    @Test
    public void findBySymbol_failure_shouldReturnEmptyListIfSymbolNotFound() {
        quoteRepository.save(quote1);
        quoteRepository.save(quote2);
        quoteRepository.save(quote3);
        quoteRepository.save(quote4);

        List<Quote> actual = quoteRepository.findAllBySymbol("TTL");

        assertThat(actual).hasSize(0);
    }
}