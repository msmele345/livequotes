package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.models.Bid;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@NoArgsConstructor
public class BidConverter implements Converter<String, Bid> {

    @Override
    public Bid convert(String source) {

        JSONObject quoteJson = new JSONObject(source);

        BigDecimal bidPrice = quoteJson
                .getBigDecimal("bidPrice")
                .setScale(2, RoundingMode.HALF_EVEN);

        Integer id = quoteJson.getInt("id");
        String symbol = quoteJson.getString("symbol");

        return Bid.builder()
                .id(id)
                .symbol(symbol)
                .bidPrice(bidPrice)
                .build();
    }
}
