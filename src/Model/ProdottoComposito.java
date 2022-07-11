package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProdottoComposito extends Prodotto implements IArticolo {

    //final: punta alla stessa zona di memoria, ma posso aggiungere o togliere
    private final List<IArticolo> sottoProdotti = new ArrayList<IArticolo>();

    public ProdottoComposito() {
        super();
    }

    public void add(IArticolo prodotto){
        //TODO
        //if ...
        sottoProdotti.add(prodotto);
    }

    public void add(List<IArticolo> prodotti){
        sottoProdotti.addAll(prodotti);
    }

    public Float getPrezzoScontato (Float sconto){
        if (getPrezzo().compareTo(sconto) > 0){
            return getPrezzo() - sconto;
        }
        return getPrezzo();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Float getPrezzo(){
        Float p = 0F; //0F float
        for (IArticolo prodotto : sottoProdotti){
            p += prodotto.getPrezzo();
        }
        p = 0F;

        Iterator<IArticolo> i = sottoProdotti.iterator();
        while(i.hasNext()){
            IArticolo prodotto = i.next();
            p += prodotto.getPrezzo();
        }
        return p;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}
