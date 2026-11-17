
package repository;

import domain.AtivoTI;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AtivoJsonRepository {

    // Método para salvar os ativos em formato JSON
    public void salvarJson(List<AtivoTI> ativos, String caminhoArquivo) {

        StringBuilder json = new StringBuilder();

        json.append("[\n");

        for (int i = 0; i < ativos.size(); i++) {

            AtivoTI ativo = ativos.get(i);

            json.append("    {\n");

            json.append(String.format(
                Locale.US,
                "        \"id\": %d,\n" +
                "        \"nome\": \"%s\",\n" +
                "        \"categoria\": \"%s\",\n" +
                "        \"valor\": %.2f\n",
                ativo.getId(),
                ativo.getNome(),
                ativo.getCategoria(),
                ativo.getValor()
            ));

            json.append("    }");

            // Evita colocar vírgula depois do último objeto
            if (i < ativos.size() - 1) {
                json.append(",");
            }

            json.append("\n");
        }

        json.append("]");

        try {
            Files.writeString(
                Paths.get(caminhoArquivo),
                json.toString(),
                StandardCharsets.UTF_8
            );

            System.out.println(
                "[JSON] Arquivo salvo com sucesso: " + caminhoArquivo
            );

        } catch (IOException e) {
            System.err.println(
                "[ERRO JSON] Falha ao salvar arquivo: " + e.getMessage()
            );
        }
    }

    // Método para carregar os ativos a partir do arquivo JSON
    public List<AtivoTI> carregarJson(String caminhoArquivo) {

        List<AtivoTI> ativos = new ArrayList<>();

        try {
            // Lê todo o conteúdo do arquivo como uma única String
            String json = Files.readString(
                Paths.get(caminhoArquivo),
                StandardCharsets.UTF_8
            );

            // Remove os colchetes do início e do final
            json = json.replace("[", "").replace("]", "").trim();

            // Verifica se o arquivo está vazio
            if (json.isEmpty()) {
                return ativos;
            }

            // Divide os objetos JSON
            String[] objetos = json.split("\\s*},\\s*\\{\\s*");

            for (String objeto : objetos) {

                objeto = objeto.replace("{", "").replace("}", "").trim();

                String[] campos = objeto.split(",");

                Long id = null;
                String nome = null;
                String categoria = null;
                double valor = 0.0;

                for (String campo : campos) {

                    String[] partes = campo.split(":", 2);

                    if (partes.length != 2) {
                        continue;
                    }

                    String chave = partes[0]
                        .replace("\"", "")
                        .trim();

                    String conteudo = partes[1]
                        .replace("\"", "")
                        .trim();

                    switch (chave) {

                        case "id":
                            id = Long.parseLong(conteudo);
                            break;

                        case "nome":
                            nome = conteudo;
                            break;

                        case "categoria":
                            categoria = conteudo;
                            break;

                        case "valor":
                            valor = Double.parseDouble(conteudo);
                            break;
                    }
                }

                // Reconstrói o objeto AtivoTI
                if (id != null && nome != null && categoria != null) {

                    AtivoTI ativo = new AtivoTI(
                        id,
                        nome,
                        categoria,
                        valor
                    );

                    ativos.add(ativo);
                }
            }

            System.out.println(
                "[JSON] Importação concluída: " + caminhoArquivo
            );

        } catch (IOException e) {
            System.err.println(
                "[ERRO JSON] Falha ao ler arquivo: " + e.getMessage()
            );

        } catch (NumberFormatException e) {
            System.err.println(
                "[ERRO JSON] Falha na conversão numérica: " + e.getMessage()
            );

        } catch (IllegalArgumentException e) {
            System.err.println(
                "[ERRO JSON] Dados inválidos: " + e.getMessage()
            );
        }

        return ativos;
    }
}
