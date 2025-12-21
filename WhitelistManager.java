import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Sistema de Gerenciamento de Whitelist de Servidores
 * 
 * Este programa demonstra como gerenciar uma lista de servidores permitidos (whitelist),
 * incluindo operações para adicionar, remover e listar servidores.
 */
public class WhitelistManager {
    private List<String> whitelist;

    public WhitelistManager() {
        this.whitelist = new ArrayList<>();
    }

    /**
     * Adiciona um servidor à whitelist
     * @param servidor O endereço ou nome do servidor
     * @return true se adicionado com sucesso, false se já existir
     */
    public boolean adicionarServidor(String servidor) {
        if (servidor == null || servidor.trim().isEmpty()) {
            System.out.println("❌ Erro: Servidor inválido!");
            return false;
        }
        
        if (whitelist.contains(servidor)) {
            System.out.println("⚠️  O servidor '" + servidor + "' já está na whitelist!");
            return false;
        }
        
        whitelist.add(servidor);
        System.out.println("✅ Servidor '" + servidor + "' adicionado à whitelist com sucesso!");
        return true;
    }

    /**
     * Remove um servidor da whitelist
     * @param servidor O endereço ou nome do servidor a ser removido
     * @return true se removido com sucesso, false se não encontrado
     */
    public boolean removerServidor(String servidor) {
        if (servidor == null || servidor.trim().isEmpty()) {
            System.out.println("❌ Erro: Servidor inválido!");
            return false;
        }
        
        if (whitelist.contains(servidor)) {
            whitelist.remove(servidor);
            System.out.println("✅ Servidor '" + servidor + "' removido da whitelist com sucesso!");
            return true;
        } else {
            System.out.println("❌ Servidor '" + servidor + "' não encontrado na whitelist!");
            return false;
        }
    }

    /**
     * Verifica se um servidor está na whitelist
     * @param servidor O endereço ou nome do servidor
     * @return true se o servidor estiver na whitelist, false caso contrário
     */
    public boolean estaWhitelisted(String servidor) {
        if (servidor == null || servidor.trim().isEmpty()) {
            return false;
        }
        return whitelist.contains(servidor);
    }

    /**
     * Lista todos os servidores na whitelist
     */
    public void listarServidores() {
        if (whitelist.isEmpty()) {
            System.out.println("\n📋 A whitelist está vazia.");
            return;
        }
        
        System.out.println("\n📋 Servidores na Whitelist:");
        System.out.println("================================");
        for (int i = 0; i < whitelist.size(); i++) {
            System.out.println((i + 1) + ". " + whitelist.get(i));
        }
        System.out.println("================================");
        System.out.println("Total: " + whitelist.size() + " servidor(es)");
    }

    /**
     * Limpa toda a whitelist
     */
    public void limparWhitelist() {
        whitelist.clear();
        System.out.println("✅ Whitelist limpa com sucesso!");
    }

    public static void main(String[] args) {
        WhitelistManager manager = new WhitelistManager();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  Sistema de Gerenciamento de Whitelist ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Adicionar servidor à whitelist");
            System.out.println("2. Remover servidor da whitelist");
            System.out.println("3. Verificar se servidor está na whitelist");
            System.out.println("4. Listar todos os servidores");
            System.out.println("5. Limpar whitelist");
            System.out.println("0. Sair");
            System.out.print("\nEscolha uma opção: ");
            
            int opcao;
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer
            } catch (Exception e) {
                System.out.println("❌ Entrada inválida! Por favor, digite um número.");
                scanner.nextLine(); // Limpar buffer em caso de erro
                continue;
            }
            
            switch (opcao) {
                case 1:
                    System.out.print("\nDigite o endereço do servidor para adicionar: ");
                    String servidorAdd = scanner.nextLine();
                    manager.adicionarServidor(servidorAdd);
                    break;
                    
                case 2:
                    System.out.print("\nDigite o endereço do servidor para remover: ");
                    String servidorRemove = scanner.nextLine();
                    manager.removerServidor(servidorRemove);
                    break;
                    
                case 3:
                    System.out.print("\nDigite o endereço do servidor para verificar: ");
                    String servidorCheck = scanner.nextLine();
                    if (manager.estaWhitelisted(servidorCheck)) {
                        System.out.println("✅ O servidor '" + servidorCheck + "' ESTÁ na whitelist!");
                    } else {
                        System.out.println("❌ O servidor '" + servidorCheck + "' NÃO está na whitelist!");
                    }
                    break;
                    
                case 4:
                    manager.listarServidores();
                    break;
                    
                case 5:
                    System.out.print("\nTem certeza que deseja limpar toda a whitelist? (s/n): ");
                    String confirmacao = scanner.nextLine();
                    if (confirmacao.equalsIgnoreCase("s")) {
                        manager.limparWhitelist();
                    } else {
                        System.out.println("❌ Operação cancelada!");
                    }
                    break;
                    
                case 0:
                    continuar = false;
                    System.out.println("\n👋 Encerrando o sistema. Até logo!");
                    break;
                    
                default:
                    System.out.println("❌ Opção inválida! Tente novamente.");
            }
        }
        
        scanner.close();
    }
}
