package hotel.repositorio;

import hotel.negocio.Hotel;
import java.io.*;

public class HotelRepo {
    private static final String ARQUIVO = "hotel.dat";

     /**
     * Salva o estado do hotel no arquivo padrão (hotel.dat).
     *
     * @param hotel Hotel a ser salvo
     * @throws IOException se ocorrer erro de escrita
     * @throws IllegalArgumentException se o hotel for nulo
     *
     * @pre O hotel não pode ser nulo
     * @post O arquivo hotel.dat contém o estado completo do hotel
     */
    public static void salvar(Hotel hotel) throws IOException {
        salvar(hotel, ARQUIVO);
    }

     /**
     * Carrega o estado do hotel do arquivo padrão (hotel.dat).
     *
     * @return Hotel restaurado
     * @throws IOException se o arquivo não existir ou não puder ser lido
     * @throws ClassNotFoundException se uma classe gravada no arquivo não for encontrada
     *
     * @pre O arquivo hotel.dat deve existir e ter sido gerado por salvar
     * @post Retorna um novo Hotel com o estado gravado; nenhum arquivo é alterado
     */
    public static Hotel carregar() throws IOException, ClassNotFoundException {
        return carregar(ARQUIVO);
    }

    /**
     * Salva o estado do hotel no arquivo informado, substituindo seu conteúdo.
     *
     * @param hotel Hotel a ser salvo
     * @param caminho Caminho do arquivo de destino
     * @throws IOException se ocorrer erro de escrita (ex.: diretório inexistente)
     * @throws IllegalArgumentException se o hotel for nulo
     *
     * @pre O hotel não pode ser nulo e o caminho deve ser gravável
     * @post O arquivo contém apartamentos, serviços, consumos e histórico de dias ocupados
     */
    public static void salvar(Hotel hotel, String caminho) throws IOException {
        if (hotel == null) {
            throw new IllegalArgumentException("Não há hotel para salvar.");
        }try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminho))) {
            out.writeObject(hotel);
        }
    }

    /**
     * Carrega o estado do hotel do arquivo informado.
     *
     * @param caminho Caminho do arquivo de origem
     * @return Hotel restaurado
     * @throws IOException se o arquivo não existir ou não puder ser lido
     * @throws ClassNotFoundException se uma classe gravada no arquivo não for encontrada
     *
     * @pre O arquivo deve existir e ter sido gerado por salvar
     * @post Retorna um novo Hotel com o estado gravado; nenhum arquivo é alterado
     */
    public static Hotel carregar(String caminho) throws IOException, ClassNotFoundException {
        if (!new File(caminho).exists()) {
            throw new FileNotFoundException("Nenhum hotel salvo foi encontrado.");
        }try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(caminho))) {
            return (Hotel) in.readObject();
        }
    }
}
