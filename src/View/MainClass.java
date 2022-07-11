package View;

import Model.IArticolo;
import Model.ListaAcquisto;
import Model.Prodotto;

public class MainClass {
    public static void main(String[] args) {
        new MyHierarchyLayout();


        //Get email from session manager
        ListaAcquisto lista = new ListaAcquisto();
        IArticolo p1 = new Prodotto();
        IArticolo p2 = new Prodotto();
        p1.setName("p1");
        p2.setName("p2");
        lista.getProdotti().add(p1);
        lista.getProdotti().add(p2);

        //Document listaAcquisto = new ListDocument(lista, new PdfBoxAPI());
        //listaAcquisto.invia("email");
    }
}
