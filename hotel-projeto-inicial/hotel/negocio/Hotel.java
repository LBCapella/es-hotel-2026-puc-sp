package hotel.negocio;

import hotel.modelo.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Fachada principal do sistema de hotelaria. Gerencia a coleção de apartamentos e coordena as operações.
 */
public class Hotel implements Serializable {
    /** Quantidade de andares do hotel. */
    public static final int NUM_ANDARES = 20;
    /** Quantidade de apartamentos em cada andar. */
    public static final int APTOS_POR_ANDAR = 14;
    /** Quantidade de apartamentos simples por andar (as primeiras posições); os demais são premium. */
    public static final int SIMPLES_POR_ANDAR = 8;
    private int proximoIdReserva;

    private Apartamento[][] matriz;
    private ArrayList<Servico> servicos;
    private ArrayList<Consumo> consumos;
    private ArrayList<Consumo> consumosEstadiaAtual;

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
        this.consumosEstadiaAtual = new ArrayList<>();
        this.proximoIdReserva = 1;
        inicializar();
    }

    /**
     * Inicializa a matriz de apartamentos do hotel, criando apartamentos simples
     * e premium conforme a posição de cada apartamento no andar.
     *
     * @pre A matriz de apartamentos deve estar inicializada
     * @post Todos os apartamentos da matriz estarão criados e disponíveis para uso
     */
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

    /**
     * Verifica se as coordenadas informadas correspondem a um apartamento válido do hotel.
     *
     * @param andar Número do andar a ser validado
     * @param numero Número do apartamento no andar a ser validado
     * @return true se o andar e o número estiverem dentro dos limites válidos; false caso contrário
     *
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    private boolean aptoValido(int andar, int numero) {
        return andar >= 0 && andar < NUM_ANDARES && numero >= 0 && numero < APTOS_POR_ANDAR;
    }

    /**
     * Reserva um apartamento no andar e número especificados.
     *
     * @param andar Número do andar (0 a 19)
     * @param numero Número do apartamento no andar (0 a 13)
     * @param reserva Reserva que será associada ao apartamento
     * @return true se a reserva foi realizada com sucesso
     * @throws IllegalArgumentException se o andar ou o número forem inválidos
     * @throws IllegalStateException se o apartamento não estiver LIVRE
     *
     * @pre O andar e número devem ser válidos e o apartamento deve estar LIVRE
     * @post O apartamento nas coordenadas dadas terá status RESERVADO e estará associado à reserva informada
     */
    public boolean reservarApartamento(int andar, int numero, Reserva reserva) {
        if (!aptoValido(andar, numero)) {
            throw new IllegalArgumentException("Andar ou numero invalido");
        }
        matriz[andar][numero].reservar(reserva);
        return true;
    }

    /**
     * Realiza o check-in no apartamento especificado.
     *
     * @param andar Número do andar (0 a 19)
     * @param numero Número do apartamento no andar (0 a 13)
     * @param reserva Reserva associada ao hóspede que realizará o check-in
     * @return true se o check-in foi realizado com sucesso
     * @throws IllegalArgumentException se o andar ou o número forem inválidos
     * @throws IllegalStateException se o apartamento já estiver OCUPADO
     *
     * @pre O andar e número devem ser válidos e o apartamento deve estar LIVRE ou RESERVADO
     * @post O apartamento nas coordenadas dadas terá status OCUPADO e estará associado à reserva informada
     */
    public boolean realizarCheckin(int andar, int numero, Reserva reserva) {
        if (!aptoValido(andar, numero)) {
            throw new IllegalArgumentException("Andar ou numero invalido");
        }
        matriz[andar][numero].checkin(reserva);
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
     * @post O apartamento nas coordenadas dadas terá status LIVRE e sem reserva associada;
     *       seus consumos deixam de constar em getConsumosDoApartamento, mas permanecem em getConsumos
     */
    public boolean realizarCheckout(int andar, int numero) {
        if (!aptoValido(andar, numero)) {
            throw new IllegalArgumentException("Andar ou numero invalido");
        }
        matriz[andar][numero].checkout();
        consumosEstadiaAtual.removeIf(c -> c.getApartamento() == matriz[andar][numero]);
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
     * @post O apartamento nas coordenadas dadas terá status LIVRE e sem reserva associada
     */
    public boolean cancelarReserva(int andar, int numero) {
        if (!aptoValido(andar, numero)) {
            throw new IllegalArgumentException("Andar ou numero invalido");
        }
        matriz[andar][numero].cancelarReserva();
        return true;
    }

    /**
     * Imprime no console o mapa de ocupação do hotel, uma linha por andar, com o símbolo
     * de cada apartamento (. = LIVRE, R = RESERVADO, O = OCUPADO).
     *
     * @pre O hotel deve estar inicializado (construtor já o faz)
     * @post Nenhuma alteração de estado; apenas exibição
     */
    public void mostrarMapa() {
        for (int a = 0; a < NUM_ANDARES; a++) {
            StringBuilder linha = new StringBuilder();
            linha.append("Andar ").append(String.format("%02d", a)).append(": ");
            for (int n = 0; n < APTOS_POR_ANDAR; n++) {
                linha.append(matriz[a][n].getSymbol()).append(" ");
            }
            System.out.println(linha.toString().trim());
        }
    }

    /**
     * Exibe o tipo, o status e a diária do apartamento e, se ele não estiver LIVRE,
     * o número da reserva, a quantidade de ocupantes e os dados do hóspede titular
     * (nome, CPF, endereço, celular e e-mail).
     *
     * @param andar Número do andar (0 a 19)
     * @param numero Número do apartamento no andar (0 a 13)
     * @throws IllegalArgumentException se o andar ou o número forem inválidos
     *
     * @pre O andar e o número devem ser válidos
     * @post Nenhuma alteração de estado; apenas exibição
     */
    public void consultarApartamento(int andar, int numero) {
        if (!aptoValido(andar, numero)) {
            throw new IllegalArgumentException("Andar ou numero invalido");
        }
        Apartamento apto = matriz[andar][numero];
        System.out.println("Andar " + andar + ", Apto " + numero + ": " + apto);
        if (!apto.estaLivre()) {
            Reserva reserva = apto.getReserva();
            Hospede titular = reserva.getTitular();
            System.out.println("Reserva nº " + reserva.getIdReserva() + " - Ocupantes: " + reserva.getQuantidadeOcupantes());
            System.out.println("Titular: " + titular.getNome());
            System.out.println("CPF: " + titular.getCpf());
            System.out.println("Endereço: " + titular.getEndereco());
            System.out.println("Celular: " + titular.getCelular());
            System.out.println("Email: " + titular.getEmail());
        }
    }

    /**
     * Conta a quantidade de apartamentos que possuem o status informado.
     *
     * @param status Status dos apartamentos que devem ser contabilizados
     * @return Quantidade de apartamentos que possuem o status informado
     *
     * @pre O status deve ser um valor válido do enum Status
     * @post Nenhuma alteração de estado
     */
    private int contar(Status status) {
        int count = 0;
        for (int a = 0; a < NUM_ANDARES; a++) {
            for (int n = 0; n < APTOS_POR_ANDAR; n++) {
                if (matriz[a][n].getStatus() == status) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Calcula a proporção de apartamentos com status OCUPADO em relação ao total do hotel.
     *
     * @return float entre 0.0 e 1.0 com a taxa de ocupação
     * @pre O hotel deve estar inicializado (construtor já o faz)
     * @post Nenhuma alteração de estado
     */
    public float calcularTaxaOcupacao() {
        return (float) contar(Status.OCUPADO) / (NUM_ANDARES * APTOS_POR_ANDAR);
    }

    /**
     * Calcula a proporção de apartamentos com status RESERVADO em relação ao total do hotel.
     *
     * @return float entre 0.0 e 1.0 com a taxa de reservas
     * @pre O hotel deve estar inicializado (construtor já o faz)
     * @post Nenhuma alteração de estado
     */
    public float calcularTaxaReservas() {
        return (float) contar(Status.RESERVADO) / (NUM_ANDARES * APTOS_POR_ANDAR);
    }

    /**
     * Cadastra um serviço no cardápio do hotel.
     *
     * @param nome Nome do serviço
     * @param preco Preço unitário do serviço
     * @throws IllegalArgumentException se o nome for nulo ou o preço for negativo
     *
     * @pre O nome não pode ser nulo e o preço não pode ser negativo
     * @post O serviço é adicionado ao final da lista de serviços do hotel
     */
    public void cadastrarServico(String nome, float preco) {
        servicos.add(new Servico(nome, preco));
    }

    /**
     * Registra o consumo de um serviço do cardápio em um apartamento ocupado.
     *
     * @param andar Número do andar (0 a 19)
     * @param numero Número do apartamento no andar (0 a 13)
     * @param indiceServico Posição do serviço na lista de serviços cadastrados
     * @param quantidade Quantidade consumida
     * @return true se o consumo foi registrado com sucesso
     * @throws IllegalArgumentException se o andar, o número, o índice do serviço ou a quantidade forem inválidos
     * @throws IllegalStateException se o apartamento não estiver OCUPADO
     *
     * @pre O apartamento deve estar OCUPADO, o serviço deve estar cadastrado e a quantidade deve ser maior que zero
     * @post Um Consumo é adicionado à lista de consumos do hotel e aos consumos da estadia atual do apartamento
     */
    public boolean registrarConsumo(int andar, int numero, int indiceServico, int quantidade) {
        if (!aptoValido(andar, numero)) {
            throw new IllegalArgumentException("Andar ou numero invalido");
        }
        if (indiceServico < 0 || indiceServico >= servicos.size()) {
            throw new IllegalArgumentException("Indice de servico invalido");
        }
        Apartamento apto = matriz[andar][numero];
        if (!apto.estaOcupado()) {
            throw new IllegalStateException("Apartamento nao esta ocupado");
        }
        Consumo consumo = new Consumo(apto, apto.getReserva().getTitular(), servicos.get(indiceServico), quantidade);
        consumos.add(consumo);
        consumosEstadiaAtual.add(consumo);
        return true;
    }

    /**
     * Obtém os consumos de serviços da estadia atual de um apartamento.
     *
     * @param andar Número do andar (0 a 19)
     * @param numero Número do apartamento no andar (0 a 13)
     * @return ArrayList com os consumos da estadia atual do apartamento (vazia se não houver nenhum)
     * @throws IllegalArgumentException se o andar ou o número forem inválidos
     *
     * @pre O andar e o número devem ser válidos
     * @post Nenhuma alteração de estado; retorna uma nova lista apenas com os consumos da estadia atual do apartamento
     */
    public ArrayList<Consumo> getConsumosDoApartamento(int andar, int numero) {
        if (!aptoValido(andar, numero)) {
            throw new IllegalArgumentException("Andar ou numero invalido");
        }
        ArrayList<Consumo> resultado = new ArrayList<>();
        for (Consumo c : consumosEstadiaAtual) {
            if (c.getApartamento() == matriz[andar][numero]) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    /**
     * Emite a fatura de um apartamento ocupado, somando as diárias e os consumos de serviços.
     *
     * @param andar Número do andar (0 a 19)
     * @param numero Número do apartamento no andar (0 a 13)
     * @param dias Quantidade de diárias a faturar
     * @return Fatura com o total das diárias e dos consumos do apartamento
     * @throws IllegalArgumentException se o andar, o número ou a quantidade de dias forem inválidos
     * @throws IllegalStateException se o apartamento não estiver OCUPADO
     *
     * @pre O apartamento deve estar OCUPADO e os dias não podem ser negativos
     * @post Nenhuma alteração de estado; retorna a Fatura calculada
     */
    public Fatura emitirFatura(int andar, int numero, int dias) {
        if (!aptoValido(andar, numero)) {
            throw new IllegalArgumentException("Andar ou numero invalido");
        }
        Apartamento apto = matriz[andar][numero];
        if (!apto.estaOcupado()) {
            throw new IllegalStateException("Apartamento nao esta ocupado");
        }
        return new Fatura(apto, dias, getConsumosDoApartamento(andar, numero));
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

    /**
     * Obtém a lista de serviços cadastrados no cardápio do hotel.
     *
     * @return ArrayList com os serviços cadastrados (vazia se nenhum foi cadastrado)
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    public ArrayList<Servico> getServicos() { return servicos; }

    /**
     * Obtém a lista de todos os consumos de serviço registrados no hotel.
     *
     * @return ArrayList com os consumos registrados (vazia se nenhum foi registrado)
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    public ArrayList<Consumo> getConsumos() { return consumos; }

    /**
     * Realiza o check-out e registra a duração da estadia no histórico de ocupação do apartamento.
     *
     * @param andar Número do andar (0 a 19)
     * @param numero Número do apartamento no andar (0 a 13)
     * @param dias Quantidade de dias da estadia encerrada (maior que zero)
     * @return true se o check-out foi realizado com sucesso
     * @throws IllegalArgumentException se o andar, o número ou a quantidade de dias forem inválidos
     * @throws IllegalStateException se o apartamento não estiver OCUPADO
     *
     * @pre O apartamento deve estar OCUPADO e dias deve ser maior que zero
     * @post O apartamento fica LIVRE e seus dias ocupados são acrescidos de dias; se houver erro, nada é alterado
     */
    public boolean realizarCheckout(int andar, int numero, int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Quantidade de dias deve ser maior que zero");
        }
        realizarCheckout(andar, numero);
        matriz[andar][numero].adicionarDiasOcupados(dias);
        return true;
    }

    /**
     * Obtém o total de dias que o apartamento ficou ocupado em estadias já encerradas.
     *
     * @param andar Número do andar (0 a 19)
     * @param numero Número do apartamento no andar (0 a 13)
     * @return int com a soma dos dias registrados nos check-outs do apartamento
     *
     * @pre O andar e o número devem ser válidos
     * @post Nenhuma alteração de estado
     */
    public int getDiasOcupado(int andar, int numero) { return matriz[andar][numero].getDiasOcupados(); }

    /**
     * Gera o identificador da próxima reserva do hotel.
     *
     * @return int com um identificador ainda não utilizado (começa em 1)
     *
     * @pre Nenhuma
     * @post O contador de reservas é incrementado em 1, de modo que identificadores não se repetem
     */
    public int gerarProximoIdReserva() { return proximoIdReserva++; }
}
