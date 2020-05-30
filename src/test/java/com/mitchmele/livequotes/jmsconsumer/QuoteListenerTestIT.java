package com.mitchmele.livequotes.jmsconsumer;


import com.mitchmele.livequotes.jmssender.QuotePublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class QuoteListenerTestIT {

    @Autowired
    QuotePublisher quotePublisher;

    @Autowired
    QuoteListener quoteListener;

    @Test
    public void quoteReceiver_success_consumesAQuote() throws InterruptedException {
    }
}