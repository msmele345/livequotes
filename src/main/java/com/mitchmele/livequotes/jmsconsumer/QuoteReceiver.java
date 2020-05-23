package com.mitchmele.livequotes.jmsconsumer;

import com.mitchmele.livequotes.models.Quote;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class QuoteReceiver {


    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return countDownLatch;
    }

    @JmsListener(destination = "${queue.boot}")
    public void receiveMessage(Quote quote) {
        System.out.println("RECEIVED QUOTE <" + quote + ">");
        countDownLatch.countDown();
        //receive quotes from trade-publisher and store in sql?
    }
}
