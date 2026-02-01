# Solução de Problemas - n8n 🔧

## Problemas Comuns e Soluções

### 1. Workflow Não Está Executando

#### Sintoma
Workflow aparece como "Active" mas não executa quando deveria.

#### Causas Possíveis

**A. Trigger não configurado corretamente**

✅ **Solução:**
1. Abra o workflow
2. Clique no node Trigger
3. Verifique:
   - Credenciais conectadas
   - Configuração correta (planilha, evento, etc.)
   - Teste manual (botão "Execute Node")

**B. Credenciais expiradas**

✅ **Solução:**
1. Vá em **Credentials**
2. Teste a credencial
3. Se erro, reconecte:
   - Clique em "Reconnect"
   - Autorize novamente

**C. Limite de execuções atingido**

✅ **Solução:**
1. Verifique seu plano
2. Se gratuito (5.000/mês):
   - Aguarde próximo mês
   - Ou faça upgrade
3. Otimize workflows para gastar menos

**D. Workflow pausado por erro**

✅ **Solução:**
1. Veja **Executions**
2. Identifique o erro
3. Corrija o problema
4. Reative o workflow

---

### 2. Erro: "NodeOperationError"

#### Sintoma
```
NodeOperationError: The operation "create" is not supported!
```

#### Causas

**A. Node mal configurado**

✅ **Solução:**
1. Verifique a operação selecionada
2. Confirme que o serviço suporta essa operação
3. Veja documentação do node

**B. Versão desatualizada do node**

✅ **Solução:**
1. Atualize n8n
2. Ou use node alternativo

---

### 3. Dados Não Aparecem na Planilha

#### Sintoma
Workflow executa sem erro, mas dados não salvam no Google Sheets.

#### Causas

**A. Mapeamento incorreto de campos**

✅ **Solução:**

```javascript
// Verifique se os nomes dos campos correspondem
Formulário: "Nome Completo"
Planilha:   "Nome"  ❌ Não bate!

// Correto:
Use node "Set" para renomear:
"Nome Completo" → "Nome"
```

**B. Formato de dados incompatível**

✅ **Solução:**

```javascript
// Exemplo: Data
Formulário: "01/02/2024"
Sheets espera: "2024-02-01"

// Use Function Node:
const data = new Date(items[0].json.data);
items[0].json.data = data.toISOString().split('T')[0];
return items;
```

**C. Permissões insuficientes**

✅ **Solução:**
1. Abra a planilha no Google
2. Verifique se a conta n8n tem acesso de edição
3. Se não: Compartilhe com email da credencial
4. Permissão: "Editor"

**D. Planilha/Aba errada**

✅ **Solução:**
1. Verifique no node:
   - Document ID correto
   - Sheet name correto
2. Copie diretamente da planilha

---

### 4. WhatsApp/Telegram Não Envia

#### Sintoma
Workflow executa, mas mensagem não chega.

#### Telegram

**A. Chat ID incorreto**

✅ **Solução:**
1. Abra Telegram
2. Procure: `@userinfobot`
3. Envie: `/start`
4. Copie seu ID
5. Cole no workflow

**B. Bot não iniciado**

✅ **Solução:**
1. Procure seu bot no Telegram
2. Clique "Start" ou envie `/start`
3. Tente enviar novamente

#### WhatsApp (Evolution API)

**A. Número mal formatado**

✅ **Solução:**
```javascript
// Errado:
"(11) 99999-9999" ❌
"11 99999-9999" ❌

// Correto:
"5511999999999" ✅

// Use Function Node para limpar:
let numero = items[0].json.whatsapp;
numero = numero.replace(/\D/g, ''); // Remove não-números
if (!numero.startsWith('55')) {
  numero = '55' + numero;
}
items[0].json.whatsappLimpo = numero;
return items;
```

**B. API desconectada**

✅ **Solução:**
1. Verifique painel da Evolution API
2. QR Code pode ter expirado
3. Escaneie novamente

---

### 5. Erro: "Invalid JSON"

#### Sintoma
```
Error: Unexpected token in JSON
```

#### Causa
Resposta de API não é JSON válido ou está vazia.

✅ **Solução:**

