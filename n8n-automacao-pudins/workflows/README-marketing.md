# Workflow 4: Marketing e Relacionamento com Clientes 🤝

## Visão Geral

Este conjunto de workflows automatiza o relacionamento com clientes, incluindo pesquisas de satisfação, publicação em redes sociais e campanhas de marketing.

## O Que Estes Workflows Fazem

1. ✅ Enviam pesquisa de satisfação após entrega
2. ✅ Publicam automaticamente em redes sociais
3. ✅ Enviam promoções e novidades
4. ✅ Acompanham feedback dos clientes
5. ✅ Identificam clientes frequentes (programa de fidelidade)
6. ✅ Lembretes de aniversário com ofertas especiais

---

## Estrutura das Planilhas

### Aba: Clientes
```
| ID | Nome | WhatsApp | Email | Data Cadastro | Total Pedidos | Última Compra | Status | Aniversário | Preferências |
```

### Aba: Pesquisas_Satisfacao
```
| Data Envio | Pedido ID | Cliente | Avaliação | Comentário | O que mais gostou | Sugestões | Data Resposta |
```

### Aba: Marketing_Posts
```
| Data Publicação | Rede Social | Tipo Post | Conteúdo | Imagem | Status | Engajamento | Link |
```

### Aba: Campanhas
```
| ID Campanha | Nome | Tipo | Data Início | Data Fim | Público Alvo | Mensagem | Enviados | Respostas | Conversões |
```

---

## Workflow A: Pesquisa de Satisfação Automática

### Estrutura

```
┌────────────────────────────────────────────────┐
│  PESQUISA DE SATISFAÇÃO (Após Entrega)        │
└────────────────────────────────────────────────┘

[1] Google Sheets Trigger (Status = "Entregue")
      ↓
[2] Wait Node (Aguardar 1 dia após entrega)
      ↓
[3] Function: Gerar link de pesquisa
      ↓
[4] Set: Preparar mensagem personalizada
      ↓
[5] Telegram/WhatsApp: Enviar pesquisa
      ↓
[6] Google Sheets: Registrar envio
      ↓
[7] Webhook: Receber resposta da pesquisa
      ↓
[8] Function: Processar feedback
      ↓
[9] Google Sheets: Salvar feedback
      ↓
[10] IF: Avaliação negativa? (<3 estrelas)
       ├─→ SIM: [11] Telegram: Alerta para contato
       └─→ NÃO: [12] Agradecer feedback
```

### Implementação

#### Node 1: Google Sheets Trigger

**Configuração:**
```
Trigger On: Row Updated
Document: [Sua planilha]
Sheet: Pedidos
Watch for: Status column changes
Filter: Status = "Entregue"
```

#### Node 2: Wait Node

**Configuração:**
```
Time: 1 day
(Aguarda 24h após entrega para enviar pesquisa)
```

**Alternativa - Schedule:**
Se trigger de Update não funcionar, use Schedule diário que busca pedidos entregues ontem.

#### Node 3: Function - Gerar Link Pesquisa

**Código:**
```javascript
const pedido = items[0].json;

// Criar formulário do Google Forms para pesquisa
// Ou usar Typeform/Jotform

// URL do formulário com parâmetros pre-preenchidos
const baseUrl = 'https://forms.gle/SEU_FORM_ID';
const params = new URLSearchParams({
  'entry.PEDIDO_ID': pedido['ID'] || pedido.pedidoId,
  'entry.NOME': pedido['Nome Cliente'],
  'entry.SABOR': pedido['Sabor']
});

const linkPesquisa = `${baseUrl}?${params.toString()}`;

// Criar link curto (opcional)
// Use bit.ly API ou similar

return [{
  json: {
    ...pedido,
    linkPesquisa: linkPesquisa
  }
}];
```

#### Node 4: Set - Preparar Mensagem

