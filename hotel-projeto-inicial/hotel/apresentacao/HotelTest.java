package hotel.apresentacao;

import hotel.modelo.*;

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
            Apartamento apto = new Apartamento(); // LIVRE
            apto.checkout();
            
            System.out.println("FALHOU: testarCheckoutApartamentoNaoOcupadoLancaExcecao - Permitiu check-out em apartamento LIVRE");
        } catch (IllegalStateException e) {
            passou++;
        } catch (Exception e) {
            System.out.println("FALHOU: testarCheckoutApartamentoNaoOcupadoLancaExcecao - Lançou exceção incorreta: " + e.getClass().getName());
        }
    }
}
