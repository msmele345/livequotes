package com.mitchmele.livequotes.sqlserver;

import com.mitchmele.livequotes.models.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuoteRepositoryTest {

    @Autowired
    QuoteRepository quoteRepository;

    Quote quote1 = new Quote(1, "EEE", BigDecimal.valueOf(90.00),  BigDecimal.valueOf(90.25), new Date(1000));
    Quote quote2 = new Quote(2, "PPO",  BigDecimal.valueOf(114.05),  BigDecimal.valueOf(141.55), new Date(2000));
    Quote quote3 = new Quote(3, "MKL",  BigDecimal.valueOf(56.05),  BigDecimal.valueOf(56.11), new Date(3000));
    Quote quote4 = new Quote(4, "MKL",  BigDecimal.valueOf(56.15),  BigDecimal.valueOf(56.25), new Date(4000));

    @BeforeEach
    void setUp() {
        quoteRepository.deleteAll();
    }

    @Test
    public void contextLoads() { }

    @Test
    public void save_success_savesQuote_entity_toSqlServer() {
        Quote actual = quoteRepository.save(quote1);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual).isEqualToIgnoringGivenFields(quote1, "id", "timeStamp");
    }

    @Test
    public void findBySymbol_success_shouldReturnAllQuotesForSymbol() {
        quoteRepository.save(quote1);
        quoteRepository.save(quote2);
        quoteRepository.save(quote3);
        quoteRepository.save(quote4);

        List<Quote> actual = quoteRepository.findAllBySymbol("MKL");

        assertThat(actual).hasSize(2);
        assertThat(actual.get(0)).isEqualToIgnoringGivenFields(quote3,"id", "timeStamp");
        assertThat(actual.get(1)).isEqualToIgnoringGivenFields(quote4, "id", "timeStamp");
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