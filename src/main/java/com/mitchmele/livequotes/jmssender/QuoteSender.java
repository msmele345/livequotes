package com.mitchmele.livequotes.jmssender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitchmele.livequotes.models.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


@Component
public class QuoteSender {

    private final Logger logger = LoggerFactory.getLogger(QuoteSender.class);

    @Autowired
    JmsTemplate jmsTemplate;

    private String jsonQuote;

    public void send(String destination, Quote quote) {
        logger.info("sending message='{}' to destination='{}'", quote.getSymbol(),
                destination);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonQuote = objectMapper.writeValueAsString(quote);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        jmsTemplate.send(destination, s -> s.createTextMessage(jsonQuote));
    }
}
