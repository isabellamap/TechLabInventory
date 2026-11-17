
import domain.AtivoTI;
import repository.AtivoArquivoRepository;
import repository.ChamadoArquivoRepository;
import repository.AtivoJsonRepository;

import java.util.ArrayList;
import java.util.List;

public class MainArquivos {

    public static void main(String[] args) {

        String arquivoCSV = "ativos.csv";
        String arquivoJSON = "ativos.json";

        AtivoArquivoRepository repository =
            new AtivoArquivoRepository();

        System.out.println("=================================================");
        System.out.println("   TECHLAB INVENTORY - TESTE DE PERSISTÊNCIA");
        System.out.println("=================================================");

        List<AtivoTI> listaOriginal = new ArrayList<>();

        listaOriginal.add(new AtivoTI(
            1L, "Servidor PowerEdge R740", "Servidores", 25000.00
        ));

        listaOriginal.add(new AtivoTI(
            2L, "Switch Cisco Catalyst 24P", "Redes", 3500.50
        ));

        listaOriginal.add(new AtivoTI(
            3L, "Notebook ThinkPad T14", "Portáteis", 6800.00
        ));

        // TESTE CSV
        System.out.println("\n--- [ETAPA 1] Exportando dados para CSV ---");
        repository.salvarTodos(listaOriginal, arquivoCSV);

        System.out.println("\n--- [ETAPA 2] Simulando limpeza da memória ---");
        listaOriginal = null;
        System.gc();
        System.out.println("Referência listaOriginal atribuída para null.");

        System.out.println("\n--- [ETAPA 3] Importando dados do CSV ---");
        List<AtivoTI> listaCarregada =
            repository.carregarTodos(arquivoCSV);

        System.out.println("\n--- [ETAPA 4] Ativos recuperados do CSV ---");
        for (AtivoTI ativo : listaCarregada) {
            System.out.println(ativo);
        }

        // TESTE TXT
        System.out.println("\n--- [ETAPA 5] Registrando chamado no TXT ---");

        ChamadoArquivoRepository chamadoRepository =
            new ChamadoArquivoRepository();

        chamadoRepository.registrarFinalizacao(
            101,
            "Troca de teclado defeituoso",
            "FINALIZADO"
        );

        // TESTE JSON
        System.out.println("\n--- [ETAPA 6] Exportando dados para JSON ---");

        AtivoJsonRepository jsonRepository =
            new AtivoJsonRepository();

        jsonRepository.salvarJson(listaCarregada, arquivoJSON);

        System.out.println("\n--- [ETAPA 7] Importando dados do JSON ---");

        List<AtivoTI> ativosJSON =
            jsonRepository.carregarJson(arquivoJSON);

        System.out.println("\n--- [ETAPA 8] Ativos recuperados do JSON ---");

        for (AtivoTI ativo : ativosJSON) {
            System.out.println(ativo);
        }

        System.out.println("\n=================================================");
        System.out.println("        PROCESSAMENTO CONCLUÍDO");
        System.out.println("=================================================");
    }
}
