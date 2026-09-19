package hotel.modelo;

/**
 * Apartamento com diária premium (R$ 350,00). Herda de {@link Apartamento} o estado e as
 * transições (reservar, check-in, check-out e cancelamento) e sobrescreve apenas o preço da diária.
 */
public class ApartamentoPremium extends Apartamento {
    private static final float PRECO_DIARIA = 350.0f;

    /**
     * Constrói um apartamento premium no estado inicial LIVRE e sem hóspede associado.
     *
     * @pre Nenhuma
     * @post Instância criada com status LIVRE, hospede null e diária de R$ 350,00
     */
    public ApartamentoPremium() {
        super();
    }

    /**
     * Obtém o valor da diária do apartamento premium.
     *
     * @return float com o valor da diária (350.0)
     * @pre Nenhuma
     * @post Retorna sempre 350.0, sem alterar o estado do apartamento
     */
    @Override
    public float getPrecoDiaria() {
        return PRECO_DIARIA;
    }
}
