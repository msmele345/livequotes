package com.mitchmele.livequotes.jmssender;

import com.mitchmele.livequotes.models.Quote;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;
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

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockJmsTemplate).send(stringArgumentCaptor.capture(), any());
        assertThat(stringArgumentCaptor.getValue()).isEqualTo("quotes");
    }

    @Test
    public void send_success_shouldCallJms_createObjectMessage() {
        quotePublisher.send("quotes", quote1);

        ArgumentCaptor<Quote> captor = ArgumentCaptor.forClass(Quote.class);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockJmsTemplate).convertAndSend(stringCaptor.capture(), captor.capture());

        assertThat(captor.getValue()).isEqualTo(quote1);
        assertThat(stringCaptor.getValue()).isEqualTo("quotes");
    }

    @Test
    public void send_failure_shouldThrowJmsExceptionIfSendFails() {
        doThrow(RuntimeException.class).when(mockJmsTemplate).send(anyString(), any());

        assertThatThrownBy(() -> quotePublisher.send("quotes", quote1))
                .isInstanceOf(RuntimeException.class);
    }
}