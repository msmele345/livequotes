package com.mitchmele.livequotes.services;

import com.mitchmele.livequotes.jmssender.QuoteSender;
import com.mitchmele.livequotes.models.Quote;
import com.mitchmele.livequotes.sqlserver.QuoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuoteLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger = LoggerFactory.getLogger(QuoteLoader.class);

    QuoteProcessor quoteProcessor;
    QuoteRepository quoteRepository;
    QuoteSender quoteSender;

    @Autowired
    public void setQuoteProcessor(QuoteProcessor quoteProcessor) {
        this.quoteProcessor = quoteProcessor;
    }

    @Autowired
    public void setQuoteRepository(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Autowired
    public void setSender(QuoteSender quoteSender) {
        this.quoteSender = quoteSender;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<Quote> newQuotes = quoteProcessor.generateQuotes();
        try {
            quoteRepository.saveAll(newQuotes);
            newQuotes.forEach(q -> {
                logger.info("SAVED QUOTE ID: " +  q.getId() + "FOR SYMBOL: " + q.getSymbol());
//                quoteSender.send("quotes", q.getSymbol());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
