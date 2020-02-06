package com.comparer.sendemailservice.service;

import org.springframework.stereotype.Component;

import com.comparer.sendemailservice.resourceobject.EmailContent;
import com.comparer.sendemailservice.resourceobject.ForgotPasswordRO;

@Component
public interface EmailConsumerService {

	public void listen(byte[] message);
	public EmailContent prepareEmail(ForgotPasswordRO forPassmessage);
}

