package org.uhc.util;

import java.util.Date;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
	private static final Logger LOG = LogManager.getLogger(EmailService.class.getName());

	private Properties properties = System.getProperties();
	
	@Value("${homeowner.smtp.username}")
	private String smtpUsername;
	
	@Value("${homeowner.smtp.host}")
	private String smtpHost;

	public EmailService() {
	}

	public void sendEmail(EmailBean emailBean) {
		try {
			MimeMessage message = new MimeMessage(Session.getInstance(properties));
			properties.setProperty("mail.smtp.host", smtpHost);
			message.setFrom(new InternetAddress(smtpUsername));
			message.setRecipients(javax.mail.Message.RecipientType.TO, emailBean.getRecipients());
			message.setSentDate(new Date());
			message.setSubject(emailBean.getSubject());
			message.setContent(emailBean.getMessageBody(), "text/html; charset=utf-8");
			javax.mail.Transport.send(message);
			LOG.info("Success - " + getLogMessage(emailBean));
		} catch (MessagingException ex) {
			LOG.error("Problem - " + getLogMessage(emailBean), ex);
		}
	}

	private String getLogMessage(EmailBean emailBean) {
		String sendTo = "";

		if (emailBean.getRecipients() == null) {
			sendTo = "null";
		} else {
			for (InternetAddress recipient : emailBean.getRecipients()) {
				sendTo = sendTo + recipient.toString() + ". ";
			}
		}
		return "Email: subject=" + emailBean.getSubject() + ", sendTo=" + sendTo;
	}

}