```javascript
// Adicione tratamento no Function Node:
try {
  const data = JSON.parse(items[0].json.response);
  items[0].json.parsed = data;
} catch (error) {
  // Se não for JSON, trate como texto
  items[0].json.parsed = {
    raw: items[0].json.response,
    error: 'Não é JSON válido'
  };
}
return items;
```

---

### 6. Erro de Autenticação (401/403)

#### Sintoma
```
401 Unauthorized
403 Forbidden
```

#### Causa
Credenciais inválidas ou sem permissão.

✅ **Solução:**

1. **Reconectar credencial:**
   - Credentials → Sua credencial
   - Test → Se falhar, reconectar

2. **Verificar permissões:**
   - Google: Todas as scopes necessárias
   - APIs: Token com permissões corretas

3. **Verificar validade:**
   - Tokens podem expirar
   - OAuth pode precisar reautorizar

---

### 7. Timeout (Tempo Esgotado)

#### Sintoma
```
Error: Timeout exceeded
```

#### Causa
Operação demora muito (ex: scraping lento).

✅ **Solução:**

**A. Aumentar timeout:**
```javascript
// No node HTTP Request:
Settings → Timeout: 30000 (30 segundos)
```

**B. Dividir operação:**
```
Ao invés de processar 1000 itens:
→ Processar 100 por vez
→ Com delay entre lotes
```

**C. Usar async quando possível:**
```
Operação longa → Webhook para resultado
Ao invés de esperar inline
```

---

### 8. Erro ao Importar Workflow

#### Sintoma
"Failed to import workflow"

#### Causa
JSON corrompido ou incompatível.

✅ **Solução:**

1. **Verificar integridade do JSON:**
   - Abra em editor de texto
   - Veja se tem caracteres estranhos
   - Use JSON validator online

2. **Versão incompatível:**
   - Workflow pode ser de versão mais nova
   - Atualize seu n8n
   - Ou peça versão compatível

3. **Recriar manualmente:**
   - Se tudo falhar
   - Use workflow como referência
   - Recrie nodes manualmente

---

### 9. Workflow Lento / Travando

#### Sintoma
Execução demora muito ou trava.

#### Causas e Soluções

**A. Muitos dados processados de uma vez**

✅ **Solução:**
```
Processar 1000 linhas → 
Split em lotes de 100 →
Processar cada lote
```

**B. Operações síncronas pesadas**

✅ **Solução:**
```
Web Scraping pesado →
Move para workflow separado →
Executa em horário específico (noturno)
```

**C. Loops infinitos**

✅ **Solução:**
```javascript
// Adicione contador de segurança:
let iterations = $execution.customData.get('iterations') || 0;
if (iterations > 100) {
  throw new Error('Loop limite atingido!');
}
$execution.customData.set('iterations', iterations + 1);
```

---

### 10. Dados Duplicados

#### Sintoma
Mesmo pedido aparece 2x na planilha.

#### Causa
Workflow executou múltiplas vezes para mesmo evento.

✅ **Solução:**

**A. Usar ID único:**
```javascript
// Antes de adicionar, verificar se já existe:
Google Sheets: Lookup com ID único
→ Se encontrar: Update
→ Se não: Insert
```

**B. Debounce no trigger:**
```javascript
// Aguardar 5 segundos antes de processar
Wait Node: 5 seconds
→ Verifica se não processou ainda
→ Processa
```

**C. Usar Sheet como lock:**
```
Criar coluna "Processado"
→ Marcar como "Sim" ao processar
→ Ignorar linhas já processadas
```

---

## Ferramentas de Debug

### 1. Modo de Teste

**Como usar:**
1. Clique "Execute Workflow"
2. Workflow roda mas não ativa
3. Veja resultados de cada node
4. Identifique onde falha

### 2. Ver Dados Entre Nodes

**Como ver:**
1. Execute workflow
2. Clique em qualquer node
3. Veja JSON INPUT e OUTPUT
4. Identifique transformações

**Exemplo:**
```json
INPUT:
{
  "nome": "Maria Silva",
  "whatsapp": "(11) 99999-9999"
}

OUTPUT:
{
  "nome": "Maria Silva",
  "whatsapp": "5511999999999"
}
```

### 3. Function Node para Log

**Adicione logs:**
```javascript
console.log('Dados recebidos:', items[0].json);

// Processar...

console.log('Dados após processar:', items[0].json);

return items;
```

**Ver logs:**
- Executions → Clique na execução → Ver console

