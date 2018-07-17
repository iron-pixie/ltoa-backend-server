package backend;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailServices {

    public String sendTestMail() {
        Properties props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        final String username = "admin@ironpixietechnologies.com";//
        final String password = "Ironpixie8";

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        // Recipient's email ID needs to be mentioned.
        String from = "admin@ironpixietechnologies.com";

        // Sender's email ID needs to be mentioned
        String to = "stephen.harb@ironpixietechnologies.com";

        // Get the default Session object.
        //  Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("This is actual message");

            Transport.send(message);

            return "Sent message successfully....";
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return "Message Not Sent";
    }

    public String sendMailAccess(String Subject, String SendMessage) {
        Properties props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        final String username = "admin@ironpixietechnologies.com";//
        final String password = "Ironpixie8";

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        // Recipient's email ID needs to be mentioned.
        String from = "admin@ironpixietechnologies.com";

        // Sender's email ID needs to be mentioned
        String to = "stephen.harb@ironpixietechnologies.com";

        // Get the default Session object.
        //  Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(Subject);

            // Now set the actual message
            message.setText(SendMessage);

            Transport.send(message);

            return "Sent message successfully....";
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return "Message Not Sent";
    }
    }