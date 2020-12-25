package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.models.Bid;
import com.mitchmele.livequotes.models.BidDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Date;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class BidConverterTest {

    private BidConverter converter;

    @BeforeEach
    void setUp() {
        converter = new BidConverter();
    }

    @Test
    public void convert_shouldCreateBidFromIncomingQuote() {

        Date mockDate = mock(Date.class);

        Bid inputBid = new Bid( 19, "UOQ", BigDecimal.valueOf(17.82), mockDate);

        BidDO expectedBid = BidDO.builder()
                .symbol("UOQ")
                .id(19)
                .bidPrice(BigDecimal.valueOf(17.82))
                .timeStamp(mockDate)
                .build();

        BidDO actual = converter.convert(inputBid);
        assertThat(actual).isEqualToIgnoringGivenFields(expectedBid, "timeStamp");
    }
}