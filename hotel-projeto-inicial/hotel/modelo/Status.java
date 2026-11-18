package hotel.modelo;

/**
 * Situação de um apartamento: LIVRE, RESERVADO ou OCUPADO.
 */
public enum Status {
    /** Apartamento disponível, sem reserva nem hóspede. */
    LIVRE,
    /** Apartamento reservado, aguardando o check-in. */
    RESERVADO,
    /** Apartamento com hóspede hospedado. */
    OCUPADO;
}
