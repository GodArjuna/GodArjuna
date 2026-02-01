# Workflow 3: Monitoramento de Preços em Supermercados 🏪

## Visão Geral

Este workflow monitora preços de ingredientes em supermercados locais, gera relatórios comparativos e notifica sobre promoções interessantes.

## Desafio

**Importante:** A maioria dos supermercados brasileiros não oferece APIs públicas para consulta de preços. Este workflow oferece **múltiplas soluções** desde as mais simples até as mais avançadas.

## Soluções Disponíveis

### ⭐ Nível 1: Manual com Automação (Recomendado para Começar)
- Você atualiza preços 1x por semana manualmente
- n8n automatiza comparação e alertas

### ⭐⭐ Nível 2: Semi-Automatizado
- Usa APIs disponíveis (Mercado Livre, etc.)
- Scraping básico quando possível

### ⭐⭐⭐ Nível 3: Totalmente Automatizado
- Web scraping avançado
- Requer manutenção técnica

**Vamos implementar o Nível 1 (mais confiável) com base para evoluir.**

---

## Estrutura das Planilhas

### Aba: Precos_Ingredientes
```
| Data | Supermercado | Ingrediente | Marca | Preço | Unidade | Promoção | Validade Promo | Link | Observação |
```

Exemplo:
```
| 01/02/2024 | Carrefour | Leite Condensado | Moça | 4.49 | 395g | Sim | 07/02/2024 | https://... | Leve 3 Pague 2 |
| 01/02/2024 | Extra | Leite Condensado | Moça | 4.99 | 395g | Não | - | https://... | - |
| 01/02/2024 | Atacadão | Leite Condensado | Moça | 3.99 | 395g | Sim | 05/02/2024 | https://... | - |
```

### Aba: Comparativo_Precos
```
| Ingrediente | Melhor Preço | Supermercado | Preço Médio | Economia vs Médio | Última Atualização |
```

### Aba: Historico_Precos
```
| Data | Ingrediente | Supermercado | Preço Anterior | Preço Novo | Variação % | Tipo |
```

### Aba: Alertas_Promocoes
```
| Data/Hora | Ingrediente | Supermercado | Preço Normal | Preço Promo | Desconto % | Validade | Enviado |
```

---

## Workflow A: Atualização Manual com Automação

### Estrutura

```
┌────────────────────────────────────────────────┐
│  ATUALIZAÇÃO MANUAL + ANÁLISE AUTOMÁTICA      │
└────────────────────────────────────────────────┘

[1] Google Sheets Trigger (nova linha em Precos_Ingredientes)
      ↓
[2] Function: Validar e processar dados
      ↓
[3] Google Sheets: Buscar preços históricos do ingrediente
      ↓
[4] Function: Comparar com preços anteriores
      ↓
[5] IF: É uma promoção significativa? (>15% desconto)
      ├─→ SIM
      │    ↓
      │   [6] Google Sheets: Registrar em Alertas_Promocoes
      │    ↓
      │   [7] Telegram: Notificar promoção 🎉
      │
      └─→ NÃO: Continua
      
[8] Google Sheets: Atualizar Comparativo_Precos
      ↓
[9] Google Sheets: Registrar em Historico_Precos
```

### Implementação

#### Node 1: Google Sheets Trigger

**Configuração:**
```
Trigger On: Row Added
Document: [Sua planilha]
Sheet: Precos_Ingredientes
```

#### Node 2: Function - Validar Dados

**Código:**
```javascript
const dados = items[0].json;

// Validar campos obrigatórios
const erros = [];

if (!dados.Supermercado) erros.push('Supermercado não informado');
if (!dados.Ingrediente) erros.push('Ingrediente não informado');
if (!dados['Preço'] || dados['Preço'] <= 0) erros.push('Preço inválido');

// Normalizar dados
const preco = parseFloat(dados['Preço']);
const data = dados.Data || new Date().toLocaleDateString('pt-BR');
const promocao = dados['Promoção']?.toLowerCase() === 'sim' || 
                 dados['Promoção']?.toLowerCase() === 's';

return [{
  json: {
    ...dados,
    precoNumerico: preco,
    dataProcessamento: new Date().toISOString(),
    ehPromocao: promocao,
    valido: erros.length === 0,
    erros: erros.join(', ')
  }
}];
```

