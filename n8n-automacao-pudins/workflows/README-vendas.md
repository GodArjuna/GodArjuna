# Workflow 1: Automação de Vendas e Pedidos 💰

## Visão Geral

Este workflow automatiza todo o processo de recebimento e gestão de pedidos de pudins, desde o formulário até a notificação ao cliente e comerciante.

## O Que Este Workflow Faz

1. ✅ Detecta novo pedido no Google Forms
2. ✅ Salva dados na planilha de Pedidos
3. ✅ Envia confirmação para o cliente (WhatsApp/Telegram/Email)
4. ✅ Notifica o comerciante sobre novo pedido
5. ✅ Atualiza status do pedido
6. ✅ Reduz estoque de ingredientes
7. ✅ Integra com meios de pagamento

---

## Estrutura do Workflow

```
┌────────────────────────────────────────────────┐
│  RECEBIMENTO DE PEDIDOS                        │
└────────────────────────────────────────────────┘

[1] Google Forms 
      ↓
[2] Google Sheets Trigger (detecta nova resposta)
      ↓
[3] Function: Processar e Validar Dados
      ↓
[4] IF: Dados Válidos?
      ├─→ SIM
      │    ↓
      │   [5] Google Sheets: Salvar na aba "Pedidos"
      │    ↓
      │   [6] Set: Preparar dados para notificações
      │    ├─→ [7A] Telegram: Notificar Cliente
      │    ├─→ [7B] Telegram: Notificar Comerciante  
      │    └─→ [7C] Email: Confirmação (opcional)
      │    ↓
      │   [8] Function: Calcular ingredientes necessários
      │    ↓
      │   [9] Google Sheets: Atualizar Estoque
      │    ↓
      │   [10] IF: Estoque baixo?
      │         └─→ SIM: [11] Alerta estoque baixo
      │
      └─→ NÃO
           ↓
          [12] Telegram: Notificar erro + dados inválidos
```

---

## Configuração Passo a Passo

### Pré-requisitos

1. ✅ Google Forms criado e conectado ao Google Sheets
2. ✅ Planilha Google Sheets com abas:
   - "Respostas ao formulário" (criada automaticamente)
   - "Pedidos" (gerenciamento)
   - "Estoque" (ingredientes)
3. ✅ Bot Telegram configurado OU WhatsApp API
4. ✅ Credenciais configuradas no n8n

### Estrutura das Planilhas

#### Aba "Respostas ao formulário" (Google Forms)
```
| Carimbo | Nome | WhatsApp | Email | Sabor | Quantidade | Data Entrega | Pagamento | Observações |
```

#### Aba "Pedidos" (Gestão)
```
| ID | Data Pedido | Nome Cliente | WhatsApp | Email | Sabor | Quantidade | Valor Total | Status | Data Entrega | Pagamento | Pago | Observações |
```

Status possíveis:
- "Pedido Recebido"
- "Pagamento Confirmado"
- "Em Produção"
- "Pronto para Entrega"
- "Entregue"
- "Cancelado"

#### Aba "Estoque"
```
| Ingrediente | Unidade | Qtd Atual | Mínimo | Status | Última Atualização |
```

Exemplo de dados:
```
| Leite Condensado | Lata | 25 | 10 | OK | 01/02/2024 |
| Leite | Litro | 8 | 5 | OK | 01/02/2024 |
| Ovos | Dúzia | 15 | 5 | OK | 01/02/2024 |
| Açúcar | Kg | 12 | 3 | OK | 01/02/2024 |
```

---

## Implementação do Workflow

### Node 1: Google Sheets Trigger

**Configuração:**
```
Node Type: Google Sheets Trigger
Credential: Sua conta Google
Trigger On: Row Added
Document: [Sua planilha]
Sheet: Respostas ao formulário
```

**O que faz:** Monitora a planilha e dispara quando nova linha é adicionada (novo pedido).

---

### Node 2: Function - Processar Dados

**Nome:** Processar e Validar Pedido

