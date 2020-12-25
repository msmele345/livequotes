package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.jmssender.QuotePublisher;
import com.mitchmele.livequotes.models.*;
import com.mitchmele.livequotes.mongo.AskDORepository;
import com.mitchmele.livequotes.mongo.BidDORepository;
import com.mitchmele.livequotes.sqlserver.AskRepository;
import com.mitchmele.livequotes.sqlserver.BidRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OutboundQuoteOrchestratorTest {

    @Mock
    BidRepository mockBidRepository;

    @Mock
    AskRepository mockAskRepository;

    @Mock
    BidDORepository bidDORepository;

    @Mock
    AskDORepository askDORepository;

    @Mock
    BidConverter bidConverter;

    @Mock
    AskConverter askConverter;

    @Mock
    QuotePublisher mockQuotePublisher;

    @InjectMocks
    OutboundQuoteOrchestrator outboundQuoteOrchestrator;

    @Test
    public void splitAndStorePrices_saveBidAndAsk_thenSendToOutbound() throws IOException {

        Date mockDate = mock(Date.class);

        Quote incomingQuote = Quote.builder()
                .symbol("UOQ")
                .bidPrice(BigDecimal.valueOf(17.82))
                .askPrice(BigDecimal.valueOf(18.01))
                .timeStamp(mockDate)
                .build();

        Bid expectedBid = new Bid(null, "UOQ", BigDecimal.valueOf(17.82), mockDate);
        Ask expectedAsk = new Ask(null, "UOQ", BigDecimal.valueOf(18.01), mockDate);

        BidDO expectedBidDO = BidDO.builder()
                .symbol("UOQ")
                .id(1)
                .bidPrice(BigDecimal.valueOf(17.82))
                .timeStamp(mockDate)
                .build();

        AskDO expectedAskDO = AskDO.builder()
                .symbol("UOQ")
                .id(1)
                .askPrice(BigDecimal.valueOf(18.01))
                .timeStamp(mockDate)
                .build();

        when(mockBidRepository.save(any())).thenReturn(expectedBid);
        when(mockAskRepository.save(any())).thenReturn(expectedAsk);
        when(bidConverter.convert(any())).thenReturn(expectedBidDO);
        when(askConverter.convert(any())).thenReturn(expectedAskDO);

        outboundQuoteOrchestrator.orchestrate(incomingQuote);

        verify(mockBidRepository).save(expectedBid);
        verify(mockAskRepository).save(expectedAsk);

        verify(bidDORepository).save(expectedBidDO);
        verify(askDORepository).save(expectedAskDO);

        verify(askConverter).convert(expectedAsk);
        verify(bidConverter).convert(expectedBid);

        verify(mockQuotePublisher).sendToOutbound("stocks", expectedBid);
        verify(mockQuotePublisher).sendToOutbound("stocks", expectedAsk);
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

        assertThatThrownBy(() -> outboundQuoteOrchestrator.orchestrate(incomingQuote))
                .isInstanceOf(IOException.class)
                .hasMessage("bad bid");
    }
}