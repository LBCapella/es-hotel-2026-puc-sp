package hotel.negocio;

import hotel.modelo.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Fachada principal do sistema de hotelaria. Gerencia a coleção de apartamentos e coordena as operações.
 */
public class Hotel implements Serializable {
    public static final int NUM_ANDARES = 20;
    public static final int APTOS_POR_ANDAR = 14;
    public static final int SIMPLES_POR_ANDAR =8;

    private Apartamento[][] matriz;
    private ArrayList<Servico> servicos;
    private ArrayList<Consumo> consumos;

    /**
     * Constrói o Hotel inicializando a matriz de apartamentos (20 andares x 14 apartamentos)
     * no estado LIVRE e as coleções de serviços e consumos.
     *
     * @pre Nenhuma
     * @post Instância de Hotel criada com 280 apartamentos com status LIVRE
     */
    public Hotel() {
        this.matriz = new Apartamento[NUM_ANDARES][APTOS_POR_ANDAR];
        this.servicos = new ArrayList<>();
        this.consumos = new ArrayList<>();
        inicializar();
    }

    private void inicializar() {
        for (int a = 0; a < NUM_ANDARES; a++) {
            for (int n = 0; n < APTOS_POR_ANDAR; n++) {
                if(n < SIMPLES_POR_ANDAR) {
                    matriz[a][n] = new ApartamentoSimples();
                } else {
                    matriz[a][n] = new ApartamentoPremium();
                }
            }
        }
    }

    private boolean aptoValido(int andar, int numero) {
        return andar >= 0 && andar < NUM_ANDARES && numero >= 0 && numero < APTOS_POR_ANDAR;
    }

    /**
     * Reserva um apartamento no andar e número especificados.
     *
     * @param andar Número do andar (0 a 19)
     * @param numero Número do apartamento no andar (0 a 13)
     * @param hospede Dados do hóspede que realizará a reserva
     * @return true se a reserva foi realizada com sucesso
     * @throws IllegalArgumentException se o andar ou o número forem inválidos
     * @throws IllegalStateException se o apartamento não estiver LIVRE
     *
     * @pre O andar e número devem ser válidos e o apartamento deve estar LIVRE
     * @post O apartamento nas coordenadas dadas terá status RESERVADO e hóspede associado
     */
    public boolean reservarApartamento(int andar, int numero, Hospede hospede) {
        if (!aptoValido(andar, numero)) {
            throw new IllegalArgumentException("Andar ou numero invalido");
        }
        matriz[andar][numero].reservar(hospede);
        return true;
    }

    /**
     * Realiza o check-in no apartamento especificado.
     *
     * @param andar Número do andar (0 a 19)
     * @param numero Número do apartamento no andar (0 a 13)
     * @param hospede Dados do hóspede que realizará o check-in
     * @return true se o check-in foi realizado com sucesso
     * @throws IllegalArgumentException se o andar ou o número forem inválidos
     * @throws IllegalStateException se o apartamento já estiver OCUPADO
     *
     * @pre O andar e número devem ser válidos e o apartamento deve estar LIVRE ou RESERVADO
     * @post O apartamento nas coordenadas dadas terá status OCUPADO e hóspede associado
     */
    public boolean realizarCheckin(int andar, int numero, Hospede hospede) {
        if (!aptoValido(andar, numero)) {
            throw new IllegalArgumentException("Andar ou numero invalido");
        }
        matriz[andar][numero].checkin(hospede);
        return true;
    }

    /**
     * Realiza o check-out no apartamento especificado.
     *
     * @param andar Número do andar (0 a 19)
     * @param numero Número do apartamento no andar (0 a 13)
     * @return true se o check-out foi realizado com sucesso
     * @throws IllegalArgumentException se o andar ou o número forem inválidos
     * @throws IllegalStateException se o apartamento não estiver OCUPADO
     *
     * @pre O andar e número devem ser válidos e o apartamento deve estar OCUPADO
     * @post O apartamento nas coordenadas dadas terá status LIVRE e hóspede nulo
     */
    public boolean realizarCheckout(int andar, int numero) {
        if (!aptoValido(andar, numero)) {
            throw new IllegalArgumentException("Andar ou numero invalido");
        }
        matriz[andar][numero].checkout();
        return true;
    }

    /**
     * Cancela uma reserva existente em um apartamento.
     *
     * @param andar Número do andar (0 a 19)
     * @param numero Número do apartamento no andar (0 a 13)
     * @return true se o cancelamento foi realizado com sucesso
     * @throws IllegalArgumentException se o andar ou o número forem inválidos
     * @throws IllegalStateException se o apartamento não estiver RESERVADO
     *
     * @pre O andar e número devem ser válidos e o apartamento deve estar RESERVADO
     * @post O apartamento nas coordenadas dadas terá status LIVRE e hóspede nulo
     */
    public boolean cancelarReserva(int andar, int numero) {
        if (!aptoValido(andar, numero)) {
            throw new IllegalArgumentException("Andar ou numero invalido");
        }
        matriz[andar][numero].cancelarReserva();
        return true;
    }

    public void mostrarMapa() {
        throw new UnsupportedOperationException("Implementar mostrarMapa");
    }

    public void consultarApartamento(int andar, int numero) {
        if (!aptoValido(andar, numero)) {
            throw new IllegalArgumentException("Andar ou numero invalido");
        }
        throw new UnsupportedOperationException("Implementar consultarApartamento");
    }

    public float calcularTaxaOcupacao() {
        throw new UnsupportedOperationException("Implementar calcularTaxaOcupacao");
    }

    public float calcularTaxaReservas() {
        throw new UnsupportedOperationException("Implementar calcularTaxaReservas");
    }

    public void cadastrarServico(String nome, float preco) {
        throw new UnsupportedOperationException("Implementar cadastrarServico");
    }

    public boolean registrarConsumo(int andar, int numero, int indiceServico, int quantidade) {
        if (!aptoValido(andar, numero)) {
            throw new IllegalArgumentException("Andar ou numero invalido");
        }
        throw new UnsupportedOperationException("Implementar registrarConsumo");
    }

    public ArrayList<Consumo> getConsumosDoApartamento(int andar, int numero) {
        if (!aptoValido(andar, numero)) {
            throw new IllegalArgumentException("Andar ou numero invalido");
        }
        throw new UnsupportedOperationException("Implementar getConsumosDoApartamento");
    }

    public Fatura emitirFatura(int andar, int numero, int dias) {
        if (!aptoValido(andar, numero)) {
            throw new IllegalArgumentException("Andar ou numero invalido");
        }
        throw new UnsupportedOperationException("Implementar emitirFatura");
    }

    /**
     * Obtém o apartamento localizado no andar e número especificados.
     *
     * @param andar Número do andar (0 a 19)
     * @param numero Número do apartamento no andar (0 a 13)
     * @return Objeto Apartamento localizado na posição informada
     * @throws IllegalArgumentException se o andar ou o número forem inválidos
     *
     * @pre O andar e o número devem ser válidos
     * @post Retorna a referência do apartamento consultado
     */
    public Apartamento getApartamento(int andar, int numero) {
        if (!aptoValido(andar, numero)) {
            throw new IllegalArgumentException("Andar ou numero invalido");
        }
        return matriz[andar][numero];
    }

    public ArrayList<Servico> getServicos() { return servicos; }
    public ArrayList<Consumo> getConsumos() { return consumos; }
}
