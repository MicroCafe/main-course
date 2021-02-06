package com.merlin.clinic.jms;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJms
public class ClinicJmsProducerTest {

	@Autowired
	private JmsTemplate jmsTemplate;

	@SpyBean
	private ClinicJmsProducer clinicJmsProducer;

	@Test
	public void testJmsProducer() {
		String message = "test message";
		clinicJmsProducer.send("patients", "clinic.update.success", message);
		String rcvMessage = (String) jmsTemplate.receiveAndConvert("patients");
		Assert.assertEquals(message, rcvMessage);
	}

}