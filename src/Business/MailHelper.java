package Business;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class MailHelper {

    private static MailHelper instance;

    private static String FROM = "pisjava3@gmail.com";
    private static String PASSWORD = "atemayvjbfqtnbzh";

    public static synchronized MailHelper getInstance() {
        if(instance == null)
            instance = new MailHelper();
        return instance;
    }
    /** Test */
    /*
    public static void main(String args[]) {

        new MailHelper().send("andrea.barone1401@gmail.com", "oggetto", "msg di test");
    }
     */

    public void send(String to,String sub,String msg){
        //Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM,PASSWORD);
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("message not sent");
        }

    }

}
