package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.models.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class QuoteProcessorTest {

    QuoteProcessor subject;

    @BeforeEach
    void setUp() {
        subject = new QuoteProcessor();
    }

    @Test
    @Disabled("Set to env variable and test with reflection")
    public void generateQuotes_success() {
        List<Quote> actual = subject.generateQuotes();
        assertThat(actual).hasSize(4);
    }

    @Test
    public void createSymbol_success_shouldCreate3CharSymbol() {
        String actual = subject.createSymbol();
        assertThat(actual.length()).isEqualTo(3);
    }

    @Test
    public void createPrice_success_shouldCreateRanDoublePrice_Rounded() {
       List<BigDecimal> actual = subject.createPrice();
        assertThat(actual).hasSize(2);
    }
}