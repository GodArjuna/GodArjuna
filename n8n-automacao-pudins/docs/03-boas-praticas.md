# Boas Práticas para Automações n8n 📚

## Organização de Workflows

### Nomenclatura Clara

**Ruim ❌:**
- workflow 1
- teste
- novo

**Bom ✅:**
- 01 - Vendas - Novo Pedido
- 02 - Estoque - Alerta Baixo
- 03 - Marketing - Pesquisa Satisfação

**Padrão recomendado:**
```
[Número] - [Categoria] - [Ação]
```

### Estrutura de Pastas

Se seu plano n8n permitir, organize por categorias:

```
📁 Vendas
  ├── 01 - Receber Pedido
  ├── 02 - Confirmar Pagamento
  └── 03 - Atualizar Status

📁 Estoque
  ├── 01 - Reduzir ao Vender
  ├── 02 - Alerta Estoque Baixo
  └── 03 - Relatório Semanal

📁 Marketing
  ├── 01 - Pesquisa Satisfação
  └── 02 - Post Redes Sociais

📁 Monitoramento
  └── 01 - Preços Supermercados
```

### Documentação Dentro do Workflow

**Use Notes (Notas):**

1. Clique com botão direito no canvas
2. Selecione **"Add Sticky Note"**
3. Adicione explicação

**Exemplo:**
```
┌─────────────────────────────┐
│ 📝 NOTA                     │
│                             │
│ Este workflow monitora      │
│ novos pedidos no formulário │
│ e envia notificações.       │
│                             │
│ Roda: Tempo real            │
│ Responsável: Maria          │
└─────────────────────────────┘
```

## Gestão de Credenciais

### Nomenclatura

**Ruim ❌:**
- google
- whatsapp1
- teste

**Bom ✅:**
- Google Sheets - Pudins Vendas
- WhatsApp - Evolution API
- Gmail - Notificações Clientes
- Mercado Pago - Produção

### Segurança

- ✅ **Use credenciais diferentes** para teste e produção
- ✅ **Revise permissões** periodicamente
- ✅ **Documente** onde cada credencial é usada
- ❌ **Nunca compartilhe** credenciais em texto plano
- ❌ **Não exponha** tokens em screenshots

### Auditoria

Mantenha planilha com:
```
| Serviço | Credencial | Uso | Última Revisão |
|---------|-----------|------|----------------|
| Google Sheets | Google OAuth Pudins | Todos workflows vendas | 01/02/2024 |
| Telegram | Bot Pudins | Notificações | 01/02/2024 |
```

## Tratamento de Erros

### Sempre Use Error Workflow

**Como funcionar:**

1. Crie workflow: **"99 - Tratamento de Erros"**
2. Configure para receber erros
3. Envie notificação quando algo falhar

**Exemplo de Error Workflow:**
```
Error Trigger → IF (Erro Crítico?) 
                  → SIM: Telegram Urgente
                  → NÃO: Salvar log no Sheets
```

### Try-Catch com IF Node

```
Node Ação → IF (Success?)
            → SIM: Continua
            → NÃO: Envia alerta e para
```

### Validação de Dados

**Sempre valide entradas:**

```javascript
// No Function Node
if (!items[0].json.whatsapp) {
  throw new Error('WhatsApp não informado!');
}

if (!items[0].json.quantidade || items[0].json.quantidade <= 0) {
  throw new Error('Quantidade inválida!');
}

return items;
```

## Performance e Otimização

### 1. Use Webhooks ao Invés de Polling

**Ruim (Polling) ❌:**
```
Timer (verifica a cada 5 min) → Google Sheets
```
- Gasta execuções mesmo sem dados novos
- Mais lento

**Bom (Webhook) ✅:**
```
Google Sheets Trigger (evento real) → Ação
```
- Só executa quando necessário
- Instantâneo

### 2. Batch Processing

**Processar múltiplos itens de uma vez:**

```
Google Sheets (pegar 100 linhas) → Loop → Processar cada uma
```

Ao invés de:
```
Google Sheets (pegar 1 linha) → Executa 100 vezes
```

### 3. Cache Quando Possível

**Use Function Node para cache:**

```javascript
// Guardar dados temporariamente
const cache = $execution.getExecutionData().staticData;
cache.ultimoPreco = preco;
```

