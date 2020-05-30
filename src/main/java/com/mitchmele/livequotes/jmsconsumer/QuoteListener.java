package com.mitchmele.livequotes.jmsconsumer;

import com.mitchmele.livequotes.services.QuoteSplitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.stereotype.Component;
import javax.jms.Message;
import javax.jms.MessageListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuoteListener implements MessageListener {

    private final QuoteSplitterService quoteSplitterService;

    @Override
    public void onMessage(Message message) {
        if (message instanceof ActiveMQMessage) {
            ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
            try {
                String text = msg.getText();
                log.info("CONSUMED MESSAGE: " + text);
                quoteSplitterService.splitAndStorePrices(text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("ActiveMQ Message Error");
        }
    }
}
/*
TODO:
1. Write tests for listener and errorHandler
3. Loader from sql server to MongoDb?

//Alternative listener approach:
@JmsListener(destination = "${destination.quote}") //second way that works on top of any method
ActiveMQMapMessage mapMessage = (ActiveMQMapMessage) message;
Map<String, Object> propertiesMap;
propertiesMap = mapMessage.getContentMap();

private final CountDownLatch countDownLatch = new CountDownLatch(1);
public CountDownLatch getLatch() {
return countDownLatch}
countDownLatch.countDown();
*/