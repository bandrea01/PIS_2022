package Model;

import java.io.File;
import java.util.ArrayList;

public interface IArticolo {
    int getId();
    void setId(int id);
    String getName ();
    void setName (String name);
    Float getPrezzo ();
    void setPrezzo (Float prezzo);
    String getDescrizione();
    void setDescrizione(String descrizione);
    ArrayList<File> getImmagini ();
    void setImmagini (ArrayList<File> immagini);
    ICategoria getCategoria();
    void setCategoria (ICategoria categoria);
}
