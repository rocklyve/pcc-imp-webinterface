package edu.kit.informatik.pcc.webinterface.mailservice;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by chris on 17.01.2017.
 *
 * This class has the method to send an email
 */
public class MailService {

    /**
     * The method to send an email. It gets the SMTP properties from mail properties file.
     *
     * @param from    from mail
     * @param to      to mail
     * @param subject subject line
     * @param text    mail body
     * @throws EmailException
     * @throws IOException
     */
    public static void send(String from, String to, String subject, String text)
            throws EmailException, IOException {

        // check for null references
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);

        // load email configuration from properties file
        Properties properties = new Properties();
        properties.load(MailService.class.getResourceAsStream("/mail.properties"));
        String host = properties.getProperty("mail.smtp.host");
        String port = properties.getProperty("mail.smtp.port");
        String ssl = properties.getProperty("mail.smtp.ssl.enable");
        String username = properties.getProperty("mail.smtp.username");
        String password = properties.getProperty("mail.smtp.password");

        // create an email message with html support
        HtmlEmail email = new HtmlEmail();

        // configure SMTP connection
        email.setHostName(host);
        email.setSmtpPort(Integer.parseInt(port));
        email.setAuthentication(username, password);
        email.setSSLOnConnect(Boolean.parseBoolean(ssl));

        // set its properties accordingly
        email.setFrom(from);
        email.addTo(to);
        email.setSubject(subject);
        email.setHtmlMsg(text);

        // send it!
        email.send();
    }
}
