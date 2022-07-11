package Test;

import Business.AbstractFactory.*;
import Model.*;
import org.junit.Assert;
import org.junit.Test;

public class AbstractFactoryTest {

    @Test
    public void getFactoryTest(){
        FactoryProvider.FactoryType type = FactoryProvider.FactoryType.PRODOTTO;
        AbstractFactory factoryProdotto = FactoryProvider.getFactory(type);
        FactoryProvider.FactoryType type2 = FactoryProvider.FactoryType.SERVIZIO;
        AbstractFactory factoryServizio = FactoryProvider.getFactory(type2);
        FactoryProvider.FactoryType type3 = FactoryProvider.FactoryType.PRODOTTO_COMPOSITO;
        AbstractFactory factoryProdottoComposito = FactoryProvider.getFactory(type3);

        Assert.assertTrue(factoryProdotto instanceof ProdottoFactory);
        Assert.assertTrue(factoryServizio instanceof ServizioFactory);
        Assert.assertTrue(factoryProdottoComposito instanceof ProdottoCompositoFactory);

        ICategoria catProdotto = factoryProdotto.creaCategoria();
        ICategoria catServizio = factoryServizio.creaCategoria();

        IArticolo prodotto = factoryProdotto.crea();
        IArticolo prodottoComposito = factoryProdottoComposito.crea();
        IArticolo servizio = factoryServizio.crea();

        Assert.assertTrue(catProdotto instanceof CategoriaProdotto);
        Assert.assertTrue(catServizio instanceof CategoriaServizio);
        Assert.assertTrue(prodotto instanceof Prodotto);
        Assert.assertTrue(prodottoComposito instanceof ProdottoComposito);
        Assert.assertTrue(servizio instanceof Servizio);




    }


}
