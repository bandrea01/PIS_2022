package Model;

import java.awt.*;
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
    ArrayList<Immagine> getImmagini ();
    void setImmagini (ArrayList<Immagine> immagini);
    ICategoria getCategoria();
    void setCategoria (ICategoria categoria);
}
