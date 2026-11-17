package hotel.modelo;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Reserva implements Serializable {
    private int idReserva;
    private Hospede titular;
    private List<Hospede> hospedes;

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
        this.hospedes = new ArrayList<>();

        hospedes.add(titular);
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

    /**
     * Adiciona um hóspede à reserva.
     *
     * @param hospede Hóspede a ser adicionado
     * @throws IllegalStateException se o hóspede for nulo
     *
     * @pre O hóspede não pode ser nulo
     * @post O hóspede é acrescentado ao final da lista de hóspedes da reserva
     */
    public void adicionarHospede(Hospede hospede) {
        if (hospede == null) {
            throw new IllegalStateException();
        }
        hospedes.add(hospede);
    }

    /**
     * Obtém uma cópia da lista de hóspedes da reserva.
     *
     * @return List com os hóspedes (alterações na cópia não afetam a reserva)
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    public List<Hospede> getHospedes() { return new ArrayList<>(hospedes); }
}