package hotel.modelo;

import java.util.List;

/**
 * Fatura de um apartamento: agrega o valor das diárias e dos consumos de serviços.
 */
public class Fatura {
    private Apartamento apartamento;
    private int dias;
    private List<Consumo> consumos;
    private float totalDiarias;
    private float totalConsumos;

    /**
     * Constrói uma Fatura validando os parâmetros e calculando os totais de diárias e de consumos.
     *
     * @param apto Apartamento a ser faturado
     * @param dias Quantidade de diárias
     * @param consumos Lista de consumos de serviços do apartamento
     * @throws IllegalArgumentException se o apartamento for nulo, se os dias forem negativos ou se a lista de consumos for nula
     *
     * @pre O apartamento e a lista de consumos não podem ser nulos e os dias não podem ser negativos
     * @post Fatura é instanciada com os totais de diárias e de consumos calculados
     */
    public Fatura(Apartamento apto, int dias, List<Consumo> consumos) {
        if (apto == null) {
            throw new IllegalArgumentException("Apartamento da Fatura não pode ser nulo.");
        }
        if (dias < 0) {
            throw new IllegalArgumentException("Quantidade de dias da Fatura não pode ser negativa.");
        }
        if (consumos == null) {
            throw new IllegalArgumentException("Lista de consumos da Fatura não pode ser nula.");
        }
        this.apartamento = apto;
        this.dias = dias;
        this.consumos = consumos;
        this.totalDiarias = calcularTotalDiarias();
        this.totalConsumos = calcularTotalConsumos();
    }

    private float calcularTotalDiarias() {
        return dias * apartamento.getPrecoDiaria();
    }

    private float calcularTotalConsumos() {
        float soma = 0f;
        for (Consumo c : consumos) {
            soma += c.getTotal();
        }
        return soma;
    }

    /**
     * Obtém o valor total da fatura (diárias mais consumos).
     *
     * @return float contendo o total geral
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    public float getTotalGeral() {
        return totalDiarias + totalConsumos;
    }

    /**
     * Obtém o apartamento faturado.
     *
     * @return Apartamento associado à fatura
     * @pre Nenhuma
     * @post Retorna a referência do apartamento
     */
    public Apartamento getApartamento() { return apartamento; }

    /**
     * Obtém a quantidade de diárias faturadas.
     *
     * @return int contendo a quantidade de dias
     * @pre Nenhuma
     * @post Retorna a quantidade de dias
     */
    public int getDias() { return dias; }

    /**
     * Obtém a lista de consumos incluídos na fatura.
     *
     * @return List com os consumos da fatura
     * @pre Nenhuma
     * @post Retorna a referência da lista de consumos
     */
    public List<Consumo> getConsumos() { return consumos; }

    /**
     * Obtém o total das diárias (dias multiplicados pelo preço da diária do apartamento).
     *
     * @return float contendo o total das diárias
     * @pre Nenhuma
     * @post Retorna o total das diárias calculado na criação da fatura
     */
    public float getTotalDiarias() {
        return totalDiarias;
    }

    /**
     * Obtém o total dos consumos de serviços.
     *
     * @return float contendo o total dos consumos
     * @pre Nenhuma
     * @post Retorna o total dos consumos calculado na criação da fatura
     */
    public float getTotalConsumos() {
        return totalConsumos;
    }

    /**
     * Retorna uma representação em texto da fatura.
     *
     * @return String no formato "Fatura: R$ total"
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    @Override
    public String toString() {
        return "Fatura: R$ " + getTotalGeral();
    }
}
