# Templates de Planilhas Google Sheets 📊

## Como Usar Estes Templates

1. Copie a estrutura para sua planilha Google Sheets
2. Ajuste conforme suas necessidades
3. Configure os workflows n8n para usar sua planilha

---

## Template 1: Planilha Principal "Gestão Pudins"

### Aba: Pedidos

```
| ID | Data Pedido | Nome Cliente | WhatsApp | Email | Sabor | Quantidade | Valor Unitário | Valor Total | Status | Data Entrega | Forma Pagamento | Pago | Data Pagamento | Observações | Última Atualização |
```

**Valores de Status:**
- Pedido Recebido
- Pagamento Confirmado
- Em Produção
- Pronto para Entrega
- Entregue
- Cancelado

**Exemplo de Dados:**
```
PUD-001 | 01/02/2024 10:30 | Maria Silva | 5511999999999 | maria@email.com | Chocolate | 2 | 40.00 | 80.00 | Entregue | 03/02/2024 | PIX | Sim | 01/02/2024 11:00 | Cliente pediu embalagem especial | 03/02/2024 18:00
```

**Fórmulas Úteis:**

Valor Total (coluna I):
```
=G2*H2
```

Status Colorido (Formatação Condicional):
- Verde: "Entregue", "Pago"
- Amarelo: "Em Produção", "Pronto"
- Azul: "Pedido Recebido"
- Vermelho: "Cancelado"

---

### Aba: Estoque_Atual

```
| ID | Ingrediente | Categoria | Unidade | Qtd Atual | Qtd Mínima | Qtd Máxima | Status | Custo Unitário | Valor Total Estoque | Fornecedor | Última Atualização |
```

**Categorias:**
- Laticínios
- Ovos
- Açúcares
- Chocolates
- Frutas
- Temperos
- Embalagens

**Exemplo de Dados:**
```
1 | Leite Condensado | Laticínio | Lata (395g) | 25 | 10 | 50 | OK | 4.50 | 112.50 | Nestlé | 01/02/2024 10:30
2 | Leite Integral | Laticínio | Litro | 8 | 5 | 20 | ATENÇÃO | 5.20 | 41.60 | Parmalat | 01/02/2024 10:30
3 | Ovos | Ovos | Dúzia | 15 | 5 | 30 | OK | 12.00 | 180.00 | Granja XYZ | 01/02/2024 10:30
4 | Açúcar Cristal | Açúcar | Kg | 12 | 3 | 20 | OK | 4.80 | 57.60 | União | 01/02/2024 10:30
5 | Chocolate em Pó | Chocolates | Kg | 2 | 2 | 10 | BAIXO | 18.00 | 36.00 | Nestlé | 01/02/2024 10:30
```

**Fórmulas Úteis:**

Valor Total Estoque (coluna J):
```
=E2*I2
```

Status (coluna H):
```
=IF(E2<=0,"ESGOTADO",IF(E2<=F2,"BAIXO",IF(E2<=F2*1.5,"ATENÇÃO","OK")))
```

**Formatação Condicional (Status):**
- Vermelho: "ESGOTADO"
- Laranja: "BAIXO"
- Amarelo: "ATENÇÃO"
- Verde: "OK"

---

### Aba: Historico_Movimentacoes

```
| Data/Hora | Tipo | Ingrediente | Quantidade | Unidade | Motivo | Pedido ID | Usuário | Saldo Anterior | Saldo Após | Observação |
```

**Tipos:**
- ENTRADA (Compra)
- SAÍDA (Venda/Uso)
- AJUSTE (Correção)
- PERDA (Vencimento/Quebra)

**Exemplo de Dados:**
```
01/02/2024 10:30 | SAÍDA | Leite Condensado | -1 | Lata | Venda | PUD-001 | Sistema | 26 | 25 | -
01/02/2024 10:30 | SAÍDA | Ovos | -0.25 | Dúzia | Venda | PUD-001 | Sistema | 15.25 | 15 | 3 ovos usados
31/01/2024 15:00 | ENTRADA | Leite Condensado | +20 | Lata | Compra | - | Maria | 6 | 26 | Compra Carrefour
```

