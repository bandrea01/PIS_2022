package Business.FactoryMethod;

public class NotificaEmail extends Notifica{
    @Override
    public boolean inviaNotifica() {
        System.out.println("Email notification");
        return true;
    }
}
