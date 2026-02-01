# Workflow 2: Gestão de Estoque de Ingredientes 📦

## Visão Geral

Este conjunto de workflows automatiza o controle de estoque de ingredientes necessários para produção dos pudins, incluindo alertas de estoque baixo e relatórios periódicos.

## O Que Estes Workflows Fazem

1. ✅ Atualiza estoque automaticamente após cada venda
2. ✅ Monitora níveis mínimos de ingredientes
3. ✅ Envia alertas quando estoque está baixo
4. ✅ Gera relatórios semanais de consumo
5. ✅ Calcula previsão de necessidade de compra
6. ✅ Registra histórico de movimentações

---

## Workflows Necessários

### A. Workflow Principal: Atualização de Estoque
- **Trigger:** Webhook do workflow de vendas
- **Frequência:** Cada venda realizada

### B. Workflow Secundário: Verificação de Estoque Baixo
- **Trigger:** Schedule (4x ao dia)
- **Frequência:** 6h, 12h, 18h, 22h

### C. Workflow Terciário: Relatório Semanal
- **Trigger:** Schedule (Segunda-feira 8h)
- **Frequência:** Semanal

---

## Estrutura da Planilha "Estoque"

### Aba: Estoque_Atual
```
| ID | Ingrediente | Categoria | Unidade | Qtd Atual | Qtd Mínima | Qtd Máxima | Status | Custo Unitário | Última Atualização |
```

Exemplo:
```
| 1 | Leite Condensado | Laticínio | Lata (395g) | 25 | 10 | 50 | OK | 4.50 | 01/02/2024 10:30 |
| 2 | Leite Integral | Laticínio | Litro | 8 | 5 | 20 | OK | 5.20 | 01/02/2024 10:30 |
| 3 | Ovos | Ovos | Dúzia | 15 | 5 | 30 | OK | 12.00 | 01/02/2024 10:30 |
| 4 | Açúcar Cristal | Açúcar | Kg | 12 | 3 | 20 | OK | 4.80 | 01/02/2024 10:30 |
| 5 | Chocolate em Pó | Chocolates | Kg | 3 | 2 | 10 | BAIXO | 18.00 | 01/02/2024 10:30 |
```

### Aba: Historico_Movimentacoes
```
| Data/Hora | Tipo | Ingrediente | Quantidade | Motivo | Pedido ID | Saldo Após |
```

Exemplo:
```
| 01/02/2024 10:30 | SAÍDA | Leite Condensado | -1 | Venda | PUD-1706777800000 | 24 |
| 01/02/2024 10:30 | SAÍDA | Ovos | -0.25 | Venda | PUD-1706777800000 | 14.75 |
| 31/01/2024 15:00 | ENTRADA | Leite Condensado | +20 | Compra | - | 25 |
```

### Aba: Relatorio_Semanal
```
| Semana | Ingrediente | Consumo Total | Valor Gasto | Média Diária | Projeção Mensal |
```

---

## Workflow A: Atualização Automática de Estoque

### Estrutura

```
┌────────────────────────────────────────────────┐
│  ATUALIZAÇÃO DE ESTOQUE (Após Venda)          │
└────────────────────────────────────────────────┘

[1] Webhook (recebe dados da venda)
      ↓
[2] Function: Calcular ingredientes necessários
      ↓
[3] Loop: Para cada ingrediente
      ↓
[4] Google Sheets: Ler estoque atual
      ↓
[5] Function: Calcular novo saldo
      ↓
[6] Google Sheets: Atualizar estoque
      ↓
[7] Google Sheets: Registrar no histórico
      ↓
[8] IF: Estoque ficou abaixo do mínimo?
      └─→ SIM: [9] Telegram: Alerta imediato
```

### Implementação

#### Node 1: Webhook

**Configuração:**
```
Node Type: Webhook
HTTP Method: POST
Path: atualizar-estoque
```

