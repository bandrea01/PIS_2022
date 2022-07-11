package Business.AbstractFactory;

import Model.IArticolo;
import Model.ICategoria;

public interface AbstractFactory {
    IArticolo crea();
    ICategoria creaCategoria();

}
