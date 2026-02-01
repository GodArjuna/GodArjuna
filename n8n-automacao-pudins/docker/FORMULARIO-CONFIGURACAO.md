# Formulário de Configuração - n8n Automação de Pudins 📋

## 📝 Informações Necessárias Para Instalação

Preencha este formulário ANTES de executar a instalação. Isso tornará o processo muito mais rápido!

---

## 1️⃣ Informações do Sistema

### Seu Sistema Operacional
- [ ] Windows 10/11
- [ ] Linux (Ubuntu/Debian)
- [ ] Linux (Fedora/CentOS)
- [ ] macOS
- [ ] Outro: _______________

### Versão do Docker
Execute `docker --version` e anote:
```
Docker version: _____________________
```

### IP do Seu Notebook/Computador

**Como descobrir:**

**Windows:**
```cmd
ipconfig
```
Procure por "Endereço IPv4" na seção "Ethernet" ou "Wi-Fi"

**Linux/Mac:**
```bash
ip a
# ou
ifconfig
```
Procure por endereço que começa com 192.168.x.x ou 10.x.x.x

**Anote aqui:**
```
Meu IP local: ___________________
Exemplo: 192.168.1.100
```

---

## 2️⃣ Credenciais de Acesso

### Usuário n8n
O nome de usuário para fazer login no n8n.

```
Usuário escolhido: ___________________
Sugestão: admin, seu_nome, pudins_admin
```

### Senha n8n
Senha forte para proteger seu sistema.

