package com.merlin.clinic.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.test.context.TestPropertySource;

import com.merlin.clinic.exception.JmsExceptionHandler;

@Profile("test")
@TestConfiguration
@TestPropertySource
public class TestJmsConfig {

	@Bean
	public DefaultJmsListenerContainerFactory jmsContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setErrorHandler(new JmsExceptionHandler());
		return factory;
	}

}
