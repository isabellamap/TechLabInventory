
public class ChamadoSuporte {

    private int id;
    private String descricao;
    private AtivoTI ativo;

    public ChamadoSuporte(String descricao, AtivoTI ativo) {
        this.descricao = descricao;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public AtivoTI getAtivo() {
        return ativo;
    }
}
