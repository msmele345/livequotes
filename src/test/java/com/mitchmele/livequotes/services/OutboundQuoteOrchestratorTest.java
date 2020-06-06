package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.models.Ask;
import com.mitchmele.livequotes.models.Bid;
import com.mitchmele.livequotes.models.Quote;
import com.mitchmele.livequotes.sqlserver.AskRepository;
import com.mitchmele.livequotes.sqlserver.BidRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OutboundQuoteOrchestratorTest {

    @Mock
    BidRepository mockBidRepository;

    @Mock
    AskRepository mockAskRepository;

    @InjectMocks
    OutboundQuoteOrchestrator outboundQuoteOrchestrator;

    /*
    * {"id":19,"symbol":"UOQ","bidPrice":17.82,"askPrice":18.00}
    * */
    @Test
    public void splitAndStorePrices_shouldCallConverters() throws IOException {
        Quote incomingQuote = Quote.builder()
                .symbol("UOQ")
                .bidPrice(BigDecimal.valueOf(17.82))
                .askPrice(BigDecimal.valueOf(18.01))
                .build();

        Bid expectedBid = new Bid(null, "UOQ", BigDecimal.valueOf(17.82), null);
        Ask expectedAsk = new Ask(null, "UOQ", BigDecimal.valueOf(18.01), null);

        outboundQuoteOrchestrator.splitAndStorePrices(incomingQuote);

        verify(mockBidRepository).save(expectedBid);
        verify(mockAskRepository).save(expectedAsk);
    }

    @Test
    public void splitAndStorePrices_failure_shouldThrowIOExceptionIfBidSaveFails() {
        Quote incomingQuote = Quote.builder()
                .id(null)
                .symbol("UOQ")
                .bidPrice(BigDecimal.valueOf(17.82))
                .askPrice(BigDecimal.valueOf(18.01))
                .build();

        when(mockBidRepository.save(any())).thenThrow(new RuntimeException("bad bid"));

        assertThatThrownBy(() -> outboundQuoteOrchestrator.splitAndStorePrices(incomingQuote))
                .isInstanceOf(IOException.class)
                .hasMessage("bad bid");
    }

    @Test
    public void splitAndStorePrices_failure_shouldThrowIOExceptionIfAskSaveFails() {
        Quote incomingQuote = Quote.builder()
                .symbol("UOQ")
                .bidPrice(BigDecimal.valueOf(17.82))
                .askPrice(BigDecimal.valueOf(18.01))
                .build();

        when(mockAskRepository.save(any())).thenThrow(new RuntimeException("bad ask"));

        assertThatThrownBy(() -> outboundQuoteOrchestrator.splitAndStorePrices(incomingQuote))
                .isInstanceOf(IOException.class)
                .hasMessage("bad ask");
    }
}