### 4. Node IF para Validação

**Validar dados antes de processar:**
```
Input → IF (Dados válidos?) 
        → SIM: Continua
        → NÃO: Stop + Notificação de erro
```

**Exemplo validação:**
```javascript
// No node IF
{{ $json.whatsapp && $json.whatsapp.length >= 10 }}
```

---

## Problemas Específicos por Integração

### Google Sheets

**Erro: "Range not found"**
- ✅ Verifique nome da aba (case sensitive!)
- ✅ Certifique-se que aba existe

**Erro: "Insufficient permissions"**
- ✅ Compartilhe planilha com email OAuth
- ✅ Dê permissão de "Editor"

**Dados não atualizam**
- ✅ Clear cache do navegador
- ✅ Aguarde alguns segundos
- ✅ Refresh da planilha

### Google Forms

**Trigger não dispara**
- ✅ Forms deve estar vinculado ao Sheets
- ✅ Trigger monitora o Sheets, não o Forms
- ✅ Use "On Row Added" no Sheets

### Mercado Pago

**Webhook não recebe dados**
- ✅ URL do webhook deve ser pública
- ✅ n8n cloud: Já é pública
- ✅ Self-hosted: Configure domínio/IP público

**Pagamento não confirma**
- ✅ Use ambiente de teste primeiro
- ✅ Verifique credenciais (produção vs teste)
- ✅ Veja logs no painel Mercado Pago

### Evolution API / WhatsApp

**Não conecta**
- ✅ Instância ativa?
- ✅ QR Code escaneado?
- ✅ Celular com internet?

**Mensagens não enviam**
- ✅ Número formatado correto
- ✅ Limite de mensagens não atingido
- ✅ WhatsApp não banido

---

## Quando Pedir Ajuda

### Informações para Incluir

1. **Descrição do problema:**
   - O que deveria acontecer
   - O que está acontecendo
   - Quando começou

2. **Workflow:**
   - Export do workflow (JSON)
   - Screenshot do fluxo
   - Configuração dos nodes relevantes

3. **Erro:**
   - Mensagem de erro completa
   - Screenshot do erro
   - Logs relacionados

4. **Ambiente:**
   - n8n Cloud ou self-hosted
   - Versão do n8n
   - Navegador usado

5. **Já tentou:**
   - Listar o que já foi testado
   - Resultados de cada tentativa

### Onde Buscar Ajuda

1. **Documentação deste projeto** (você está aqui!)
2. **[Documentação oficial n8n](https://docs.n8n.io)**
3. **[Fórum da comunidade](https://community.n8n.io)**
4. **[Discord n8n](https://discord.gg/n8n)**
5. **Grupos brasileiros:**
   - Facebook: Grupos de n8n Brasil
   - Telegram: @n8n_brasil
   - WhatsApp: Comunidades de automação

---

## Checklist de Troubleshooting

Quando algo não funcionar, siga esta ordem:

- [ ] 1. Workflow está ativo?
- [ ] 2. Credenciais válidas?
- [ ] 3. Testado manualmente (Execute Workflow)?
- [ ] 4. Viu execução em "Executions"?
- [ ] 5. Mensagem de erro clara?
- [ ] 6. Procurou erro na documentação?
- [ ] 7. Verificou logs de cada node?
- [ ] 8. Dados no formato correto?
- [ ] 9. Permissões nos serviços?
- [ ] 10. Tentou recriar o node problemático?

Se ainda não resolver:
- [ ] 11. Pesquisar no Google/fórum
- [ ] 12. Pedir ajuda com informações completas

---

## Prevenção de Problemas

### Antes de Ativar Workflow

✅ **Checklist:**
- [ ] Testado com dados reais
- [ ] Error workflow configurado
- [ ] Validações de entrada
- [ ] Tratamento de exceções
- [ ] Logs adequados
- [ ] Documentação clara
- [ ] Backup feito

### Manutenção Regular

✅ **Semanal:**
- [ ] Revisar execuções com erro
- [ ] Verificar alertas

✅ **Mensal:**
- [ ] Testar workflows críticos
- [ ] Atualizar credenciais se necessário
- [ ] Revisar performance

---

⬅️ **Anterior:** [Boas Práticas](03-boas-praticas.md)  
🏠 **Voltar:** [README Principal](../README.md)
