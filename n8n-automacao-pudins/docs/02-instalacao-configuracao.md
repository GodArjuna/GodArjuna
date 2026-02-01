# Instalação e Configuração do n8n 🛠️

## Escolhendo a Versão do n8n

### Opção 1: n8n Cloud (Recomendado para Iniciantes) ☁️

**Vantagens:**
- ✅ Não precisa instalar nada
- ✅ Sempre atualizado
- ✅ Suporte incluso
- ✅ Comece em 5 minutos
- ✅ Backups automáticos

**Desvantagens:**
- ❌ Limite de execuções no plano gratuito (5.000/mês)
- ❌ Custo mensal após passar do gratuito

**Preço:** 
- Gratuito: até 5.000 execuções/mês
- Starter: $20/mês (20.000 execuções)
- Pro: $50/mês (50.000 execuções)

### Opção 2: Self-Hosted (Auto-hospedado) 🖥️

**Vantagens:**
- ✅ Gratuito e ilimitado
- ✅ Controle total
- ✅ Sem restrições

**Desvantagens:**
- ❌ Requer conhecimento técnico
- ❌ Precisa de servidor
- ❌ Você gerencia atualizações

**Custo:**
- Servidor VPS: R$ 20-50/mês (DigitalOcean, Hostinger, etc.)

## Guia de Instalação - n8n Cloud

### Passo 1: Criar Conta