**Código:**
```javascript
// Recebe dados do Forms
const dados = items[0].json;

// Gerar ID único para o pedido
const pedidoId = `PUD-${Date.now()}`;

// Processar data/hora
const agora = new Date();
const dataHora = agora.toLocaleString('pt-BR');

// Limpar número de WhatsApp
let whatsapp = dados['WhatsApp'] || '';
whatsapp = whatsapp.replace(/\D/g, ''); // Remove tudo que não é número
if (whatsapp.length === 11 && !whatsapp.startsWith('55')) {
  whatsapp = '55' + whatsapp; // Adiciona código do Brasil
}

// Calcular valor total
const precos = {
  'Tradicional': 35.00,
  'Chocolate': 40.00,
  'Coco': 38.00,
  'Maracujá': 42.00,
  'Leite Condensado': 38.00
};

const sabor = dados['Sabor do pudim'] || 'Tradicional';
const quantidade = parseInt(dados['Quantidade']) || 1;
const valorUnitario = precos[sabor] || 35.00;
const valorTotal = valorUnitario * quantidade;

// Validações
const erros = [];

if (!dados['Nome completo']) {
  erros.push('Nome não informado');
}

if (whatsapp.length < 12) {
  erros.push('WhatsApp inválido');
}

if (quantidade < 1 || quantidade > 50) {
  erros.push('Quantidade inválida (min: 1, max: 50)');
}

// Preparar saída
return [{
  json: {
    // Dados originais
    ...dados,
    
    // Dados processados
    pedidoId: pedidoId,
    dataHoraPedido: dataHora,
    nomeCliente: dados['Nome completo'],
    whatsappLimpo: whatsapp,
    email: dados['Email'] || '',
    sabor: sabor,
    quantidade: quantidade,
    valorUnitario: valorUnitario,
    valorTotal: valorTotal,
    status: 'Pedido Recebido',
    dataEntrega: dados['Data de entrega desejada'],
    formaPagamento: dados['Forma de pagamento'] || 'A combinar',
    pago: 'Não',
    observacoes: dados['Observações'] || '',
    
    // Validação
    valido: erros.length === 0,
    erros: erros.join(', ')
  }
}];
```

---

### Node 3: IF - Validar Dados

**Configuração:**
```
Condition: {{ $json.valido }} equals true
```

**Ramo SIM:** Continua para salvar pedido  
**Ramo NÃO:** Envia notificação de erro

---

### Node 4: Google Sheets - Salvar Pedido (Ramo SIM)

**Configuração:**
```
Node Type: Google Sheets
Operation: Append
Document: [Sua planilha]
Sheet: Pedidos
Data Mode: Auto-Map Input Data
```

**Campos mapeados:**
- ID → {{ $json.pedidoId }}
- Data Pedido → {{ $json.dataHoraPedido }}
- Nome Cliente → {{ $json.nomeCliente }}
- WhatsApp → {{ $json.whatsappLimpo }}
- Email → {{ $json.email }}
- Sabor → {{ $json.sabor }}
- Quantidade → {{ $json.quantidade }}
- Valor Total → {{ $json.valorTotal }}
- Status → {{ $json.status }}
- Data Entrega → {{ $json.dataEntrega }}
- Pagamento → {{ $json.formaPagamento }}
- Pago → {{ $json.pago }}
- Observações → {{ $json.observacoes }}

---

### Node 5: Set - Preparar Notificações

**Nome:** Preparar Mensagens

**Campos:**
```javascript
mensagemCliente:
🎉 Pedido Confirmado!

Olá {{ $json.nomeCliente }}!

Seu pedido foi recebido com sucesso:

📦 Pedido: {{ $json.pedidoId }}
🍮 Sabor: {{ $json.sabor }}
📊 Quantidade: {{ $json.quantidade }}
💰 Valor Total: R$ {{ $json.valorTotal }}
📅 Entrega: {{ $json.dataEntrega }}
💳 Pagamento: {{ $json.formaPagamento }}

Em breve entraremos em contato para confirmar detalhes.

Obrigado pela preferência! ❤️

mensagemComerciantе:
🔔 NOVO PEDIDO RECEBIDO!

📝 ID: {{ $json.pedidoId }}
👤 Cliente: {{ $json.nomeCliente }}
📱 WhatsApp: {{ $json.whatsappLimpo }}
✉️ Email: {{ $json.email }}

🍮 Sabor: {{ $json.sabor }}
📊 Quantidade: {{ $json.quantidade }}
💰 Valor: R$ {{ $json.valorTotal }}
📅 Entrega: {{ $json.dataEntrega }}
💳 Pagamento: {{ $json.formaPagamento }}

📋 Obs: {{ $json.observacoes }}

⚡ Acesse a planilha para gerenciar!
```

