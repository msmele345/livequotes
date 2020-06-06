package com.mitchmele.livequotes.jmssender;

import com.mitchmele.livequotes.common.QuoteErrorType;
import com.mitchmele.livequotes.common.QuoteMessageError;
import com.mitchmele.livequotes.common.QuoteMessageException;
import com.mitchmele.livequotes.models.Bid;
import com.mitchmele.livequotes.models.Quote;
import com.mitchmele.livequotes.models.QuotePrice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuotePublisherTest {

    @Mock
    JmsTemplate mockJmsTemplate;

    @InjectMocks
    QuotePublisher quotePublisher;

    private final Quote quote1 = new Quote(1, "ABC", BigDecimal.valueOf(20.00), BigDecimal.valueOf(21.00), new Date(1000));

    @Test
    public void send_success_shouldCallJMS_sendWithProvidedQuote() {
        quotePublisher.send("quotes", quote1);
        verify(mockJmsTemplate).convertAndSend("quotes", quote1);
    }

    @Test
    public void send_success_sendsMessageToQuotesDestination() {
        quotePublisher.send("quotes", quote1);

        ArgumentCaptor<Quote> captor = ArgumentCaptor.forClass(Quote.class);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockJmsTemplate).convertAndSend(stringCaptor.capture(), captor.capture());

        assertThat(captor.getValue()).isEqualTo(quote1);
        assertThat(stringCaptor.getValue()).isEqualTo("quotes");
    }

    @Test
    public void sendToOutbound_sendsMessageContainingQuotePrice_toOutbound() {
        Bid newBid = Bid.builder()
                .symbol("ZYY")
                .bidPrice(BigDecimal.valueOf(201.56))
                .build();

        String destination = "stocks";

        quotePublisher.sendToOutbound(destination, newBid);

        verify(mockJmsTemplate).convertAndSend(destination, newBid);
    }

    @Test
    public void sendToOutbound_throwsQuoteMessageExceptionWithErrors_toErrorQueue() {
        Bid incomingBid = Bid.builder()
                .symbol("ZYY")
                .bidPrice(BigDecimal.valueOf(201.56))
                .build();

        doThrow(new RuntimeException("bad news")).when(mockJmsTemplate).convertAndSend(anyString(), any(QuotePrice.class));

        assertThatThrownBy(() -> quotePublisher.sendToOutbound("stocks", incomingBid))
                .isInstanceOf(QuoteMessageException.class)
                .hasMessage("Error Type: ROUTING, Domain: Publisher: ZYY  - sendToOutbound(), Message: bad news");
    }

    @Test
    public void send_failure_shouldThrowJmsExceptionIfSendFails() {

        doThrow(new RuntimeException("bad news")).when(mockJmsTemplate).convertAndSend(anyString(), any(Quote.class));

        assertThatThrownBy(() -> quotePublisher.send("quotes", quote1))
                .isInstanceOf(QuoteMessageException.class)
                .hasMessage("Error Type: ROUTING, Domain: Publisher: ABC  - send(), Message: bad news");
    }
}