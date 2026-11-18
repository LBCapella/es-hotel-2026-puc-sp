package hotel.modelo;

import java.io.Serializable;

/**
 * Associa um serviço do cardápio a um apartamento, com a quantidade consumida. Objeto imutável.
 */
public class Consumo implements Serializable {
    private final Apartamento apartamento;
    private final Hospede titular;
    private final Servico servico;
    private final int quantidade;

    /**
     * Constrói um objeto Consumo validando os parâmetros.
     *
     * @param apto Apartamento que consumiu o serviço
     * @param titular Hóspede titular da reserva no momento do consumo
     * @param servico Serviço consumido
     * @param quantidade Quantidade consumida
     * @throws IllegalArgumentException se o apartamento, o titular ou o serviço forem nulos, ou se a quantidade for menor ou igual a zero
     *
     * @pre O apartamento, o titular e o serviço não podem ser nulos e a quantidade deve ser maior que zero
     * @post Objeto Consumo é instanciado e torna-se imutável
     */
    public Consumo(Apartamento apto, Hospede titular, Servico servico, int quantidade) {
        if (apto == null) {
            throw new IllegalArgumentException("Apartamento do Consumo não pode ser nulo.");
        }
        if (titular == null) {
            throw new IllegalArgumentException("Titular do Consumo não pode ser nulo.");
        }
        if (servico == null) {
            throw new IllegalArgumentException("Serviço do Consumo não pode ser nulo.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade do Consumo deve ser maior que zero.");
        }
        this.apartamento = apto;
        this.titular = titular;
        this.servico = servico;
        this.quantidade = quantidade;
    }

    /**
     * Obtém o apartamento que consumiu o serviço.
     *
     * @return Apartamento associado ao consumo
     * @pre Nenhuma
     * @post Retorna a referência do apartamento
     */
    public Apartamento getApartamento() { return apartamento; }

    /**
     * Obtém o hóspede titular da reserva no momento em que o serviço foi consumido.
     *
     * @return Hospede titular associado ao consumo
     * @pre Nenhuma
     * @post Retorna a referência do titular
     */
    public Hospede getTitular() { return titular; }

    /**
     * Obtém o serviço consumido.
     *
     * @return Servico associado ao consumo
     * @pre Nenhuma
     * @post Retorna a referência do serviço
     */
    public Servico getServico() { return servico; }

    /**
     * Obtém a quantidade consumida.
     *
     * @return int contendo a quantidade
     * @pre Nenhuma
     * @post Retorna a quantidade consumida
     */
    public int getQuantidade() { return quantidade; }

    /**
     * Calcula o valor total do consumo (preço do serviço multiplicado pela quantidade).
     *
     * @return float contendo o valor total
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    public float getTotal() {
        return servico.getPreco() * quantidade;
    }

    /**
     * Retorna uma representação em texto do consumo.
     *
     * @return String no formato "serviço xquantidade = R$ total"
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    @Override
    public String toString() {
        return servico.getNome() + " x" + quantidade + " = R$ " + getTotal();
    }
}
