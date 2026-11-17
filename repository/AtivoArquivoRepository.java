
package repository;

import domain.AtivoTI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AtivoArquivoRepository {

    // EXPORTAR: salvar os ativos em um arquivo CSV
    public void salvarTodos(List<AtivoTI> ativos, String caminhoArquivo) {

        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(caminhoArquivo),
                StandardCharsets.UTF_8)) {

            // Cabeçalho do arquivo
            writer.write("id;nome;categoria;valor");
            writer.newLine();

            // Gravar cada ativo
            for (AtivoTI ativo : ativos) {

                String linha = String.format(
                    Locale.US,
                    "%d;%s;%s;%.2f",
                    ativo.getId(),
                    ativo.getNome(),
                    ativo.getCategoria(),
                    ativo.getValor()
                );

                writer.write(linha);
                writer.newLine();
            }

            System.out.println(
                "[REPOSITÓRIO] Exportação realizada com sucesso para: "
                + caminhoArquivo
            );

        } catch (IOException e) {
            System.err.println(
                "[ERRO I/O] Falha ao escrever no arquivo: " + e.getMessage()
            );
        }
    }

    // IMPORTAR: carregar os ativos de um arquivo CSV
    public List<AtivoTI> carregarTodos(String caminhoArquivo) {

        List<AtivoTI> ativos = new ArrayList<>();
        int linhaContador = 0;

        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get(caminhoArquivo),
                StandardCharsets.UTF_8)) {

            // Ignorar o cabeçalho
            String linha = reader.readLine();
            linhaContador++;

            // Ler o restante do arquivo
            while ((linha = reader.readLine()) != null) {

                linhaContador++;

                if (linha.trim().isEmpty()) {
                    continue;
                }

                String[] campos = linha.split(";");

                if (campos.length == 4) {

                    try {
                        Long id = Long.parseLong(campos[0].trim());
                        String nome = campos[1].trim();
                        String categoria = campos[2].trim();
                        double valor = Double.parseDouble(campos[3].trim());

                        AtivoTI ativo = new AtivoTI(
                            id,
                            nome,
                            categoria,
                            valor
                        );

                        ativos.add(ativo);

                    } catch (NumberFormatException e) {

                        System.err.println(String.format(
                            "[ERRO PARSING] Falha de conversão numérica na linha %d: '%s'. Mensagem: %s",
                            linhaContador,
                            linha,
                            e.getMessage()
                        ));

                    } catch (IllegalArgumentException e) {

                        System.err.println(String.format(
                            "[ERRO DOMÍNIO] Violação de regra de negócio na linha %d: %s",
                            linhaContador,
                            e.getMessage()
                        ));
                    }

                } else {

                    System.err.println(String.format(
                        "[ERRO FORMATO] Linha %d fora do padrão do delimitador (esperado 4 campos): '%s'",
                        linhaContador,
                        linha
                    ));
                }
            }

            System.out.println(
                "[REPOSITÓRIO] Importação concluída a partir de: "
                + caminhoArquivo
            );

        } catch (IOException e) {

            System.err.println(
                "[ERRO I/O] Falha ao ler o arquivo: " + e.getMessage()
            );
        }

        return ativos;
    }

    // IMPORTAR utilizando os métodos utilitários NIO.2
    public List<AtivoTI> carregarTodosViaNio2(String caminhoArquivo)
            throws IOException {

        List<AtivoTI> ativos = new ArrayList<>();

        List<String> linhas = Files.readAllLines(
            Paths.get(caminhoArquivo),
            StandardCharsets.UTF_8
        );

        // Remover o cabeçalho
        if (!linhas.isEmpty()) {
            linhas.remove(0);
        }

        for (String linha : linhas) {

            if (linha.trim().isEmpty()) {
                continue;
            }

            String[] campos = linha.split(";");

            if (campos.length == 4) {

                ativos.add(new AtivoTI(
                    Long.parseLong(campos[0].trim()),
                    campos[1].trim(),
                    campos[2].trim(),
                    Double.parseDouble(campos[3].trim())
                ));
            }
        }

        return ativos;
    }
}
