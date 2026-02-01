# 🍮 n8n Self-Hosted para Windows - Automação Grátis e Ilimitada

## 🎯 O Que É Isto?

Sistema completo de automação para pequenos negócios usando **n8n self-hosted** (100% GRATUITO).

**Perfeito para:** Vendedores de pudins, doces, alimentos, artesanatos e qualquer pequeno negócio.

---

## ✨ Por Que Self-Hosted?

### ❌ n8n Cloud (Pago)
- ❌ 5.000 execuções/mês (limite)
- ❌ Após limite: $20-50/mês (R$ 100-250)
- ❌ Dados na nuvem
- ❌ Depende de internet

### ✅ n8n Self-Hosted (ESTE PROJETO)
- ✅ **Execuções ILIMITADAS**
- ✅ **100% GRATUITO**
- ✅ Dados no seu computador
- ✅ Funciona offline
- ✅ Controle total

**Economia:** R$ 1.680/ano! 💰

---

## 🚀 Início Rápido (Windows)

### 1️⃣ Pré-requisitos

- ✅ Windows 10/11
- ✅ Docker Desktop (instale: https://www.docker.com/products/docker-desktop)
- ✅ VSCode (opcional: https://code.visualstudio.com)

### 2️⃣ Instalação (10 minutos)

```powershell
# 1. Baixe este projeto
# Opção A: Git
git clone https://github.com/GodArjuna/GodArjuna.git
cd GodArjuna\n8n-automacao-pudins\docker

# Opção B: Download ZIP
# Baixe, extraia e navegue até a pasta docker/

# 2. Execute o instalador
.\scripts\setup.ps1

# 3. Responda às perguntas (usuário, senha, IP)
# 4. Aguarde instalação (~10 minutos)
```

### 3️⃣ Acessar

```
http://localhost:5678
```

Login com as credenciais que você configurou!

---

## 📚 Guias Principais

### 🪟 **[WINDOWS-SETUP.md](WINDOWS-SETUP.md)** ⭐ COMECE AQUI
**Guia completo Windows + VSCode:**
- Como baixar e instalar
- Como abrir no VSCode
- Terminal integrado
- Comandos úteis
- Dicas de produtividade

### 🐳 **[docker/](docker/)** - Projeto Docker
- `setup.ps1` - Instalador automático Windows
- `docker-compose.yml` - Configuração
- `.env.example` - Template
- `backup.ps1` - Backup automático

### 📖 **[docs/](docs/)** - Documentação Completa
- Configuração de integrações
- Boas práticas
- Solução de problemas
- Dicas de especialista

### 🤖 **[workflows/](workflows/)** - Automações Prontas
- **Vendas** - Receber pedidos, notificar, registrar
- **Estoque** - Controlar ingredientes, alertas
- **Preços** - Monitorar promoções supermercados
- **Marketing** - CRM, satisfação, redes sociais

---

## 🎯 O Que Você Pode Automatizar

### 💰 Vendas
- Receber pedidos (Google Forms, WhatsApp)
- Registrar em planilha automaticamente
- Notificar cliente e você (WhatsApp/Email/Telegram)
- Atualizar status do pedido
- Registrar pagamentos (Mercado Pago/PagSeguro)

### 📦 Estoque
- Reduzir ingredientes ao vender
- Alertar quando acabar
- Relatórios semanais de consumo
- Sugestões de compra

### 🏪 Monitoramento de Preços
- Verificar preços de ingredientes
- Comparar supermercados
- Avisar promoções
- Relatório semanal de economia

### 🤝 Marketing & CRM
- Pesquisa de satisfação automática
- Enviar ofertas para clientes
- Postar em redes sociais
- Lembrar aniversários

---

## 📂 Estrutura do Projeto

```
n8n-automacao-pudins/
├── 📄 WINDOWS-SETUP.md ⭐ LEIA PRIMEIRO (Windows + VSCode)
├── 📄 README.md (este arquivo)
├── 📄 PROJETO-PRONTO.md (Informações necessárias)
│
├── 📁 docker/ 🐳
│   ├── docker-compose.yml (configuração)
│   ├── .env.example (template)
│   └── scripts/
│       ├── setup.ps1 ⭐ EXECUTE ESTE
│       ├── backup.ps1 (backup)
│       └── health-check.sh
│
├── 📁 docs/ (6 guias detalhados)
│   ├── 01-introducao-n8n.md
│   ├── 02-instalacao-configuracao.md
│   ├── 03-boas-praticas.md
│   ├── 04-solucao-problemas.md
│   ├── 05-servidor-notebook.md
│   └── 06-recomendacoes-especialista.md
│
├── 📁 workflows/ (4 automações prontas)
│   ├── README-vendas.md
│   ├── README-estoque.md
│   ├── README-precos.md
│   └── README-marketing.md
│
└── 📁 examples/ (templates)
    ├── planilhas-templates/
    └── mensagens-templates/
```

---

## 🪟 Abrir no VSCode

### Método 1: Pelo VSCode
```
1. Abra VSCode
2. File → Open Folder (Ctrl+K Ctrl+O)
3. Selecione a pasta: n8n-automacao-pudins
```

### Método 2: Pelo Terminal
```powershell
cd C:\Users\SeuNome\Documents\GodArjuna\n8n-automacao-pudins
code .
```

### Método 3: Pelo Explorer
```
1. Abra a pasta do projeto
2. Botão direito → "Open with Code"
```

**Veja guia completo:** [WINDOWS-SETUP.md](WINDOWS-SETUP.md)

---

## 💻 Comandos Úteis (PowerShell)

```powershell
# Ver status
docker-compose ps

# Ver logs
docker-compose logs -f

# Parar
docker-compose down

# Iniciar
docker-compose up -d

# Reiniciar
docker-compose restart

# Backup
.\scripts\backup.ps1

# Verificar saúde
Invoke-WebRequest http://localhost:5678/healthz
```

---

## ⚙️ Configuração

### Informações Necessárias

Antes de instalar, tenha em mãos:

1. **Usuário e senha** para n8n (você escolhe)
2. **IP do seu PC** (descubra com `ipconfig` no CMD)
3. **Porta** (padrão: 5678, mude se necessário)

### Arquivo de Configuração

Após instalação, edite `docker/.env` se precisar mudar algo:

```env
N8N_USER=admin
N8N_PASSWORD=SuaSenha123!
WEBHOOK_URL=http://192.168.1.100:5678/
N8N_PORT=5678
```

---

## 🎓 Tutorial Passo a Passo

### 1. Instalar Docker Desktop

1. Baixe: https://www.docker.com/products/docker-desktop
2. Execute o instalador
3. Reinicie o computador
4. Abra Docker Desktop
5. Aguarde iniciar (ícone baleia na bandeja)

### 2. Baixar o Projeto

**Opção A: Git (se tiver)**
```powershell
git clone https://github.com/GodArjuna/GodArjuna.git
cd GodArjuna\n8n-automacao-pudins
```

**Opção B: ZIP**
- Baixe: https://github.com/GodArjuna/GodArjuna/archive/refs/heads/main.zip
- Extraia para: `C:\Users\SeuNome\Documents\`

### 3. Executar Instalação

```powershell
# Abra PowerShell
# Navegue até a pasta
cd C:\Users\SeuNome\Documents\GodArjuna\n8n-automacao-pudins\docker

# Execute o instalador
.\scripts\setup.ps1
```

### 4. Responder Perguntas

```
Usuário: admin
Senha: Pudim2024! (ou sua escolha)
IP: S (use o detectado)
Confirmar: S
```

### 5. Aguardar

O script vai:
- Criar configuração
- Baixar n8n (5-10 minutos)
- Iniciar sistema
- Verificar

### 6. Acessar

```
http://localhost:5678
```

Faça login e comece a usar!

---

## 🆘 Problemas Comuns

### Docker não inicia

**Solução:** Abra o Docker Desktop e aguarde o ícone ficar verde

### Erro ao executar .ps1

**Solução:**
```powershell
Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
```

### Porta em uso

**Solução:** Edite `docker/.env` e mude `N8N_PORT=5679`

### Mais problemas?

Veja: `docs/04-solucao-problemas.md`

---

## 📊 Integrações Disponíveis

### Gratuitas e Recomendadas

- ✅ **Google Sheets** - Planilhas automáticas
- ✅ **Google Forms** - Receber pedidos
- ✅ **Gmail** - Enviar emails
- ✅ **Telegram** - Notificações grátis
- ✅ **WhatsApp** - Via Evolution API (grátis)
- ✅ **Mercado Pago** - Receber pagamentos
- ✅ **Instagram/Facebook** - Postar automático

Todas as integrações documentadas em `docs/`!

---

## 💰 Investimento

### Custo Zero!

- n8n: **R$ 0,00** (software gratuito)
- Docker: **R$ 0,00** (gratuito)
- VSCode: **R$ 0,00** (gratuito)
- Execuções: **Ilimitadas** (sem cobrança!)
- Energia: **~R$ 10/mês** (PC ligado 24/7)

**Total:** R$ 10/mês

**vs n8n Cloud (Pago):** Economiza R$ 1.680/ano! 💸

---

## ✅ Checklist Rápido

- [ ] Docker Desktop instalado
- [ ] Docker rodando (ícone verde)
- [ ] Projeto baixado
- [ ] PowerShell aberto
- [ ] Executei `.\scripts\setup.ps1`
- [ ] Instalação concluída
- [ ] Acesso http://localhost:5678 funcionando
- [ ] Fiz login com sucesso
- [ ] Explorei a interface n8n

**Tudo OK?** Próximo passo: importar workflows!

---

## 🚀 Próximos Passos

### 1. Configurar Integrações

Veja `docs/02-instalacao-configuracao.md`:
- Conectar Google Sheets
- Configurar Telegram
- Integrar WhatsApp
- Adicionar Mercado Pago

### 2. Importar Workflows

Veja `workflows/`:
- Automação de Vendas
- Gestão de Estoque
- Monitor de Preços
- CRM e Marketing

### 3. Personalizar

Adapte para seu negócio específico!

### 4. Agendar Manutenção

Configure reinicialização automática:
- Veja: `WINDOWS-SETUP.md` seção de manutenção

---

## 📞 Suporte

### Documentação
- **[WINDOWS-SETUP.md](WINDOWS-SETUP.md)** - Guia Windows + VSCode completo
- **[docker/README-INSTALACAO.md](docker/README-INSTALACAO.md)** - Instalação detalhada
- **[docs/](docs/)** - Todos os guias

### Comunidade
- Forum n8n: https://community.n8n.io
- Docs oficiais: https://docs.n8n.io
- YouTube n8n: https://www.youtube.com/@n8n-io

---

## 🎯 Resumo Ultra-Rápido

```
1. Instale Docker Desktop
2. Baixe este projeto (Git ou ZIP)
3. Abra PowerShell na pasta docker/
4. Execute: .\scripts\setup.ps1
5. Responda perguntas (usuário, senha)
6. Aguarde 10 minutos
7. Acesse: http://localhost:5678
8. Faça login e use!
```

**Tempo total:** 15 minutos
**Custo:** R$ 0,00
**Limite de execuções:** NENHUM!

---

## 🎉 Está Pronto!

Você agora tem:
- ✅ n8n self-hosted funcionando
- ✅ 100% gratuito
- ✅ Execuções ilimitadas
- ✅ Workflows prontos
- ✅ Documentação completa
- ✅ Suporte via comunidade

**Comece a automatizar seu negócio AGORA! 🚀🍮**

---

**Desenvolvido para pequenos empreendedores brasileiros que querem automatizar sem custos! ❤️**
