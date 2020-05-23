package com.mitchmele.livequotes.jmsconsumer;


import com.mitchmele.livequotes.jmssender.QuoteSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class QuoteReceiverTest {

    @Autowired
    QuoteSender quoteSender;

    @Autowired
    QuoteReceiver quoteReceiver;

    @Test
    public void quoteReceiver_success_consumesAQuote() throws InterruptedException {
        quoteSender.send("boot.q", "Hello Boot!");

        quoteReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

}