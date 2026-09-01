package hotel.modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Representa um hóspede do hotel. Objeto imutável identificado unicamente pelo CPF.
 */
public class Hospede implements Serializable {
    private final String cpf;
    private final String nome;
    private final String endereco;
    private final String celular;
    private final String email;

    /**
     * Constrói um objeto Hospede validando parâmetros obrigatórios.
     *
     * @param cpf Número do CPF do hóspede
     * @param nome Nome completo do hóspede
     * @param endereco Endereço de residência
     * @param celular Telefone celular de contato
     * @param email Endereço de e-mail
     * @throws IllegalArgumentException se qualquer um dos parâmetros for nulo
     *
     * @pre Nenhum dos parâmetros pode ser nulo
     * @post Objeto Hospede é instanciado e torna-se imutável
     */
    public Hospede(String cpf, String nome, String endereco, String celular, String email) {
        if (cpf == null || nome == null || endereco == null || celular == null || email == null) {
            throw new IllegalArgumentException("Nenhum parâmetro do Hóspede pode ser nulo.");
        }
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.celular = celular;
        this.email = email;
    }

    /**
     * Obtém o CPF do hóspede.
     *
     * @return String contendo o CPF
     * @pre Nenhuma
     * @post Retorna a String do CPF do hóspede
     */
    public String getCpf() { return cpf; }

    /**
     * Obtém o nome completo do hóspede.
     *
     * @return String contendo o nome
     * @pre Nenhuma
     * @post Retorna a String do nome do hóspede
     */
    public String getNome() { return nome; }

    /**
     * Obtém o endereço do hóspede.
     *
     * @return String contendo o endereço
     * @pre Nenhuma
     * @post Retorna a String do endereço do hóspede
     */
    public String getEndereco() { return endereco; }

    /**
     * Obtém o telefone celular do hóspede.
     *
     * @return String contendo o celular
     * @pre Nenhuma
     * @post Retorna a String do celular do hóspede
     */
    public String getCelular() { return celular; }

    /**
     * Obtém o e-mail do hóspede.
     *
     * @return String contendo o e-mail
     * @pre Nenhuma
     * @post Retorna a String do e-mail do hóspede
     */
    public String getEmail() { return email; }

    /**
     * Compara este hóspede com outro objeto. Dois hóspedes são iguais se possuem o mesmo CPF.
     *
     * @param o Objeto a ser comparado
     * @return true se o objeto for um Hospede com mesmo CPF, false caso contrário
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hospede hospede = (Hospede) o;
        return Objects.equals(cpf, hospede.cpf);
    }

    /**
     * Retorna o valor de hash code do hóspede baseado no CPF.
     *
     * @return int contendo o código hash
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    /**
     * Retorna uma representação em texto amigável do hóspede.
     *
     * @return String formatada no padrão "Nome (CPF: ...)"
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    @Override
    public String toString() {
        return nome + " (CPF: " + cpf + ")";
    }
}
