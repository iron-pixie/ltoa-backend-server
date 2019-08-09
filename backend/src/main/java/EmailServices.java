package backend;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

    public String sendMailAccess(String Subject, String SendMessage, String emailAddress) {
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
        String to = emailAddress;

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

    public String selectMail(String userName)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/users", "test", "testtest");
            Statement stmt = con.createStatement();
            String queryString = "select * from Users where userName = '" + userName + "'";
            ResultSet rs = stmt.executeQuery(queryString);
            rs.next();
            String name = rs.getString(1);

            Connection con2 = DriverManager.getConnection("jdbc:mysql://aadnxib9b7f6cj.cebbknh24dty.us-west-2.rds.amazonaws.com:3306/members", "test", "testtest");
            Statement stmt2 = con2.createStatement();
            String queryString2 = "select * from Members where memberName = '" + name + "'";
            ResultSet rs2 = stmt2.executeQuery(queryString2);
            rs2.next();
            return rs2.getString(4);

        }
        catch(Exception e)
        {
            return "Error" + e.toString();
        }
    }
    }