**Chamada do workflow de vendas:**
```
HTTP Request para: 
https://sua-instancia-n8n.cloud/webhook/atualizar-estoque

Body:
{
  "pedidoId": "PUD-123456",
  "sabor": "Chocolate",
  "quantidade": 2,
  "ingredientes": {
    "Leite Condensado": 2,
    "Leite Integral": 1,
    "Ovos": 0.5,
    "Chocolate em Pó": 0.2,
    "Açúcar Cristal": 0.6
  }
}
```

#### Node 2: Function - Processar Atualização

**Código:**
```javascript
const dados = items[0].json.body;

// Preparar lista de ingredientes para atualizar
const ingredientes = [];
for (const [nome, quantidade] of Object.entries(dados.ingredientes)) {
  ingredientes.push({
    nome: nome,
    quantidadeReduzir: quantidade,
    pedidoId: dados.pedidoId,
    dataHora: new Date().toLocaleString('pt-BR')
  });
}

// Retornar array de ingredientes
return ingredientes.map(item => ({ json: item }));
```

#### Node 3: Loop - Processar Cada Ingrediente

**Use node "Split Out"** para processar cada ingrediente individualmente.

#### Node 4: Google Sheets - Ler Estoque Atual

**Configuração:**
```
Operation: Lookup
Sheet: Estoque_Atual
Lookup Column: Ingrediente
Lookup Value: {{ $json.nome }}
```

#### Node 5: Function - Calcular Novo Saldo

**Código:**
```javascript
const estoqueAtual = parseFloat(items[0].json['Qtd Atual']) || 0;
const quantidadeReduzir = parseFloat(items[0].json.quantidadeReduzir) || 0;
const minimo = parseFloat(items[0].json['Qtd Mínima']) || 0;

const novoSaldo = estoqueAtual - quantidadeReduzir;

// Determinar status
let status = 'OK';
if (novoSaldo <= 0) {
  status = 'ESGOTADO';
} else if (novoSaldo <= minimo) {
  status = 'BAIXO';
} else if (novoSaldo <= minimo * 1.5) {
  status = 'ATENÇÃO';
}

return [{
  json: {
    ...items[0].json,
    novoSaldo: novoSaldo,
    status: status,
    alertar: novoSaldo <= minimo
  }
}];
```

#### Node 6: Google Sheets - Atualizar Estoque

**Configuração:**
```
Operation: Update
Sheet: Estoque_Atual
Lookup Column: Ingrediente
Lookup Value: {{ $json.nome }}

Columns to Update:
- Qtd Atual: {{ $json.novoSaldo }}
- Status: {{ $json.status }}
- Última Atualização: {{ $json.dataHora }}
```

#### Node 7: Google Sheets - Registrar Histórico

**Configuração:**
```
Operation: Append
Sheet: Historico_Movimentacoes

Columns:
- Data/Hora: {{ $json.dataHora }}
- Tipo: SAÍDA
- Ingrediente: {{ $json.nome }}
- Quantidade: -{{ $json.quantidadeReduzir }}
- Motivo: Venda
- Pedido ID: {{ $json.pedidoId }}
- Saldo Após: {{ $json.novoSaldo }}
```

#### Node 8: IF - Verificar Se Deve Alertar

**Condição:**
```
{{ $json.alertar }} equals true
```

#### Node 9: Telegram - Alerta Estoque Baixo

**Mensagem:**
```
⚠️ ALERTA DE ESTOQUE!

Ingrediente: {{ $json.nome }}
Saldo atual: {{ $json.novoSaldo }} {{ $json['Unidade'] }}
Mínimo: {{ $json['Qtd Mínima'] }} {{ $json['Unidade'] }}

Status: {{ $json.status }}

🛒 Providenciar reposição!
```

---

## Workflow B: Verificação Periódica de Estoque

### Estrutura

```
┌────────────────────────────────────────────────┐
│  VERIFICAÇÃO PERIÓDICA (4x ao dia)            │
└────────────────────────────────────────────────┘

[1] Schedule Trigger
      ↓
[2] Google Sheets: Ler todos ingredientes
      ↓
[3] Function: Filtrar ingredientes baixos/esgotados
      ↓
[4] IF: Há ingredientes com problema?
      ├─→ SIM
      │    ↓
      │   [5] Function: Gerar relatório
      │    ↓
      │   [6] Telegram: Enviar alerta consolidado
      │    ↓
      │   [7] Email: Enviar lista de compras
      │
      └─→ NÃO: Workflow termina silenciosamente
```

