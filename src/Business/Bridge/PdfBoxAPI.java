package Business.Bridge;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class PdfBoxAPI implements PdfAPI {

    @Override
    public void createPdf(List<String> text, String outfile) {
        //Creiamo un documento PDF
        try (PDDocument doc = new PDDocument()){
            //Creiamo una pagina
            PDPage page = new PDPage();
            //Aggiungiamo la pagina al documento
            doc.addPage(page);

            try (PDPageContentStream contents = new PDPageContentStream(doc, page))
            {
                contents.setLeading(15.0f);
                contents.beginText();;
                contents.newLineAtOffset(100F,650F);
                contents.setFont(PDType1Font.TIMES_ROMAN, 12);
                for(String t : text){
                    contents.showText(t);
                    contents.newLine();
                }
                contents.endText();
            }
            doc.save(outfile); //Salvataggio testo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