**Mensagem personalizada:**
```javascript
Olá {{ $json["Nome Cliente"] }}! 😊

Esperamos que tenha adorado seu pudim de {{ $json["Sabor"] }}! 🍮

Sua opinião é muito importante para nós! 
Poderia avaliar seu pedido?

⭐ É rapidinho, menos de 1 minuto!

{{ $json.linkPesquisa }}

Agradecemos muito! ❤️

Pudins da Maria
```

#### Node 5: Telegram/WhatsApp - Enviar

**Configuração:**
```
Chat/Number: {{ $json["WhatsApp"] }}
Text: {{ $json.mensagemPesquisa }}
```

#### Node 6: Google Sheets - Registrar Envio

**Configuração:**
```
Operation: Append
Sheet: Pesquisas_Satisfacao

Columns:
- Data Envio: {{ $now }}
- Pedido ID: {{ $json["ID"] }}
- Cliente: {{ $json["Nome Cliente"] }}
- Status: Enviado
- Link: {{ $json.linkPesquisa }}
```

#### Node 7: Webhook - Receber Resposta

**Configuração Google Forms:**
1. No Google Forms, vá em Respostas
2. Clique nos três pontos → "Selecionar destino de resposta"
3. Vincule com Google Sheets
4. Use Google Sheets Trigger no n8n para detectar nova resposta

**OU use Webhook direto (Typeform, Jotform):**
```
Webhook URL: https://sua-instancia.n8n.cloud/webhook/pesquisa-resposta
```

#### Node 8: Function - Processar Feedback

**Código:**
```javascript
const resposta = items[0].json;

// Extrair avaliação (1-5 estrelas)
const avaliacao = parseInt(resposta['Avaliação (1-5)']) || 0;

// Classificar feedback
let classificacao = 'Neutro';
let emoji = '😐';

if (avaliacao >= 4) {
  classificacao = 'Positivo';
  emoji = '😊';
}
if (avaliacao === 5) {
  classificacao = 'Excelente';
  emoji = '🤩';
}
if (avaliacao <= 2) {
  classificacao = 'Negativo';
  emoji = '😞';
}

// Extrair insights
const comentario = resposta['Comentário'] || '';
const gostou = resposta['O que mais gostou?'] || '';
const sugestoes = resposta['Sugestões'] || '';

return [{
  json: {
    ...resposta,
    avaliacaoNumerica: avaliacao,
    classificacao: classificacao,
    emoji: emoji,
    requerAtencao: avaliacao <= 2,
    comentarioCompleto: `${comentario} | Gostou: ${gostou} | Sugestões: ${sugestoes}`
  }
}];
```

#### Node 9: Google Sheets - Salvar Feedback

**Configuração:**
```
Operation: Update
Sheet: Pesquisas_Satisfacao
Lookup: Pedido ID

Columns:
- Avaliação: {{ $json.avaliacaoNumerica }}
- Classificação: {{ $json.classificacao }}
- Comentário: {{ $json.comentarioCompleto }}
- Data Resposta: {{ $now }}
```

#### Node 10: IF - Avaliação Negativa?

**Condição:**
```
{{ $json.requerAtencao }} equals true
```

#### Node 11: Telegram - Alerta (Ramo SIM)

**Mensagem:**
```
🚨 FEEDBACK NEGATIVO!

Cliente: {{ $json["Nome Cliente"] }}
Pedido: {{ $json["Pedido ID"] }}
Avaliação: {{ $json.avaliacaoNumerica }}/5 ⭐

💬 Comentário:
{{ $json.comentarioCompleto }}

📞 Entre em contato URGENTE para resolver!
WhatsApp: {{ $json["WhatsApp Cliente"] }}
```

#### Node 12: Telegram - Agradecer (Ramo NÃO)

**Enviar de volta para cliente:**
```
{{ $json.emoji }} Muito obrigado pelo feedback!

Ficamos felizes em saber que você gostou! 

🎁 Como agradecimento, na sua próxima compra ganhe 10% de desconto com o cupom: OBRIGADO10

Até breve! ❤️
```