---

### Node 6A: Telegram - Notificar Cliente

**Configuração:**
```
Node Type: Telegram
Operation: Send Message
Chat ID: {{ $json.whatsappLimpo }} 
  (se usar Telegram pelo número)
  OU seu chat ID fixo se for grupo
Text: {{ $json.mensagemCliente }}
```

**Alternativa WhatsApp (Evolution API):**
```
Node Type: HTTP Request
Method: POST
URL: https://sua-evolution-api.com/message/sendText
Body:
{
  "number": "{{ $json.whatsappLimpo }}",
  "text": "{{ $json.mensagemCliente }}"
}
Headers:
apikey: [sua-api-key]
```

---

### Node 6B: Telegram - Notificar Comerciante

**Configuração:**
```
Node Type: Telegram
Chat ID: [SEU_CHAT_ID]
Text: {{ $json.mensagemComerciante }}
```

---

### Node 7: Function - Calcular Ingredientes

**Nome:** Calcular Redução de Estoque

**Código:**
```javascript
// Receitas por sabor (ingredientes necessários para 1 pudim)
const receitas = {
  'Tradicional': {
    'Leite Condensado': 1,    // 1 lata
    'Leite': 0.5,             // 500ml
    'Ovos': 0.25,             // 3 ovos (0.25 dúzia)
    'Açúcar': 0.2             // 200g
  },
  'Chocolate': {
    'Leite Condensado': 1,
    'Leite': 0.5,
    'Ovos': 0.25,
    'Chocolate em Pó': 0.1,   // 100g
    'Açúcar': 0.3
  },
  'Coco': {
    'Leite Condensado': 1,
    'Leite': 0.4,
    'Ovos': 0.25,
    'Coco Ralado': 0.1,       // 100g
    'Açúcar': 0.2
  },
  'Maracujá': {
    'Leite Condensado': 1,
    'Leite': 0.4,
    'Ovos': 0.25,
    'Polpa Maracujá': 0.2,    // 200ml
    'Açúcar': 0.25
  },
  'Leite Condensado': {
    'Leite Condensado': 2,    // 2 latas
    'Leite': 0.6,
    'Ovos': 0.33,             // 4 ovos
    'Açúcar': 0.3
  }
};

const sabor = items[0].json.sabor;
const quantidade = items[0].json.quantidade;
const receita = receitas[sabor] || receitas['Tradicional'];

// Calcular quantidade necessária de cada ingrediente
const ingredientesNecessarios = {};
for (const [ingrediente, qtdPorPudim] of Object.entries(receita)) {
  ingredientesNecessarios[ingrediente] = qtdPorPudim * quantidade;
}

return [{
  json: {
    ...items[0].json,
    ingredientesReduzir: ingredientesNecessarios
  }
}];
```

---

### Node 8: Google Sheets - Atualizar Estoque

Este é mais complexo e requer um loop ou múltiplas chamadas.

**Opção Simples - Function que prepara update:**

**Código:**
```javascript
const ingredientes = items[0].json.ingredientesReduzir;

// Preparar updates para cada ingrediente
const updates = [];
for (const [nome, quantidade] of Object.entries(ingredientes)) {
  updates.push({
    ingrediente: nome,
    reduzir: quantidade,
    dataAtualizacao: new Date().toLocaleString('pt-BR')
  });
}

return updates.map(update => ({ json: update }));
```

Depois use **Google Sheets** em modo **Update** para cada ingrediente.

**Nota:** Para simplificar, você pode usar um script do Google Apps Script na planilha que é acionado por um webhook do n8n.

