package com.mariamkatamashvili.trainerwork.config.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.HashMap;
import java.util.Map;

@EnableJms
@Configuration
@RequiredArgsConstructor
public class JmsConfig {

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(
            CachingConnectionFactory connectionFactory,
            MessageConverter messageConverter
    ) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setPubSubDomain(false);
        return factory;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper);

        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("WorkloadDTO", com.mariamkatamashvili.trainerwork.dto.WorkloadDTO.class);
        converter.setTypeIdMappings(typeIdMappings);

        return converter;
    }

    @Bean
    public JmsTemplate jmsTemplate(CachingConnectionFactory connectionFactory, MessageConverter messageConverter) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setMessageConverter(messageConverter);
        return jmsTemplate;
    }
}