package Business.AbstractFactory;

public class FactoryProvider {

    public enum FactoryType { PRODOTTO, PRODOTTO_COMPOSITO, SERVIZIO }

    public static AbstractFactory getFactory(FactoryType type) {
        if (type == null) return null;

        switch (type){
            case PRODOTTO: return new ProdottoFactory();
            case SERVIZIO: return new ServizioFactory();
            case PRODOTTO_COMPOSITO: return new ProdottoCompositoFactory();
            default: return null;
        }
    }
}
