package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.models.Ask;
import com.mitchmele.livequotes.models.Bid;
import com.mitchmele.livequotes.models.Quote;
import com.mitchmele.livequotes.sqlserver.AskRepository;
import com.mitchmele.livequotes.sqlserver.BidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuoteSplitterService {

    private final BidRepository bidRepository;
    private final AskRepository askRepository;

    public void splitAndStorePrices(Quote incomingQuote) throws IOException {
        try {
            if (!isNull(incomingQuote)) {
                Bid bidPrice = Bid.builder()
                        .symbol(incomingQuote.getSymbol())
                        .bidPrice(incomingQuote.getBidPrice())
                        .build();
                bidRepository.save(bidPrice);
                log.info("SUCCESSFULLY SAVED BID: " + bidPrice.getBidPrice());

                Ask askPrice = Ask.builder()
                        .symbol(incomingQuote.getSymbol())
                        .askPrice(incomingQuote.getAskPrice())
                        .build();
                log.info("SUCCESSFULLY SAVED ASK: " + askPrice.getAskPrice());
                askRepository.save(askPrice);
            }
        } catch (Exception ex) {
            throw new IOException(ex.getLocalizedMessage());
        }
    }
}
