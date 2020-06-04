package com.mitchmele.livequotes.jmsconsumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JmsQuoteErrorHandlerTest {

    @Mock
    JmsTemplate mockJmsTemplate;

    @InjectMocks
    JmsQuoteErrorHandler jmsQuoteErrorHandler;

    @Test
    public void handleError_shouldSendMessageToErrorQueue() {
        Throwable mockThrowable = mock(Throwable.class);

        String expectedErrorMessage = "Error with this message";
        when(mockThrowable.getLocalizedMessage()).thenReturn(expectedErrorMessage);

        jmsQuoteErrorHandler.handleError(mockThrowable);

        verify(mockJmsTemplate).convertAndSend("errors", expectedErrorMessage);
    }
}