---

### Node 9: IF - Verificar Estoque Baixo

**Após atualizar, ler estoque e verificar:**

```
Google Sheets: Read
Sheet: Estoque
Filter: {{ $json["Qtd Atual"] < $json["Mínimo"] }}
```

Se encontrar items → Enviar alerta

---

### Node 10: Telegram - Alerta Estoque Baixo

**Configuração:**
```
Text:
⚠️ ALERTA DE ESTOQUE BAIXO!

Os seguintes ingredientes estão abaixo do mínimo:

{{ $json.ingredients }}

📊 Acesse a planilha e faça reposição!
```

---

### Node 11: Telegram - Notificar Erro (Ramo NÃO)

**Para dados inválidos:**

```
Text:
❌ ERRO NO PEDIDO!

Novo pedido recebeu dados inválidos:

Nome: {{ $json.nomeCliente || 'Não informado' }}
WhatsApp: {{ $json['WhatsApp'] || 'Não informado' }}
Erros: {{ $json.erros }}

Verifique o formulário e entre em contato com o cliente.
```

---

## Como Importar e Usar

### Importar Workflow

1. Copie o JSON completo (veja arquivo `01-vendas-pedidos.json`)
2. No n8n: **Workflows** → **Import from File**
3. Cole o JSON ou selecione arquivo
4. Clique **Import**

### Configurar

1. **Reconectar credenciais:**
   - Google Sheets
   - Telegram/WhatsApp
   
2. **Ajustar IDs:**
   - Chat ID do Telegram
   - URL da API WhatsApp (se usar)
   - Document ID da planilha

3. **Customizar:**
   - Preços dos pudins
   - Receitas (ingredientes)
   - Textos das mensagens
   - Validações

### Testar

1. Clique **Execute Workflow**
2. Preencha um pedido de teste no formulário
3. Veja execução node por node
4. Verifique se:
   - Dados salvaram na planilha
   - Notificações chegaram
   - Estoque foi atualizado

### Ativar

1. Se tudo OK, clique **Active** (toggle no topo)
2. Workflow agora roda automaticamente!

---

## Expansões Futuras

### 1. Integração com Pagamento

Adicione após confirmação do pedido:

```
[...] → Mercado Pago: Gerar link de pagamento
      → Enviar link para cliente
      → Webhook: Confirmar pagamento
      → Atualizar Status: "Pago"
```

### 2. Controle de Status

Crie workflow separado para atualizar status:

```
Google Sheets Trigger (campo Status muda)
  → IF Status = "Pago"
    → Notificar: "Pagamento confirmado! Em produção..."
  → IF Status = "Pronto"
    → Notificar: "Seu pudim está pronto!"
  → IF Status = "Entregue"
    → Enviar pesquisa de satisfação
```

### 3. Lembretes Automáticos

```
Schedule (diário às 9h)
  → Google Sheets: Buscar pedidos pendentes
  → Filter: Data entrega = Hoje
  → Telegram: Lembrete de pedidos para hoje
```

### 4. Relatório Diário

```
Schedule (diário às 20h)
  → Google Sheets: Contar pedidos do dia
  → Calcular faturamento
  → Telegram: Enviar resumo
```

---

## Problemas Comuns

### Workflow não dispara
- ✅ Verifique se está Active
- ✅ Confirme que Forms está vinculado ao Sheets
- ✅ Teste manualmente primeiro

### Notificação não chega
- ✅ Verifique Chat ID
- ✅ Confirme que bot foi iniciado
- ✅ Veja logs de execução

### Estoque não atualiza
- ✅ Verifique nomes dos ingredientes (case sensitive!)
- ✅ Confirme que aba "Estoque" existe
- ✅ Veja se há permissão de escrita

---

## Suporte

Dúvidas sobre este workflow?
- 📖 [Documentação completa](../docs/)
- 💬 [Fórum n8n](https://community.n8n.io)
- 🏠 [README Principal](../README.md)

---

**Próximo:** [Workflow 2 - Gestão de Estoque](02-estoque-ingredientes.md)
