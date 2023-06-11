package Business.Bridge;

public abstract class Document {
    protected PdfAPI pdfAPI;

    public Document(PdfAPI pdfAPI) {
        this.pdfAPI = pdfAPI;
    }

    public void invia(String indirizzo) {}
}