#### Node 3: Google Sheets - Buscar Histórico

**Configuração:**
```
Operation: Lookup
Sheet: Historico_Precos
Filter By: Ingrediente = {{ $json.Ingrediente }}
           AND Supermercado = {{ $json.Supermercado }}
Sort: Data DESC
Limit: 10 (últimos 10 registros)
```

#### Node 4: Function - Análise de Preços

**Código:**
```javascript
const precoAtual = items[0].json.precoNumerico;
const ingrediente = items[0].json.Ingrediente;
const supermercado = items[0].json.Supermercado;

// Buscar preço anterior (primeiro item do histórico)
let precoAnterior = null;
let variacao = 0;
let tipoVariacao = 'PRIMEIRO_REGISTRO';

// Assumindo que histórico vem nos próximos items
const historico = items.slice(1);

if (historico.length > 0) {
  precoAnterior = parseFloat(historico[0].json['Preço Novo']) || 
                  parseFloat(historico[0].json['Preço']);
  
  variacao = ((precoAtual - precoAnterior) / precoAnterior) * 100;
  
  if (variacao < -15) {
    tipoVariacao = 'QUEDA_SIGNIFICATIVA';
  } else if (variacao < -5) {
    tipoVariacao = 'QUEDA';
  } else if (variacao > 15) {
    tipoVariacao = 'ALTA_SIGNIFICATIVA';
  } else if (variacao > 5) {
    tipoVariacao = 'ALTA';
  } else {
    tipoVariacao = 'ESTAVEL';
  }
}

// Calcular preço médio do histórico
let precoMedio = precoAtual;
if (historico.length > 0) {
  const soma = historico.reduce((acc, item) => {
    const p = parseFloat(item.json['Preço Novo']) || 
              parseFloat(item.json['Preço']) || 0;
    return acc + p;
  }, precoAtual);
  precoMedio = soma / (historico.length + 1);
}

// Determinar se é promoção interessante
const ehPromocaoInteressante = variacao < -15 || 
                                precoAtual < precoMedio * 0.85;

return [{
  json: {
    ...items[0].json,
    precoAnterior: precoAnterior,
    variacao: variacao,
    variacaoFormatada: variacao.toFixed(2) + '%',
    tipoVariacao: tipoVariacao,
    precoMedio: precoMedio,
    economiaVsMedio: ((precoMedio - precoAtual) / precoMedio * 100).toFixed(2) + '%',
    ehPromocaoInteressante: ehPromocaoInteressante,
    alertar: ehPromocaoInteressante || 
             tipoVariacao === 'QUEDA_SIGNIFICATIVA' ||
             tipoVariacao === 'ALTA_SIGNIFICATIVA'
  }
}];
```

#### Node 5: IF - Deve Alertar?

**Condição:**
```
{{ $json.alertar }} equals true
```

#### Node 6: Google Sheets - Registrar Alerta (Ramo SIM)

**Configuração:**
```
Operation: Append
Sheet: Alertas_Promocoes

Columns:
- Data/Hora: {{ $json.dataProcessamento }}
- Ingrediente: {{ $json.Ingrediente }}
- Supermercado: {{ $json.Supermercado }}
- Preço Normal: {{ $json.precoMedio }}
- Preço Promo: {{ $json.precoNumerico }}
- Desconto %: {{ $json.economiaVsMedio }}
- Validade: {{ $json['Validade Promo'] }}
- Enviado: Sim
```

#### Node 7: Telegram - Notificação de Promoção

**Mensagem:**
```javascript
let emoji = '🎉';
if (items[0].json.variacao < -20) emoji = '🔥';
if (items[0].json.variacao < -30) emoji = '💥';

const msg = `${emoji} PROMOÇÃO ENCONTRADA!