---

## Workflow B: Publicação Automática em Redes Sociais

### Estrutura

```
┌────────────────────────────────────────────────┐
│  PUBLICAÇÃO AUTOMÁTICA (Agendada)             │
└────────────────────────────────────────────────┘

[1] Google Sheets Trigger (nova linha em Marketing_Posts)
     OU Schedule Trigger
      ↓
[2] Function: Processar conteúdo e imagem
      ↓
[3] IF: Rede Social?
      ├─→ Instagram: [4A] HTTP Request (API Instagram)
      ├─→ Facebook: [4B] HTTP Request (API Facebook)
      ├─→ Twitter: [4C] HTTP Request (API Twitter)
      └─→ Telegram: [4D] Telegram Channel Post
      ↓
[5] Google Sheets: Atualizar status "Publicado"
      ↓
[6] Telegram: Confirmar publicação
```

### Implementação Simplificada

**⚠️ APIs de redes sociais são complexas. Alternativa mais simples:**

### Opção A: Buffer / Later (Recomendado)

Use serviços de agendamento:
1. Buffer.com (gratuito para 3 contas)
2. Later.com (gratuito limitado)

**Integração n8n:**
```
Planilha com posts → n8n → Buffer API → Publica automaticamente
```

### Opção B: Telegram Channel (Mais Fácil)

**Criar canal no Telegram:**

#### Node 1: Schedule Trigger

**Configuração:**
```
Triggers: 
- Segunda: 10:00 (Promoção da semana)
- Quarta: 15:00 (Dica/receita)
- Sexta: 18:00 (Cardápio fim de semana)
```

#### Node 2: Function - Gerar Conteúdo

**Código:**
```javascript
const dia = new Date().getDay();
const posts = {
  1: { // Segunda
    tipo: 'promocao',
    texto: `🎉 PROMOÇÃO DA SEMANA!

Sabor Especial: Maracujá 🥭
De: R$ 42,00
Por: R$ 35,00

Válido até domingo!

Peça já: [LINK DO FORMULÁRIO]`,
    imagem: 'https://sua-url.com/pudim-maracuja.jpg'
  },
  3: { // Quarta
    tipo: 'dica',
    texto: `💡 VOCÊ SABIA?

Nossos pudins são feitos com ingredientes frescos e naturais!

Cada pudim leva em média:
🥛 500ml de leite
🥚 4 ovos frescos
🍯 Receita especial da família

Feito com amor! ❤️`,
    imagem: 'https://sua-url.com/ingredientes.jpg'
  },
  5: { // Sexta
    tipo: 'cardapio',
    texto: `🎊 FIM DE SEMANA CHEGANDO!

Temos pudins fresquinhos para sua sobremesa:
✨ Tradicional
🍫 Chocolate
🥥 Coco
🥭 Maracujá
🍼 Leite Condensado

Entrega sábado e domingo!
Peça até hoje: [LINK]`,
    imagem: 'https://sua-url.com/todos-sabores.jpg'
  }
};

const post = posts[dia] || posts[1];

return [{
  json: {
    ...post,
    dataAgendada: new Date().toISOString()
  }
}];
```

#### Node 3: Telegram - Publicar no Canal

**Configuração:**
```
Chat ID: @seu_canal_pudins
Text: {{ $json.texto }}

Se tiver imagem:
Use "Send Photo" com URL da imagem
Caption: {{ $json.texto }}
```

#### Node 4: Google Sheets - Registrar

**Configuração:**
```
Operation: Append
Sheet: Marketing_Posts

Columns:
- Data Publicação: {{ $now }}
- Rede Social: Telegram
- Tipo Post: {{ $json.tipo }}
- Conteúdo: {{ $json.texto }}
- Status: Publicado
```

### Opção C: WhatsApp Status (Manual com Lembrete)

**Workflow que lembra de postar:**

