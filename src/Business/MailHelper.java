package Business;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailHelper {

    private static MailHelper instance;

    private static String FROM = "pis.myshopjava@gmail.com";
    private static String PASSWORD = "pismyshop";

    public static synchronized MailHelper getInstance() {
        if(instance == null)
            instance = new MailHelper();
        return instance;
    }

    public static void main(String args[]) {

        new MailHelper().send("andrea.barone1401@gmail.com", "oggetto", "msg di test");
    }

    public void send(String destinatario, String oggetto, String messaggio){
        //Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        //get Session
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM,PASSWORD);
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(destinatario));
            message.setSubject(oggetto);
            message.setText(messaggio);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("message not sent");
        }

    }

}
