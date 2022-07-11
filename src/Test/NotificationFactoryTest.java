package Test;

import Business.FactoryMethod.Notifica;
import Business.FactoryMethod.NotificationFactory;
import Model.Cliente;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NotificationFactoryTest {
    @Before
    public void setUp() {
        Cliente c = new Cliente();
    }

    @After
    public void tearDown(){
    }

    @Test
    public void getCanaleNotificaTest(){
        Cliente c = new Cliente();
        c.setCanalePreferito(NotificationFactory.TipoNotifica.SMS);
        String msg = "Test message";

        NotificationFactory factory = new NotificationFactory();
        Notifica n = factory.getCanaleNotifica(c.getCanalePreferito());

        Assert.assertNotNull(n);

        if (n != null) {
            n.setCliente(c);
            n.setMsg(msg);
            boolean esito = n.inviaNotifica();
            Assert.assertTrue(esito);
        }
    }
}
