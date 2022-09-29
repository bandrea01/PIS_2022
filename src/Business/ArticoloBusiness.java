package Business;

import java.awt.event.ActionListener;
import java.sql.Blob;

public class ArticoloBusiness {
    private static ArticoloBusiness instance;

    public static synchronized ArticoloBusiness getInstance() {
        if (instance == null) {
            instance = new ArticoloBusiness();
        }
        return instance;
    }
    public ActionListener showImagePopup() {
        return null;
    }
}
