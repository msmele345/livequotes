package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.jmssender.QuotePublisher;
import com.mitchmele.livequotes.models.Ask;
import com.mitchmele.livequotes.models.Bid;
import com.mitchmele.livequotes.models.Quote;
import com.mitchmele.livequotes.sqlserver.AskRepository;
import com.mitchmele.livequotes.sqlserver.BidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboundQuoteOrchestrator {

    private final BidRepository bidRepository;
    private final AskRepository askRepository;
    private final QuotePublisher quotePublisher;

    public void orchestrate(Quote incomingQuote) throws IOException {
        try {
            if (!isNull(incomingQuote)) {
                Bid bidPrice = Bid.builder()
                        .symbol(incomingQuote.getSymbol())
                        .bidPrice(incomingQuote.getBidPrice())
                        .build();

                bidRepository.save(bidPrice);
                log.info("SUCCESSFULLY SAVED BID: " + bidPrice.getBidPrice());
                quotePublisher.sendToOutbound("stocks", bidPrice);

                Ask askPrice = Ask.builder()
                        .symbol(incomingQuote.getSymbol())
                        .askPrice(incomingQuote.getAskPrice())
                        .build();

                askRepository.save(askPrice);
                log.info("SUCCESSFULLY SAVED ASK: " + askPrice.getAskPrice());
                quotePublisher.sendToOutbound("stocks", askPrice);
            }
        } catch (Exception ex) {
            throw new IOException(ex.getLocalizedMessage());
        }
    }
}
