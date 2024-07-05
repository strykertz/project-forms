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

    final String username = "ze.petronni@hotmail.com";
    final String password = "(blackswan)";


    public void sendAipEmail(EmailSenderEntity emailSenderEntity) {
        Session session = getSession();

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("marcus-franco@live.com"));

            if(emailSenderEntity.isApproved()){
                message.setSubject("AIP Aprovado");
                message.setText("O seu AIP foi aprovado!" + emailSenderEntity.getObservacao());
            }

            if(emailSenderEntity.isRejected()){
                message.setSubject("AIP Rejeitado");
                message.setText("O seu AIP foi Rejeitado!" + emailSenderEntity.getObservacao());

            }
            if(emailSenderEntity.isInconsistent()){
                message.setSubject("AIP Inconsistente");
                message.setText("Foi identificado inconsistência no seu AIP!" + emailSenderEntity.getObservacao());

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