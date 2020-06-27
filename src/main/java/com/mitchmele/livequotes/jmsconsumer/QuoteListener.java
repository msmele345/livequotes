package com.mitchmele.livequotes.jmsconsumer;

import com.mitchmele.livequotes.common.QuoteErrorType;
import com.mitchmele.livequotes.common.QuoteMessageError;
import com.mitchmele.livequotes.common.QuoteMessageException;
import com.mitchmele.livequotes.models.Quote;
import com.mitchmele.livequotes.services.OutboundQuoteOrchestrator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuoteListener implements MessageListener {

    private final OutboundQuoteOrchestrator outboundQuoteOrchestrator;

    @Override
    public void onMessage(Message message) {
        if (message instanceof ActiveMQMessage) {
            try {
                ActiveMQObjectMessage msg = (ActiveMQObjectMessage) message;
                Quote incomingQuote = (Quote) msg.getObject();

                log.info("CONSUMED MESSAGE: " + incomingQuote);
                outboundQuoteOrchestrator.orchestrate(incomingQuote);
            } catch (Exception e) {
                QuoteMessageError quoteMessageError = QuoteMessageError.builder()
                        .cause(e)
                        .quoteErrorType(QuoteErrorType.ROUTING)
                        .exMessage(parseException(e.getLocalizedMessage()))
                        .domain("Quote Listener")
                        .build();
                log.info("QUOTE MESSAGE ERROR IN DOMAIN: " + quoteMessageError.getDomain());
                throw new QuoteMessageException(quoteMessageError);
            }
        } else {
            QuoteMessageError quoteMessageError = QuoteMessageError.builder()
                    .quoteErrorType(QuoteErrorType.ROUTING)
                    .domain("INVALID INBOUND MESSAGE TYPE (NON ACTIVE MQ)")
                    .build();
            throw new QuoteMessageException(quoteMessageError);
        }
    }

    private String parseException(String message) {
        String[] msg = message.split(" ");
        String[] copied = Arrays.copyOfRange(msg, 1, msg.length);
        return String.join(" ", copied);
    }
}

/*
TODO:
1. Setup error Log service and repo to store error messages
3. Throw custom exceptions instead of runtime and have it route to a DLQ
4. Setup Conditional error routing with ActiveMq
*/