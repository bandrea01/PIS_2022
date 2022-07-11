package Business.FactoryMethod;

public class NotificaSMS extends Notifica{
    @Override
    public boolean inviaNotifica() {
        System.out.println("SMS notification");
        return true;
    }
}
