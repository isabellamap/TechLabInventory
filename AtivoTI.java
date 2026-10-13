
public class AtivoTI {

    private int id;
    private String patrimonio;
    private String nome;
    private String categoria;

    public AtivoTI(String patrimonio, String nome, String categoria) {
        this.patrimonio = patrimonio;
        this.nome = nome;
        this.categoria = categoria;
    }

    public AtivoTI(int id, String patrimonio, String nome, String categoria) {
        this.id = id;
        this.patrimonio = patrimonio;
        this.nome = nome;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return "AtivoTI{" +
                "id=" + id +
                ", patrimonio='" + patrimonio + '\'' +
                ", nome='" + nome + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
