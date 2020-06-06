package com.mitchmele.livequotes.jmsconsumer;

import com.mitchmele.livequotes.models.Quote;
import com.mitchmele.livequotes.services.OutboundQuoteOrchestrator;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuoteListenerTest {

    @Mock
    OutboundQuoteOrchestrator mockOutboundQuoteOrchestrator;

    @InjectMocks
    QuoteListener quoteListener;

    @Test
    public void onMessage_shouldConsumeObjectMessageAndCallSplitterService() throws Exception {
        Quote incomingQuote = Quote.builder()
                .id(3)
                .symbol("UOQ")
                .bidPrice(BigDecimal.valueOf(17.82))
                .askPrice(BigDecimal.valueOf(18.01))
                .build();

        ActiveMQObjectMessage incomingMessage = mock(ActiveMQObjectMessage.class);
        when(incomingMessage.getObject()).thenReturn(incomingQuote);

        quoteListener.onMessage(incomingMessage);
        verify(mockOutboundQuoteOrchestrator).splitAndStorePrices(incomingQuote);
    }

    @Test
    public void onMessage_shouldThrowIllegalArgumentException_whenIncomingMessageIsNotObjectMsg() throws IOException {
        ActiveMQTextMessage incomingMessage = mock(ActiveMQTextMessage.class);

        assertThatThrownBy(() -> quoteListener.onMessage(incomingMessage))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("cannot be cast to org.apache.activemq.command.ActiveMQObjectMessage");

        verify(mockOutboundQuoteOrchestrator, times(0)).splitAndStorePrices(any());
    }
}