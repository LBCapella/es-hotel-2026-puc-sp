package hotel.apresentacao;

import hotel.modelo.*;

public class HotelTest {
    private static int passou = 0;
    private static int total = 0;

    public static void main(String[] args) {
        testarCriacaoHospedeValido();
        testarHospedeParametroNuloLancaExcecao();
        testarHospedeEqualsEHashCodePorCpf();

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
}
