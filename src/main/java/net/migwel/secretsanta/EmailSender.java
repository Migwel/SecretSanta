package net.migwel.secretsanta;

import org.springframework.stereotype.Component;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public final class EmailSender {

    public void send(String santaEmailAddress, SecretSanta secretSanta) {

        final String username = "USER";
        final String password = "PASSWORD";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("EMAILADDRESS"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(santaEmailAddress));
            message.setSubject("Secret Santa");
            String secretSantaName = secretSanta.getSecretSanta().getName();
            String receivingParticipantName = secretSanta.getReceiver().getName();
            message.setText("Hello "+ secretSantaName +","
                    + "\n\n You are the secret santa for "+ receivingParticipantName +"."
                    + "\n\n\n\n\n\n Salut "+ secretSantaName +","
                    + "\n\n Tu es le Père Noel secret de "+ receivingParticipantName +".");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}