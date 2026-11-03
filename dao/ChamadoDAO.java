
package dao;

import model.AtivoTI;
import model.ChamadoSuporte;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChamadoDAO {

    private Connection conexao;

    public ChamadoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Salvar um chamado
    public void salvar(ChamadoSuporte chamado) throws SQLException {

        String sql = "INSERT INTO chamados (descricao, status, ativo_id) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, chamado.getDescricao());
            stmt.setString(2, chamado.getStatus());
            stmt.setInt(3, chamado.getAtivo().getId());

            stmt.executeUpdate();
        }
    }

    // Listar chamados vinculados a um ativo
    public List<ChamadoSuporte> listarPorAtivo(int ativoId) throws SQLException {

        List<ChamadoSuporte> chamados = new ArrayList<>();

        String sql = "SELECT id, descricao, status, ativo_id FROM chamados WHERE ativo_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, ativoId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    AtivoTI ativoStub = new AtivoTI();
                    ativoStub.setId(rs.getInt("ativo_id"));

                    ChamadoSuporte chamado = new ChamadoSuporte(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getString("status"),
                        ativoStub
                    );

                    chamados.add(chamado);
                }
            }
        }

        return chamados;
    }

    // Excluir um chamado pelo ID
    public boolean excluir(int id) throws SQLException {

        String sql = "DELETE FROM chamados WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        }
    }
}
