package Model;

public class CategoriaServizio implements ICategoria{
    private String nome;

    public CategoriaServizio(String nome) {
        this.nome = nome;
    }

    public CategoriaServizio(){
        this.nome = "";
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
