package Model;

import java.util.ArrayList;
import java.util.List;

public class CategoriaProdotto implements ICategoria {
    private String nome;
    private List<CategoriaProdotto> sottocategorie;

    public CategoriaProdotto(String nome) {
        this.nome = nome;
        this.sottocategorie = new ArrayList<CategoriaProdotto>();
    }

    public CategoriaProdotto() {
        this.nome = "";
        this.sottocategorie = new ArrayList<>();
    }

    public List<CategoriaProdotto> getSottocategorie() {
        return sottocategorie;
    }

    public void aggiungiSottocategoria(CategoriaProdotto c) {
        this.sottocategorie.add(c);
    }

    public void aggiungiSottocategoria(List<CategoriaProdotto> c) {
        this.sottocategorie.addAll(c);
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
