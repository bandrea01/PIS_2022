package Business.Bridge;

import Business.MailHelper;
import Model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocumentoOrdine extends Document{

    private Ordine ordine;
    public DocumentoOrdine(Ordine ordine, PdfAPI pdfAPI) {
        super(pdfAPI);
        this.ordine = ordine;
    }

    @Override
    public void invia(String indirizzo) {
        ArrayList<ProdottoOrdine> prodottiOrdine = ordine.getProdotti();
        ArrayList<Servizio> serviziOrdine = ordine.getServizi();

        //Intestazione
        String riga = "";
        List<String> testoMail = new ArrayList<>();
        riga = "Lista d'Acquisto #" + ordine.getIdOrdine() + " | Cliente: " + ordine.getUtente().getName() + " " + ordine.getUtente().getSurname();
        testoMail.add(riga);

        //Elenco prodotti
        riga = "Prodotti:";
        testoMail.add(riga);
        Float costoTotaleProdotti = 0F;
        for(ProdottoOrdine p : prodottiOrdine){
            Float costoTotaleRiga = p.getProdotto().getPrezzo() * p.getQuantita();
            costoTotaleProdotti += costoTotaleRiga;
            riga = "* " + p.getProdotto().getName() + " (Quantità: " + p.getQuantita() + ")" + " | (Costo: " + costoTotaleRiga + "€);";
            testoMail.add(riga);
        }
        //Elenco servizi
        riga = "Servizi:";
        testoMail.add(riga);
        Float costoTotaleServizi = 0F;
        for(Servizio s : serviziOrdine){
            Float costoTotaleRiga = s.getPrezzo();
            costoTotaleServizi += costoTotaleRiga;
            riga = "* " + s.getName() + " | (Costo: " + costoTotaleRiga + "€);" ;
            testoMail.add(riga);
        }
        riga = "Totale Prodotti: " + costoTotaleProdotti + "€";
        testoMail.add(riga);
        riga = "Totale Servizi: " + costoTotaleServizi + "€";
        testoMail.add(riga);
        riga = "Totale: " + (costoTotaleProdotti + costoTotaleServizi) + "€";
        testoMail.add(riga);

        try{
            File temp = File.createTempFile("PIS_ordine_" + ordine.getIdOrdine(), ".pdf");
            pdfAPI.createPdf(testoMail, temp.getAbsolutePath());
            System.out.println(temp.getAbsolutePath());
            MailHelper mailHelper = new MailHelper();
            mailHelper.send(indirizzo, "PIS Order #" + ordine.getIdOrdine(), "Dear " + ordine.getUtente().getUsername() + "" +
                    "\n Generated PDF of your order can be found in the attachments, thank you for your purchase.", temp);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }
}
