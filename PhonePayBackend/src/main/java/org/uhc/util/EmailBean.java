package org.uhc.util;

import java.util.ArrayList;
import java.util.List;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmailBean {
	private static final Logger LOG = LogManager.getLogger(EmailBean.class.getName());
	private final List<InternetAddress> recipients = new ArrayList<>();
	private String subject = "";
	private String messageBody = "";

	public EmailBean() {
	}

	public void addRecipient(String recipient) {
		try {
			recipients.add(new InternetAddress(recipient));
		} catch (AddressException ex) {
			LOG.error("Problem adding email address: " + recipient, ex);
		}
	}

	public InternetAddress[] getRecipients() {
		return (InternetAddress[]) recipients.toArray(new InternetAddress[recipients.size()]);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
}
