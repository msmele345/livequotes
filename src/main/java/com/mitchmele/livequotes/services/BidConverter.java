package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.models.Bid;
import com.mitchmele.livequotes.models.BidDO;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class BidConverter implements Converter<Bid, BidDO> {

    @Override
    public BidDO convert(Bid source) {
        return BidDO.builder()
                .id(source.getId())
                .symbol(source.getSymbol())
                .bidPrice(source.getBidPrice())
                .timeStamp(source.getTimeStamp())
                .build();
    }
}
