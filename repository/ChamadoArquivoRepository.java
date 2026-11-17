
package repository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChamadoArquivoRepository {

    public void registrarFinalizacao(
            int id,
            String descricao,
            String status) {

        // Formato da data e hora
        DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String dataHora = LocalDateTime.now().format(formato);

        // Montar a linha do registro
        String registro = String.format(
            "[%s] - TICKET #%d - %s - %s",
            dataHora,
            id,
            descricao,
            status
        );

        // Abrir o arquivo em modo de anexação
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get("chamados.txt"),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            writer.write(registro);
            writer.newLine();

            System.out.println(
                "[LOG] Finalização do chamado registrada com sucesso!"
            );

        } catch (IOException e) {
            System.err.println(
                "[ERRO] Não foi possível registrar o chamado: "
                + e.getMessage()
            );
        }
    }
}
