package com.merlin.clinic.config;

import javax.jms.ConnectionFactory;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import com.merlin.clinic.exception.JmsExceptionHandler;

@Profile("dev")
@Configuration
@EnableJms
public class JmsConfig {

	@Bean
	public ConnectionFactory jmsConnectionFactory() {
		return new JmsConnectionFactory();
	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(jmsConnectionFactory());
		factory.setErrorHandler(new JmsExceptionHandler());
		return factory;
	}

}
