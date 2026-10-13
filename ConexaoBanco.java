
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBanco {

    private static final String URL = "jdbc:sqlite:techlab.db";

    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void inicializarBanco() throws SQLException {

        String sqlAtivos = """
            CREATE TABLE IF NOT EXISTS ativos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                patrimonio TEXT NOT NULL UNIQUE,
                nome TEXT NOT NULL,
                categoria TEXT NOT NULL,
                detalhe_especifico TEXT
            );
        """;

        String sqlChamados = """
            CREATE TABLE IF NOT EXISTS chamados (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                descricao TEXT NOT NULL,
                ativo_id INTEGER NOT NULL,
                FOREIGN KEY (ativo_id) REFERENCES ativos(id)
            );
        """;

        try (Connection conexao = obterConexao();
             Statement stmt = conexao.createStatement()) {

            stmt.execute(sqlAtivos);
            stmt.execute(sqlChamados);
        }
    }
}
