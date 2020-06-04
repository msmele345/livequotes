package com.mitchmele.livequotes.jmsconsumer;

import com.mitchmele.livequotes.models.Quote;
import com.mitchmele.livequotes.services.QuoteSplitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.stereotype.Component;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuoteListener implements MessageListener {

    private final QuoteSplitterService quoteSplitterService;

    @Override
    public void onMessage(Message message) {
        if (message instanceof ActiveMQMessage) {
            try {
                ActiveMQObjectMessage msg = (ActiveMQObjectMessage) message;
                Quote incomingQuote = (Quote) msg.getObject();

                log.info("CONSUMED MESSAGE: " + incomingQuote);
                quoteSplitterService.splitAndStorePrices(incomingQuote);
            } catch (Exception e) {
                throw new RuntimeException(parseException(e.getMessage()));
            }
        } else {
            throw new IllegalArgumentException("ActiveMQ Message Error");
        }
    }

    private String parseException(String message) {
        String[] msg = message.split(" ");
        String[] copied = Arrays.copyOfRange(msg, 1, msg.length);
        return Arrays.stream(copied).collect(Collectors.joining(" "));
    }
}
/*
TODO:
1. Setup Conditional error routing with ActiveMq
2. Loader from sql server to MongoDb?
*/