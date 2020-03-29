package com.comparer.sendemailservice.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.comparer.sendemailservice.resourceobject.EmailContent;
import com.comparer.sendemailservice.resourceobject.ForgotPasswordRO;
import com.comparer.sendemailservice.service.EmailConsumerService;
import com.comparer.sendemailservice.service.SendEmail;
import com.google.gson.Gson;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;

@Component
public class EmailConsumerServiceImpl implements EmailConsumerService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void listen(byte[] message) {
		String msg = new String(message);
		ForgotPasswordRO forPassmessage = new Gson().fromJson(msg, ForgotPasswordRO.class);
		EmailContent content = prepareEmail(forPassmessage);
		SendEmail emailSend = new SendEmailImpl();
		try {
			emailSend.sendEmail(content);
		} catch (MailjetException e) {
			logger.error("Error in send email");
			e.printStackTrace();
		} catch (MailjetSocketTimeoutException e) {
			logger.error("Error in send email");
			e.printStackTrace();
		}

	}

	@Override
	public EmailContent prepareEmail(ForgotPasswordRO forPassmessage) {
		EmailContent content = new EmailContent();
		content.setFromEmail("admin@abc.com");
		content.setToEmail(forPassmessage.getEmail());
		content.setSubject("Test Email");
		content.setContentBody("This is a test email");
		return content;
	}

}
