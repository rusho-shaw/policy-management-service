package com.cts.application.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cts.application.exception.PolicyServiceException;
import com.cts.application.exception.response.PolicyExceptionResponse;

@ControllerAdvice
public class PolicyExceptionHandler {

	@ExceptionHandler(PolicyServiceException.class)
	public ResponseEntity<PolicyExceptionResponse> handlePolicyException(
			PolicyServiceException pse) {
		PolicyExceptionResponse response = new PolicyExceptionResponse();
		response.setErrorMessage(pse.getMessage());
		response.setStatus("0");
		System.out.println("####Here here #######");
		return new ResponseEntity<PolicyExceptionResponse> (response, HttpStatus.NOT_FOUND);
	}
}
