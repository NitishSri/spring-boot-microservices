package com.comparer.sendemailservice.service;

import com.comparer.sendemailservice.resourceobject.EmailContent;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;

public interface SendEmail {

	public void sendEmail(EmailContent content) throws MailjetException, MailjetSocketTimeoutException;
}
