package com.mitchmele.livequotes.jmssender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitchmele.livequotes.common.QuoteErrorType;
import com.mitchmele.livequotes.common.QuoteMessageError;
import com.mitchmele.livequotes.common.QuoteMessageException;
import com.mitchmele.livequotes.models.Quote;
import com.mitchmele.livequotes.models.QuotePrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import javax.management.JMException;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuotePublisher {

    private final JmsTemplate jmsTemplate;

    public void send(String destination, Quote quote)  {
        log.info("JMS Sending Message='{}' to destination='{}'", quote.getSymbol(),
                destination);
        try {
            jmsTemplate.convertAndSend(destination, quote);
        } catch (Exception e) {
            log.info("OUTBOUND JMS ERROR FOR: " + quote.getSymbol() + "EN ROUTE TO: ", destination);
            QuoteMessageError error = QuoteMessageError.builder()
                    .quoteErrorType(QuoteErrorType.ROUTING)
                    .cause(e)
                    .exMessage(e.getLocalizedMessage())
                    .domain("Publisher: " + quote.getSymbol() + " " + " - send()")
                    .build();
            throw new QuoteMessageException(error);
        }
    }

    public void sendToOutbound(String destination, QuotePrice quotePrice) {
        log.info("OUTBOUND JMS Sending Message='{}' to destination='{}'", quotePrice.getSymbol(),
                destination);
        try {
            jmsTemplate.convertAndSend(destination, quotePrice);
        } catch (Exception e) {
            log.info("OUTBOUND JMS ERROR FOR: " + quotePrice.getSymbol() + "EN ROUTE TO: ", destination);
            QuoteMessageError error = QuoteMessageError.builder()
                    .quoteErrorType(QuoteErrorType.ROUTING)
                    .cause(e)
                    .exMessage(e.getLocalizedMessage())
                    .domain("Publisher: " + quotePrice.getSymbol() + " " + " - sendToOutbound()")
                    .build();
           throw new QuoteMessageException(error);
        }
    }
}
