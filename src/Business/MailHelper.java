package Business;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

public class MailHelper {

    private static MailHelper instance;

    private static String FROM = "pisjava3@gmail.com";
    private static String PASSWORD = "atemayvjbfqtnbzh";

    public static synchronized MailHelper getInstance() {
        if(instance == null)
            instance = new MailHelper();
        return instance;
    }

    public void send(String to,String sub,String msg, File allegato){
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
            // Crea un oggetto `Message` per comporre l'email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(sub);

            // Crea la parte del messaggio testuale
            MimeBodyPart testo = new MimeBodyPart();
            testo.setText(msg);

            // Crea la parte dell'allegato
            MimeBodyPart sezioneAllegato = new MimeBodyPart();
            if (allegato != null) {
                DataSource source = new FileDataSource(allegato.getAbsolutePath());
                sezioneAllegato.setDataHandler(new DataHandler(source));
                sezioneAllegato.setFileName(allegato.getName());
            }

            // Crea il contenitore per le parti del messaggio
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(testo);
            if (allegato != null) {
                multipart.addBodyPart(sezioneAllegato);
            }
            // Imposta il contenuto del messaggio come il contenitore multipart
            message.setContent(multipart);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("message not sent");
        }

    }

}
