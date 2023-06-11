package Business.Bridge;

import java.util.List;

public interface PdfAPI {
    void createPdf(List<String> text, String outfile);

}