**Regras:**
- Mínimo 8 caracteres
- Inclua letras maiúsculas e minúsculas
- Inclua números
- Inclua símbolos (@, #, !, etc.)

```
Senha escolhida: ___________________
(Anote em local seguro!)

Exemplos de senhas fortes:
- Pudim@2024!Seguro
- N8n#Automacao2024
- MinhaSenha!123Strong
```

---

## 3️⃣ Configuração de Rede

### URL para Webhooks

Escolha uma das opções:

**Opção A: Uso Local (apenas neste computador)**
```
WEBHOOK_URL=http://localhost:5678/
```

**Opção B: Rede Local (acessar de outros dispositivos da rede)**
```
WEBHOOK_URL=http://SEU_IP_LOCAL:5678/
Exemplo: http://192.168.1.100:5678/
```

**Opção C: Acesso Externo (com VPN/Ngrok)**
```
WEBHOOK_URL=https://seu-dominio.com/
(Configure depois com Tailscale ou Ngrok)
```

**Minha escolha:**
```
WEBHOOK_URL: ___________________
```

### Porta

Porta onde n8n vai rodar (padrão: 5678)

```
Porta: _____ (deixe 5678 se não tiver conflito)
```

---

## 4️⃣ Recursos do Sistema

### Memória RAM Disponível

Quanto de RAM seu computador tem?

```
RAM Total: _____ GB

Alocar para n8n: _____ GB
Sugestões:
- 4GB RAM total → Alocar 1GB
- 8GB RAM total → Alocar 2GB
- 16GB+ RAM total → Alocar 4GB
```

### CPU

Quantos núcleos de CPU?

```
CPU Cores: _____

Alocar para n8n: _____ cores
Sugestões:
- 2 cores total → Alocar 1 core
- 4 cores total → Alocar 2 cores
- 8+ cores total → Alocar 4 cores
```

---

## 5️⃣ Preferências de Manutenção

### Reinicialização Automática

Com que frequência reiniciar o n8n automaticamente?

- [ ] A cada 3 dias (recomendado para uso intenso)
- [ ] A cada 7 dias (recomendado para uso normal) ⭐
- [ ] A cada 14 dias (para uso leve)
- [ ] Não configurar agora (fazer manual)

**Horário de reinicialização:**
```
Horário: ___:___ (padrão: 03:00 - 3h da manhã)
```

### Backup Automático

- [ ] Sim, quero backup diário automático ⭐
- [ ] Apenas backup manual (quando eu executar)

**Horário do backup (se automático):**
```
Horário: ___:___ (padrão: 02:00 - 2h da manhã)
```

### Retenção de Dados

Por quantos dias manter histórico de execuções?

```
_____ dias

Sugestões:
- 3 dias (economiza espaço)
- 7 dias (recomendado) ⭐
- 14 dias (mais histórico)
```

---

## 6️⃣ Integrações (Configurar Depois)

Marque quais integrações você pretende usar:

### Formulários
- [ ] Google Forms
- [ ] Typeform
- [ ] Jotform
- [ ] Outro: _______________

### Planilhas
- [ ] Google Sheets ⭐
- [ ] Microsoft Excel Online
- [ ] Airtable

### Comunicação
- [ ] Telegram ⭐
- [ ] WhatsApp (via Evolution API)
- [ ] Email (Gmail) ⭐
- [ ] SMS

### Pagamento
- [ ] Mercado Pago
- [ ] PagSeguro
- [ ] PayPal
- [ ] Outro: _______________

### Redes Sociais
- [ ] Instagram
- [ ] Facebook
- [ ] Twitter
- [ ] TikTok

---

## 7️⃣ Informações do Negócio

### Nome do Negócio
```
_____________________________________
```

### Produtos/Sabores de Pudins
```
1. _____________________
2. _____________________
3. _____________________
4. _____________________
5. _____________________
```

### Ingredientes Principais
```
1. _____________________
2. _____________________
3. _____________________
4. _____________________
5. _____________________
```

### Volume Estimado
```
Pedidos por dia: _____ (estimativa)
Pedidos por mês: _____ (estimativa)
```

---

## 8️⃣ Checklist Pré-Instalação

Antes de executar o instalador, verifique:

- [ ] Docker está instalado e rodando
- [ ] Tenho 10GB de espaço livre em disco
- [ ] Tenho pelo menos 4GB de RAM
- [ ] Anotei meu IP local
- [ ] Escolhi usuário e senha forte
- [ ] Decidi URL de webhook
- [ ] Li a documentação básica
- [ ] Fiz backup de dados importantes (se for migração)
- [ ] Tenho 15 minutos disponíveis agora

---

## 9️⃣ Plano de Ação

Após preencher este formulário:

### Próximos Passos:

1. **[ ] Executar instalador**
   - Windows: `.\scripts\setup.ps1`
   - Linux/Mac: `./scripts/setup.sh`

2. **[ ] Fornecer informações** durante instalação
   (usar as respostas acima)

3. **[ ] Acessar n8n** no navegador
   - URL: http://localhost:5678 ou http://SEU_IP:5678

4. **[ ] Fazer primeiro login**
   - Usuário e senha que você configurou

5. **[ ] Configurar integrações**
   - Seguir guias em `docs/02-instalacao-configuracao.md`

6. **[ ] Importar workflows**
   - Ver workflows prontos em `workflows/`

7. **[ ] Testar automações**
   - Começar com workflow de vendas

8. **[ ] Configurar backup** (se não fez automaticamente)

---

## 🎯 Resumo das Suas Configurações

Anote aqui um resumo para referência rápida:

```
========================================
CONFIGURAÇÃO DO MEU N8N
========================================

IP Local: _________________
Usuário: _________________
Senha: _________________ (SEGREDO!)
URL: _________________
Porta: _________________

Reiniciar a cada: _____ dias
Backup: [ ] Sim [ ] Não
Histórico: _____ dias

Integrações principais:
- _________________
- _________________
- _________________

Data de instalação: ___/___/______
========================================
```

---

## 📞 Precisa de Ajuda?

- **Documentação**: `../docs/`
- **Guia de Instalação**: `README-INSTALACAO.md`
- **Referência Rápida**: `../REFERENCIA-RAPIDA.md`
- **Comunidade n8n**: https://community.n8n.io

---

## ✅ Pronto?

Após preencher este formulário, você está pronto para instalar!

Execute:
- **Windows**: `.\scripts\setup.ps1`
- **Linux/Mac**: `./scripts/setup.sh`

**Tempo estimado**: 10-15 minutos

**Boa sorte! 🍮🚀**
