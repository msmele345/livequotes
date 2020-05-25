package com.mitchmele.livequotes.jmsconsumer;

import com.mitchmele.livequotes.models.Quote;
import org.apache.activemq.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import javax.jms.JMSException;
import java.util.concurrent.CountDownLatch;

@Component
public class QuoteReceiver {

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return countDownLatch;
    }

    @JmsListener(destination = "${destination.quote}")
    public void receiveMessage(Message message) {
        System.out.println("RECEIVED QUOTE <" + message + ">");
        countDownLatch.countDown();
        //receive quotes from trade-publisher and store in sql?
    }
}
