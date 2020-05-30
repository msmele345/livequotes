package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.models.Ask;
import com.mitchmele.livequotes.models.Bid;
import com.mitchmele.livequotes.sqlserver.AskRepository;
import com.mitchmele.livequotes.sqlserver.BidRepository;
import com.mitchmele.livequotes.sqlserver.QuoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuoteSplitterServiceTest {

    @Mock
    BidRepository mockBidRepository;

    @Mock
    AskRepository mockAskRepository;

    @Mock
    BidConverter mockBidConverter;

    @Mock
    AskConverter mockAskConverter;

    @InjectMocks
    QuoteSplitterService quoteSplitterService;

    /*
    * {"id":19,"symbol":"UOQ","bidPrice":17.82,"askPrice":18.00}
    * */
    @Test
    public void splitAndStorePrices_shouldCallConverters() throws IOException {
        String incomingJson = "{\"id\":19,\"symbol\":\"UOQ\",\"bidPrice\":17.82,\"askPrice\":18.01}";

        Bid expectedBid = new Bid(19, "UOQ", BigDecimal.valueOf(17.82));
        Ask expectedAsk = new Ask(19, "UOQ", BigDecimal.valueOf(18.01));

        when(mockBidConverter.convert(anyString())).thenReturn(expectedBid);
        when(mockAskConverter.convert(anyString())).thenReturn(expectedAsk);

        quoteSplitterService.splitAndStorePrices(incomingJson);

        verify(mockBidConverter).convert(incomingJson);
        verify(mockAskConverter).convert(incomingJson);

        verify(mockBidRepository).save(expectedBid);
        verify(mockAskRepository).save(expectedAsk);
    }

    @Test
    public void splitAndStorePrices_failure_shouldThrowIOExceptionIfBidSaveFails() {
        String incomingJson = "{\"id\":19,\"symbol\":\"UOQ\",\"bidPrice\":17.82,\"askPrice\":18.01}";

        Bid expectedBid = new Bid(19, "UOQ", BigDecimal.valueOf(17.82));
        Ask expectedAsk = new Ask(19, "UOQ", BigDecimal.valueOf(18.01));

        when(mockBidConverter.convert(anyString())).thenReturn(expectedBid);
        when(mockAskConverter.convert(anyString())).thenReturn(expectedAsk);

        when(mockBidRepository.save(any())).thenThrow(new RuntimeException("bad bid"));

        assertThatThrownBy(() -> quoteSplitterService.splitAndStorePrices(incomingJson))
                .isInstanceOf(IOException.class)
                .hasMessage("bad bid");
    }

    @Test
    public void splitAndStorePrices_failure_shouldThrowIOExceptionIfAskSaveFails() {
        String incomingJson = "{\"id\":19,\"symbol\":\"UOQ\",\"bidPrice\":17.82,\"askPrice\":18.01}";

        Bid expectedBid = new Bid(19, "UOQ", BigDecimal.valueOf(17.82));
        Ask expectedAsk = new Ask(19, "UOQ", BigDecimal.valueOf(18.01));

        when(mockBidConverter.convert(anyString())).thenReturn(expectedBid);
        when(mockAskConverter.convert(anyString())).thenReturn(expectedAsk);

        when(mockAskRepository.save(any())).thenThrow(new RuntimeException("bad ask"));

        assertThatThrownBy(() -> quoteSplitterService.splitAndStorePrices(incomingJson))
                .isInstanceOf(IOException.class)
                .hasMessage("bad ask");
    }
}