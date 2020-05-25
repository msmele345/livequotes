package com.mitchmele.livequotes.jmssender;

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

    public void send(String destination, String message) {
        logger.info("sending message='{}' to destination='{}'", message,
                destination);
        jmsTemplate.convertAndSend(message);
    }
}
