package hotel.apresentacao;

import hotel.modelo.*;
import hotel.negocio.Hotel;

public class HotelTest {
    private static int passou = 0;
    private static int total = 0;

    public static void main(String[] args) {
        // Ciclo 1 - Hospede
        testarCriacaoHospedeValido();
        testarHospedeParametroNuloLancaExcecao();
        testarHospedeEqualsEHashCodePorCpf();

        // Ciclo 2 - Apartamento (Reserva & Cancelamento)
        testarReservarApartamentoLivre();
        testarReservarApartamentoNaoLivreLancaExcecao();
        testarCancelarReservaComSucesso();
        testarCancelarReservaInvalidaLancaExcecao();

        // Ciclo 3 - Apartamento (Check-in & Check-out)
        testarCheckinDiretoSemReserva();
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

    static void testarReservarApartamentoLivre() {
        total++;
        try {
            Apartamento apto = new Apartamento();
            Hospede h = new Hospede("12345678900", "João Silva", "Rua A", "11999998888", "joao@email.com");
            
            apto.reservar(h);
            
            if (apto.estaReservado() && h.equals(apto.getHospede()) && apto.getSymbol() == 'R') {
                passou++;
            } else {
                System.out.println("FALHOU: testarReservarApartamentoLivre - Estado/símbolo/hóspede incorretos após reserva");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarReservarApartamentoLivre - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarReservarApartamentoNaoLivreLancaExcecao() {
        total++;
        try {
            Apartamento apto = new Apartamento();
            Hospede h1 = new Hospede("12345678900", "João", "Rua A", "11999998888", "joao@email.com");
            Hospede h2 = new Hospede("98765432100", "Maria", "Rua B", "11888887777", "maria@email.com");
            
            apto.reservar(h1);
            apto.reservar(h2);
            
            System.out.println("FALHOU: testarReservarApartamentoNaoLivreLancaExcecao - Permitiu reservar apartamento que já estava reservado");
        } catch (IllegalStateException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarReservarApartamentoNaoLivreLancaExcecao - Lançou exceção incorreta: " + e.getClass().getName());
        }
    }

    static void testarCancelarReservaComSucesso() {
        total++;
        try {
            Apartamento apto = new Apartamento();
            Hospede h = new Hospede("12345678900", "João Silva", "Rua A", "11999998888", "joao@email.com");
            
            apto.reservar(h);
            apto.cancelarReserva();
            
            if (apto.estaLivre() && apto.getHospede() == null && apto.getSymbol() == '.') {
                passou++;
            } else {
                System.out.println("FALHOU: testarCancelarReservaComSucesso - Estado/hóspede não resetados após cancelamento");
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

    static void testarCheckinDiretoSemReserva() {
        total++;
        try {
            Apartamento apto = new Apartamento();
            Hospede h = new Hospede("12345678900", "João Silva", "Rua A", "11999998888", "joao@email.com");
            
            apto.checkin(h);
            
            if (apto.estaOcupado() && h.equals(apto.getHospede()) && apto.getSymbol() == 'O') {
                passou++;
            } else {
                System.out.println("FALHOU: testarCheckinDiretoSemReserva - Estado/símbolo/hóspede incorretos após check-in direto");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: testarCheckinDiretoSemReserva - Lançou exceção inesperada: " + e.getMessage());
        }
    }

    static void testarCheckinApartamentoReservado() {
        total++;
        try {
            Apartamento apto = new Apartamento();
            Hospede h = new Hospede("12345678900", "João Silva", "Rua A", "11999998888", "joao@email.com");
            
            apto.reservar(h);
            apto.checkin(h);
            
            if (apto.estaOcupado() && h.equals(apto.getHospede()) && apto.getSymbol() == 'O') {
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
            Hospede h1 = new Hospede("12345678900", "João", "Rua A", "11999998888", "joao@email.com");
            Hospede h2 = new Hospede("98765432100", "Maria", "Rua B", "11888887777", "maria@email.com");
            
            apto.checkin(h1);
            apto.checkin(h2);
            
            System.out.println("FALHOU: testarCheckinApartamentoJaOcupadoLancaExcecao - Permitiu check-in em apartamento já ocupado");
        } catch (IllegalStateException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarCheckinApartamentoJaOcupadoLancaExcecao - Lançou exceção incorreta: " + e.getClass().getName());
        }
    }

    static void testarCheckoutComSucesso() {
        total++;
        try {
            Apartamento apto = new Apartamento();
            Hospede h = new Hospede("12345678900", "João Silva", "Rua A", "11999998888", "joao@email.com");
            
            apto.checkin(h);
            apto.checkout();
            
            if (apto.estaLivre() && apto.getHospede() == null && apto.getSymbol() == '.') {
                passou++;
            } else {
                System.out.println("FALHOU: testarCheckoutComSucesso - Estado/hóspede não resetados após check-out");
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
            Hospede h = new Hospede("12345678900", "João", "Rua A", "11999998888", "joao@email.com");

            boolean capturouExcecoes = true;

            try { hotel.getApartamento(-1, 0); capturouExcecoes = false; } catch (IllegalArgumentException e) {}
            try { hotel.reservarApartamento(20, 0, h); capturouExcecoes = false; } catch (IllegalArgumentException e) {}
            try { hotel.realizarCheckin(0, -1, h); capturouExcecoes = false; } catch (IllegalArgumentException e) {}
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
            Hospede h = new Hospede("12345678900", "João Silva", "Rua A", "11999998888", "joao@email.com");

            boolean res1 = hotel.reservarApartamento(0, 0, h);
            boolean estadoReservado = hotel.getApartamento(0, 0).estaReservado();

            boolean res2 = hotel.realizarCheckin(0, 0, h);
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
            Apartamento[] tipos = { new ApartamentoSimples(), new ApartamentoPremium() };
            Hospede h = new Hospede("12345678900", "João Silva", "Rua A", "11999998888", "joao@email.com");
            boolean estadoCorreto = true;

            for ( Apartamento apto : tipos) {
                apto.reservar(h);
                if (!apto.estaReservado()){
                    estadoCorreto = false;
                    break;
                }
                apto.checkin(h);
                if (!apto.estaOcupado()){
                    estadoCorreto = false;
                    break;
                }
                apto.checkout();
                if (!apto.estaLivre()){
                    estadoCorreto = false;
                    break;
                }
            }
            if (estadoCorreto) {
                passou++;
            } else {
                System.out.println("FALHOU: testarSubclassesHerdamTransicoesDeEstado - Alguma subclasse não manteve transições de estado corretas");
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
}