1. Acesse [n8n.cloud](https://n8n.cloud)
2. Clique em **"Start Free"**
3. Preencha seus dados:
   - Email
   - Senha forte
   - Nome da empresa/projeto
4. Confirme seu email
5. Faça login

✅ **Pronto!** Sua conta n8n está ativa.

### Passo 2: Conhecendo a Interface

Após login, você verá:

```
┌─────────────────────────────────────┐
│  [+] New Workflow    [≡] Menu       │
├─────────────────────────────────────┤
│                                     │
│     Click "+" to add your          │
│     first node                     │
│                                     │
│         [+] Add Node               │
│                                     │
└─────────────────────────────────────┘
```

**Elementos principais:**
- **Workflows**: Lista de automações
- **Credentials**: Suas conexões com serviços
- **Executions**: Histórico de execuções
- **Settings**: Configurações

## Configurando Integrações Essenciais

### 1. Google Sheets 📊

**Por que?** Armazenar pedidos, estoque e relatórios.

**Como configurar:**

1. No n8n, vá em **Credentials** → **New** → **Google Sheets OAuth2 API**
2. Clique em **"Connect my account"**
3. Escolha sua conta Google
4. Autorize o acesso
5. Dê um nome: "Google Sheets - Pudins"
6. Salve

**Criar sua planilha base:**

1. Acesse [Google Sheets](https://sheets.google.com)
2. Crie nova planilha: **"Gestão Pudins"**
3. Crie abas:
   - **Pedidos**: Dados dos clientes e pedidos
   - **Estoque**: Controle de ingredientes
   - **Preços**: Comparação de supermercados
   - **Histórico**: Registro de ações

**Estrutura da aba "Pedidos":**
```
| Data | Nome Cliente | WhatsApp | Sabor | Quantidade | Valor | Status | Pagamento |
```

**Estrutura da aba "Estoque":**
```
| Ingrediente | Unidade | Quantidade Atual | Mínimo | Status | Última Atualização |
```

### 2. Google Forms 📝

**Por que?** Receber pedidos dos clientes.

**Como configurar:**

1. Acesse [Google Forms](https://forms.google.com)
2. Crie novo formulário: **"Pedido de Pudim"**
3. Adicione perguntas:
   - Nome completo (texto curto)
   - WhatsApp com DDD (texto curto)
   - Email (email)
   - Sabor do pudim (múltipla escolha)
     - Tradicional
     - Chocolate
     - Coco
     - Maracujá
     - Leite Condensado
   - Quantidade (número)
   - Data de entrega desejada (data)
   - Forma de pagamento (múltipla escolha)
     - PIX
     - Mercado Pago
     - Dinheiro na entrega
   - Observações (texto longo - opcional)

4. Configurar:
   - **Respostas** → Vincular com Google Sheets
   - Cria automaticamente aba "Respostas ao formulário"

5. Copie o link do formulário para compartilhar

**No n8n:**
1. Use node **"Google Sheets Trigger"**
2. Selecione a planilha do Forms
3. Escolha "On Row Added"
4. Configure para monitorar novas respostas

### 3. WhatsApp 💬

**IMPORTANTE:** WhatsApp Business API oficial é pago e complexo para pequenos negócios.

**Alternativas Práticas:**

#### Opção A: Evolution API (Recomendada) 🌟

**Vantagens:**
- ✅ Gratuita (se auto-hospedar)
- ✅ Conecta WhatsApp real
- ✅ Sem limites
- ✅ API completa

**Como usar:**
1. Contrate serviço que oferece Evolution API:
   - [Z-API](https://www.z-api.io) - R$ 35/mês
   - [Chat-API](https://chat-api.com) - A partir de $39/mês
2. Ou auto-hospede (requer VPS)

**Configuração no n8n:**
1. **Credentials** → **HTTP Request**
2. Adicione:
   - URL base da API
   - API Key
   - Salve como "WhatsApp Evolution"

**Enviar mensagem (exemplo):**
```javascript
URL: https://sua-api.com/message/sendText
Method: POST
Body:
{
  "number": "5511999999999",
  "text": "Olá! Seu pedido foi recebido!"
}
```

#### Opção B: Telegram (Mais Simples) 📨

**Vantagens:**
- ✅ Totalmente gratuito
- ✅ API oficial fácil
- ✅ Configuração em 5 minutos

**Como configurar:**

1. Abra Telegram e procure: **@BotFather**
2. Envie: `/newbot`
3. Escolha nome do bot: "Pudins da Maria Bot"
4. Escolha username: "pudinsdamariabot"
5. Copie o **Token** que ele enviar

**No n8n:**
1. **Credentials** → **Telegram API**
2. Cole o Token
3. Salve como "Telegram Pudins"

#### Opção C: Email (Alternativa Universal) 📧

**Gmail com n8n:**

1. **Credentials** → **Gmail OAuth2**
2. Conecte sua conta Google
3. Autorize envio de emails
4. Salve

### 4. Mercado Pago 💳

**Como obter credenciais:**

1. Acesse [Mercado Pago Developers](https://www.mercadopago.com.br/developers)
2. Faça login
3. Vá em **"Suas integrações"** → **"Criar aplicação"**
4. Preencha:
   - Nome: "Pudins - Sistema de Vendas"
   - Modelo de integração: "Pagamentos online"
5. Copie:
   - **Public Key** (teste e produção)
   - **Access Token** (teste e produção)

**No n8n:**
1. **Credentials** → **HTTP Request**
2. Configure com Access Token
3. Salve como "Mercado Pago"

**Webhooks do Mercado Pago:**
- Configure no painel para receber notificações de pagamento
- URL do webhook será do n8n (veremos no workflow)

### 5. APIs de Supermercados (Web Scraping)

**Desafio:** Supermercados brasileiros geralmente não têm API pública.

**Soluções:**

#### Opção A: Web Scraping Manual
- Use node **"HTTP Request"** + **"HTML Extract"**
- Captura preços de sites
- **Atenção:** Pode quebrar se site mudar

#### Opção B: Google Shopping
- Pesquisa comparativa
- API disponível

#### Opção C: Planilha Manual
- Você atualiza preços 1x por semana
- n8n compara e alerta

**Configuração básica (Scraping):**
```javascript
// Exemplo para capturar preço
1. HTTP Request → URL do produto
2. HTML Extract → Seletor CSS do preço
3. Google Sheets → Salvar preço e data
```

## Criando Seu Primeiro Workflow

### Exemplo: Notificação de Novo Pedido

**Passo 1: Criar Workflow**
1. Clique **"+ New Workflow"**
2. Nome: "01 - Novo Pedido"

**Passo 2: Adicionar Trigger**
1. Clique **"+ Add node"**
2. Procure: "Google Sheets"
3. Escolha: **"Google Sheets Trigger"**
4. Configure:
   - Credential: Sua conta Google
   - Document: Selecione planilha do Forms
   - Sheet: "Respostas ao formulário"
   - Trigger On: "Row Added"

**Passo 3: Adicionar Ação**
1. Conecte um novo node: **"Telegram"**
2. Configure:
   - Credential: Seu bot Telegram
   - Chat ID: Seu ID (descubra com @userinfobot)
   - Text: 
   ```
   🎉 NOVO PEDIDO!
   
   Cliente: {{$json["Nome"]}}
   WhatsApp: {{$json["WhatsApp"]}}
   Sabor: {{$json["Sabor"]}}
   Quantidade: {{$json["Quantidade"]}}
   ```

**Passo 4: Testar**
1. Clique **"Execute Workflow"**
2. Preencha seu formulário de teste
3. Veja a mensagem chegar no Telegram!

**Passo 5: Ativar**
1. Clique no toggle no topo: **"Active"**
2. Workflow agora roda automaticamente!

## Checklist de Configuração Inicial

- [ ] Conta n8n criada e ativa
- [ ] Google Sheets configurado
- [ ] Google Forms criado e vinculado
- [ ] WhatsApp/Telegram configurado
- [ ] Email configurado
- [ ] Mercado Pago ou PagSeguro configurado
- [ ] Planilhas base criadas:
  - [ ] Pedidos
  - [ ] Estoque
  - [ ] Preços
- [ ] Primeiro workflow de teste funcionando

## Recursos de Backup

**Importante:** Sempre faça backup dos workflows!

1. Abra o workflow
2. **Menu** (⋮) → **"Download"**
3. Salva arquivo .json
4. Guarde em local seguro (Google Drive, Dropbox)

**Restaurar:**
1. **"Import from File"**
2. Selecione o .json
3. Pronto!

## Próximos Passos

Agora que tudo está configurado:

👉 **Vamos criar as automações específicas!**

Escolha o que montar primeiro:
- [Automação de Vendas](../workflows/README-vendas.md)
- [Gestão de Estoque](../workflows/README-estoque.md)
- [Monitor de Preços](../workflows/README-precos.md)
- [Marketing e Clientes](../workflows/README-marketing.md)

---

⬅️ **Anterior:** [Introdução ao n8n](01-introducao-n8n.md)  
➡️ **Próximo:** [Boas Práticas](03-boas-praticas.md)
