package hotel.modelo;

import java.util.List;

public class Fatura {
    private Apartamento apartamento;
    private int dias;
    private List<Consumo> consumos;
    private float totalDiarias;
    private float totalConsumos;

    public Fatura(Apartamento apto, int dias, List<Consumo> consumos) {
        if (apto == null) {
            throw new IllegalArgumentException("Apartamento da Fatura não pode ser nulo.");
        }
        if (dias < 0) {
            throw new IllegalArgumentException("Quantidade de dias da Fatura não pode ser negativa.");
        }
        if (consumos == null) {
            throw new IllegalArgumentException("Lista de consumos da Fatura não pode ser nula.");
        }
        this.apartamento = apto;
        this.dias = dias;
        this.consumos = consumos;
        this.totalDiarias = calcularTotalDiarias();
        this.totalConsumos = calcularTotalConsumos();
    }

    private float calcularTotalDiarias() {
        return dias * apartamento.getPrecoDiaria();
    }

    private float calcularTotalConsumos() {
        float soma = 0f;
        for (Consumo c : consumos) {
            soma += c.getTotal();
        }
        return soma;
    }

    public float getTotalGeral() {
        return totalDiarias + totalConsumos;
    }

    public Apartamento getApartamento() { return apartamento; }
    public int getDias() { return dias; }
    public List<Consumo> getConsumos() { return consumos; }

    public float getTotalDiarias() {
        return totalDiarias;
    }

    public float getTotalConsumos() {
        return totalConsumos;
    }

    @Override
    public String toString() {
        return "Fatura: R$ " + getTotalGeral();
    }
}
