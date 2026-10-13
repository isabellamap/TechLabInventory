
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            ConexaoBanco.inicializarBanco();

            AtivoDAO dao = new AtivoDAO();

            AtivoTI ativo1 = new AtivoTI(
                "SRV-001",
                "Servidor Dell",
                "Servidor"
            );

            AtivoTI ativo2 = new AtivoTI(
                "EST-042",
                "Computador HP",
                "Estação de Trabalho"
            );

            if (dao.buscarPorPatrimonio("SRV-001") == null) {
                dao.salvar(ativo1);
            }

            if (dao.buscarPorPatrimonio("EST-042") == null) {
                dao.salvar(ativo2);
            }

            List<AtivoTI> ativos = dao.listarTodos();

            System.out.println("=== ATIVOS CADASTRADOS ===");

            for (AtivoTI ativo : ativos) {
                System.out.println(ativo);
            }

            System.out.println("\n=== BUSCAR POR PATRIMÔNIO ===");

            AtivoTI encontrado =
                dao.buscarPorPatrimonio("SRV-001");

            if (encontrado != null) {
                System.out.println("Equipamento encontrado:");
                System.out.println(encontrado);
            } else {
                System.out.println("Equipamento não encontrado.");
            }
 
            if (encontrado != null) {
                ChamadoSuporte chamado = new ChamadoSuporte(
                    "Servidor apresentando falhas",
                    encontrado
                );

                ChamadoDAO chamadoDAO = new ChamadoDAO();
                chamadoDAO.salvar(chamado);

                System.out.println("\nChamado de suporte registrado com sucesso!");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao acessar o banco de dados:");
            e.printStackTrace();
        }
    }
}
