package com.merlin.patient.exception;

import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Component
public class JmsExceptionHandler implements ErrorHandler {

	@Override
	public void handleError(Throwable t) {

	}

}