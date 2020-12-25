package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.models.Ask;
import com.mitchmele.livequotes.models.AskDO;
import com.mitchmele.livequotes.models.Bid;
import com.mitchmele.livequotes.models.BidDO;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class AskConverter implements Converter<Ask, AskDO> {

    @Override
    public AskDO convert(Ask source) {
        return AskDO.builder()
                .id(source.getId())
                .symbol(source.getSymbol())
                .askPrice(source.getAskPrice())
                .timeStamp(source.getTimeStamp())
                .build();
    }
}