```
Schedule → Preparar conteúdo → Enviar para você → Você posta manualmente
```

---

## Workflow C: Campanhas de Promoção

### Estrutura

```
┌────────────────────────────────────────────────┐
│  CAMPANHA PROMOCIONAL (Ex: Dia das Mães)      │
└────────────────────────────────────────────────┘

[1] Schedule ou Manual Trigger
      ↓
[2] Google Sheets: Buscar clientes ativos
      ↓
[3] Function: Filtrar público-alvo
      ↓
[4] Function: Personalizar mensagem
      ↓
[5] Loop: Para cada cliente
      ↓
[6] Telegram/WhatsApp: Enviar promoção
      ↓
[7] Wait: 2 segundos (evitar spam)
      ↓
[8] Google Sheets: Registrar envio
      ↓
[9] Telegram: Relatório de envios
```

### Implementação

#### Node 1: Schedule/Manual

**Manualmente quando quiser lançar campanha.**

#### Node 2: Google Sheets - Buscar Clientes

**Configuração:**
```
Operation: Read
Sheet: Clientes
Filter: Status = "Ativo" AND Total Pedidos > 0
```

#### Node 3: Function - Segmentar

**Código:**
```javascript
const clientes = items;

// Segmentar por perfil
const segmentado = clientes.map(item => {
  const cliente = item.json;
  const totalPedidos = parseInt(cliente['Total Pedidos']) || 0;
  
  let segmento = 'Novo';
  if (totalPedidos >= 10) segmento = 'VIP';
  else if (totalPedidos >= 5) segmento = 'Frequente';
  else if (totalPedidos >= 2) segmento = 'Regular';
  
  return {
    json: {
      ...cliente,
      segmento: segmento
    }
  };
});

return segmentado;
```

#### Node 4: Function - Personalizar Mensagem

**Código:**
```javascript
const cliente = items[0].json;

// Mensagem base
let mensagem = `Olá ${cliente.Nome}! 🍮\n\n`;

// Personalizar por segmento
if (cliente.segmento === 'VIP') {
  mensagem += `Como cliente VIP, temos uma oferta EXCLUSIVA para você!\n\n`;
  mensagem += `🎁 30% de desconto em qualquer sabor!\n`;
  mensagem += `Cupom: VIP30\n\n`;
} else if (cliente.segmento === 'Frequente') {
  mensagem += `Você é um cliente especial para nós! ❤️\n\n`;
  mensagem += `🎁 20% de desconto neste fim de semana!\n`;
  mensagem += `Cupom: ESPECIAL20\n\n`;
} else {
  mensagem += `Que tal experimentar um pudim delicioso? 😋\n\n`;
  mensagem += `🎁 15% de desconto na sua próxima compra!\n`;
  mensagem += `Cupom: PROMO15\n\n`;
}

// Adicionar sabor preferido se houver
if (cliente['Preferências']) {
  mensagem += `Vimos que você adora ${cliente['Preferências']}! Temos ele fresquinho! 🎉\n\n`;
}

mensagem += `Válido até domingo!\n`;
mensagem += `Peça aqui: [LINK DO FORMULÁRIO]\n\n`;
mensagem += `Abraços,\nPudins da Maria 💕`;

return [{
  json: {
    ...cliente,
    mensagemPersonalizada: mensagem
  }
}];
```

#### Node 5: Split Out

**Para processar cada cliente individualmente.**

#### Node 6: Telegram/WhatsApp - Enviar

**Configuração:**
```
Number: {{ $json["WhatsApp"] }}
Text: {{ $json.mensagemPersonalizada }}
```

#### Node 7: Wait

**Configuração:**
```
Time: 2 seconds
(Evita ser bloqueado por spam)
```

#### Node 8: Google Sheets - Registrar

**Configuração:**
```
Operation: Append
Sheet: Campanhas

Columns:
- Data: {{ $now }}
- Campanha: "Promoção Fim de Semana"
- Cliente: {{ $json.Nome }}
- Segmento: {{ $json.segmento }}
- Enviado: Sim
```

