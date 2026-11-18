package hotel.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Reserva de um apartamento: identifica a estadia, o hóspede titular responsável e a
 * quantidade de ocupantes do quarto.
 */
public class Reserva implements Serializable {
    private int idReserva;
    private Hospede titular;
    private int quantidadeOcupantes;

    /**
     * Constrói uma reserva com um titular como ocupante inicial.
     *
     * @param idReserva Identificador da reserva
     * @param titular Hóspede titular
     * @throws IllegalStateException se o titular for nulo
     *
     * @pre O titular não pode ser nulo
     * @post Reserva criada com o titular e quantidade de ocupantes igual a 1
     */
    public Reserva(int idReserva, Hospede titular) {
        if (titular == null) {
            throw new IllegalStateException();
        }

        this.idReserva = idReserva;
        this.titular = titular;
        this.quantidadeOcupantes = 1;
    }

    /**
     * Obtém o identificador da reserva.
     *
     * @return int com o identificador
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    public int getIdReserva() { return idReserva; }

    /**
     * Obtém o hóspede titular da reserva.
     *
     * @return Hospede titular
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    public Hospede getTitular() { return titular; }

    /**
     * Obtém a quantidade total de ocupantes da reserva.
     * O titular é contabilizado como um ocupante.
     *
     * @return quantidade total de ocupantes
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    public int getQuantidadeOcupantes() { return quantidadeOcupantes; }

    /**
     * Adiciona um ocupante à reserva.
     *
     * @pre Nenhuma
     * @post A quantidade de ocupantes é incrementada em 1
     */
    public void adicionarOcupante() { quantidadeOcupantes++; }
}