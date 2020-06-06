package com.mitchmele.livequotes.config;


import com.mitchmele.livequotes.jmsconsumer.JmsQuoteErrorHandler;
import com.mitchmele.livequotes.jmsconsumer.QuoteListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import javax.jms.Destination;
import java.util.Arrays;

@Configuration
@EnableJms
public class JmsConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${destination.quote}")
    private String quotes;

    @Value("${destination.error}")
    private String errors;

    @Value("${destination.outbound}")
    private String stocks;


    @Bean
    public ActiveMQConnectionFactory senderActiveMqConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setTrustAllPackages(true);
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        return activeMQConnectionFactory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(senderActiveMqConnectionFactory());
        cachingConnectionFactory.setSessionCacheSize(10);
        return cachingConnectionFactory;
    }

    @Bean
    Destination quoteDestination() {
        return new ActiveMQQueue(quotes);
    }

    @Bean
    Destination errorDestination() {
        return new ActiveMQQueue(errors);
    }

    @Bean
    Destination outboundDestination() {
        return new ActiveMQQueue(stocks);
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory());
        jmsTemplate.setDefaultDestination(quoteDestination());
        jmsTemplate.setReceiveTimeout(5000);
        return jmsTemplate;
    }

    @Bean
    DefaultMessageListenerContainer defaultMessageListenerContainer(
            QuoteListener quoteListener,
            JmsQuoteErrorHandler jmsQuoteErrorHandler
    ) {
        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
        defaultMessageListenerContainer.setMessageListener(quoteListener);
        defaultMessageListenerContainer.setConnectionFactory(cachingConnectionFactory());
        defaultMessageListenerContainer.setMessageConverter(jacksonJmsMessageConverter());
        defaultMessageListenerContainer.setErrorHandler(jmsQuoteErrorHandler);
        defaultMessageListenerContainer.setDestinationName(quotes);
        return defaultMessageListenerContainer;
    }


    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
//        converter.setTypeIdPropertyName("_id");
        return converter;
    }

}