### Implementação

#### Node 1: Schedule Trigger

**Configuração:**
```
Trigger Times: 06:00, 12:00, 18:00, 22:00
Timezone: America/Sao_Paulo
```

#### Node 2: Google Sheets - Ler Estoque

**Configuração:**
```
Operation: Read
Sheet: Estoque_Atual
Read All Data: Yes
```

#### Node 3: Function - Filtrar Problemas

**Código:**
```javascript
const todos = items.map(item => item.json);

// Filtrar apenas ingredientes com problema
const problemas = todos.filter(item => {
  const qtdAtual = parseFloat(item['Qtd Atual']) || 0;
  const minimo = parseFloat(item['Qtd Mínima']) || 0;
  return qtdAtual <= minimo;
});

// Organizar por gravidade
problemas.sort((a, b) => {
  const percA = parseFloat(a['Qtd Atual']) / parseFloat(a['Qtd Mínima']);
  const percB = parseFloat(b['Qtd Atual']) / parseFloat(b['Qtd Mínima']);
  return percA - percB;
});

if (problemas.length === 0) {
  return [];
}

return [{
  json: {
    temProblemas: true,
    quantidade: problemas.length,
    ingredientes: problemas
  }
}];
```

#### Node 4: IF - Tem Problemas?

**Condição:**
```
{{ $json.temProblemas }} equals true
```

#### Node 5: Function - Gerar Relatório

**Código:**
```javascript
const ingredientes = items[0].json.ingredientes;

// Gerar relatório formatado
let relatorio = '⚠️ RELATÓRIO DE ESTOQUE\n\n';
relatorio += `📊 ${ingredientes.length} ingrediente(s) necessitam atenção:\n\n`;

let custoTotal = 0;
let listaCompras = [];

for (const item of ingredientes) {
  const qtdAtual = parseFloat(item['Qtd Atual']) || 0;
  const minimo = parseFloat(item['Qtd Mínima']) || 0;
  const maximo = parseFloat(item['Qtd Máxima']) || 0;
  const custo = parseFloat(item['Custo Unitário']) || 0;
  
  // Calcular quanto comprar (até o máximo)
  const comprar = Math.max(0, maximo - qtdAtual);
  const valorCompra = comprar * custo;
  custoTotal += valorCompra;
  
  let emoji = '⚠️';
  if (qtdAtual <= 0) emoji = '🚫';
  else if (qtdAtual <= minimo * 0.5) emoji = '⛔';
  
  relatorio += `${emoji} ${item.Ingrediente}\n`;
  relatorio += `   Atual: ${qtdAtual} ${item.Unidade}\n`;
  relatorio += `   Mínimo: ${minimo} ${item.Unidade}\n`;
  relatorio += `   Comprar: ${comprar.toFixed(2)} ${item.Unidade}\n`;
  relatorio += `   Valor: R$ ${valorCompra.toFixed(2)}\n\n`;
  
  listaCompras.push({
    ingrediente: item.Ingrediente,
    quantidade: comprar,
    unidade: item.Unidade,
    valorUnitario: custo,
    valorTotal: valorCompra
  });
}

relatorio += `💰 Investimento Total: R$ ${custoTotal.toFixed(2)}\n`;
relatorio += `\n📅 ${new Date().toLocaleString('pt-BR')}`;

return [{
  json: {
    relatorio: relatorio,
    listaCompras: listaCompras,
    custoTotal: custoTotal
  }
}];
```

#### Node 6: Telegram - Alerta

**Mensagem:**
```
{{ $json.relatorio }}
```

#### Node 7: Email - Lista de Compras