---

### Aba: Clientes

```
| ID | Nome | WhatsApp | Email | Data Cadastro | Total Pedidos | Valor Total Gasto | Última Compra | Média por Pedido | Status | Sabor Preferido | Aniversário | Observações |
```

**Exemplo de Dados:**
```
CLI-001 | Maria Silva | 5511999999999 | maria@email.com | 15/01/2024 | 5 | 190.00 | 01/02/2024 | 38.00 | Ativo | Chocolate | 15/03 | Cliente VIP, gosta de embalagem especial
```

**Fórmulas Úteis:**

Total Pedidos (contagem automática):
```
=COUNTIF(Pedidos!C:C,A2)
```

Valor Total Gasto:
```
=SUMIF(Pedidos!C:C,A2,Pedidos!I:I)
```

Média por Pedido (coluna I):
```
=G2/F2
```

Status:
```
=IF(F2>=10,"VIP",IF(F2>=5,"Frequente",IF(F2>=2,"Regular","Novo")))
```

---

### Aba: Precos_Ingredientes

```
| Data | Supermercado | Ingrediente | Marca | Preço | Unidade | Promoção | Validade Promo | Link | Observação |
```

**Exemplo de Dados:**
```
01/02/2024 | Carrefour | Leite Condensado | Moça | 4.49 | 395g | Sim | 07/02/2024 | https://... | Leve 3 Pague 2
01/02/2024 | Extra | Leite Condensado | Moça | 4.99 | 395g | Não | - | https://... | -
01/02/2024 | Atacadão | Leite Condensado | Moça | 3.99 | 395g | Sim | 05/02/2024 | https://... | Desconto no caixa
```

---

### Aba: Comparativo_Precos

```
| Ingrediente | Melhor Preço | Supermercado | Preço Médio | Pior Preço | Economia % | Última Atualização |
```

**Fórmulas Úteis:**

Melhor Preço:
```
=MINIFS(Precos_Ingredientes!E:E,Precos_Ingredientes!C:C,A2)
```

Preço Médio:
```
=AVERAGEIF(Precos_Ingredientes!C:C,A2,Precos_Ingredientes!E:E)
```

Pior Preço:
```
=MAXIFS(Precos_Ingredientes!E:E,Precos_Ingredientes!C:C,A2)
```

Economia %:
```
=(E2-B2)/E2*100
```

---

### Aba: Pesquisas_Satisfacao

```
| Data Envio | Pedido ID | Cliente | Avaliação | Classificação | Comentário | O que mais gostou | Sugestões | Data Resposta | Ação Tomada |
```

**Exemplo de Dados:**
```
02/02/2024 10:00 | PUD-001 | Maria Silva | 5 | Excelente | Pudim maravilhoso! | Sabor e textura | Mais sabores de frutas | 02/02/2024 15:30 | Agradecimento enviado
```

---

### Aba: Marketing_Posts

```
| Data Publicação | Rede Social | Tipo Post | Conteúdo | Imagem | Status | Curtidas | Comentários | Compartilhamentos | Link |
```

**Tipos de Post:**
- Promoção
- Produto Novo
- Dica/Receita
- Institucional
- Testemunho Cliente

---

### Aba: Relatorio_Semanal

```
| Semana | Ingrediente | Consumo Total | Valor Gasto | Média Diária | Projeção Mensal | Categoria |
```

---

### Aba: Dashboard (Resumo)

Crie visualizações com:

**Vendas:**
```
Total Vendas Mês: =SUMIFS(Pedidos!I:I,Pedidos!B:B,">="&DATE(YEAR(TODAY()),MONTH(TODAY()),1))
Pedidos Mês: =COUNTIFS(Pedidos!B:B,">="&DATE(YEAR(TODAY()),MONTH(TODAY()),1))
Ticket Médio: =[Total Vendas Mês]/[Pedidos Mês]
```

**Estoque:**
```
Valor Total Estoque: =SUM(Estoque_Atual!J:J)
Itens Baixos: =COUNTIF(Estoque_Atual!H:H,"BAIXO")
Itens Esgotados: =COUNTIF(Estoque_Atual!H:H,"ESGOTADO")
```

