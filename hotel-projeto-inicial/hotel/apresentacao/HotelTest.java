//REFACTOR
package hotel.apresentacao;

import hotel.modelo.*;
import hotel.negocio.Hotel;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HotelTest {
    private static int passou = 0;
    private static int total = 0;

    public static void main(String[] args) {
        // Ciclo 1 - Hospede
        testarCriacaoHospedeValido();
        testarHospedeParametroNuloLancaExcecao();
        testarHospedeEqualsEHashCodePorCpf();

        // Ciclo 2 - Apartamento (Reserva & Cancelamento)
        testarReservaPodeTerVariosHospedes();
        testarReservaNaoPermiteHospedeNulo();
        testarReservaNaoPermiteTitularNulo();
        testarApartamentoReservadoComReserva();
        testarReservaNaoPermiteAlteracaoExternaDaLista();

        testarReservarApartamentoLivre();
        testarReservarApartamentoNaoLivreLancaExcecao();
        testarCancelarReservaComSucesso();
        testarCancelarReservaInvalidaLancaExcecao();

        // Ciclo 3 - Apartamento (Check-in & Check-out)
        testarCheckinApartamentoReservado();
        testarCheckinApartamentoJaOcupadoLancaExcecao();
        testarCheckoutComSucesso();
        testarCheckoutApartamentoNaoOcupadoLancaExcecao();

        // Ciclo 4 - Fachada Hotel & Coordenadas
        testarInicializacaoHotelMatrizLivre();
        testarCoordenadasInvalidasLancaExcecao();
        testarOperacoesFachadaHotelDelegacao();

        // Ciclo 5 - Hierarquia de Apartamentos & Integração
        testarApartamentoBasePrecoDiaria();
        testarApartamentoSimplesPrecoDiaria();
        testarApartamentoPremiumPrecoDiaria();
        testarHotelInicializaSimplesEPremiumPorAndar();
        testarSubclassesHerdamTransicoesDeEstado();
        testarSubclassesHerdamValidacaoDeEstado();

        // Ciclo 6 - Apartamento.toString()
        testarToStringApartamentoSimplesOcupado();
        testarToStringApartamentoPremiumOcupado();
        testarToStringAcompanhaMudancaDeEstado();

        // Ciclo 7 - Apartamento taxas
        testarTaxaHotelVazio(); // devem ser 0.0
        testarTaxaOcupacaoProporcionalAosOcupados();
        testarTaxaReservasProporcionalAosReservados();
        testarTaxasNaoConfundemReservadoOcupado();

        // Ciclo 8 - mostrarMapa()
        testarMostrarMapaHotelVazio();
        testarMostrarMapaRefleteReservaECheckin();
        testarMostrarMapaNaoAlteraEstado();

        // Ciclo 9 - Servico
        testarCriacaoServicoValido();
        testarServicoNomeNuloLancaExcecao();
        testarServicoPrecoNegativoLancaExcecao();

        // Ciclo 10 - Consumo
        testarCriacaoConsumoValido();
        testarConsumoApartamentoNuloLancaExcecao();
        testarConsumoServicoNuloLancaExcecao();
        testarConsumoQuantidadeInvalidaLancaExcecao();

        // Ciclo 11 - Hotel cadastrarServico
        testarCadastrarServicoAdicionaAoCardapio();
        testarCadastrarServicoMantemOrdemDeCadastro();
        testarCadastrarServicoInvalidoLancaExcecao();

        // Ciclo 12 - Hotel registrarConsumo
        testarRegistrarConsumoApartamentoOcupado();
        testarRegistrarConsumoApartamentoLivreLancaExcecao();
        testarRegistrarConsumoApartamentoReservadoLancaExcecao();
        testarRegistrarConsumoCoordenadasInvalidasLancaExcecao();
        testarRegistrarConsumoIndiceServicoInvalidoLancaExcecao();
        testarRegistrarConsumoQuantidadeInvalidaLancaExcecao();

        // Ciclo 13 - Hotel getConsumosDoApartamento
        testarConsumosDoApartamentoRetornaApenasDoApartamento();
        testarConsumosDoApartamentoSemConsumosRetornaListaVazia();
        testarConsumosDoApartamentoCoordenadasInvalidasLancaExcecao();

        // Ciclo 14 - Fatura
        testarFaturaCalculaTotais();
        testarFaturaSemConsumos();
        testarFaturaApartamentoNuloLancaExcecao();
        testarFaturaDiasNegativosLancaExcecao();
        testarFaturaConsumosNulosLancaExcecao();

        // Ciclo 15 - Hotel emitirFatura
        testarEmitirFaturaApartamentoSimplesComConsumos();
        testarEmitirFaturaApartamentoPremium();
        testarEmitirFaturaApartamentoLivreLancaExcecao();
        testarEmitirFaturaApartamentoReservadoLancaExcecao();
        testarEmitirFaturaCoordenadasInvalidasLancaExcecao();
        testarEmitirFaturaDiasNegativosLancaExcecao();

        System.out.println(passou + "/" + total + " testes passaram");
        
        if (passou < total) {
            System.exit(1);
        }
    }

    static void testarCriacaoHospedeValido() {
        total++;
        try {
            Hospede h = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            if ("12345678900".equals(h.getCpf()) &&
                "João Silva".equals(h.getNome()) &&
                "Rua A, 123".equals(h.getEndereco()) &&
                "11999998888".equals(h.getCelular()) &&
                "joao@email.com".equals(h.getEmail())) {
                passou++;
            } else {
                System.out.println("FALHOU: testarCriacaoHospedeValido - Getters retornaram valores inconsistentes");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarCriacaoHospedeValido - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarHospedeParametroNuloLancaExcecao() {
        total++;
        try {
            new Hospede(null, "João", "Rua A", "11999998888", "joao@email.com");
            System.out.println("FALHOU: testarHospedeParametroNuloLancaExcecao - Permitiu criar Hospede com CPF nulo");
        } catch (IllegalArgumentException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarHospedeParametroNuloLancaExcecao - Lançou exceção diferente de IllegalArgumentException: " + e.getClass().getName());
        }
    }

    static void testarHospedeEqualsEHashCodePorCpf() {
        total++;
        try {
            Hospede h1 = new Hospede("12345678900", "João Silva", "Rua A", "11999998888", "joao@email.com");
            Hospede h2 = new Hospede("12345678900", "Maria Souza", "Rua B", "11888887777", "maria@email.com");
            Hospede h3 = new Hospede("98765432100", "João Silva", "Rua A", "11999998888", "joao@email.com");

            boolean equalsIgual = h1.equals(h2);
            boolean hashCodeIgual = h1.hashCode() == h2.hashCode();
            boolean equalsDiferente = !h1.equals(h3);

            if (equalsIgual && hashCodeIgual && equalsDiferente) {
                passou++;
            } else {
                System.out.println("FALHOU: testarHospedeEqualsEHashCodePorCpf - Comportamento de equals/hashCode incorreto");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarHospedeEqualsEHashCodePorCpf - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    // --- CICLO 2 ---

    static void testarReservaPodeTerVariosHospedes() {
        total++;
        try {
            Hospede h1 = new Hospede("12345678900", "João Silva", "Rua A", "11999998888", "joao@email.com");
            Hospede h2 = new Hospede("98765432100", "Maria Souza", "Rua B", "11888887777", "maria@email.com");
            
            Reserva reserva = new Reserva(1, h1);

            reserva.adicionarHospede(h2);

            if (reserva.getTitular().equals(h1) && reserva.getHospedes().size() == 2 && reserva.getHospedes().contains(h1) && reserva.getHospedes().contains(h2)) {
                passou++;
            } else {
                System.out.println("FALHOU: testarReservaPodeTerVariosHospedes - Reserva não possui os hóspedes esperados");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarReservaPodeTerVariosHospedes - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarReservaNaoPermiteHospedeNulo() {
        total++;
        try {
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A", "11999998888", "joao@email.com");

            Reserva reserva = new Reserva(1, titular);
            boolean rejeitouNulo = true;

            try {
                reserva.adicionarHospede(null);
                rejeitouNulo = false;
            } catch (Exception e) {
            }

            if (rejeitouNulo) {
                passou++;
            } else {
                System.out.println("FALHOU: testarReservaNaoPermiteHospedeNulo - Reserva permitiu adicionar hóspede nulo");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarReservaNaoPermiteHospedeNulo - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarReservaNaoPermiteTitularNulo() {
        total++;
        try {
            new Reserva(1, null);

            System.out.println("FALHOU: testarReservaNaoPermiteTitularNulo - Reserva permitiu titular nulo");
        } catch (IllegalStateException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarReservaNaoPermiteTitularNulo - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarApartamentoReservadoComReserva() {
        total++;
        try {
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A", "11999998888", "joao@email.com");
            Hospede hospede = new Hospede("98765432100", "Maria Silva", "Rua B", "11988887777", "maria@email.com");

            Reserva reserva = new Reserva(1, titular);
            reserva.adicionarHospede(hospede);

            Apartamento apto = new Apartamento();

            apto.reservar(reserva);

            if (apto.estaReservado() && apto.getSymbol() == 'R') {
                passou++;
            } else {
                System.out.println("FALHOU: testarApartamentoReservadoComReserva - Apartamento não ficou reservado corretamente");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarApartamentoReservadoComReserva - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarReservaNaoPermiteAlteracaoExternaDaLista() {
        total++;
        try {
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A", "11999998888", "joao@email.com");
            Hospede hospede = new Hospede( "98765432100", "Maria Silva", "Rua B", "11888887777", "maria@email.com");

            Reserva reserva = new Reserva(1, titular);
            reserva.adicionarHospede(hospede);

            List<Hospede> hospedes = reserva.getHospedes();
            hospedes.clear();

            if (reserva.getHospedes().size() == 2) {
                passou++;
            } else {
                System.out.println("FALHOU: testarReservaNaoPermiteAlteracaoExternaDaLista - " + "A lista interna da reserva foi alterada externamente");
            }

        } catch (Exception e) {
            System.out.println("FALHOU: testarReservaNaoPermiteAlteracaoExternaDaLista - " + "Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarReservarApartamentoLivre() {
        total++;
        try {
            Apartamento apto = new Apartamento();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A", "11999998888", "joao@email.com");
            Reserva reserva = new Reserva(1, titular);
            
            apto.reservar(reserva);
            
            if (apto.estaReservado() && reserva.equals(apto.getReserva()) && apto.getSymbol() == 'R') {
                passou++;
            } else {
                System.out.println("FALHOU: testarReservarApartamentoLivre - Estado/símbolo/reserva incorretos após reserva");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarReservarApartamentoLivre - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarReservarApartamentoNaoLivreLancaExcecao() {
        total++;
        try {
            Apartamento apto = new Apartamento();
            Hospede titular1 = new Hospede("12345678900", "João", "Rua A", "11999998888", "joao@email.com");
            Hospede titular2 = new Hospede("98765432100", "Maria", "Rua B", "11888887777", "maria@email.com");
            
            Reserva reserva1 = new Reserva(1, titular1); 
            Reserva reserva2 = new Reserva(2, titular2);

            apto.reservar(reserva1);
            apto.reservar(reserva2);
            
            System.out.println("FALHOU: testarReservarApartamentoNaoLivreLancaExcecao - Permitiu reservar apartamento que já estava reservado");
        } catch (IllegalStateException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarReservarApartamentoNaoLivreLancaExcecao - Lançou exceção incorreta: " + e.getMessage());
        }
    }

    static void testarCancelarReservaComSucesso() {
        total++;
        try {
            Apartamento apto = new Apartamento();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A", "11999998888", "joao@email.com");
            Reserva reserva = new Reserva(1, titular);

            apto.reservar(reserva);
            apto.cancelarReserva();
            
            if (apto.estaLivre() && apto.getReserva() == null && apto.getSymbol() == '.') {
                passou++;
            } else {
                System.out.println("FALHOU: testarCancelarReservaComSucesso - Estado/reserva não resetados após cancelamento");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarCancelarReservaComSucesso - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarCancelarReservaInvalidaLancaExcecao() {
        total++;
        try {
            Apartamento apto = new Apartamento();
            apto.cancelarReserva();
            
            System.out.println("FALHOU: testarCancelarReservaInvalidaLancaExcecao - Permitiu cancelar reserva em apartamento LIVRE");
        } catch (IllegalStateException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarCancelarReservaInvalidaLancaExcecao - Lançou exceção incorreta: " + e.getClass().getName());
        }
    }

    // --- CICLO 3 ---

    static void testarCheckinApartamentoReservado() {
        total++;
        try {
            Apartamento apto = new Apartamento();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A", "11999998888", "joao@email.com");
            Reserva reserva = new Reserva(1, titular);

            apto.reservar(reserva);
            apto.checkin(reserva);
            
            if (apto.estaOcupado() && reserva.equals(apto.getReserva()) && apto.getSymbol() == 'O') {
                passou++;
            } else {
                System.out.println("FALHOU: testarCheckinApartamentoReservado - Estado incorreto ao realizar check-in de apartamento reservado");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarCheckinApartamentoReservado - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarCheckinApartamentoJaOcupadoLancaExcecao() {
        total++;
        try {
            Apartamento apto = new Apartamento();
            Hospede titular1 = new Hospede("12345678900", "João", "Rua A", "11999998888", "joao@email.com");
            Hospede titular2 = new Hospede("98765432100", "Maria", "Rua B", "11888887777", "maria@email.com");
            
            Reserva reserva1 = new Reserva(1, titular1); 
            Reserva reserva2 = new Reserva(2, titular2); 

            apto.reservar(reserva1); 
            apto.checkin(reserva1);
            apto.checkin(reserva2);
            
            System.out.println("FALHOU: testarCheckinApartamentoJaOcupadoLancaExcecao - Permitiu check-in em apartamento já ocupado");
        } catch (IllegalStateException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarCheckinApartamentoJaOcupadoLancaExcecao - Lançou exceção incorreta: " + e.getMessage());
        }
    }

    static void testarCheckoutComSucesso() {
        total++;
        try {
            Apartamento apto = new Apartamento();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A", "11999998888", "joao@email.com");
            Reserva reserva = new Reserva(1, titular); 

            apto.reservar(reserva);
            apto.checkin(reserva);
            apto.checkout();
            
            if (apto.estaLivre() && apto.getReserva() == null && apto.getSymbol() == '.') {
                passou++;
            } else {
                System.out.println("FALHOU: testarCheckoutComSucesso - Estado incorreto após check-out");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarCheckoutComSucesso - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarCheckoutApartamentoNaoOcupadoLancaExcecao() {
        total++;
        try {
            Apartamento apto = new Apartamento();
            apto.checkout();
            
            System.out.println("FALHOU: testarCheckoutApartamentoNaoOcupadoLancaExcecao - Permitiu check-out em apartamento LIVRE");
        } catch (IllegalStateException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarCheckoutApartamentoNaoOcupadoLancaExcecao - Lançou exceção incorreta: " + e.getClass().getName());
        }
    }

    // --- CICLO 4 ---

    static void testarInicializacaoHotelMatrizLivre() {
        total++;
        try {
            Hotel hotel = new Hotel();
            boolean todosLivres = true;

            for (int a = 0; a < Hotel.NUM_ANDARES; a++) {
                for (int n = 0; n < Hotel.APTOS_POR_ANDAR; n++) {
                    Apartamento apto = hotel.getApartamento(a, n);
                    if (apto == null || !apto.estaLivre()) {
                        todosLivres = false;
                        break;
                    }
                }
            }

            if (todosLivres) {
                passou++;
            } else {
                System.out.println("FALHOU: testarInicializacaoHotelMatrizLivre - Nem todos os apartamentos iniciaram como LIVRE");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarInicializacaoHotelMatrizLivre - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarCoordenadasInvalidasLancaExcecao() {
        total++;
        try {
            Hotel hotel = new Hotel();
            Hospede titular = new Hospede("12345678900", "João", "Rua A", "11999998888", "joao@email.com");
            Reserva reserva = new Reserva(1, titular);

            boolean capturouExcecoes = true;

            try { hotel.getApartamento(-1, 0); capturouExcecoes = false; } catch (IllegalArgumentException e) {}
            try { hotel.reservarApartamento(20, 0, reserva); capturouExcecoes = false; } catch (IllegalArgumentException e) {}
            try { hotel.realizarCheckin(0, -1, reserva); capturouExcecoes = false; } catch (IllegalArgumentException e) {}
            try { hotel.realizarCheckout(0, 14); capturouExcecoes = false; } catch (IllegalArgumentException e) {}
            try { hotel.cancelarReserva(25, 30); capturouExcecoes = false; } catch (IllegalArgumentException e) {}

            if (capturouExcecoes) {
                passou++;
            } else {
                System.out.println("FALHOU: testarCoordenadasInvalidasLancaExcecao - Alguma coordenada inválida não lançou IllegalArgumentException");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarCoordenadasInvalidasLancaExcecao - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarOperacoesFachadaHotelDelegacao() {
        total++;
        try {
            Hotel hotel = new Hotel();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A", "11999998888", "joao@email.com");
            Reserva reserva = new Reserva(1, titular);

            boolean res1 = hotel.reservarApartamento(0, 0, reserva);
            boolean estadoReservado = hotel.getApartamento(0, 0).estaReservado();

            boolean res2 = hotel.realizarCheckin(0, 0, reserva);
            boolean estadoOcupado = hotel.getApartamento(0, 0).estaOcupado();

            boolean res3 = hotel.realizarCheckout(0, 0);
            boolean estadoLivre = hotel.getApartamento(0, 0).estaLivre();

            if (res1 && estadoReservado && res2 && estadoOcupado && res3 && estadoLivre) {
                passou++;
            } else {
                System.out.println("FALHOU: testarOperacoesFachadaHotelDelegacao - Retornos ou delegação da fachada Hotel inconsistentes");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarOperacoesFachadaHotelDelegacao - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    // --- CICLO 5 ---

    static void testarApartamentoBasePrecoDiaria() {
        total++;
        try {
            Apartamento apto = new Apartamento();

            if (apto.getPrecoDiaria() == 0f) {
                passou++;
            } else {
                System.out.println("FALHOU: testarApartamentoBasePrecoDiaria - Esperado: 0.0, obtido: " + apto.getPrecoDiaria());
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarApartamentoBasePrecoDiaria - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarApartamentoSimplesPrecoDiaria() {
        total++;
        try {
            Apartamento apto = new ApartamentoSimples();

            if (apto.getPrecoDiaria() == 150.0f) {
                passou++;
            } else {
                System.out.println("FALHOU: testarApartamentoSimplesPrecoDiaria - Esperado: 150.0, obtido: " + apto.getPrecoDiaria());
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarApartamentoSimplesPrecoDiaria - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarApartamentoPremiumPrecoDiaria() {
        total++;
        try {
            Apartamento apto = new ApartamentoPremium();

            if (apto.getPrecoDiaria() == 350.0f) {
                passou++;
            } else {
                System.out.println("FALHOU: testarApartamentoPremiumPrecoDiaria - Esperado: 350.0, obtido: " + apto.getPrecoDiaria());
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarApartamentoPremiumPrecoDiaria - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarHotelInicializaSimplesEPremiumPorAndar() {
        total++;
        try {
            Hotel hotel = new Hotel();
            int simples = 0;
            int premium = 0;
            boolean aptoCorreto = true;

            for (int a = 0; a < Hotel.NUM_ANDARES; a++){
                for(int n = 0; n < Hotel.APTOS_POR_ANDAR; n++){
                    Apartamento apto = hotel.getApartamento(a, n);
                    if(apto instanceof ApartamentoSimples){
                        simples++;
                        if(n >= 8) aptoCorreto = false; // verificando se está entre os aptos simples
                    } else if (apto instanceof ApartamentoPremium){
                        premium++;
                        if(n < 8) aptoCorreto = false;
                    }
                }
            }
            if(simples == 160 && premium == 120 && aptoCorreto){
                passou++;
            }else {
                System.out.println("FALHOU: testarHotelInicializaSimplesEPremiumPorAndar - Simples: "
                + simples 
                +", Premium: " + premium
                +", aptoCorreto: "+ aptoCorreto);
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarHotelInicializaSimplesEPremiumPorAndar - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarSubclassesHerdamTransicoesDeEstado() { 
        total++; 
        try { 
            Apartamento simples = new ApartamentoSimples(); 
            Apartamento premium = new ApartamentoPremium(); 

            Hospede titular1 = new Hospede("12345678900", "João", "Rua A", "11999998888", "joao@email.com"); 
            Hospede titular2 = new Hospede("98765432100", "Maria", "Rua B", "11888887777", "maria@email.com"); 

            Reserva reserva1 = new Reserva(1, titular1); 
            Reserva reserva2 = new Reserva(2, titular2); 

            simples.reservar(reserva1); 
            simples.checkin(reserva1); 

            premium.reservar(reserva2); 
            premium.checkin(reserva2); 

            if (simples.estaOcupado() && premium.estaOcupado()) { 
                passou++; 
            } else { 
                System.out.println("FALHOU: testarSubclassesHerdamTransicoesDeEstado - Subclasses não herdaram as transições corretamente"); 
            } 
        } catch (Exception e) { 
            System.out.println("FALHOU: testarSubclassesHerdamTransicoesDeEstado - Lançou exceção inesperada: " + e.getMessage()); 
        } 
    }

    static void testarSubclassesHerdamValidacaoDeEstado() {
        total++;
        try {
            Apartamento[] tipos = { new ApartamentoSimples(), new ApartamentoPremium() };
            boolean validacaoCorreta = true;

            for ( Apartamento apto : tipos) {
                try {
                    apto.checkout();
                    validacaoCorreta = false;
                    break;
                } catch (IllegalStateException e) {
                    // Esperado
                }
            }
            if(validacaoCorreta) {
                passou++;
            } else {
                System.out.println("FALHOU: testarSubclassesHerdamValidacaoDeEstado - Alguma subclasse não manteve validação de estado correta");
            }            
        } catch (Exception e) {
            System.out.println("FALHOU: testarSubclassesHerdamValidacaoDeEstado - Lançou exceção incorreta: " + e.getClass().getName());
        }
    }

    static void testarToStringApartamentoSimplesOcupado(){
        total++;
        try {
            Apartamento apto = new ApartamentoSimples();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            Reserva reserva = new Reserva(1, titular);

            apto.reservar(reserva); 
            apto.checkin(reserva); 

            String texto = apto.toString();

            if (texto.contains("ApartamentoSimples") && texto.contains("OCUPADO") && texto.contains("150.0")){
                passou++;
            } else {
                System.out.println("FALHOU: testarToStringApartamentoSimplesOcupado - toString devolveu: " + texto);
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarToStringApartamentoSimplesOcupado - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarToStringApartamentoPremiumOcupado(){
        total++;
        try {
            Apartamento apto = new ApartamentoPremium();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            Reserva reserva = new Reserva(1, titular); 
            
            apto.reservar(reserva); 
            apto.checkin(reserva); 

            String texto = apto.toString();

            if (texto.contains("ApartamentoPremium") && texto.contains("OCUPADO") && texto.contains("350.0")){
                passou++;
            } else {
                System.out.println("FALHOU: testarToStringApartamentoPremiumOcupado - toString devolveu: " + texto);
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarToStringApartamentoPremiumOcupado - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarToStringAcompanhaMudancaDeEstado(){
        total++;
        try {
            Apartamento apto = new ApartamentoSimples();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            Reserva reserva = new Reserva(1, titular);

            apto.reservar(reserva);
            String textoReservado = apto.toString();

            apto.cancelarReserva();
            String textoLivre = apto.toString();

            boolean reservadoCorreto = textoReservado.contains("RESERVADO") && !textoReservado.contains("LIVRE"); // Verificando caso bom e triste
            boolean canceladoCorreto = textoLivre.contains("LIVRE") && !textoLivre.contains("RESERVADO");

            if (reservadoCorreto && canceladoCorreto) {
                passou++;
            } else {
                System.out.println("FALHOU: testarToStringAcompanhaMudancaDeEstado - toString reservado devolveu: " 
                + textoReservado 
                +", toString cancelado devolveu: "+ textoLivre);
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarToStringAcompanhaMudancaDeEstado - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarTaxaHotelVazio() {
        total++;
        try {
            Hotel hotel = new Hotel();
            float taxaOcupado = hotel.calcularTaxaOcupacao();
            float taxaReservado = hotel.calcularTaxaReservas();

            if ( taxaOcupado == 0.0f && taxaReservado == 0.0f){
              passou++;
            } else {
              System.out.println("FALHOU: testarTaxaHotelVazio - taxaOcupado devolveu: "+ taxaOcupado + ", taxaReservado devolveu: "+ taxaReservado);
            }
        } catch (Exception e){
            System.out.println("FALHOU: testarTaxaHotelVazio - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarTaxaOcupacaoProporcionalAosOcupados() {
        total++;
        try {
            Hotel hotel = new Hotel();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            Reserva reservaInicial = new Reserva(1, titular);
            hotel.realizarCheckin(2, 3, reservaInicial);

            float umOcupado = hotel.calcularTaxaOcupacao();

            for (int n = 0; n < 14; n++) {
                Reserva reserva = new Reserva(2 + n, titular);
                hotel.realizarCheckin(0, n, reserva);
            }
            float quinzeOcupados = hotel.calcularTaxaOcupacao();

            if (aproximadamente(umOcupado, 1f / 280) && aproximadamente(quinzeOcupados, 15f / 280)) {
                passou++;
            } else {
                System.out.println("FALHOU: testarTaxaOcupacaoProporcionalAosOcupados - 1 ocupado: " + umOcupado
                        + " (esperado " + 1f / 280 + "), 15 ocupados: " + quinzeOcupados + " (esperado " + 15f / 280 + ")");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarTaxaOcupacaoProporcionalAosOcupados - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarTaxaReservasProporcionalAosReservados() {
        total++;
        try {
            Hotel hotel = new Hotel();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            Reserva reservaInicial = new Reserva(1, titular);
            hotel.reservarApartamento(2, 3, reservaInicial);

            float umReservado = hotel.calcularTaxaReservas();

            for (int n = 0; n < 14; n++) {
                Reserva reserva = new Reserva(2 + n, titular);
                hotel.reservarApartamento(0, n, reserva);
            }
            float quinzeReservados = hotel.calcularTaxaReservas();
            float taxaOcupacao = hotel.calcularTaxaOcupacao();

            if (aproximadamente(umReservado, 1f / 280) && aproximadamente(quinzeReservados, 15f / 280)
                    && aproximadamente(taxaOcupacao, 0f)) {
                passou++;
            } else {
                System.out.println("FALHOU: testarTaxaReservasProporcionalAosReservados - 1 reservado: " + umReservado
                        + " (esperado " + 1f / 280 + "), 15 reservados: " + quinzeReservados + " (esperado " + 15f / 280
                        + "), taxa de ocupação: " + taxaOcupacao + " (esperado 0.0)");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarTaxaReservasProporcionalAosReservados - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarTaxasNaoConfundemReservadoOcupado() {
        total++;
        try {
            Hotel hotel = new Hotel();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            Reserva reserva = new Reserva(1, titular);
            Reserva reservaCheckin = new Reserva(2, titular);

            hotel.reservarApartamento(0, 0, reserva);
            hotel.realizarCheckin(0, 1, reservaCheckin);

            float taxaReservas = hotel.calcularTaxaReservas();
            float taxaOcupacao = hotel.calcularTaxaOcupacao();

            if (aproximadamente(taxaReservas, 1f / 280) && aproximadamente(taxaOcupacao, 1f / 280)) {
                passou++;
            } else {
                System.out.println("FALHOU: testarTaxasNaoConfundemReservadoOcupado - taxa de reservas: " + taxaReservas
                        + ", taxa de ocupação: " + taxaOcupacao + " (esperado " + 1f / 280 + " para ambas)");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarTaxasNaoConfundemReservadoOcupado - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static boolean aproximadamente(float a, float b) {
        return Math.abs(a - b) < 0.000001f;
    }

    // --- CICLO 8 ---

    /**
     * Executa acao capturando tudo que for impresso em System.out, restaurando
     * o System.out original ao final (mesmo se acao lançar exceção).
     */
    static String capturarSaida(Runnable acao) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
            acao.run();
        } finally {
            System.setOut(original);
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }

    static void testarMostrarMapaHotelVazio() {
        total++;
        try {
            Hotel hotel = new Hotel();

            String saida = capturarSaida(() -> hotel.mostrarMapa());
            String[] linhas = saida.split("\n");
            long pontos = saida.chars().filter(c -> c == '.').count();

            if (linhas.length == 20 && pontos == 280) {
                passou++;
            } else {
                System.out.println("FALHOU: testarMostrarMapaHotelVazio - linhas: " + linhas.length
                        + " (esperado 20), pontos: " + pontos + " (esperado 280)");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarMostrarMapaHotelVazio - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarMostrarMapaRefleteReservaECheckin() {
        total++;
        try {
            Hotel hotel = new Hotel();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            Reserva reserva = new Reserva(1, titular);
            Reserva reservaCheckin = new Reserva(2, titular);

            hotel.reservarApartamento(0, 0, reserva);
            hotel.realizarCheckin(1, 1, reservaCheckin);

            String saida = capturarSaida(() -> hotel.mostrarMapa());
            long reservados = saida.chars().filter(c -> c == 'R').count();
            long ocupados = saida.chars().filter(c -> c == 'O').count();

            if (reservados == 1 && ocupados == 1) {
                passou++;
            } else {
                System.out.println("FALHOU: testarMostrarMapaRefleteReservaECheckin - R: " + reservados
                        + " (esperado 1), O: " + ocupados + " (esperado 1)");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarMostrarMapaRefleteReservaECheckin - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarMostrarMapaNaoAlteraEstado() {
        total++;
        try {
            Hotel hotel = new Hotel();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            Reserva reserva = new Reserva(1, titular);
            hotel.realizarCheckin(5, 5, reserva);

            capturarSaida(() -> hotel.mostrarMapa());
            boolean aindaOcupadoAntes = hotel.getApartamento(5, 5).estaOcupado();

            capturarSaida(() -> hotel.mostrarMapa());
            boolean aindaOcupadoDepois = hotel.getApartamento(5, 5).estaOcupado();

            if (aindaOcupadoAntes && aindaOcupadoDepois) {
                passou++;
            } else {
                System.out.println("FALHOU: testarMostrarMapaNaoAlteraEstado - estado do apartamento mudou apos mostrarMapa");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarMostrarMapaNaoAlteraEstado - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    // --- CICLO 9 ---

    static void testarCriacaoServicoValido() {
        total++;
        try {
            Servico s = new Servico("Jantar", 80.0f);
            if ("Jantar".equals(s.getNome()) && s.getPreco() == 80.0f) {
                passou++;
            } else {
                System.out.println("FALHOU: testarCriacaoServicoValido - Getters retornaram valores inconsistentes");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarCriacaoServicoValido - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarServicoNomeNuloLancaExcecao() {
        total++;
        try {
            new Servico(null, 80.0f);
            System.out.println("FALHOU: testarServicoNomeNuloLancaExcecao - Permitiu criar Servico com nome nulo");
        } catch (IllegalArgumentException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarServicoNomeNuloLancaExcecao - Lançou exceção diferente de IllegalArgumentException: " + e.getClass().getName());
        }
    }

    static void testarServicoPrecoNegativoLancaExcecao() {
        total++;
        try {
            new Servico("Jantar", -1.0f);
            System.out.println("FALHOU: testarServicoPrecoNegativoLancaExcecao - Permitiu criar Servico com preço negativo");
        } catch (IllegalArgumentException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarServicoPrecoNegativoLancaExcecao - Lançou exceção diferente de IllegalArgumentException: " + e.getClass().getName());
        }
    }

    // --- CICLO 10 ---

    static void testarCriacaoConsumoValido() {
        total++;
        try {
            Apartamento apto = new ApartamentoSimples();
            Servico servico = new Servico("Jantar", 80.0f);
            Consumo consumo = new Consumo(apto, servico, 3);
            if (consumo.getApartamento() == apto &&
                consumo.getServico() == servico &&
                consumo.getQuantidade() == 3 &&
                consumo.getTotal() == 240.0f) {
                passou++;
            } else {
                System.out.println("FALHOU: testarCriacaoConsumoValido - Getters ou total retornaram valores inconsistentes");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarCriacaoConsumoValido - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarConsumoApartamentoNuloLancaExcecao() {
        total++;
        try {
            new Consumo(null, new Servico("Jantar", 80.0f), 1);
            System.out.println("FALHOU: testarConsumoApartamentoNuloLancaExcecao - Permitiu criar Consumo com apartamento nulo");
        } catch (IllegalArgumentException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarConsumoApartamentoNuloLancaExcecao - Lançou exceção diferente de IllegalArgumentException: " + e.getClass().getName());
        }
    }

    static void testarConsumoServicoNuloLancaExcecao() {
        total++;
        try {
            new Consumo(new ApartamentoSimples(), null, 1);
            System.out.println("FALHOU: testarConsumoServicoNuloLancaExcecao - Permitiu criar Consumo com serviço nulo");
        } catch (IllegalArgumentException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarConsumoServicoNuloLancaExcecao - Lançou exceção diferente de IllegalArgumentException: " + e.getClass().getName());
        }
    }

    static void testarConsumoQuantidadeInvalidaLancaExcecao() {
        total++;
        try {
            new Consumo(new ApartamentoSimples(), new Servico("Jantar", 80.0f), 0);
            System.out.println("FALHOU: testarConsumoQuantidadeInvalidaLancaExcecao - Permitiu criar Consumo com quantidade zero");
        } catch (IllegalArgumentException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarConsumoQuantidadeInvalidaLancaExcecao - Lançou exceção diferente de IllegalArgumentException: " + e.getClass().getName());
        }
    }

    // --- CICLO 11 ---

    static void testarCadastrarServicoAdicionaAoCardapio() {
        total++;
        try {
            Hotel hotel = new Hotel();
            hotel.cadastrarServico("Jantar", 80.0f);

            if (hotel.getServicos().size() == 1 &&
                "Jantar".equals(hotel.getServicos().get(0).getNome()) &&
                hotel.getServicos().get(0).getPreco() == 80.0f) {
                passou++;
            } else {
                System.out.println("FALHOU: testarCadastrarServicoAdicionaAoCardapio - Serviço não foi adicionado corretamente ao cardápio");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarCadastrarServicoAdicionaAoCardapio - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarCadastrarServicoMantemOrdemDeCadastro() {
        total++;
        try {
            Hotel hotel = new Hotel();
            hotel.cadastrarServico("Jantar", 80.0f);
            hotel.cadastrarServico("Lavanderia", 40.0f);

            if (hotel.getServicos().size() == 2 &&
                "Jantar".equals(hotel.getServicos().get(0).getNome()) &&
                "Lavanderia".equals(hotel.getServicos().get(1).getNome())) {
                passou++;
            } else {
                System.out.println("FALHOU: testarCadastrarServicoMantemOrdemDeCadastro - Serviços fora da ordem de cadastro");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarCadastrarServicoMantemOrdemDeCadastro - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarCadastrarServicoInvalidoLancaExcecao() {
        total++;
        try {
            Hotel hotel = new Hotel();
            hotel.cadastrarServico(null, 80.0f);
            System.out.println("FALHOU: testarCadastrarServicoInvalidoLancaExcecao - Permitiu cadastrar serviço com nome nulo");
        } catch (IllegalArgumentException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarCadastrarServicoInvalidoLancaExcecao - Lançou exceção diferente de IllegalArgumentException: " + e.getClass().getName());
        }
    }

    // --- CICLO 12 ---

    static void testarRegistrarConsumoApartamentoOcupado() {
        total++;
        try {
            Hotel hotel = new Hotel();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            hotel.cadastrarServico("Jantar", 80.0f);
            hotel.realizarCheckin(0, 0, new Reserva(1, titular));

            boolean registrado = hotel.registrarConsumo(0, 0, 0, 2);

            if (registrado &&
                hotel.getConsumos().size() == 1 &&
                hotel.getConsumos().get(0).getApartamento() == hotel.getApartamento(0, 0) &&
                hotel.getConsumos().get(0).getServico() == hotel.getServicos().get(0) &&
                hotel.getConsumos().get(0).getQuantidade() == 2) {
                passou++;
            } else {
                System.out.println("FALHOU: testarRegistrarConsumoApartamentoOcupado - Consumo não foi registrado corretamente");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarRegistrarConsumoApartamentoOcupado - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarRegistrarConsumoApartamentoLivreLancaExcecao() {
        total++;
        try {
            Hotel hotel = new Hotel();
            hotel.cadastrarServico("Jantar", 80.0f);

            hotel.registrarConsumo(0, 0, 0, 1);
            System.out.println("FALHOU: testarRegistrarConsumoApartamentoLivreLancaExcecao - Permitiu registrar consumo em apartamento livre");
        } catch (IllegalStateException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarRegistrarConsumoApartamentoLivreLancaExcecao - Lançou exceção diferente de IllegalStateException: " + e.getClass().getName());
        }
    }

    static void testarRegistrarConsumoApartamentoReservadoLancaExcecao() {
        total++;
        try {
            Hotel hotel = new Hotel();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            hotel.cadastrarServico("Jantar", 80.0f);
            hotel.reservarApartamento(0, 0, new Reserva(1, titular));

            hotel.registrarConsumo(0, 0, 0, 1);
            System.out.println("FALHOU: testarRegistrarConsumoApartamentoReservadoLancaExcecao - Permitiu registrar consumo em apartamento reservado");
        } catch (IllegalStateException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarRegistrarConsumoApartamentoReservadoLancaExcecao - Lançou exceção diferente de IllegalStateException: " + e.getClass().getName());
        }
    }

    static void testarRegistrarConsumoCoordenadasInvalidasLancaExcecao() {
        total++;
        try {
            Hotel hotel = new Hotel();
            hotel.cadastrarServico("Jantar", 80.0f);

            hotel.registrarConsumo(20, 0, 0, 1);
            System.out.println("FALHOU: testarRegistrarConsumoCoordenadasInvalidasLancaExcecao - Permitiu registrar consumo com andar inválido");
        } catch (IllegalArgumentException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarRegistrarConsumoCoordenadasInvalidasLancaExcecao - Lançou exceção diferente de IllegalArgumentException: " + e.getClass().getName());
        }
    }

    static void testarRegistrarConsumoIndiceServicoInvalidoLancaExcecao() {
        total++;
        try {
            Hotel hotel = new Hotel();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            hotel.cadastrarServico("Jantar", 80.0f);
            hotel.realizarCheckin(0, 0, new Reserva(1, titular));

            hotel.registrarConsumo(0, 0, 1, 1);
            System.out.println("FALHOU: testarRegistrarConsumoIndiceServicoInvalidoLancaExcecao - Permitiu registrar consumo com índice de serviço inexistente");
        } catch (IllegalArgumentException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarRegistrarConsumoIndiceServicoInvalidoLancaExcecao - Lançou exceção diferente de IllegalArgumentException: " + e.getClass().getName());
        }
    }

    static void testarRegistrarConsumoQuantidadeInvalidaLancaExcecao() {
        total++;
        try {
            Hotel hotel = new Hotel();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            hotel.cadastrarServico("Jantar", 80.0f);
            hotel.realizarCheckin(0, 0, new Reserva(1, titular));

            hotel.registrarConsumo(0, 0, 0, 0);
            System.out.println("FALHOU: testarRegistrarConsumoQuantidadeInvalidaLancaExcecao - Permitiu registrar consumo com quantidade zero");
        } catch (IllegalArgumentException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarRegistrarConsumoQuantidadeInvalidaLancaExcecao - Lançou exceção diferente de IllegalArgumentException: " + e.getClass().getName());
        }
    }

    // --- CICLO 13 ---

    static void testarConsumosDoApartamentoRetornaApenasDoApartamento() {
        total++;
        try {
            Hotel hotel = new Hotel();
            Hospede titular1 = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            Hospede titular2 = new Hospede("98765432100", "Maria Souza", "Rua B, 456", "11888887777", "maria@email.com");
            hotel.cadastrarServico("Jantar", 80.0f);
            hotel.cadastrarServico("Lavanderia", 40.0f);
            hotel.realizarCheckin(0, 0, new Reserva(1, titular1));
            hotel.realizarCheckin(0, 1, new Reserva(2, titular2));
            hotel.registrarConsumo(0, 0, 0, 1);
            hotel.registrarConsumo(0, 1, 1, 3);
            hotel.registrarConsumo(0, 0, 1, 2);

            List<Consumo> consumos00 = hotel.getConsumosDoApartamento(0, 0);
            List<Consumo> consumos01 = hotel.getConsumosDoApartamento(0, 1);

            if (consumos00.size() == 2 &&
                consumos01.size() == 1 &&
                consumos00.get(0).getApartamento() == hotel.getApartamento(0, 0) &&
                consumos00.get(1).getApartamento() == hotel.getApartamento(0, 0) &&
                consumos01.get(0).getApartamento() == hotel.getApartamento(0, 1) &&
                consumos01.get(0).getQuantidade() == 3) {
                passou++;
            } else {
                System.out.println("FALHOU: testarConsumosDoApartamentoRetornaApenasDoApartamento - Consumos não foram filtrados corretamente por apartamento");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarConsumosDoApartamentoRetornaApenasDoApartamento - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarConsumosDoApartamentoSemConsumosRetornaListaVazia() {
        total++;
        try {
            Hotel hotel = new Hotel();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            hotel.cadastrarServico("Jantar", 80.0f);
            hotel.realizarCheckin(0, 0, new Reserva(1, titular));
            hotel.realizarCheckin(0, 1, new Reserva(2, titular));
            hotel.registrarConsumo(0, 1, 0, 1);

            if (hotel.getConsumosDoApartamento(0, 0).isEmpty()) {
                passou++;
            } else {
                System.out.println("FALHOU: testarConsumosDoApartamentoSemConsumosRetornaListaVazia - Retornou consumos de outro apartamento");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarConsumosDoApartamentoSemConsumosRetornaListaVazia - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarConsumosDoApartamentoCoordenadasInvalidasLancaExcecao() {
        total++;
        try {
            Hotel hotel = new Hotel();
            hotel.getConsumosDoApartamento(0, 14);
            System.out.println("FALHOU: testarConsumosDoApartamentoCoordenadasInvalidasLancaExcecao - Permitiu consultar consumos com número de apartamento inválido");
        } catch (IllegalArgumentException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarConsumosDoApartamentoCoordenadasInvalidasLancaExcecao - Lançou exceção diferente de IllegalArgumentException: " + e.getClass().getName());
        }
    }

    // --- CICLO 14 ---

    static void testarFaturaCalculaTotais() {
        total++;
        try {
            Apartamento apto = new ApartamentoPremium();
            List<Consumo> consumos = new java.util.ArrayList<>();
            consumos.add(new Consumo(apto, new Servico("Jantar", 80.0f), 2));
            consumos.add(new Consumo(apto, new Servico("Lavanderia", 40.0f), 1));

            Fatura fatura = new Fatura(apto, 3, consumos);

            if (fatura.getApartamento() == apto &&
                fatura.getDias() == 3 &&
                fatura.getConsumos().size() == 2 &&
                fatura.getTotalDiarias() == 1050.0f &&
                fatura.getTotalConsumos() == 200.0f &&
                fatura.getTotalGeral() == 1250.0f) {
                passou++;
            } else {
                System.out.println("FALHOU: testarFaturaCalculaTotais - Totais incorretos: diárias " + fatura.getTotalDiarias()
                        + " (esperado 1050.0), consumos " + fatura.getTotalConsumos()
                        + " (esperado 200.0), geral " + fatura.getTotalGeral() + " (esperado 1250.0)");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarFaturaCalculaTotais - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarFaturaSemConsumos() {
        total++;
        try {
            Fatura fatura = new Fatura(new ApartamentoSimples(), 2, new java.util.ArrayList<>());

            if (fatura.getTotalDiarias() == 300.0f &&
                fatura.getTotalConsumos() == 0.0f &&
                fatura.getTotalGeral() == 300.0f) {
                passou++;
            } else {
                System.out.println("FALHOU: testarFaturaSemConsumos - Total geral " + fatura.getTotalGeral() + " (esperado 300.0)");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarFaturaSemConsumos - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarFaturaApartamentoNuloLancaExcecao() {
        total++;
        try {
            new Fatura(null, 1, new java.util.ArrayList<>());
            System.out.println("FALHOU: testarFaturaApartamentoNuloLancaExcecao - Permitiu criar Fatura com apartamento nulo");
        } catch (IllegalArgumentException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarFaturaApartamentoNuloLancaExcecao - Lançou exceção diferente de IllegalArgumentException: " + e.getClass().getName());
        }
    }

    static void testarFaturaDiasNegativosLancaExcecao() {
        total++;
        try {
            new Fatura(new ApartamentoSimples(), -1, new java.util.ArrayList<>());
            System.out.println("FALHOU: testarFaturaDiasNegativosLancaExcecao - Permitiu criar Fatura com dias negativos");
        } catch (IllegalArgumentException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarFaturaDiasNegativosLancaExcecao - Lançou exceção diferente de IllegalArgumentException: " + e.getClass().getName());
        }
    }

    static void testarFaturaConsumosNulosLancaExcecao() {
        total++;
        try {
            new Fatura(new ApartamentoSimples(), 1, null);
            System.out.println("FALHOU: testarFaturaConsumosNulosLancaExcecao - Permitiu criar Fatura com lista de consumos nula");
        } catch (IllegalArgumentException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarFaturaConsumosNulosLancaExcecao - Lançou exceção diferente de IllegalArgumentException: " + e.getClass().getName());
        }
    }

    // --- CICLO 15 ---

    static void testarEmitirFaturaApartamentoSimplesComConsumos() {
        total++;
        try {
            Hotel hotel = new Hotel();
            Hospede titular1 = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            Hospede titular2 = new Hospede("98765432100", "Maria Souza", "Rua B, 456", "11888887777", "maria@email.com");
            hotel.cadastrarServico("Jantar", 80.0f);
            hotel.realizarCheckin(0, 0, new Reserva(1, titular1));
            hotel.realizarCheckin(0, 1, new Reserva(2, titular2));
            hotel.registrarConsumo(0, 0, 0, 2);
            hotel.registrarConsumo(0, 1, 0, 5);

            Fatura fatura = hotel.emitirFatura(0, 0, 2);

            if (fatura.getApartamento() == hotel.getApartamento(0, 0) &&
                fatura.getDias() == 2 &&
                fatura.getConsumos().size() == 1 &&
                fatura.getTotalDiarias() == 300.0f &&
                fatura.getTotalConsumos() == 160.0f &&
                fatura.getTotalGeral() == 460.0f) {
                passou++;
            } else {
                System.out.println("FALHOU: testarEmitirFaturaApartamentoSimplesComConsumos - Fatura incorreta: total geral "
                        + fatura.getTotalGeral() + " (esperado 460.0), consumos " + fatura.getConsumos().size() + " (esperado 1)");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarEmitirFaturaApartamentoSimplesComConsumos - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarEmitirFaturaApartamentoPremium() {
        total++;
        try {
            Hotel hotel = new Hotel();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            hotel.realizarCheckin(0, Hotel.SIMPLES_POR_ANDAR, new Reserva(1, titular));

            Fatura fatura = hotel.emitirFatura(0, Hotel.SIMPLES_POR_ANDAR, 3);

            if (fatura.getTotalDiarias() == 1050.0f &&
                fatura.getTotalConsumos() == 0.0f &&
                fatura.getTotalGeral() == 1050.0f) {
                passou++;
            } else {
                System.out.println("FALHOU: testarEmitirFaturaApartamentoPremium - Total geral " + fatura.getTotalGeral() + " (esperado 1050.0)");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarEmitirFaturaApartamentoPremium - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarEmitirFaturaApartamentoLivreLancaExcecao() {
        total++;
        try {
            Hotel hotel = new Hotel();

            hotel.emitirFatura(0, 0, 1);
            System.out.println("FALHOU: testarEmitirFaturaApartamentoLivreLancaExcecao - Permitiu emitir fatura de apartamento livre");
        } catch (IllegalStateException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarEmitirFaturaApartamentoLivreLancaExcecao - Lançou exceção diferente de IllegalStateException: " + e.getClass().getName());
        }
    }

    static void testarEmitirFaturaApartamentoReservadoLancaExcecao() {
        total++;
        try {
            Hotel hotel = new Hotel();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            hotel.reservarApartamento(0, 0, new Reserva(1, titular));

            hotel.emitirFatura(0, 0, 1);
            System.out.println("FALHOU: testarEmitirFaturaApartamentoReservadoLancaExcecao - Permitiu emitir fatura de apartamento reservado");
        } catch (IllegalStateException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarEmitirFaturaApartamentoReservadoLancaExcecao - Lançou exceção diferente de IllegalStateException: " + e.getClass().getName());
        }
    }

    static void testarEmitirFaturaCoordenadasInvalidasLancaExcecao() {
        total++;
        try {
            Hotel hotel = new Hotel();

            hotel.emitirFatura(-1, 0, 1);
            System.out.println("FALHOU: testarEmitirFaturaCoordenadasInvalidasLancaExcecao - Permitiu emitir fatura com andar inválido");
        } catch (IllegalArgumentException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarEmitirFaturaCoordenadasInvalidasLancaExcecao - Lançou exceção diferente de IllegalArgumentException: " + e.getClass().getName());
        }
    }

    static void testarEmitirFaturaDiasNegativosLancaExcecao() {
        total++;
        try {
            Hotel hotel = new Hotel();
            Hospede titular = new Hospede("12345678900", "João Silva", "Rua A, 123", "11999998888", "joao@email.com");
            hotel.realizarCheckin(0, 0, new Reserva(1, titular));

            hotel.emitirFatura(0, 0, -1);
            System.out.println("FALHOU: testarEmitirFaturaDiasNegativosLancaExcecao - Permitiu emitir fatura com dias negativos");
        } catch (IllegalArgumentException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarEmitirFaturaDiasNegativosLancaExcecao - Lançou exceção diferente de IllegalArgumentException: " + e.getClass().getName());
        }
    }
}
