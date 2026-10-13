
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtivoDAO {

    public void salvar(AtivoTI ativo) throws SQLException {

        String sql = "INSERT INTO ativos (patrimonio, nome, categoria) VALUES (?, ?, ?)";

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, ativo.getPatrimonio());
            stmt.setString(2, ativo.getNome());
            stmt.setString(3, ativo.getCategoria());

            stmt.executeUpdate();
        }
    }

    public List<AtivoTI> listarTodos() throws SQLException {

        List<AtivoTI> ativos = new ArrayList<>();

        String sql = "SELECT id, patrimonio, nome, categoria FROM ativos ORDER BY id ASC";

        try (Connection conexao = ConexaoBanco.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                AtivoTI ativo = new AtivoTI(
                    rs.getInt("id"),
                    rs.getString("patrimonio"),
                    rs.getString("nome"),
                    rs.getString("categoria")
                );

                ativos.add(ativo);
            }
        }

        return ativos;
    }

    
    public AtivoTI buscarPorPatrimonio(String patrimonio) throws SQLException {

        String sql = "SELECT id, patrimonio, nome, categoria FROM ativos WHERE patrimonio = ?";

        try (Connection conexao = ConexaoBanco.obterConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, patrimonio);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return new AtivoTI(
                        rs.getInt("id"),
                        rs.getString("patrimonio"),
                        rs.getString("nome"),
                        rs.getString("categoria")
                    );
                }
            }
        }

        return null;
    }

}
