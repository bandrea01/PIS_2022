package Business.Bridge;

import Model.IArticolo;
import Model.Ordine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class DocumentoOrdine extends Document{

    private Ordine ordine;
    public DocumentoOrdine(Ordine ordine, PdfAPI pdfAPI) {
        super(pdfAPI);
        this.ordine = ordine;
    }

    @Override
    public void invia(String email) {
       /* ArrayList<IArticolo> articoli = this.ordine.getArticoli();
        String text = "";
        for (IArticolo a : articoli) {
            text += a.getName() + ", ";
        }

        try {
            File tempFile = File.createTempFile("myShop", ".pdf");
            pdfAPI.createPdf(text, email);
            System.out.println(tempFile.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException();
        }*/
    }
}
