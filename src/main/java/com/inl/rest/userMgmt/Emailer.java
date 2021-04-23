package com.inl.rest.userMgmt;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.sun.mail.smtp.SMTPTransport;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Date;
import java.util.Properties;

public class Emailer {
    @Autowired
	private Environment env;

    private static final String EMAIL_FROM = "noreply@socialdecisionmakinglaboratory.com";
    private static final String EMAIL_SUBJECT = "INL lab site password reset";

    private static final String messageBody = "This is an email from the INL lab password resetter.\n\n\n";
    
    public String sendMail(String toAddress, String username, String newPassword){
        
        String smtpServer = env.getRequiredProperty("emailer.smtpServer");
        String emailerUsername = env.getRequiredProperty("emailer.username");
        String emailerPassword = env.getRequiredProperty("emailer.password");
        
        Properties properties = System.getProperties();
        
        properties.put("mail.smtp.host", smtpServer);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        
        Session session = Session.getInstance(properties, null);
        Message msg = new MimeMessage(session);
        
        String formattedBody = (new StringBuilder())
            .append(this.messageBody)
            .append("Your username is:")
            .append("\n")
            .append(username)
            .append("\n")
            .append("Your new generated password is:")
            .append("\n")
            .append(newPassword)
            .append("\n")
            .append("\n")
            .append("\n")
            .append("PLEASE login and change your password as soon as possible for security.")
            .toString();
        
        try {
            msg.setFrom(new InternetAddress(EMAIL_FROM));
            
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toAddress, false));

            msg.setSubject(EMAIL_SUBJECT);
            
            msg.setText(formattedBody);
            
            msg.setSentDate(new Date());

            SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");
            
            transport.connect(smtpServer, emailerUsername, emailerPassword);
            
            transport.sendMessage(msg, msg.getAllRecipients());

            String response = transport.getLastServerResponse();
            System.out.println("Response: " + response);

            transport.close();
            
            return response;

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "email not sent!";
    }
    
    
}