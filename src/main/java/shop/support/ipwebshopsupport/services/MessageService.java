package shop.support.ipwebshopsupport.services;

import shop.support.ipwebshopsupport.beans.MessageBean;
import shop.support.ipwebshopsupport.DAO.MessageDAO;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class MessageService {

    public List<MessageBean> getAll() {
        return MessageDAO.getAllMessages();
    }

    public boolean sendEmail(String to, String messageText) {
        final String senderEmail = "ipwebshopproject@gmail.com";
        final String senderPassword = "fxlnxnwdczutfycy";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Support");
            message.setText(messageText);

            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setSeen(Integer messageId){
        return MessageDAO.setSeen(messageId);
    }
}