**Clientes:**
```
Total Clientes: =COUNTA(Clientes!A:A)-1
Novos Este Mês: =COUNTIFS(Clientes!E:E,">="&DATE(YEAR(TODAY()),MONTH(TODAY()),1))
Taxa Retorno: =COUNTIF(Clientes!F:F,">1")/[Total Clientes]
```

---

## Dicas de Configuração

### 1. Proteção de Células

Proteja células com fórmulas:
- Dados → Proteger planilha e intervalos
- Permita apenas células de entrada

### 2. Validação de Dados

Configure listas suspensas:
- Status: Lista de valores
- Sabores: Lista de sabores disponíveis
- Formas de Pagamento: PIX, Cartão, Dinheiro, etc.

**Como fazer:**
```
Dados → Validação de dados → Lista de itens
```

### 3. Formatação Condicional

Use cores para status:
- Verde: Positivo
- Amarelo: Atenção
- Vermelho: Urgente

### 4. Gráficos Úteis

**Vendas por Sabor:**
- Tipo: Pizza
- Dados: Sabor vs Quantidade

**Faturamento Mensal:**
- Tipo: Coluna
- Dados: Mês vs Valor Total

**Estoque por Categoria:**
- Tipo: Barra
- Dados: Categoria vs Valor Estoque

### 5. Filtros

Ative filtros em todas as abas:
- Selecione linha de cabeçalho
- Dados → Criar filtro

---

## Scripts Google Apps Script (Opcional)

### Auto-preencher Data/Hora

```javascript
function onEdit(e) {
  var sheet = e.source.getActiveSheet();
  var range = e.range;
  
  // Se for aba "Pedidos" e coluna for "Status"
  if (sheet.getName() == "Pedidos" && range.getColumn() == 10) {
    // Atualizar "Última Atualização" (coluna 16)
    var row = range.getRow();
    sheet.getRange(row, 16).setValue(new Date());
  }
}
```

### Gerar ID Único

```javascript
function gerarIDPedido() {
  return "PUD-" + new Date().getTime();
}
```

### Notificação por Email

```javascript
function alertarEstoqueBaixo() {
  var sheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("Estoque_Atual");
  var data = sheet.getDataRange().getValues();
  
  var baixos = [];
  
  for (var i = 1; i < data.length; i++) {
    if (data[i][7] == "BAIXO" || data[i][7] == "ESGOTADO") {
      baixos.push(data[i][1]); // Nome do ingrediente
    }
  }
  
  if (baixos.length > 0) {
    MailApp.sendEmail({
      to: "seu-email@exemplo.com",
      subject: "⚠️ Alerta de Estoque Baixo",
      body: "Ingredientes com estoque baixo:\n\n" + baixos.join("\n")
    });
  }
}
```

---

## Como Criar Sua Planilha

1. **Acesse:** [Google Sheets](https://sheets.google.com)
2. **Criar:** Nova planilha em branco
3. **Nomear:** "Gestão Pudins - [Seu Nome]"
4. **Criar abas:** Use os templates acima
5. **Preencher:** Dados iniciais
6. **Testar:** Fórmulas funcionando
7. **Compartilhar:** Com conta do n8n (permissão de editor)

---

## Backup e Segurança

### Backup Automático

**Opção 1:** Google Drive mantém histórico de versões
- Arquivo → Histórico de versões

**Opção 2:** Export periódico
- Use workflow n8n para exportar cópia semanal

### Permissões

- ✅ Você: Proprietário
- ✅ n8n: Editor
- ❌ Outros: Visualizador (se necessário)

---

## Recursos Adicionais

### Add-ons Úteis

1. **Remove Duplicates** - Limpar dados duplicados
2. **Advanced Find and Replace** - Busca avançada
3. **Power Tools** - Ferramentas extras

### Templates Prontos

Google oferece templates gratuitos:
- [Template Gallery](https://docs.google.com/spreadsheets/u/0/template/showcase)

---

**Voltar:** [README Principal](../README.md)
