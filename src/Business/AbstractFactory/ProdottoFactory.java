package Business.AbstractFactory;

import Model.CategoriaProdotto;
import Model.IArticolo;
import Model.ICategoria;
import Model.Prodotto;

public class ProdottoFactory implements AbstractFactory{
    @Override
    public IArticolo crea() {
        return new Prodotto();
    }
    @Override
    public ICategoria creaCategoria () { return new CategoriaProdotto();}
}
