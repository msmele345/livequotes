package com.mitchmele.livequotes.jmsconsumer;

import org.apache.activemq.Message;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
public class QuoteReceiver implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(QuoteReceiver.class);

    @Autowired
    JmsTemplate jmsTemplate;

    @JmsListener(destination = "${destination.quote}")
    public void receiveMessage(Message message) {
        if (message instanceof ActiveMQMessage) {
            ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
            String text = null;
            try {
                text = msg.getText();
                logger.info("CONSUMED MESSAGE: " + text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Message Error");
        }
    }

    @Override
    public void onMessage(javax.jms.Message message) {
        if (message instanceof TextMessage) {
            try {
                String msg = ((TextMessage) message).getText();
                logger.info("CONSUMED MESSAGE: " + msg);
            } catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            throw new IllegalArgumentException("Message Error");
        }
    }
}


/*

TODO:
1. receive quotes from trade-publisher and store in sql?
2. Add tests for JMS components
3. Remove MessageListener + onMessage?
4. Loader from sql server to MongoDb?

ActiveMQMapMessage mapMessage = (ActiveMQMapMessage) message;
Map<String, Object> propertiesMap;
propertiesMap = mapMessage.getContentMap();

private final CountDownLatch countDownLatch = new CountDownLatch(1);
public CountDownLatch getLatch() {
return countDownLatch}
countDownLatch.countDown();

*/