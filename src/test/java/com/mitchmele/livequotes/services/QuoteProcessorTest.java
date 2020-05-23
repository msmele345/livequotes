package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.models.Bid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class QuoteProcessorTest {

    QuoteProcessor subject;

    @BeforeEach
    void setUp() {
        subject = new QuoteProcessor();
    }

    @Test
    public void generateBids_success() {
        List<Bid> actual = subject.generateBids();
        assertThat(actual).hasSize(20);
    }

    @Test
    public void createSymbol_success_shouldCreate3CharSymbol() {
        String actual = subject.createSymbol();
        assertThat(actual.length()).isEqualTo(3);
    }

    @Test
    public void createBidPrice_success_shouldCreateRanDoublePrice_Rounded() {
        Double actual = subject.createPrice();
        assertThat(actual).isInstanceOf(Double.class);
        assertThat(actual).isGreaterThan(0.0);
    }
}