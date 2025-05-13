import java.util.Scanner;

public class ContaTerminal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Entrada de dados
        System.out.print("Por favor, digite o número da Conta: ");
        int numero = scanner.nextInt();
        scanner.nextLine(); // limpar quebra de linha

        System.out.print("Por favor, digite o número da Agência: ");
        String agencia = scanner.nextLine();

        System.out.print("Por favor, digite o nome do Cliente: ");
        String nomeCliente = scanner.nextLine();

        System.out.print("Por favor, digite o saldo: ");
        double saldo = scanner.nextDouble();

        // Mensagem de boas-vindas
        System.out.println("\nConta criada com sucesso!");
        System.out.println();

        // Impressão da tabela
        System.out.println("+----------------------+---------------------------+");
        System.out.printf("| %-20s | %-25s |\n", "Campo", "Valor");
        System.out.println("+----------------------+---------------------------+");
        System.out.printf("| %-20s | %-25d |\n", "Número da Conta", numero);
        System.out.printf("| %-20s | %-25s |\n", "Agência", agencia);
        System.out.printf("| %-20s | %-25s |\n", "Nome do Cliente", nomeCliente);
        System.out.printf("| %-20s | R$ %-22.2f |\n", "Saldo", saldo);
        System.out.println("+----------------------+---------------------------+");

        scanner.close();
    }
}
