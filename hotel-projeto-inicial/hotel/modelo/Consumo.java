package hotel.modelo;

import java.io.Serializable;

public class Consumo implements Serializable {
    private final Apartamento apartamento;
    private final Servico servico;
    private final int quantidade;

    public Consumo(Apartamento apto, Servico servico, int quantidade) {
        if (apto == null) {
            throw new IllegalArgumentException("Apartamento do Consumo não pode ser nulo.");
        }
        if (servico == null) {
            throw new IllegalArgumentException("Serviço do Consumo não pode ser nulo.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade do Consumo deve ser maior que zero.");
        }
        this.apartamento = apto;
        this.servico = servico;
        this.quantidade = quantidade;
    }

    public Apartamento getApartamento() { return apartamento; }
    public Servico getServico() { return servico; }
    public int getQuantidade() { return quantidade; }

    public float getTotal() {
        return servico.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return servico.getNome() + " x" + quantidade + " = R$ " + getTotal();
    }
}
