package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.models.Ask;
import com.mitchmele.livequotes.models.Bid;
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
    private final BidConverter bidConverter;
    private final AskConverter askConverter;

    //store in bid/ask tables
    public void splitAndStorePrices(String incomingJson) throws IOException {
        Bid bidPrice = bidConverter.convert(incomingJson);
        Ask askPrice = askConverter.convert(incomingJson);

        try {
            if (!isNull(bidPrice)) {
                bidRepository.save(bidPrice);
                log.info("SUCCESSFULLY SAVED BID: " + bidPrice.getBidPrice());
            }
            if (!isNull(askPrice)) {
                askRepository.save(askPrice);
                log.info("SUCCESSFULLY SAVED ASK: " + askPrice.getAskPrice());
            }
        } catch (Exception ex) {
            throw new IOException(ex.getLocalizedMessage());
        }
    }
}
