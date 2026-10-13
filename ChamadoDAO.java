
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChamadoDAO {

    public void salvar(ChamadoSuporte chamado) throws SQLException {

        String sql = "INSERT INTO chamados (descricao, ativo_id) VALUES (?, ?)";

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, chamado.getDescricao());
            stmt.setInt(2, chamado.getAtivo().getId());

            stmt.executeUpdate();
        }
    }
}
