# 📋 Guia de Gerenciamento de Whitelist

## 🤔 O que é uma Whitelist?

Uma **whitelist** (lista branca) é uma lista de servidores, endereços IP, ou recursos que são **autorizados** ou **permitidos** a acessar um determinado sistema, aplicação ou recurso. É o oposto de uma blacklist (lista negra), que contém elementos bloqueados.

### Exemplo de uso comum:
- **Segurança de Rede**: Apenas servidores na whitelist podem acessar seu banco de dados
- **APIs**: Apenas domínios whitelisted podem fazer requisições à sua API
- **Firewalls**: Apenas IPs whitelisted podem passar pelo firewall

---

## 🚀 Como usar o WhitelistManager

### Compilar o programa
```bash
javac WhitelistManager.java
```

### Executar o programa
```bash
java WhitelistManager
```

---

## 📖 Funcionalidades

### 1. ✅ Adicionar servidor à whitelist
Permite adicionar um novo servidor à lista de servidores permitidos.

**Exemplo:**
```
Digite o endereço do servidor para adicionar: 192.168.1.100
✅ Servidor '192.168.1.100' adicionado à whitelist com sucesso!
```

---

### 2. ❌ Remover servidor da whitelist

**Esta é a resposta para sua pergunta: "Como faço para tirar meu servidor da whitelist?"**

Para remover um servidor da whitelist, você tem duas opções:

#### Opção A: Usando o programa interativo
1. Execute o programa: `java WhitelistManager`
2. Escolha a opção **2** (Remover servidor da whitelist)
3. Digite o endereço do servidor que deseja remover
4. O servidor será removido imediatamente

**Exemplo:**
```
--- Menu ---
1. Adicionar servidor à whitelist
2. Remover servidor da whitelist
3. Verificar se servidor está na whitelist
4. Listar todos os servidores
5. Limpar whitelist
0. Sair

Escolha uma opção: 2

Digite o endereço do servidor para remover: 192.168.1.100
✅ Servidor '192.168.1.100' removido da whitelist com sucesso!
```

#### Opção B: Usando o código programaticamente
```java
WhitelistManager manager = new WhitelistManager();

// Adicionar servidor
manager.adicionarServidor("192.168.1.100");

// Remover servidor da whitelist
manager.removerServidor("192.168.1.100");
```

---

### 3. 🔍 Verificar se servidor está na whitelist
Permite verificar se um determinado servidor está ou não na whitelist.

**Exemplo:**
```
Digite o endereço do servidor para verificar: 192.168.1.100
✅ O servidor '192.168.1.100' ESTÁ na whitelist!
```

---

### 4. 📋 Listar todos os servidores
Mostra todos os servidores que estão atualmente na whitelist.

**Exemplo:**
```
📋 Servidores na Whitelist:
================================
1. 192.168.1.100
2. 192.168.1.101
3. server.exemplo.com
================================
Total: 3 servidor(es)
```

---

### 5. 🗑️ Limpar whitelist
Remove TODOS os servidores da whitelist de uma vez.

**Exemplo:**
```
Tem certeza que deseja limpar toda a whitelist? (s/n): s
✅ Whitelist limpa com sucesso!
```

---

## 💡 Exemplos de Uso Prático

### Cenário 1: Gerenciar acesso ao banco de dados
```java
WhitelistManager dbWhitelist = new WhitelistManager();

// Adicionar servidores de aplicação
dbWhitelist.adicionarServidor("app-server-1.empresa.com");
dbWhitelist.adicionarServidor("app-server-2.empresa.com");

// Verificar se um servidor pode acessar
if (dbWhitelist.estaWhitelisted("app-server-1.empresa.com")) {
    System.out.println("Acesso permitido ao banco de dados!");
}

// Remover servidor desativado
dbWhitelist.removerServidor("app-server-1.empresa.com");
```

### Cenário 2: Controle de acesso à API
```java
WhitelistManager apiWhitelist = new WhitelistManager();

// Adicionar domínios permitidos
apiWhitelist.adicionarServidor("cliente1.com");
apiWhitelist.adicionarServidor("cliente2.com");
apiWhitelist.adicionarServidor("parceiro.org");

// Cliente cancelou o serviço - remover da whitelist
apiWhitelist.removerServidor("cliente1.com");

// Listar clientes ativos
apiWhitelist.listarServidores();
```

---

## 🔐 Boas Práticas de Segurança

1. **✅ Mantenha a whitelist atualizada**: Remova servidores que não são mais necessários
2. **✅ Use nomes descritivos**: Facilita a identificação (ex: "prod-db-server" ao invés de "192.168.1.1")
3. **✅ Documente mudanças**: Registre quando e por que servidores foram adicionados/removidos
4. **✅ Revise periodicamente**: Faça auditorias regulares da sua whitelist
5. **✅ Princípio do menor privilégio**: Apenas adicione servidores que realmente precisam de acesso

---

## ❓ Perguntas Frequentes

### P: Como faço para tirar meu servidor da whitelist?
**R:** Use a opção 2 do menu interativo ou chame o método `removerServidor("seu-servidor")`.

### P: O que acontece se eu tentar remover um servidor que não está na whitelist?
**R:** O sistema informará que o servidor não foi encontrado e nenhuma alteração será feita.

### P: Posso adicionar o mesmo servidor duas vezes?
**R:** Não, o sistema detectará duplicatas e impedirá a adição do mesmo servidor novamente.

### P: Como salvar a whitelist para uso futuro?
**R:** A implementação atual armazena em memória. Para persistência, você pode estender o código para salvar em arquivo, banco de dados ou sistema de configuração.

---

## 📚 Próximos Passos

Se você quiser expandir este sistema, considere adicionar:

- 💾 **Persistência**: Salvar whitelist em arquivo JSON ou banco de dados
- 📝 **Logs**: Registrar todas as mudanças na whitelist
- 🔒 **Autenticação**: Adicionar controle de acesso para quem pode modificar a whitelist
- 🌐 **API REST**: Criar endpoints para gerenciar whitelist remotamente
- ⏱️ **Expiração**: Adicionar data de expiração para entradas temporárias

---

## 📞 Suporte

Se você tiver dúvidas sobre o gerenciamento de whitelist, consulte:
- Este guia completo
- Comentários no código fonte (WhitelistManager.java)
- Execute o programa interativamente para experimentar

**Desenvolvido como exemplo educacional por Kawan Villar**
