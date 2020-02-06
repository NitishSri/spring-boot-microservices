package com.comparer.sendemailservice.service.implementation;

import org.json.JSONArray;
import org.json.JSONObject;

import com.comparer.sendemailservice.resourceobject.EmailContent;
import com.comparer.sendemailservice.service.SendEmail;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;

public class SendEmailImpl implements SendEmail {

	@Override
	public void sendEmail(EmailContent content) throws MailjetException, MailjetSocketTimeoutException {
		MailjetClient client;
		MailjetRequest request;
		MailjetResponse response;
		client = new MailjetClient("5824a2a68b17da97c5c4bdb7befb1999", "04b2b77ec86a5fb444c997261196822d");
		client.setDebug(MailjetClient.VERBOSE_DEBUG);
		request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES,
				new JSONArray().put(new JSONObject().put(Emailv31.Message.SENDER, content.getFromEmail())
						.put(Emailv31.Message.TO, content.getToEmail())
						.put(Emailv31.Message.SUBJECT, content.getSubject())
						.put(Emailv31.Message.TEXTPART, content.getContentBody())
						.put(Emailv31.Message.HTMLPART,
								"<h3>Dear user, Welcome to comparer. May the delivery force be with you!</h3>")
						.put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
		response = client.post(request);
		System.out.println(request);
		System.out.println(response.getStatus());
		System.out.println(response.getData());
	}

}
