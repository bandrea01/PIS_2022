package Business;

public class CatalogBusiness {

    private static CatalogBusiness instance;
    public static synchronized CatalogBusiness getInstance() {
        if (instance == null) {
            instance = new CatalogBusiness();
        }
        return instance;
    }
}
