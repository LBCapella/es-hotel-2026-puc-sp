package hotel.apresentacao;

import hotel.modelo.*;
import hotel.negocio.Hotel;
import hotel.repositorio.HotelRepo;
import hotel.servico.RelatorioSvc;
import java.util.Scanner;

/**
 * Ponto de entrada do sistema: menu textual operado pela recepção.
 */
public class Main {
    private static Hotel hotel = new Hotel();
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Lê do terminal os dados de um hóspede (CPF, nome, endereço, celular e e-mail).
     *
     * @return Hospede criado com os dados digitados
     * @throws IllegalArgumentException se algum dado for nulo (validação feita pelo construtor de Hospede)
     *
     * @pre O scanner deve estar aberto e ligado à entrada padrão
     * @post Um novo Hospede é retornado; o estado do hotel não é alterado
     */
    private static Hospede lerHospede() {
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Celular: ");
        String celular = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        return new Hospede(cpf, nome, endereco, celular, email);
    }

    /**
     * Lê do terminal o número do andar.
     *
     * @return int digitado pelo usuário
     * @throws NumberFormatException se o texto digitado não for um número inteiro
     *
     * @pre O scanner deve estar aberto e ligado à entrada padrão
     * @post Nenhuma alteração de estado; apenas leitura da entrada
     */
    private static int lerAndar() {
        System.out.print("Andar (0-" + (Hotel.NUM_ANDARES - 1) + "): ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Lê do terminal o número do apartamento dentro do andar.
     *
     * @return int digitado pelo usuário
     * @throws NumberFormatException se o texto digitado não for um número inteiro
     *
     * @pre O scanner deve estar aberto e ligado à entrada padrão
     * @post Nenhuma alteração de estado; apenas leitura da entrada
     */
    private static int lerNumero() {
        System.out.print("Número (0-" + (Hotel.APTOS_POR_ANDAR - 1) + "): ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Exibe o menu em laço, lê a opção escolhida e chama o Hotel, o RelatorioSvc ou o HotelRepo,
     * capturando as exceções para mostrar mensagens amigáveis.
     *
     * @param args Argumentos de linha de comando (não utilizados)
     *
     * @pre Nenhuma
     * @post O programa termina quando a opção 0 é escolhida
     */
    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n=== SISTEMA DE GESTÃO DE HOTEL ===");
            System.out.println("1  - Reservar apartamento");
            System.out.println("2  - Realizar Check-in");
            System.out.println("3  - Realizar Check-out");
            System.out.println("4  - Cancelar reserva");
            System.out.println("5  - Mostrar mapa de ocupação");
            System.out.println("6  - Consultar apartamento");
            System.out.println("7  - Mostrar taxas de ocupação e reservas");
            System.out.println("8  - Cadastrar serviço no cardápio");
            System.out.println("9  - Registrar consumo de serviço");
            System.out.println("10 - Emitir fatura de apartamento");
            System.out.println("11 - Relatório de ocupação");
            System.out.println("12 - Ranking de serviços");
            System.out.println("13 - Salvar dados em arquivo");
            System.out.println("14 - Carregar dados de arquivo");
            System.out.println("15 - Resumo financeiro");
            System.out.println("16 - Listar serviços do cardápio");
            System.out.println("0  - Sair");
            System.out.print("Opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                        System.out.println("Opção inválida.");
                opcao = -1;
                continue;
            }

            try {
                switch (opcao) {
                    case 1:
                        int andar = lerAndar();
                        int numero = lerNumero();
                        Hospede h = lerHospede();
                        Reserva reserva = new Reserva(hotel.gerarProximoIdReserva(), h);
                        if (hotel.reservarApartamento(andar, numero, reserva)) {
                            System.out.println("Reserva realizada com sucesso.");
                        } else {
                            System.out.println("Não foi possível reservar.");
                        }
                        break;
                    case 2:
                        andar = lerAndar();
                        numero = lerNumero();
                        Apartamento aptoCheckin = hotel.getApartamento(andar, numero);
                        if (aptoCheckin.estaReservado()) {
                            reserva = aptoCheckin.getReserva();
                            System.out.println("Reserva existente encontrada para " + reserva.getTitular() + ".");
                        } else {
                            h = lerHospede();
                            reserva = new Reserva(hotel.gerarProximoIdReserva(), h);
                        }
                        if (hotel.realizarCheckin(andar, numero, reserva)) {
                            System.out.println("Check-in realizado com sucesso.");
                        } else {
                            System.out.println("Não foi possível realizar check-in.");
                        }
                        break;
                    case 3:
                        andar = lerAndar();
                        numero = lerNumero();
                        System.out.print("Quantidade de dias da estadia: ");
                        int diasEstadia = Integer.parseInt(scanner.nextLine());
                        if (hotel.realizarCheckout(andar, numero, diasEstadia)) {
                            System.out.println("Check-out realizado com sucesso.");
                        } else {
                            System.out.println("Não foi possível realizar check-out.");
                        }
                        break;
                    case 4:
                        andar = lerAndar();
                        numero = lerNumero();
                        if (hotel.cancelarReserva(andar, numero)) {
                            System.out.println("Reserva cancelada com sucesso.");
                        } else {
                            System.out.println("Não foi possível cancelar reserva.");
                        }
                        break;
                    case 5:
                        hotel.mostrarMapa();
                        break;
                    case 6:
                        andar = lerAndar();
                        numero = lerNumero();
                        hotel.consultarApartamento(andar, numero);
                        break;
                    case 7:
                        System.out.printf("Taxa de ocupação: %.1f%%\n", hotel.calcularTaxaOcupacao() * 100);
                        System.out.printf("Taxa de reservas: %.1f%%\n", hotel.calcularTaxaReservas() * 100);
                        break;
                    case 8:
                        System.out.print("Nome do serviço: ");
                        String nome = scanner.nextLine();
                        System.out.print("Preço: ");
                        float preco = Float.parseFloat(scanner.nextLine());
                        hotel.cadastrarServico(nome, preco);
                        System.out.println("Serviço cadastrado.");
                        break;
                    case 9:
                        andar = lerAndar();
                        numero = lerNumero();
                        hotel.mostrarCardapio();
                        if (hotel.getServicos().isEmpty()) {
                            break;
                        }
                        System.out.print("Índice do serviço (0 a " + (hotel.getServicos().size() - 1) + "): ");
                        int idx = Integer.parseInt(scanner.nextLine());
                        System.out.print("Quantidade: ");
                        int qtd = Integer.parseInt(scanner.nextLine());
                        if (hotel.registrarConsumo(andar, numero, idx, qtd)) {
                            System.out.println("Consumo registrado.");
                        } else {
                            System.out.println("Não foi possível registrar consumo.");
                        }
                        break;
                    case 10:
                        andar = lerAndar();
                        numero = lerNumero();
                        System.out.print("Quantidade de dias: ");
                        int dias = Integer.parseInt(scanner.nextLine());
                        Fatura fatura = hotel.emitirFatura(andar, numero, dias);
                        System.out.println(fatura);
                        break;
                    case 11:
                        new RelatorioSvc(hotel).gerarRelatorioOcupacao();
                        break;
                    case 12:
                        new RelatorioSvc(hotel).gerarRankingServicos();
                        break;
                    case 13:
                        HotelRepo.salvar(hotel);
                        System.out.println("Dados salvos.");
                        break;
                    case 14:
                        hotel = HotelRepo.carregar();
                        System.out.println("Dados carregados.");
                        break;
                    case 15:
                        new RelatorioSvc(hotel).gerarResumoFinanceiro();
                        break;
                    case 16:
                        hotel.mostrarCardapio();
                        break;
                    case 0:
                        System.out.println("Encerrando.");
                        break;
                    default:
                System.out.println("Opção inválida.");
                }
            } catch (UnsupportedOperationException e) {
                System.out.println("Funcionalidade não implementada: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (java.io.IOException e) {
                System.out.println("Erro de arquivo: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("Arquivo incompatível com esta versão do sistema.");
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        } while (opcao != 0);
    }
}
