package com.merlin.clinic.jms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJms
public class ClinicJmsConsumerTest {

	@SpyBean
	private ClinicJmsProducer clinicJmsProducer;

	@SpyBean
	private ClinicJmsConsumer clinicJmsConsumer;

	@Test
	public void testJmsConsumer() {
		String message = "test message";
		clinicJmsProducer.send("clinics", "patient.create.success", message);
		Mockito.verify(clinicJmsConsumer).patientCreateSuccess(message);
	}

}