### 4. Limite Execuções Desnecessárias

**Use IF para filtrar:**

```
Trigger → IF (Realmente precisa processar?) 
          → SIM: Continua
          → NÃO: Para workflow
```

**Exemplo:**
```
Novo email → IF (Assunto contém "PEDIDO"?) 
             → SIM: Processar
             → NÃO: Ignorar
```

## Controle de Versões

### Backup Regular

**Automatize backups:**

1. Crie workflow: **"00 - Backup Automático"**
2. Schedule: Toda segunda-feira
3. Ação:
   - Export todos workflows (via API)
   - Salvar no Google Drive
   - Enviar confirmação

### Versionamento Manual

Quando fizer mudanças grandes:

1. **Duplicar** workflow antes
2. Renomear: `[Original] - v1 - BACKUP`
3. Trabalhar na cópia
4. Testar
5. Ativar nova versão
6. Desativar antiga (não deletar ainda)

### Log de Mudanças

Mantenha nota no workflow:
```
📝 CHANGELOG
────────────
v1.3 - 01/02/2024
- Adicionado validação de WhatsApp
- Melhorado texto da notificação

v1.2 - 15/01/2024
- Corrigido erro no cálculo de estoque

v1.1 - 10/01/2024
- Adicionado campo observações
```

## Testes e Validação

### Ambiente de Teste

**Crie estrutura duplicada:**

```
📁 Produção
  └── 01 - Vendas - Novo Pedido

📁 Teste (Desativados)
  └── [TESTE] 01 - Vendas - Novo Pedido
```

**Use dados de teste:**
- Planilha separada para testes
- Número de WhatsApp/Telegram seu
- Email pessoal

### Checklist Antes de Ativar

- [ ] Testado com dados reais
- [ ] Tratamento de erros configurado
- [ ] Notificações funcionando
- [ ] Credenciais corretas (produção!)
- [ ] Documentação atualizada
- [ ] Backup do workflow feito
- [ ] Time/responsável avisado

### Teste de Carga

Para workflows críticos:

1. Crie 10 pedidos de teste de uma vez
2. Veja se todos processam corretamente
3. Verifique performance
4. Identifique gargalos

## Monitoramento

### Dashboard de Execuções

**Verifique diariamente:**
1. Abra **"Executions"** no n8n
2. Veja se há erros
3. Analise tempos de execução
4. Identifique padrões

### Alertas Inteligentes

**Configure notificações:**

**Workflow de Monitoramento:**
```
Schedule (todo dia 9h) → HTTP Request (pega execuções API)
                      → IF (Erros nas últimas 24h?)
                      → SIM: Envia relatório Telegram
```

### Métricas Importantes

Acompanhe:
- **Taxa de sucesso**: % workflows sem erro
- **Tempo médio**: Quanto demora cada automação
- **Uso de execuções**: Quanto do limite mensal usado
- **Erros frequentes**: Quais problemas mais comuns

**Planilha de Métricas:**
```
| Semana | Execuções | Erros | Taxa Sucesso | Tempo Médio |
|--------|-----------|-------|--------------|-------------|
| 1      | 234       | 3     | 98.7%        | 2.3s        |
| 2      | 289       | 1     | 99.6%        | 2.1s        |
```

## Segurança e Privacidade

### Dados Sensíveis

**Nunca armazene em texto plano:**
- ❌ Senhas
- ❌ Tokens de API
- ❌ Números de cartão
- ❌ CPF/documentos

**Use:**
- ✅ Credenciais do n8n
- ✅ Variáveis de ambiente
- ✅ Criptografia quando necessário

### LGPD e Dados de Clientes

**Boas práticas:**

1. **Minimize coleta**: Só peça dados necessários
2. **Retenção limitada**: Delete dados antigos
3. **Acesso restrito**: Só quem precisa vê
4. **Transparência**: Cliente sabe o que você coleta
5. **Portabilidade**: Cliente pode pedir seus dados

**Workflow de Limpeza de Dados:**
```
Schedule (mensal) → Google Sheets
                  → Delete linhas > 1 ano
                  → Log ação
```

### Auditoria de Acessos

**Registre ações importantes:**

