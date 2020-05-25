package com.mitchmele.livequotes.jmsconsumer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

@Service
public class JmsQuoteErrorHandler implements ErrorHandler {

    private Logger logger = LoggerFactory.getLogger(JmsQuoteErrorHandler.class);

    @Override
    public void handleError(Throwable t) {
        logger.info("ERROR ON JMS: " + t.getLocalizedMessage());
    }
}
