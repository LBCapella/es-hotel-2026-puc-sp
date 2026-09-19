package hotel.modelo;

/**
 * Apartamento com diária padrão (R$ 150,00). Herda de {@link Apartamento} o estado e as
 * transições (reservar, check-in, check-out e cancelamento) e sobrescreve apenas o preço da diária.
 */
public class ApartamentoSimples extends Apartamento {
    private static final float PRECO_DIARIA = 150.0f;

    /**
     * Constrói um apartamento simples no estado inicial LIVRE e sem hóspede associado.
     *
     * @pre Nenhuma
     * @post Instância criada com status LIVRE, hospede null e diária de R$ 150,00
     */
    public ApartamentoSimples() {
        super();
    }

    /**
     * Obtém o valor da diária do apartamento simples.
     *
     * @return float com o valor da diária (150.0)
     * @pre Nenhuma
     * @post Retorna sempre 150.0, sem alterar o estado do apartamento
     */
    @Override
    public float getPrecoDiaria() {
        return PRECO_DIARIA;
    }
}
