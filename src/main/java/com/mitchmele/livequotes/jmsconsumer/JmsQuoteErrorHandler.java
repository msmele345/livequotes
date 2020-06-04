package com.mitchmele.livequotes.jmsconsumer;


import com.mitchmele.livequotes.jmssender.QuotePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class JmsQuoteErrorHandler implements ErrorHandler {

    @Value("${destination.error}")
    private String errors;

    private final JmsTemplate jmsTemplate;

    @Override
    public void handleError(Throwable t) {
        log.info("ERROR ON JMS: " + t.getLocalizedMessage());
        jmsTemplate.convertAndSend("errors", t.getLocalizedMessage());
    }
}
