package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.jmssender.QuotePublisher;
import com.mitchmele.livequotes.models.Quote;
import com.mitchmele.livequotes.sqlserver.QuoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
public class QuoteLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${destination.quote}")
    String defaultDestination;

    private QuoteProcessor quoteProcessor;
    private QuoteRepository quoteRepository;
    private QuotePublisher quotePublisher;

    @Autowired
    public void setQuoteProcessor(QuoteProcessor quoteProcessor) {
        this.quoteProcessor = quoteProcessor;
    }

    @Autowired
    public void setQuoteRepository(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Autowired
    public void setSender(QuotePublisher quotePublisher) {
        this.quotePublisher = quotePublisher;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<Quote> newQuotes = quoteProcessor.generateQuotes();
        try {
            quoteRepository.saveAll(newQuotes);
            newQuotes.forEach(q -> {
                log.info("SUCCESSFULLY SAVED QUOTE WITH ID: " + q.getId() + " FOR SYMBOL: " + q.getSymbol());
                quotePublisher.send("quotes", q);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
