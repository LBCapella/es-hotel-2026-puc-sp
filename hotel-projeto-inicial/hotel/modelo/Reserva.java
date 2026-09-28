package hotel.modelo;

import java.util.ArrayList;
import java.util.List;

public class Reserva {
    private int idReserva;
    private Hospede titular;
    private List<Hospede> hospedes;

    public Reserva(int idReserva, Hospede titular) {
        this.idReserva = idReserva;
        this.titular = titular;
        this.hospedes = new ArrayList<>();

        hospedes.add(titular);
    }

    public Hospede getTitular() { return titular; }
    public void adicionarHospede(Hospede hospede) { hospedes.add(hospede); }
    public List<Hospede> getHospedes() { return hospedes; }
}