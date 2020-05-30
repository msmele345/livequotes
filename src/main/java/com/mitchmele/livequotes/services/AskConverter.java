package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.models.Ask;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@NoArgsConstructor
public class AskConverter implements Converter<String, Ask> {

    @Override
    public Ask convert(String source) {
        JSONObject quoteJson = new JSONObject(source);

        BigDecimal askPrice = quoteJson
                .getBigDecimal("askPrice")
                .setScale(2, RoundingMode.HALF_EVEN);

        Integer id = quoteJson.getInt("id");
        String symbol = quoteJson.getString("symbol");

        return Ask.builder()
                .id(id)
                .symbol(symbol)
                .askPrice(askPrice)
                .build();
    }
}
