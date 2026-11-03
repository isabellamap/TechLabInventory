
import dao.AtivoDAO;
import dao.ChamadoDAO;
import model.AtivoTI;
import model.ChamadoSuporte;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            ConexaoBanco.inicializarBanco();

            try (Connection conexao = DriverManager.getConnection("jdbc:sqlite:techlab.db")) {

                // Ativar as chaves estrangeiras
                try (Statement stmt = conexao.createStatement()) {
                    stmt.execute("PRAGMA foreign_keys = ON;");
                }

                AtivoDAO ativoDAO = new AtivoDAO(conexao);
                ChamadoDAO chamadoDAO = new ChamadoDAO(conexao);

                // Inserir um novo ativo
                AtivoTI novoAtivo = new AtivoTI(
                    0,
                    "Notebook Dell Latitude",
                    "PAT-2026-001",
                    4500.00
                );

                ativoDAO.inserir(novoAtivo);

                // Listar os ativos cadastrados
                List<AtivoTI> ativos = ativoDAO.listar();

                System.out.println("=== ATIVOS CADASTRADOS ===");

                for (AtivoTI ativo : ativos) {
                    System.out.println(ativo);
                }

                // Recuperar o último ativo cadastrado
                AtivoTI ativoSalvo = ativos.get(ativos.size() - 1);

                // Criar e salvar um chamado
                ChamadoSuporte chamado = new ChamadoSuporte(
                    0,
                    "Troca de teclado defeituoso",
                    "ABERTO",
                    ativoSalvo
                );

                chamadoDAO.salvar(chamado);

                System.out.println("\nChamado registrado com sucesso!");

                // Atualizar os dados do ativo
                ativoSalvo.setNome("Notebook Dell Latitude 5420");
                ativoSalvo.setValor(4800.00);

                boolean atualizado = ativoDAO.atualizar(ativoSalvo);

                if (atualizado) {
                    System.out.println("\nAtivo atualizado com sucesso!");
                }

                // Listar chamados vinculados ao ativo
                System.out.println("\n=== CHAMADOS DO ATIVO ===");

                List<ChamadoSuporte> chamados =
                    chamadoDAO.listarPorAtivo(ativoSalvo.getId());

                for (ChamadoSuporte c : chamados) {
                    System.out.println(c);
                }

                // Tentar excluir o ativo enquanto possui chamado
                System.out.println("\n=== TESTE DE EXCLUSÃO ===");

                try {
                    ativoDAO.excluir(ativoSalvo.getId());
                } catch (SQLException e) {
                    System.out.println("Não foi possível excluir o ativo: "
                        + e.getMessage());
                }

                // Excluir primeiro os chamados associados
                for (ChamadoSuporte c : chamados) {
                    chamadoDAO.excluir(c.getId());
                }

                // Agora excluir o ativo
                boolean excluido = ativoDAO.excluir(ativoSalvo.getId());

                if (excluido) {
                    System.out.println("Ativo excluído com sucesso!");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao acessar o banco de dados:");
            e.printStackTrace();
        }
    }
}
