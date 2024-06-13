package br.com.dataprev.service;

import javax.mail.*;

import br.com.dataprev.entity.EmailSenderEntity;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailSenderService {

    final String username = "marcus-franco@live.com";
    final String password = "(blackswan)";


    public void sendAipEmail(EmailSenderEntity emailSenderEntity) {


        Session session = getSession();

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("marcusvgf16@gmail.com"));

            if(("sim").equals(emailSenderEntity.getApproved())){
                message.setSubject("AIP Aprovado");
                message.setText("O seu AIP foi aprovado!");
            }

            if(("sim").equals(emailSenderEntity.getRejected())){
                message.setSubject("AIP Rejeitado");
                message.setText("O seu AIP foi Rejeitado!");

            }
            if(("sim").equals(emailSenderEntity.getRejected())){
                message.setSubject("AIP Inconsistente");
                message.setText("Foi identificado inconsistência no seu AIP!");

            }

            Transport.send(message);

    } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        session.setDebug(true);
        return session;
    }
}