#### Node 9: Telegram - Relatório Final

**Ao terminar loop:**

```javascript
// No final, agregar resultados
const totalEnviados = items.length;
const porSegmento = items.reduce((acc, item) => {
  const seg = item.json.segmento;
  acc[seg] = (acc[seg] || 0) + 1;
  return acc;
}, {});

const relatorio = `📊 CAMPANHA CONCLUÍDA!

Total de mensagens enviadas: ${totalEnviados}

Por segmento:
${Object.entries(porSegmento).map(([seg, qtd]) => 
  `• ${seg}: ${qtd}`
).join('\n')}

✅ Campanha finalizada com sucesso!`;

return relatorio;
```

---

## Workflow D: Lembretes de Aniversário

### Estrutura

```
┌────────────────────────────────────────────────┐
│  ANIVERSÁRIO DO CLIENTE (Diário 8h)           │
└────────────────────────────────────────────────┘

[1] Schedule (todo dia 8h)
      ↓
[2] Google Sheets: Buscar clientes
      ↓
[3] Function: Filtrar aniversariantes do dia
      ↓
[4] IF: Há aniversariantes?
      └─→ SIM
           ↓
          [5] Function: Gerar mensagem de parabéns
           ↓
          [6] Telegram/WhatsApp: Enviar
           ↓
          [7] Google Sheets: Registrar
```

### Implementação

#### Node 3: Function - Filtrar Aniversariantes

**Código:**
```javascript
const clientes = items;
const hoje = new Date();
const mesHoje = hoje.getMonth() + 1;
const diaHoje = hoje.getDate();

const aniversariantes = clientes.filter(item => {
  const aniv = item.json['Aniversário']; // Formato: DD/MM ou DD/MM/AAAA
  
  if (!aniv) return false;
  
  const [dia, mes] = aniv.split('/').map(n => parseInt(n));
  
  return dia === diaHoje && mes === mesHoje;
});

if (aniversariantes.length === 0) {
  return [];
}

return aniversariantes;
```

#### Node 5: Function - Mensagem Parabéns

**Código:**
```javascript
const cliente = items[0].json;

const mensagem = `🎉🎂 FELIZ ANIVERSÁRIO, ${cliente.Nome}! 🎂🎉

Hoje é um dia muito especial e queríamos te dar um presente! 🎁

🍮 GANHE um pudim GRÁTIS!
Escolha seu sabor favorito! 

Use o cupom: ANIVER${new Date().getFullYear()}

Válido até ${new Date(Date.now() + 7 * 24 * 60 * 60 * 1000).toLocaleDateString('pt-BR')}

Parabéns e muito sucesso! 🎊

Com carinho,
Pudins da Maria ❤️`;

return [{
  json: {
    ...cliente,
    mensagemAniversario: mensagem
  }
}];
```

---

## Workflow E: Recuperação de Clientes Inativos

### Estrutura

```
┌────────────────────────────────────────────────┐
│  REATIVAR CLIENTES (Mensal)                   │
└────────────────────────────────────────────────┘

[1] Schedule (1º dia do mês, 10h)
      ↓
[2] Google Sheets: Buscar todos clientes
      ↓
[3] Function: Identificar inativos (>60 dias sem comprar)
      ↓
[4] IF: Há inativos?
      └─→ SIM
           ↓
          [5] Function: Gerar mensagem "Sentimos sua falta"
           ↓
          [6] Telegram/WhatsApp: Enviar
           ↓
          [7] Google Sheets: Registrar tentativa
```

### Implementação

#### Node 3: Function - Identificar Inativos

