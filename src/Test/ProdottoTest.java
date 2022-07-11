package Test;

import Model.Prodotto;
import Model.ProdottoComposito;
import org.junit.Assert;
import org.junit.Test;

public class ProdottoTest {
    @Test
    public void prodottoCompositoTest(){
        Prodotto p1 = new Prodotto();
        p1.setPrezzo(5.0F);
        Prodotto p2 = new Prodotto();
        p2.setPrezzo(7.0F);

        ProdottoComposito p3 = new ProdottoComposito();
        p3.add(p1);
        p3.add(p2);

        Assert.assertTrue(p3.getPrezzo() == 12.0F);
        p3.add(p3);
        //p3.getPrezzo(); stack overflow

    }
}
