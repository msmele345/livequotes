package com.mitchmele.livequotes.jmssender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitchmele.livequotes.models.Quote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuotePublisher {

    private final JmsTemplate jmsTemplate;

    private String jsonQuote;

    public QuotePublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(String destination, Quote quote)  {
        log.info("JMS Sending Message='{}' to destination='{}'", quote.getSymbol(),
                destination);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonQuote = objectMapper.writeValueAsString(quote);
//            jmsTemplate.send(destination, s -> s.createTextMessage(jsonQuote));
            jmsTemplate.convertAndSend(destination, quote);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
