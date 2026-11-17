
package domain;

public class AtivoTI {

    private Long id;
    private String nome;
    private String categoria;
    private double valor;

    // Construtor padrão
    public AtivoTI() {
    }

    // Construtor completo
    public AtivoTI(Long id, String nome, String categoria, double valor) {
        this.setId(id);
        this.setNome(nome);
        this.setCategoria(categoria);
        this.setValor(valor);
    }

    // Getter e Setter do ID
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                "O ID deve ser um valor numérico positivo válido."
            );
        }
        this.id = id;
    }

    // Getter e Setter do nome
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "O nome do ativo não pode ser nulo ou vazio."
            );
        }
        this.nome = nome.trim();
    }

    // Getter e Setter da categoria
    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "A categoria não pode ser nula ou vazia."
            );
        }
        this.categoria = categoria.trim();
    }

    // Getter e Setter do valor
    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        if (valor < 0.0) {
            throw new IllegalArgumentException(
                "O valor do ativo não pode ser negativo. Valor informado: " + valor
            );
        }
        this.valor = valor;
    }

    // Representação textual do objeto
    @Override
    public String toString() {
        return String.format(
            java.util.Locale.US,
            "AtivoTI [ID=%d, Nome='%s', Categoria='%s', Valor=R$ %.2f]",
            this.id,
            this.nome,
            this.categoria,
            this.valor
        );
    }
}