🏪 ${items[0].json.Supermercado}
🛒 ${items[0].json.Ingrediente}
${items[0].json.Marca ? '🏷️ ' + items[0].json.Marca : ''}

💰 R$ ${items[0].json.precoNumerico.toFixed(2)}
📊 Economia: ${items[0].json.economiaVsMedio} vs média
${items[0].json.precoAnterior ? '📉 Variação: ' + items[0].json.variacaoFormatada : ''}

${items[0].json['Validade Promo'] ? '⏰ Válido até: ' + items[0].json['Validade Promo'] : ''}

${items[0].json.Observacao ? '📝 ' + items[0].json.Observacao : ''}

🔗 ${items[0].json.Link || 'Sem link'}
`;

return msg;
```

#### Node 8: Google Sheets - Atualizar Comparativo

**Configuração:**
```
Operation: Update or Append
Sheet: Comparativo_Precos
Lookup Column: Ingrediente
Lookup Value: {{ $json.Ingrediente }}

Columns:
- Ingrediente: {{ $json.Ingrediente }}
- Melhor Preço: Usar função MIN no Sheets
- Supermercado: {{ $json.Supermercado }}
- Preço Médio: {{ $json.precoMedio }}
- Economia vs Médio: {{ $json.economiaVsMedio }}
- Última Atualização: {{ $json.dataProcessamento }}
```

#### Node 9: Google Sheets - Histórico

**Configuração:**
```
Operation: Append
Sheet: Historico_Precos

Columns:
- Data: {{ $json.Data }}
- Ingrediente: {{ $json.Ingrediente }}
- Supermercado: {{ $json.Supermercado }}
- Preço Anterior: {{ $json.precoAnterior }}
- Preço Novo: {{ $json.precoNumerico }}
- Variação %: {{ $json.variacaoFormatada }}
- Tipo: {{ $json.tipoVariacao }}
```

---

## Workflow B: Relatório Comparativo Semanal

### Estrutura

```
┌────────────────────────────────────────────────┐
│  RELATÓRIO SEMANAL (Domingo 18h)              │
└────────────────────────────────────────────────┘

[1] Schedule Trigger
      ↓
[2] Google Sheets: Ler todos preços atuais
      ↓
[3] Function: Agrupar por ingrediente
      ↓
[4] Function: Identificar melhores preços
      ↓
[5] Function: Gerar relatório comparativo
      ↓
[6] Telegram: Enviar relatório
      ↓
[7] Email: Enviar relatório detalhado com tabela
```

### Implementação

#### Node 1: Schedule

**Configuração:**
```
Trigger Time: Sunday at 18:00
Timezone: America/Sao_Paulo
```

#### Node 2: Google Sheets - Ler Preços

**Configuração:**
```
Operation: Read
Sheet: Precos_Ingredientes
Filter: Última semana (opcional)
```

#### Node 3: Function - Agrupar

**Código:**
```javascript
const precos = items.map(i => i.json);

// Agrupar por ingrediente
const porIngrediente = {};

for (const preco of precos) {
  const ing = preco.Ingrediente;
  
  if (!porIngrediente[ing]) {
    porIngrediente[ing] = [];
  }
  
  porIngrediente[ing].push(preco);
}

return [{
  json: {
    ingredientes: porIngrediente
  }
}];
```

#### Node 4: Function - Identificar Melhores

**Código:**
```javascript
const ingredientes = items[0].json.ingredientes;
const comparativo = [];

for (const [nome, precos] of Object.entries(ingredientes)) {
  // Ordenar por preço
  precos.sort((a, b) => parseFloat(a['Preço']) - parseFloat(b['Preço']));
  
  const melhor = precos[0];
  const pior = precos[precos.length - 1];
  const media = precos.reduce((sum, p) => sum + parseFloat(p['Preço']), 0) / precos.length;
  
  const economia = ((parseFloat(pior['Preço']) - parseFloat(melhor['Preço'])) / 
                    parseFloat(pior['Preço']) * 100);
  
  comparativo.push({
    ingrediente: nome,
    melhorPreco: parseFloat(melhor['Preço']),
    melhorSupermercado: melhor.Supermercado,
    piorPreco: parseFloat(pior['Preço']),
    piorSupermercado: pior.Supermercado,
    precoMedio: media,
    economiaMaxima: economia,
    todosPre cos: precos
  });
}

// Ordenar por economia (maiores economias primeiro)
comparativo.sort((a, b) => b.economiaMaxima - a.economiaMaxima);

return [{
  json: {
    comparativo: comparativo,
    dataRelatorio: new Date().toLocaleDateString('pt-BR')
  }
}];
```

