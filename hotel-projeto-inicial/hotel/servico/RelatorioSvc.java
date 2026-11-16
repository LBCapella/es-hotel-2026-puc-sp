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
        throw new UnsupportedOperationException("Implementar relatorio de ocupacao");
    }

    public void gerarRankingServicos() {
        Map<Servico, Integer> quantidades = new LinkedHashMap<>();
        for (Consumo c : hotel.getConsumos()) {
            quantidades.merge(c.getServico(), c.getQuantidade(), Integer::sum);
        }
        List<Map.Entry<Servico, Integer>> ranking = new ArrayList<>(quantidades.entrySet());
        ranking.sort((a, b) -> b.getValue() - a.getValue());
        System.out.println("***RANKING DE SERVIÇOS MAIS CONSUMIDOS***");
        int posicao = 1;
        for (Map.Entry<Servico, Integer> e : ranking) {
            System.out.println(posicao++ + ". " + e.getKey().getNome() + " - " + e.getValue() + " unidades");
        }
    }

    public void gerarResumoFinanceiro() {
        throw new UnsupportedOperationException("Implementar resumo financeiro");
    }
}
