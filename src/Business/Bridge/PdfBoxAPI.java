package Business.Bridge;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class PdfBoxAPI implements PdfAPI {

    @Override
    public void createPdf(String text, String outfile) {
        //Creiamo un documento PDF
        try (PDDocument doc = new PDDocument()){
            //Creiamo una pagina
            PDPage page = new PDPage();
            //Aggiungiamo la pagina al documento
            doc.addPage(page);

            //Impostiamo il font
            PDFont font = PDType1Font.TIMES_ROMAN;

            try (PDPageContentStream contents = new PDPageContentStream(doc, page))
            {
                contents.beginText(); //Inizio testo
                contents.setFont(font, 12);
                contents.newLineAtOffset(100, 700); //Posizione di scrittura
                contents.showText(text);
                contents.endText(); //Fine testo
            }

            doc.save(outfile); //Salvataggio testo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
