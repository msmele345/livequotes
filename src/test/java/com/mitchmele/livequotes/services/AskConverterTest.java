package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.models.Ask;
import com.mitchmele.livequotes.models.AskDO;
import com.mitchmele.livequotes.models.Bid;
import com.mitchmele.livequotes.models.BidDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AskConverterTest {

    private AskConverter converter;

    @BeforeEach
    void setUp() {
        converter = new AskConverter();
    }

    @Test
    public void convert_shouldCreateBidFromIncomingQuote() {

        Date mockDate = mock(Date.class);

        Ask inputAsk = new Ask( 19, "UOQ", BigDecimal.valueOf(17.82), mockDate);

        AskDO expectedBid = AskDO.builder()
                .symbol("UOQ")
                .id(19)
                .askPrice(BigDecimal.valueOf(17.82))
                .timeStamp(mockDate)
                .build();

        AskDO actual = converter.convert(inputAsk);
        assertThat(actual).isEqualToIgnoringGivenFields(expectedBid, "timeStamp");
    }
}