**Configuração:**
```
To: seu-email@exemplo.com
Subject: 🛒 Lista de Compras - Reposição de Estoque
Body:

Olá!

Segue a lista de ingredientes que precisam ser repostos:

{{ $json.relatorio }}

Lista detalhada em anexo na planilha.

Att,
Sistema de Gestão
```

---

## Workflow C: Relatório Semanal de Consumo

### Estrutura

```
┌────────────────────────────────────────────────┐
│  RELATÓRIO SEMANAL (Segunda 8h)               │
└────────────────────────────────────────────────┘

[1] Schedule Trigger
      ↓
[2] Google Sheets: Ler histórico da semana
      ↓
[3] Function: Calcular estatísticas
      ↓
[4] Function: Gerar relatório completo
      ↓
[5] Google Sheets: Salvar em Relatorio_Semanal
      ↓
[6] Telegram: Enviar resumo
      ↓
[7] Email: Enviar relatório completo
```

### Implementação

#### Node 1: Schedule Trigger

**Configuração:**
```
Trigger Time: Monday at 08:00
Timezone: America/Sao_Paulo
```

#### Node 2: Google Sheets - Ler Histórico

**Configuração:**
```
Operation: Read
Sheet: Historico_Movimentacoes
Filter: Últimos 7 dias
```

**Ou use Function para filtrar:**
```javascript
const hoje = new Date();
const seteDiasAtras = new Date(hoje - 7 * 24 * 60 * 60 * 1000);

const movimentacoesSemana = items.filter(item => {
  const data = new Date(item.json['Data/Hora']);
  return data >= seteDiasAtras;
});

return movimentacoesSemana;
```

#### Node 3: Function - Calcular Estatísticas

**Código:**
```javascript
const movimentacoes = items;

// Agrupar por ingrediente
const consumoPorIngrediente = {};

for (const mov of movimentacoes) {
  const ingrediente = mov.json.Ingrediente;
  const quantidade = Math.abs(parseFloat(mov.json.Quantidade) || 0);
  const tipo = mov.json.Tipo;
  
  if (tipo === 'SAÍDA') {
    if (!consumoPorIngrediente[ingrediente]) {
      consumoPorIngrediente[ingrediente] = {
        nome: ingrediente,
        consumoTotal: 0,
        numeroSaidas: 0
      };
    }
    
    consumoPorIngrediente[ingrediente].consumoTotal += quantidade;
    consumoPorIngrediente[ingrediente].numeroSaidas += 1;
  }
}

// Converter para array e calcular médias
const estatisticas = Object.values(consumoPorIngrediente).map(item => {
  const mediaDiaria = item.consumoTotal / 7;
  const projecaoMensal = mediaDiaria * 30;
  
  return {
    ingrediente: item.nome,
    consumoSemanal: item.consumoTotal,
    mediaDiaria: mediaDiaria,
    projecaoMensal: projecaoMensal,
    numeroSaidas: item.numeroSaidas
  };
});

// Ordenar por consumo (maior primeiro)
estatisticas.sort((a, b) => b.consumoSemanal - a.consumoSemanal);

return [{
  json: {
    estatisticas: estatisticas,
    periodo: {
      inicio: new Date(Date.now() - 7 * 24 * 60 * 60 * 1000).toLocaleDateString('pt-BR'),
      fim: new Date().toLocaleDateString('pt-BR')
    }
  }
}];
```

#### Node 4: Function - Gerar Relatório

