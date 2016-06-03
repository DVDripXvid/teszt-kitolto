package xyz.codingmentor.ejb;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import javax.ejb.Asynchronous;
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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import xyz.codingmentor.entity.User;

/**
 *
 * @author Olivér
 */
@Stateless(name = "emailService")
@Asynchronous
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final int port = 587;
    private final String host = "smtp.gmail.com";
    private final String from = "TestFiller Registration";
    private final String username = "codingmentor.testfiller@gmail.com";
    private final String password = "javateam2";

    public void sendRegistrationEmail(User user, String appPath) {
        String body = getBodyByTemplate("email/template", user, appPath);
        sendEmail(user.getEmail(), 
                "Registration accepted on testfiller", 
                body, 
                "text/html; charset=utf-8");
    }

    public void sendEmail(String to, String subject, String body) {
        sendEmail(to, subject, body, "text/plane; charset=utf-8");
    }

    public void sendEmail(String to, String subject, String body, String type) {
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
            message.setContent(body, type);
            message.saveChanges();
            Transport.send(message);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

    private String getBodyByTemplate(String template, User user, String appPath) {

        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode("HTML");
        resolver.setSuffix(".html");
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        final Context context = new Context(Locale.ENGLISH);
        context.setVariable("fullname",
                user.getFirstName()
                + " "
                + user.getLastName());
        context.setVariable("password", user.getPassword());
        context.setVariable("link", appPath);

        final String html = templateEngine.process(template, context);
        return html;
    }

}
