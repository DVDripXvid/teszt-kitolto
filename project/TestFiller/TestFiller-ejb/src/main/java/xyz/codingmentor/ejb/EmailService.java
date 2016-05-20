package xyz.codingmentor.ejb;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.codingmentor.entity.User;

/**
 *
 * @author Olivér
 */
@Stateless(name = "emailService")
public class EmailService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final int port = 587;
    private final String host = "smtp.gmail.com";
    private final String from = "TestFiller Registration";
    private final String username = "codingmentor.testfiller@gmail.com";
    private final String password = "javateam2";
    private final String regSubject = "registration accepted on testfiller";
    private final String regBody = ",\nyour password is: ";
    
    public void sendRegistrationEmail(User user){
        String body = "Dear " + user.getFirstName() + regBody + user.getPassword();
        sendEmail(user.getEmail(), regSubject, body);
    }

    public void sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.starttls.enable", true);

        props.put("mail.smtp.auth", true);
        Authenticator authenticator = new Authenticator() {
            private final PasswordAuthentication pa = new PasswordAuthentication(username, password);

            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return pa;
            }
        };

        Session session = Session.getInstance(props, authenticator);

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(username, from));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

}
