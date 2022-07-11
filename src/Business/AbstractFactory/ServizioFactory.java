package Business.AbstractFactory;

import Model.CategoriaServizio;
import Model.IArticolo;
import Model.ICategoria;
import Model.Servizio;

public class ServizioFactory implements AbstractFactory{

    @Override
    public IArticolo crea() {
        return new Servizio();
    }
    @Override
    public ICategoria creaCategoria() {
        return new CategoriaServizio();
    }
}