**Código:**
```javascript
const clientes = items;
const hoje = new Date();
const sessentaDiasAtras = new Date(hoje - 60 * 24 * 60 * 60 * 1000);

const inativos = clientes.filter(item => {
  const ultimaCompra = new Date(item.json['Última Compra']);
  const totalPedidos = parseInt(item.json['Total Pedidos']) || 0;
  
  // Cliente já comprou mas está inativo
  return totalPedidos > 0 && ultimaCompra < sessentaDiasAtras;
});

return inativos;
```

#### Node 5: Function - Mensagem Reativação

**Código:**
```javascript
const cliente = items[0].json;

const diasInativo = Math.floor(
  (new Date() - new Date(cliente['Última Compra'])) / (1000 * 60 * 60 * 24)
);

const mensagem = `Olá ${cliente.Nome}! 👋

Sentimos sua falta! 😢

Faz ${diasInativo} dias que você não pede um de nossos pudins...

Será que fizemos algo errado? 
Ou você simplesmente está sem tempo?

🎁 Preparamos um cupom especial para você voltar:

VOLTEI20 - 20% de desconto

Válido por 15 dias!

Esperamos você de volta! ❤️

Pudins da Maria`;

return [{
  json: {
    ...cliente,
    mensagemReativacao: mensagem
  }
}];
```

---

## Melhores Práticas de Marketing

### 1. Frequência de Mensagens

**Evite spam:**
- Máximo 1 mensagem por semana por cliente
- Exceções: Confirmações e updates de pedidos

### 2. Segmentação

**Personalize sempre:**
- Novos clientes: Boas-vindas
- Frequentes: Recompensas
- Inativos: Incentivos para voltar
- VIPs: Ofertas exclusivas

### 3. Horários

**Melhores horários para enviar:**
- Manhã: 9h-11h
- Tarde: 15h-17h
- Evite: Antes das 8h, depois das 21h

### 4. Conteúdo

**Varie os tipos:**
- 40% Promocional
- 30% Educativo (dicas, receitas)
- 20% Engajamento (pesquisas, interação)
- 10% Institucional (valores, equipe)

### 5. Métricas

**Acompanhe:**
```
- Taxa de abertura
- Taxa de resposta
- Conversões (cliques → compras)
- Custo por aquisição
- ROI das campanhas
```

---

## Expansões Futuras

### 1. Programa de Indicação

```
Cliente indica amigo →  Ambos ganham desconto
```

### 2. Gamificação

```
Selo de Fidelidade: A cada 5 pudins, ganhe 1 grátis
```

### 3. Conteúdo Gerado por IA

```
n8n → OpenAI API → Gerar posts criativos automaticamente
```

### 4. Análise de Sentimento

```
Feedback → Análise de sentimento → Classificação automática
```

---

## Ferramentas Complementares

### Design de Imagens

- **Canva**: Templates profissionais gratuitos
- **Remove.bg**: Remover fundo de fotos
- **Unsplash**: Fotos gratuitas

### Gestão de Redes Sociais

- **Buffer**: Agendamento multi-plataforma
- **Hootsuite**: Gerenciamento completo
- **Later**: Especializado em Instagram

### Análise

- **Google Analytics**: Rastrear cliques
- **Bitly**: Links curtos e estatísticas
- **Meta Business Suite**: Facebook e Instagram

---

## Como Começar

### Semana 1: Pesquisa de Satisfação

1. Crie formulário Google Forms
2. Implemente Workflow A
3. Teste com 2-3 pedidos
4. Ajuste conforme feedback

### Semana 2: Redes Sociais

1. Crie canal Telegram
2. Implemente Workflow B
3. Agende 3 posts por semana
4. Analise engajamento

### Semana 3: Campanhas

1. Importe base de clientes
2. Segmente audiência
3. Lance primeira campanha pequena
4. Meça resultados

### Semana 4: Otimizar

1. Analise métricas
2. Ajuste mensagens
3. Teste novos horários
4. Expanda o que funcionou

---

**Anterior:** [Workflow 3 - Preços](README-precos.md)  
**Voltar:** [README Principal](../README.md)
