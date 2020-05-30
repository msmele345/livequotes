package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.models.Ask;
import com.mitchmele.livequotes.models.Bid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AskConverterTest {

    private AskConverter converter;

    @BeforeEach
    void setUp() {
        converter = new AskConverter();
    }

    @Test
    public void convert_shouldParseJsonAndReturnAsk() {
        Ask expectedAsk = new Ask(19, "UOQ", BigDecimal.valueOf(18.01));

        String incomingJson = "{\"id\":19,\"symbol\":\"UOQ\",\"bidPrice\":17.82,\"askPrice\":18.01}";

        Ask actual = converter.convert(incomingJson);
        assertThat(actual).isEqualTo(expectedAsk);
    }

}