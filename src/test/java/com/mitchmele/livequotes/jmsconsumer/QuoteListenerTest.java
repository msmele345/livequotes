package com.mitchmele.livequotes.jmsconsumer;

import com.mitchmele.livequotes.common.QuoteMessageException;
import com.mitchmele.livequotes.models.Quote;
import com.mitchmele.livequotes.services.OutboundQuoteOrchestrator;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.jms.Message;
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
        verify(mockOutboundQuoteOrchestrator).orchestrate(incomingQuote);
    }

    @Test
    public void onMessage_throwsQuoteMessageException_whenIncomingMessageIsNotObjectMsg() throws IOException {
        ActiveMQTextMessage incomingInvalidMessageType = mock(ActiveMQTextMessage.class);

        assertThatThrownBy(() -> quoteListener.onMessage(incomingInvalidMessageType))
                .isInstanceOf(QuoteMessageException.class)
                .hasMessage("Error Type: ROUTING, Domain: Quote Listener, Message: cannot be cast to org.apache.activemq.command.ActiveMQObjectMessage");

        verify(mockOutboundQuoteOrchestrator, times(0)).orchestrate(any());
    }

    @Test
    public void onMessage_throwsQuoteMessageExceptionWithGenericMessage_whenIncomingMsgIsNotFromActiveMq() {
        Message msg = mock(Message.class);

        assertThatThrownBy(() -> quoteListener.onMessage(msg))
                .isInstanceOf(QuoteMessageException.class)
                .hasMessage("Error Type: ROUTING, Domain: INVALID INBOUND MESSAGE TYPE (NON ACTIVE MQ), Message: null");
    }
}