#### Node 5: Function - Gerar Relatório

**Código:**
```javascript
const comp = items[0].json.comparativo;
const data = items[0].json.dataRelatorio;

let relatorio = '🏪 RELATÓRIO DE PREÇOS SEMANAL\n';
relatorio += `📅 ${data}\n\n`;
relatorio += '═══════════════════════════════\n\n';

let economiaTotal = 0;

for (const item of comp) {
  relatorio += `🛒 ${item.ingrediente}\n\n`;
  
  // Melhor opção
  relatorio += `✅ MELHOR: ${item.melhorSupermercado}\n`;
  relatorio += `   R$ ${item.melhorPreco.toFixed(2)}\n\n`;
  
  // Comparação
  if (item.todosPrecos.length > 1) {
    relatorio += `📊 Outros preços:\n`;
    for (const p of item.todosPrecos.slice(1)) {
      const diff = parseFloat(p['Preço']) - item.melhorPreco;
      relatorio += `   • ${p.Supermercado}: R$ ${parseFloat(p['Preço']).toFixed(2)} (+R$ ${diff.toFixed(2)})\n`;
    }
    relatorio += '\n';
  }
  
  relatorio += `💰 Economia máxima: ${item.economiaMaxima.toFixed(0)}%\n`;
  relatorio += `📈 Preço médio: R$ ${item.precoMedio.toFixed(2)}\n`;
  relatorio += '\n─────────────────────────\n\n';
  
  economiaTotal += item.economiaMaxima;
}

relatorio += `💡 RESUMO:\n`;
relatorio += `📊 ${comp.length} ingredientes comparados\n`;
relatorio += `💰 Economia média possível: ${(economiaTotal / comp.length).toFixed(0)}%\n\n`;

// Top 3 maiores economias
relatorio += `🎯 Maiores oportunidades:\n`;
comp.slice(0, 3).forEach((item, i) => {
  relatorio += `${i + 1}. ${item.ingrediente}: ${item.economiaMaxima.toFixed(0)}% no ${item.melhorSupermercado}\n`;
});

return [{
  json: {
    ...items[0].json,
    relatorioTexto: relatorio
  }
}];
```

#### Node 6: Telegram - Enviar

**Mensagem:**
```
{{ $json.relatorioTexto }}
```

#### Node 7: Email - Detalhado

**Incluir tabela HTML:**
```html
<html>
<body>
<h2>Relatório de Preços - {{ $json.dataRelatorio }}</h2>
<table border="1" style="border-collapse: collapse;">
<tr>
  <th>Ingrediente</th>
  <th>Melhor Preço</th>
  <th>Supermercado</th>
  <th>Economia</th>
</tr>
<!-- Loop pelos itens -->
</table>
</body>
</html>
```

---

## Workflow C: Coleta Semi-Automatizada (Opcional)

### Usando APIs Públicas

#### Opção 1: Mercado Livre API

**Buscar preços de produtos:**

```
HTTP Request
URL: https://api.mercadolibre.com/sites/MLB/search?q=leite+condensado+moça
Method: GET

Response: Lista de produtos com preços
```

#### Opção 2: Google Shopping (Scraping)

**Usar pesquisa do Google:**

```javascript
// HTTP Request
URL: https://www.google.com/search?q=leite+condensado+preço&tbm=shop

// HTML Extract
Seletor CSS: div[data-sh-pr]

// Processar resultados
```

### Web Scraping Básico

**Exemplo para site de supermercado:**

