
package dao;

import model.AtivoTI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtivoDAO {

    private Connection conexao;

    public AtivoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // CREATE: inserir um novo ativo
    public void inserir(AtivoTI ativo) throws SQLException {

        String sql = "INSERT INTO ativos (nome, patrimonio, valor) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, ativo.getNome());
            stmt.setString(2, ativo.getPatrimonio());
            stmt.setDouble(3, ativo.getValor());

            stmt.executeUpdate();
        }
    }

    // READ: listar todos os ativos
    public List<AtivoTI> listar() throws SQLException {

        List<AtivoTI> ativos = new ArrayList<>();

        String sql = "SELECT id, nome, patrimonio, valor FROM ativos";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                AtivoTI ativo = new AtivoTI(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("patrimonio"),
                    rs.getDouble("valor")
                );

                ativos.add(ativo);
            }
        }

        return ativos;
    }

    // UPDATE: atualizar os dados de um ativo
    public boolean atualizar(AtivoTI ativo) throws SQLException {

        String sql = "UPDATE ativos SET nome = ?, patrimonio = ?, valor = ? WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, ativo.getNome());
            stmt.setString(2, ativo.getPatrimonio());
            stmt.setDouble(3, ativo.getValor());
            stmt.setInt(4, ativo.getId());

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        }
    }

    // DELETE: excluir um ativo
    public boolean excluir(int id) throws SQLException {
    String sqlVerificar = "SELECT COUNT(*) FROM chamados WHERE ativo_id = ?";

    try (PreparedStatement stmt = conexao.prepareStatement(sqlVerificar)) {
        stmt.setInt(1, id);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException(
                    "Operação Negada: O ativo selecionado possui chamados vinculados e não pode ser removido sem antes tratar os chamados associados."
                );
            }
        }
    }

    String sqlExcluir = "DELETE FROM ativos WHERE id = ?";

    try (PreparedStatement stmt = conexao.prepareStatement(sqlExcluir)) {
        stmt.setInt(1, id);
        int linhasAfetadas = stmt.executeUpdate();
        return linhasAfetadas > 0;
    }
    }
}
