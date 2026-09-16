package hotel.modelo;

import java.io.Serializable;

/**
 * Representa um apartamento do hotel, controlando seu status e transições de estado.
 */
public class Apartamento implements Serializable {
    private Status status;
    private Hospede hospede;

    /**
     * Constrói um Apartamento no estado inicial LIVRE e sem hóspede associado.
     *
     * @pre Nenhuma
     * @post Instância criada com status LIVRE e hospede null
     */
    public Apartamento() {
        this.status = Status.LIVRE;
        this.hospede = null;
    }

    /**
     * Retorna o status atual do apartamento.
     *
     * @return Status enum (LIVRE, RESERVADO ou OCUPADO)
     * @pre Nenhuma
     * @post Retorna o status corrente
     */
    public Status getStatus() { return status; }

    /**
     * Retorna o hóspede associado ao apartamento.
     *
     * @return Hospede ou null se o apartamento estiver LIVRE
     * @pre Nenhuma
     * @post Retorna a referência do hóspede associado
     */
    public Hospede getHospede() { return hospede; }

    /**
     * Realiza a reserva de um apartamento para um hóspede.
     *
     * @param h Dados do hóspede que realizará a reserva
     * @throws IllegalArgumentException se o hóspede for nulo
     * @throws IllegalStateException se o apartamento não estiver no status LIVRE
     *
     * @pre O apartamento deve estar com status LIVRE e o hóspede não pode ser nulo
     * @post O apartamento transiciona para RESERVADO e o hóspede é armazenado
     */
    public void reservar(Hospede h) {
        if (h == null) {
            throw new IllegalArgumentException("Hóspede não pode ser nulo para reserva.");
        }
        if (status != Status.LIVRE) {
            throw new IllegalStateException("Apartamento não está livre para ser reservado.");
        }
        this.status = Status.RESERVADO;
        this.hospede = h;
    }

    /**
     * Realiza o check-in de um hóspede no apartamento.
     * Aceita transição a partir dos status LIVRE ou RESERVADO.
     *
     * @param h Dados do hóspede que fará o check-in
     * @throws IllegalArgumentException se o hóspede for nulo
     * @throws IllegalStateException se o apartamento já estiver no status OCUPADO
     *
     * @pre O apartamento deve estar com status LIVRE ou RESERVADO e o hóspede não pode ser nulo
     * @post O apartamento transiciona para OCUPADO e o hóspede é armazenado
     */
    public void checkin(Hospede h) {
        if (h == null) {
            throw new IllegalArgumentException("Hóspede não pode ser nulo para check-in.");
        }
        if (status == Status.OCUPADO) {
            throw new IllegalStateException("Apartamento já está ocupado.");
        }
        this.status = Status.OCUPADO;
        this.hospede = h;
    }

    /**
     * Realiza o check-out do apartamento, liberando a unidade.
     *
     * @throws IllegalStateException se o apartamento não estiver no status OCUPADO
     *
     * @pre O apartamento deve estar com status OCUPADO
     * @post O apartamento transiciona para LIVRE e o hóspede torna-se null
     */
    public void checkout() {
        if (status != Status.OCUPADO) {
            throw new IllegalStateException("Apenas apartamentos ocupados podem realizar check-out.");
        }
        this.status = Status.LIVRE;
        this.hospede = null;
    }

    /**
     * Cancela uma reserva previamente realizada no apartamento.
     *
     * @throws IllegalStateException se o apartamento não estiver no status RESERVADO
     *
     * @pre O apartamento deve estar com status RESERVADO
     * @post O apartamento transiciona para LIVRE e o hóspede torna-se null
     */
    public void cancelarReserva() {
        if (status != Status.RESERVADO) {
            throw new IllegalStateException("Apenas apartamentos reservados podem ter a reserva cancelada.");
        }
        this.status = Status.LIVRE;
        this.hospede = null;
    }

    /**
     * Verifica se o apartamento está livre.
     *
     * @return true se status for LIVRE, false caso contrário
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    public boolean estaLivre() { return status == Status.LIVRE; }

    /**
     * Verifica se o apartamento está reservado.
     *
     * @return true se status for RESERVADO, false caso contrário
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    public boolean estaReservado() { return status == Status.RESERVADO; }

    /**
     * Verifica se o apartamento está ocupado.
     *
     * @return true se status for OCUPADO, false caso contrário
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    public boolean estaOcupado() { return status == Status.OCUPADO; }

    /**
     * Obtém o valor da diária do apartamento.
     *
     * @return float representando o valor da diária (0f por padrão na classe base)
     * @pre Nenhuma
     * @post Retorna o preço base da diária
     */
    public float getPrecoDiaria() { return 0f; }

    /**
     * Retorna o caractere que representa o status do apartamento no mapa visual.
     *
     * @return '.' para LIVRE, 'R' para RESERVADO, 'O' para OCUPADO
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    public char getSymbol() {
        switch (status) {
            case LIVRE: return '.';
            case RESERVADO: return 'R';
            case OCUPADO: return 'O';
            default: return '?';
        }
    }
}
