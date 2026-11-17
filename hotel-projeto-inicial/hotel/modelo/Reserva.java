package hotel.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Reserva implements Serializable {
    private int idReserva;
    private Hospede titular;
    private int quantidadeOcupantes;

     /**
     * Constrói uma reserva com o titular já incluído na lista de hóspedes.
     *
     * @param idReserva Identificador da reserva
     * @param titular Hóspede titular
     * @throws IllegalStateException se o titular for nulo
     *
     * @pre O titular não pode ser nulo
     * @post Reserva criada com o titular como único hóspede
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
    public int getIdReserva() {
        return idReserva;
    }

    /**
     * Obtém o hóspede titular da reserva.
     *
     * @return Hospede titular
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    public Hospede getTitular() { return titular; }

    public int getQuantidadeOcupantes() { return quantidadeOcupantes; }

    public void adicionarOcupante() { quantidadeOcupantes++; }
}