package Business;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailHelper {

    private static MailHelper instance;

    private static String FROM = ".................";
    private static String PASSWORD = ".................";

    public static synchronized MailHelper getInstance() {
        if(instance == null)
            instance = new MailHelper();
        return instance;
    }

    public static void main(String args[]) {

        new MailHelper().send("...................", "oggetto", "msg di test");
    }

    public void send(String destinatario, String oggetto, String messaggio){
        //Get properties object
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "host");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("java.net.preferIPv4Stack" , "true");

        //get Session
        Session session = Session.getInstance(properties, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM,PASSWORD);
                    }
                });
        //Debug SMTP
        session.setDebug(true);
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
