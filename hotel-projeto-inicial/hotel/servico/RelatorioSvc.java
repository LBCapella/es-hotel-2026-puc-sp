package hotel.servico;

import hotel.modelo.*;
import hotel.negocio.Hotel;
import java.util.*;

/**
 * Geração dos relatórios gerenciais do hotel: histórico de ocupação, ranking de serviços e resumo financeiro.
 */
public class RelatorioSvc {
    private Hotel hotel;

    /**
     * Constrói o serviço de relatórios sobre um hotel.
     *
     * @param hotel Hotel cujos dados serão consultados
     *
     * @pre O hotel não deve ser nulo
     * @post Serviço criado; o hotel não é alterado
     */
    public RelatorioSvc(Hotel hotel) {
        this.hotel = hotel;
    }

    /**
     * Imprime o histórico de ocupação: quantos dias cada apartamento ficou ocupado
     * em estadias já encerradas. Apartamentos sem histórico não aparecem.
     *
     * @pre O hotel deve estar inicializado
     * @post Nenhuma alteração de estado; apenas exibição
     */
    public void gerarRelatorioOcupacao() {
        System.out.println("=== HISTÓRICO DE OCUPAÇÃO (dias ocupados por apartamento) ===");
        for (int a = 0; a < Hotel.NUM_ANDARES; a++) {
            for (int n = 0; n < Hotel.APTOS_POR_ANDAR; n++) {
                int dias = hotel.getDiasOcupado(a, n);
                if (dias > 0) {
                    System.out.println("Andar " + a + ", Apto " + n + ": " + dias + " dias");
                }
            }
        }
    }

    /**
     * Imprime o ranking dos serviços mais consumidos, ordenado pela quantidade total consumida.
     *
     * @pre O hotel deve estar inicializado
     * @post Nenhuma alteração de estado; apenas exibição
     */
    public void gerarRankingServicos() {
        Map<Servico, Integer> quantidades = new LinkedHashMap<>();
        for (Consumo c : hotel.getConsumos()) {
            quantidades.merge(c.getServico(), c.getQuantidade(), Integer::sum);
        }
        List<Map.Entry<Servico, Integer>> ranking = new ArrayList<>(quantidades.entrySet());
        ranking.sort((a, b) -> b.getValue() - a.getValue());
        System.out.println("=== RANKING DE SERVIÇOS MAIS CONSUMIDOS ===");
        int posicao = 1;
        for (Map.Entry<Servico, Integer> e : ranking) {
            System.out.println(posicao++ + ". " + e.getKey().getNome() + " - " + e.getValue() + " unidades");
        }
    }

    /**
     * Imprime o resumo financeiro: total de diárias das estadias encerradas, total de consumos
     * e faturamento total.
     *
     * @pre O hotel deve estar inicializado
     * @post Nenhuma alteração de estado; apenas exibição
     */
    public void gerarResumoFinanceiro() {
        float totalDiarias = 0f;
        for (int a = 0; a < Hotel.NUM_ANDARES; a++) {
            for (int n = 0; n < Hotel.APTOS_POR_ANDAR; n++) {
                totalDiarias += hotel.getDiasOcupado(a, n) * hotel.getApartamento(a, n).getPrecoDiaria();
            }
        }

        float totalConsumos = 0f;
        for (Consumo c : hotel.getConsumos()) {
            totalConsumos += c.getTotal();
        }

        System.out.println("=== RESUMO FINANCEIRO ===");
        System.out.println("Total de diárias: R$ " + totalDiarias);
        System.out.println("Total de consumos: R$ " + totalConsumos);
        System.out.println("Faturamento total: R$ " + (totalDiarias + totalConsumos));
    }
}
