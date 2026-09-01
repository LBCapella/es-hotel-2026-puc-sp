package hotel.modelo;

import java.io.Serializable;

public class Apartamento implements Serializable {
    private Status status;
    private Hospede hospede;

    public Apartamento() {
        this.status = Status.LIVRE;
        this.hospede = null;
    }

    public Status getStatus() { return status; }
    public Hospede getHospede() { return hospede; }

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

    public void checkin(Hospede h) {
        throw new UnsupportedOperationException("Implementar: LIVRE/RESERVADO -> OCUPADO");
    }

    public void checkout() {
        throw new UnsupportedOperationException("Implementar: OCUPADO -> LIVRE");
    }

    public void cancelarReserva() {
        if (status != Status.RESERVADO) {
            throw new IllegalStateException("Apenas apartamentos reservados podem ter a reserva cancelada.");
        }
        this.status = Status.LIVRE;
        this.hospede = null;
    }

    public boolean estaLivre() { return status == Status.LIVRE; }
    public boolean estaReservado() { return status == Status.RESERVADO; }
    public boolean estaOcupado() { return status == Status.OCUPADO; }

    public float getPrecoDiaria() { return 0f; }

    public char getSymbol() {
        switch (status) {
            case LIVRE: return '.';
            case RESERVADO: return 'R';
            case OCUPADO: return 'O';
            default: return '?';
        }
    }
}
