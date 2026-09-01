package hotel.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Hospede implements Serializable {
    private final String cpf;
    private final String nome;
    private final String endereco;
    private final String celular;
    private final String email;

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

    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public String getCelular() { return celular; }
    public String getEmail() { return email; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hospede hospede = (Hospede) o;
        return Objects.equals(cpf, hospede.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return nome + " (CPF: " + cpf + ")";
    }
}
