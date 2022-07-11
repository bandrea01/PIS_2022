package Business.Bridge;

import Model.IArticolo;
import Model.ListaAcquisto;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ListDocument extends Document{

    private ListaAcquisto lista;
    public ListDocument(ListaAcquisto lista, PdfAPI pdfAPI) {
        super(pdfAPI);
        this.lista = lista;
    }

    @Override
    public void invia(String email) {
        List<IArticolo> prodotti = this.lista.getProdotti();
        String text = "";
        Iterator<IArticolo> iterator = prodotti.iterator();
        while (iterator.hasNext()) {
            IArticolo p = iterator.next();
            text += p.getName() + ", ";
        }

        try {
            File tempFile = File.createTempFile("myShop", ".pdf");
            pdfAPI.createPdf(text, email);
            System.out.println(tempFile.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