**Código:**
```javascript
const stats = items[0].json.estatisticas;
const periodo = items[0].json.periodo;

let relatorio = '📊 RELATÓRIO SEMANAL DE ESTOQUE\n\n';
relatorio += `📅 Período: ${periodo.inicio} a ${periodo.fim}\n\n`;
relatorio += '═══════════════════════════════\n\n';

for (const item of stats) {
  relatorio += `🍽️ ${item.ingrediente}\n`;
  relatorio += `   Consumo Semanal: ${item.consumoSemanal.toFixed(2)}\n`;
  relatorio += `   Média Diária: ${item.mediaDiaria.toFixed(2)}\n`;
  relatorio += `   Projeção Mensal: ${item.projecaoMensal.toFixed(2)}\n`;
  relatorio += `   Número de Saídas: ${item.numeroSaidas}\n\n`;
}

// Adicionar insights
relatorio += '💡 INSIGHTS:\n\n';

// Top 3 mais consumidos
const top3 = stats.slice(0, 3);
relatorio += '🥇 Ingredientes mais consumidos:\n';
top3.forEach((item, index) => {
  relatorio += `   ${index + 1}. ${item.ingrediente} (${item.consumoSemanal.toFixed(2)})\n`;
});

relatorio += '\n📈 Recomendações:\n';
for (const item of stats) {
  if (item.projecaoMensal > 50) {
    relatorio += `   • Considere compra em atacado de ${item.ingrediente}\n`;
  }
}

return [{
  json: {
    ...items[0].json,
    relatorioTexto: relatorio
  }
}];
```

#### Node 5: Google Sheets - Salvar Relatório

**Configuração:**
```
Operation: Append
Sheet: Relatorio_Semanal

Columns:
- Semana: {{ $json.periodo.fim }}
- Ingrediente: Usar loop para cada item
- Consumo Total: {{ $json.consumoSemanal }}
- Média Diária: {{ $json.mediaDiaria }}
- Projeção Mensal: {{ $json.projecaoMensal }}
```

#### Node 6: Telegram - Resumo

**Mensagem:**
```
{{ $json.relatorioTexto }}
```

#### Node 7: Email - Relatório Completo

**Configuração:**
```
Subject: 📊 Relatório Semanal de Estoque

Body:
{{ $json.relatorioTexto }}

Acesse a planilha para detalhes completos.
```

---

## Como Usar

### Setup Inicial

1. **Criar estrutura de planilhas:**
   - Crie as 3 abas mencionadas
   - Preencha dados iniciais de estoque

2. **Importar workflows:**
   - Importe os 3 workflows
   - Configure credenciais
   - Ajuste IDs e parâmetros

3. **Testar cada workflow:**
   - Teste manualmente primeiro
   - Verifique se dados salvam corretamente

4. **Ativar:**
   - Ative os 3 workflows
   - Monitore primeiros dias

### Entrada Manual de Estoque

Para registrar compras manualmente:

**Opção 1 - Direto na Planilha:**
1. Abra aba Estoque_Atual
2. Atualize Qtd Atual
3. Registre em Historico_Movimentacoes

**Opção 2 - Criar Workflow Simples:**
```
Google Form (Entrada de Estoque)
  → Processar dados
  → Atualizar Estoque_Atual
  → Registrar em Histórico
```

---

## Expansões Futuras

### 1. Previsão Inteligente

Usar histórico para prever necessidade:

```javascript
// Calcular tendência de consumo
const ultimos30Dias = [...];
const tendencia = calcularTendencia(ultimos30Dias);
const previsaoProximaSemana = projetar(tendencia);
```

### 2. Integração com Fornecedores

Enviar pedido automático quando baixo:

```
Estoque Baixo → Gerar Pedido de Compra
              → Enviar Email para Fornecedor
              → Aguardar Confirmação
```

### 3. Controle de Validade

Adicionar datas de validade:

```
Schedule Diário → Verificar Validades
                → Alertar produtos próximos do vencimento
                → Sugerir uso prioritário
```

### 4. Custo por Pudim

Calcular custo real de produção:

```javascript
const custoPorPudim = calcularCustoIngredientes(sabor);
const margemLucro = precoVenda - custoPorPudim;
```

---

## Problemas Comuns

### Saldo negativo
- ✅ Adicione validação para não permitir
- ✅ Alerte imediatamente

### Inconsistências
- ✅ Reconcilie semanalmente com estoque físico
- ✅ Corrija discrepâncias

### Performance
- ✅ Limite histórico (ex: últimos 6 meses)
- ✅ Archive dados antigos

---

**Anterior:** [Workflow 1 - Vendas](README-vendas.md)  
**Próximo:** [Workflow 3 - Monitoramento de Preços](README-precos.md)
