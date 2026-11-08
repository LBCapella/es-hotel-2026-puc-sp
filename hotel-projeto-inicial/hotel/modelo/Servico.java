package hotel.modelo;

import java.io.Serializable;

public class Servico implements Serializable {
    private final String nome;
    private final float preco;

    public Servico(String nome, float preco) {
        if (nome == null) {
            throw new IllegalArgumentException("Nome do Serviço não pode ser nulo.");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("Preço do Serviço não pode ser negativo.");
        }
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() { return nome; }
    public float getPreco() { return preco; }

    @Override
    public String toString() {
        return nome + " (R$ " + preco + ")";
    }
}
