package za.co.discovery.banksystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailService {

  private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

  public static void sendEmail(String fromEmail, String toEmails, String subject, String body, String attachment) {
    /*
    Out of scope
     */
    LOG.info("sendEmail: email sent");
  }
}