```javascript
// 1. HTTP Request - Buscar página
const url = 'https://www.supermercado.com.br/busca?q=leite+condensado';

// 2. HTML Extract - Extrair preços
const seletores = {
  nome: '.product-name',
  preco: '.product-price',
  link: '.product-link'
};

// 3. Function - Processar
const produtos = items.map(item => ({
  nome: item.json.nome,
  preco: parseFloat(item.json.preco.replace('R$', '').replace(',', '.')),
  link: item.json.link
}));

// 4. Salvar na planilha
```

**⚠️ Atenção:**
- Scraping pode quebrar se site mudar
- Alguns sites bloqueiam bots
- Considere termos de uso

---

## Como Começar (Passo a Passo)

### Semana 1: Manual

1. Crie planilha com abas
2. Visite 3 supermercados (online ou físico)
3. Anote preços manualmente na planilha
4. Importe Workflow A
5. Veja notificações automáticas funcionarem

### Semana 2: Sistemático

1. Defina dia fixo para atualizar (ex: Sábado)
2. Crie rotina de 30min para coletar preços
3. Ative Workflow B (relatório semanal)
4. Analise tendências

### Semana 3+: Otimizar

1. Identifique supermercados com melhores preços
2. Foque neles para monitoramento
3. Se quiser, explore scraping básico
4. Automatize o que for possível

---

## Dicas Práticas

### Coleta Manual Eficiente

**Use o celular:**
1. Entre no site do supermercado
2. Busque o ingrediente
3. Screenshot do preço
4. No fim, transcreva todos de uma vez

**Apps auxiliares:**
- Zoom (comparador de preços brasileiro)
- Pelando (promoções)
- App dos supermercados

### Priorização

**Não precisa monitorar tudo:**
- Foque nos 5-7 ingredientes principais
- Ingredientes caros (leite condensado, chocolate)
- Itens que você compra em quantidade

### Frequência

**Atualização semanal é suficiente:**
- Preços não mudam tanto
- Economiza seu tempo
- Ainda captura promoções

---

## Expansões Futuras

### 1. Integração com Cartões de Desconto

Considerar descontos de programas de fidelidade:

```javascript
const descontosCartao = {
  'Carrefour': 0.05, // 5%
  'Extra': 0.03      // 3%
};

precoFinal = preco * (1 - descontosCartao[supermercado]);
```

### 2. Cálculo de Custo de Transporte

Incluir distância/custo de ir ao mercado:

```javascript
const custoTransporte = {
  'Carrefour': 8.00,  // Uber
  'Extra': 0,         // Próximo
  'Atacadão': 15.00   // Longe
};
```

### 3. Lista de Compras Otimizada

Gerar lista com melhor custo-benefício:

```
Considerar:
- Preços de cada ingrediente
- Custo de transporte
- Quantidade necessária
→ Calcular melhor combinação
```

### 4. Alertas Personalizados

Configurar limites personalizados:

```javascript
const alertarSe = {
  'Leite Condensado': { precoMax: 4.00 },
  'Chocolate em Pó': { descontoMin: 20 }
};
```

---

## Alternativas Sem n8n

Se preferir algo mais simples inicialmente:

1. **Planilha Google com Apps Script:**
   - Atualização manual
   - Cálculos automáticos no Sheets
   - Alertas por email (nativo)

2. **IFTTT:**
   - Mais simples
   - Menos flexível
   - Gratuito

3. **Apenas Planilha:**
   - Sem automação
   - Você controla tudo
   - Zero custo técnico

---

## Problemas Comuns

### Dados inconsistentes
- ✅ Padronize unidades (sempre em kg, litro, etc.)
- ✅ Use lista suspensa no Sheets

### Marcas diferentes
- ✅ Compare por ingrediente, não marca
- ✅ Registre marca mas compare genérico

### Promoções temporárias
- ✅ Registre validade
- ✅ Workflow pode limpar automaticamente expiradas

---

**Anterior:** [Workflow 2 - Estoque](README-estoque.md)  
**Próximo:** [Workflow 4 - Marketing](README-marketing.md)
