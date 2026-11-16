package hotel.servico;

import hotel.modelo.*;
import hotel.negocio.Hotel;
import java.util.*;

public class RelatorioSvc {
    private Hotel hotel;

    public RelatorioSvc(Hotel hotel) {
        this.hotel = hotel;
    }

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
