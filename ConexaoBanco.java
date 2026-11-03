
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBanco {

    private static final String URL = "jdbc:sqlite:techlab.db";

    public static Connection obterConexao() throws SQLException {

        Connection conexao = DriverManager.getConnection(URL);

        try (Statement stmt = conexao.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }

        return conexao;
    }

    public static void inicializarBanco() throws SQLException {

        String sqlAtivos = """
            CREATE TABLE IF NOT EXISTS ativos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                patrimonio TEXT NOT NULL,
                valor REAL NOT NULL
            );
        """;

        String sqlChamados = """
            CREATE TABLE IF NOT EXISTS chamados (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                descricao TEXT NOT NULL,
                status TEXT NOT NULL,
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