```
Qualquer mudança no pedido → Log no Sheets:
  - Quem: usuário/workflow
  - O quê: mudança feita  
  - Quando: timestamp
  - Por quê: motivo (se aplicável)
```

## Escalabilidade

### Planeje para Crescimento

**Hoje:**
- 10 pedidos/dia
- 1 planilha
- 1 pessoa

**Futuro próximo:**
- 50 pedidos/dia
- Múltiplas planilhas
- Equipe

**Prepare-se:**

1. **Use IDs únicos**: Não confie em linha da planilha
2. **Normalize dados**: Separe clientes de pedidos
3. **Modularize**: Workflows pequenos e reutilizáveis
4. **Documente**: Outras pessoas vão usar

### Sub-workflows

Quando workflow fica muito grande:

**Antes (Ruim):**
```
1 Workflow com 30 nodes ❌
```

**Depois (Bom):**
```
Workflow Principal → Chamar Sub-workflow 1
                  → Chamar Sub-workflow 2
                  → Chamar Sub-workflow 3
```

**Benefícios:**
- Mais fácil manter
- Reutilizável
- Mais claro

## Manutenção Regular

### Checklist Semanal

- [ ] Revisar execuções com erro
- [ ] Verificar consumo de execuções
- [ ] Testar workflows críticos
- [ ] Limpar logs antigos
- [ ] Revisar relatórios de estoque

### Checklist Mensal

- [ ] Backup de todos workflows
- [ ] Revisar credenciais
- [ ] Atualizar documentação
- [ ] Análise de performance
- [ ] Limpar dados antigos (LGPD)
- [ ] Revisar e otimizar workflows
- [ ] Verificar atualizações n8n

### Checklist Trimestral

- [ ] Auditoria completa de segurança
- [ ] Revisão de custos e ROI
- [ ] Planejamento de melhorias
- [ ] Treinamento da equipe
- [ ] Revisão de processos de negócio

## Comunicação com a Equipe

### Documentação Compartilhada

Mantenha documento com:

1. **Inventário de Workflows**
   - Nome, propósito, responsável
   
2. **Contatos Importantes**
   - Suporte n8n, APIs, fornecedores
   
3. **Procedimentos de Emergência**
   - O que fazer se workflow parar
   
4. **Glossário**
   - Termos técnicos explicados

### Treinamento

**Para novos usuários:**

1. **Dia 1**: Conceitos básicos n8n
2. **Dia 2**: Tour pelos workflows existentes
3. **Dia 3**: Criar workflow simples
4. **Dia 4**: Modificar workflow existente
5. **Dia 5**: Resolver problema real

## Troubleshooting Comum

### Workflow Não Executa

**Checklist:**
- [ ] Está ativo? (Toggle verde)
- [ ] Trigger configurado corretamente?
- [ ] Credenciais válidas?
- [ ] Sem erros no último teste?

### Dados Não Aparecem

**Checklist:**
- [ ] Mapeamento de campos correto?
- [ ] Formato de dados compatível?
- [ ] Permissões no destino?
- [ ] Filtros ou IFs bloqueando?

### Lentidão

**Causas comuns:**
- Muitos nodes em sequência
- Operações pesadas (scraping)
- Falta de índices em planilhas
- Processamento de muitos dados

**Soluções:**
- Dividir em sub-workflows
- Usar cache
- Otimizar queries
- Processar em lote

## Recursos Úteis

### Templates da Comunidade

- [n8n.io/workflows](https://n8n.io/workflows) - Templates oficiais
- Comunidade n8n - Workflows compartilhados
- GitHub - Projetos open source

### Aprendizado Contínuo

- 📺 YouTube: Tutoriais práticos
- 📖 Documentação: Sempre atualizada
- 💬 Fórum: Tire dúvidas
- 🎓 Cursos: Udemy, YouTube

### Ferramentas Complementares

- **Postman**: Testar APIs
- **Regex101**: Criar expressões regulares
- **JSON Formatter**: Visualizar JSON
- **Cron Expression**: Gerar schedules

---

⬅️ **Anterior:** [Instalação e Configuração](02-instalacao-configuracao.md)  
➡️ **Próximo:** [Solução de Problemas](04-solucao-problemas.md)
