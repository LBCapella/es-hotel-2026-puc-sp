package hotel.modelo;

import java.io.Serializable;

/**
 * Representa um item do cardápio de serviços do hotel. Objeto imutável.
 */
public class Servico implements Serializable {
    private final String nome;
    private final float preco;

    /**
     * Constrói um objeto Servico validando os parâmetros.
     *
     * @param nome Nome do serviço
     * @param preco Preço unitário do serviço
     * @throws IllegalArgumentException se o nome for nulo ou o preço for negativo
     *
     * @pre O nome não pode ser nulo e o preço não pode ser negativo
     * @post Objeto Servico é instanciado e torna-se imutável
     */
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

    /**
     * Obtém o nome do serviço.
     *
     * @return String contendo o nome
     * @pre Nenhuma
     * @post Retorna a String do nome do serviço
     */
    public String getNome() { return nome; }

    /**
     * Obtém o preço unitário do serviço.
     *
     * @return float contendo o preço
     * @pre Nenhuma
     * @post Retorna o preço do serviço
     */
    public float getPreco() { return preco; }

    /**
     * Retorna uma representação em texto do serviço.
     *
     * @return String no formato "nome (R$ preço)"
     * @pre Nenhuma
     * @post Nenhuma alteração de estado
     */
    @Override
    public String toString() {
        return nome + " (R$ " + preco + ")";
    }
}
