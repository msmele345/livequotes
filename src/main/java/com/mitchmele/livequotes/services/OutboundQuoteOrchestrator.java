package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.jmssender.QuotePublisher;
import com.mitchmele.livequotes.models.Ask;
import com.mitchmele.livequotes.models.Bid;
import com.mitchmele.livequotes.models.Quote;
import com.mitchmele.livequotes.mongo.AskDORepository;
import com.mitchmele.livequotes.mongo.BidDORepository;
import com.mitchmele.livequotes.sqlserver.AskRepository;
import com.mitchmele.livequotes.sqlserver.BidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Objects;
import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboundQuoteOrchestrator {

    private final BidConverter bidConverter;
    private final AskConverter askConverter;
    private final AskRepository askRepository;
    private final BidRepository bidRepository;
    private final AskDORepository askDORepository;
    private final BidDORepository bidDORepository;
    private final QuotePublisher quotePublisher;

    public void orchestrate(Quote incomingQuote) throws IOException {

        try {
            if (!isNull(incomingQuote)) {
                Bid bidPrice = Bid.builder()
                        .symbol(incomingQuote.getSymbol())
                        .bidPrice(incomingQuote.getBidPrice())
                        .timeStamp(incomingQuote.getTimeStamp())
                        .build();

                bidRepository.save(bidPrice);
                bidDORepository.save(Objects.requireNonNull(bidConverter.convert(bidPrice)));

                log.info("SUCCESSFULLY SAVED BID: " + bidPrice.getBidPrice());
                quotePublisher.sendToOutbound("stocks", bidPrice);

                Ask askPrice = Ask.builder()
                        .symbol(incomingQuote.getSymbol())
                        .askPrice(incomingQuote.getAskPrice())
                        .timeStamp(incomingQuote.getTimeStamp())
                        .build();

                askRepository.save(askPrice);
                askDORepository.save(Objects.requireNonNull(askConverter.convert(askPrice)));

                log.info("SUCCESSFULLY SAVED ASK: " + askPrice.getAskPrice());
                quotePublisher.sendToOutbound("stocks", askPrice);
            }
        } catch (Exception ex) {
            throw new IOException(ex.getLocalizedMessage());
        }
    }
}
