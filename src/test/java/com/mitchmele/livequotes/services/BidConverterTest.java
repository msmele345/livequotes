package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.models.Bid;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class BidConverterTest {

    private BidConverter converter;

    @BeforeEach
    void setUp() {
        converter = new BidConverter();
    }

    @Test
    public void convert_shouldCreateBidFromIncomingQuote() {
        Bid expectedBid = new Bid(19, "UOQ", BigDecimal.valueOf(17.82));

        String incomingJson = "{\"id\":19,\"symbol\":\"UOQ\",\"bidPrice\":17.82,\"askPrice\":18.00}";

        Bid actual = converter.convert(incomingJson);
        assertThat(actual).isEqualTo(expectedBid);
    }

    @Test
    public void convert_failure_shouldThrowIOExceptionIfParsingFails() {

        String incomingBadJson = "{\"id\":19,\"symbol\":\"UOQ\",\"bidPre\":17.82,\"askPrice\":18.00}";

        assertThatThrownBy(() -> converter.convert(incomingBadJson))
                .isInstanceOf(JSONException.class)
                .hasMessage("JSONObject[\"bidPrice\"] not found.");

    }

}