# n8n Docker - Projeto Pronto para Deploy 🚀

## 🎉 Bem-vindo!

Este diretório contém **tudo que você precisa** para rodar o n8n no seu notebook usando Docker.

---

## 📦 O Que Está Incluído

```
docker/
├── README.md                      # Este arquivo
├── README-INSTALACAO.md           # Guia de instalação detalhado
├── FORMULARIO-CONFIGURACAO.md     # Preencha ANTES de instalar
├── docker-compose.yml             # Configuração do Docker ✅
├── .env.example                   # Template de configuração ✅
├── .gitignore                     # Arquivos a ignorar no Git
│
├── scripts/                       # Scripts prontos
│   ├── setup.sh                   # Instalador Linux/Mac ✅
│   ├── setup.ps1                  # Instalador Windows ✅
│   ├── backup.sh                  # Backup manual Linux/Mac ✅
│   ├── backup.ps1                 # Backup manual Windows ✅
│   └── health-check.sh            # Verificação de saúde ✅
│
├── data/                          # Dados do n8n (criado na instalação)
├── backups/                       # Backups automáticos
├── logs/                          # Logs de operação
└── config/                        # Configurações extras
```

✅ = Arquivo pronto para usar!

---

## 🚀 Início Rápido (3 Passos)

### Passo 1: Preencher Formulário
```bash
# Abra e preencha com suas informações:
FORMULARIO-CONFIGURACAO.md
```

### Passo 2: Executar Instalador

**Windows:**
```powershell
.\scripts\setup.ps1
```

**Linux/Mac:**
```bash
./scripts/setup.sh
```

### Passo 3: Acessar
```
http://localhost:5678
```

**Pronto!** 🎉

---

## 📖 Documentação

### Para Começar
1. **[FORMULARIO-CONFIGURACAO.md](FORMULARIO-CONFIGURACAO.md)** - Preencha PRIMEIRO
2. **[README-INSTALACAO.md](README-INSTALACAO.md)** - Guia completo de instalação
3. **[../REFERENCIA-RAPIDA.md](../REFERENCIA-RAPIDA.md)** - Comandos úteis

### Documentação Completa
- **[../docs/](../docs/)** - Guias detalhados de tudo
- **[../workflows/](../workflows/)** - Workflows prontos para importar
- **[../examples/](../examples/)** - Templates e exemplos

---

## ⚙️ Configuração

### Opção A: Instalação Automática (RECOMENDADO)

Execute o script de setup e ele faz tudo:
- Coleta informações
- Cria arquivo .env
- Configura Docker
- Inicia n8n
- Oferece configurar backups e reinicialização

**Windows:**
```powershell
.\scripts\setup.ps1
```

**Linux/Mac:**
```bash
chmod +x scripts/setup.sh
./scripts/setup.sh
```

### Opção B: Instalação Manual

1. **Copiar template:**
   ```bash
   cp .env.example .env
   ```

2. **Editar .env:**
   ```bash
   # Mude estas linhas:
   N8N_USER=admin
   N8N_PASSWORD=SuaSenhaForte123!
   WEBHOOK_URL=http://SEU_IP:5678/
   ```

3. **Iniciar:**
   ```bash
   docker-compose up -d
   ```

---

## 🔧 Comandos Essenciais

```bash
# Iniciar n8n
docker-compose up -d

# Parar n8n
docker-compose down

# Reiniciar
docker-compose restart

# Ver logs
docker-compose logs -f

# Ver status
docker-compose ps

# Verificar saúde
./scripts/health-check.sh

# Fazer backup
./scripts/backup.sh           # Linux/Mac
.\scripts\backup.ps1          # Windows

# Atualizar n8n
docker-compose pull
docker-compose up -d
```

---

## 📋 Checklist de Instalação

- [ ] Docker instalado e rodando
- [ ] Formulário de configuração preenchido
- [ ] 10GB de espaço livre
- [ ] IP local anotado
- [ ] Usuário e senha definidos
- [ ] Instalador executado
- [ ] n8n acessível no navegador
- [ ] Login funcionando
- [ ] (Opcional) Reinicialização automática configurada
- [ ] (Opcional) Backup automático configurado

---

## 🎯 Informações Necessárias

Antes de instalar, você precisa saber:

### 1. **IP do seu notebook**
   - Windows: `ipconfig`
   - Linux/Mac: `ip a` ou `ifconfig`

### 2. **Usuário e senha**
   - Para fazer login no n8n
   - Senha forte (mínimo 8 caracteres)

### 3. **URL de webhook**
   - Localhost: `http://localhost:5678/`
   - Rede local: `http://SEU_IP:5678/`

### 4. **Recursos**
   - Quanto de RAM alocar (sugestão: 2GB)
   - Quantos CPUs usar (sugestão: 2 cores)

**Dica:** Preencha o [FORMULARIO-CONFIGURACAO.md](FORMULARIO-CONFIGURACAO.md) com antecedência!

---

## 🆘 Problemas?

### n8n não inicia

```bash
# Ver o que está errado
docker-compose logs

# Tentar novamente
docker-compose down
docker-compose up -d
```

### Porta já em uso

Edite `.env` e mude:
```
N8N_PORT=5679  # ou outra porta
```

### Esqueci minha senha

1. Pare: `docker-compose down`
2. Edite `.env` e mude `N8N_PASSWORD`
3. Inicie: `docker-compose up -d`

### Mais ajuda

- **[README-INSTALACAO.md](README-INSTALACAO.md)** - Troubleshooting completo
- **[../docs/04-solucao-problemas.md](../docs/04-solucao-problemas.md)** - Problemas comuns

---

## 🔐 Segurança

### ⚠️ IMPORTANTE

1. **MUDE A SENHA** padrão
2. **NÃO compartilhe** o arquivo `.env` (tem sua senha!)
3. **Use senha forte** (8+ caracteres, letras e números)
4. **Faça backups** regularmente
5. **Não exponha** na internet sem HTTPS

### Arquivo .env

O arquivo `.env` contém suas configurações **incluindo senha**.

- ✅ Está no `.gitignore` (não vai para Git)
- ✅ Mantenha backup em local seguro
- ❌ Nunca compartilhe publicamente

---

## 📊 Estrutura Após Instalação

```
docker/
├── .env                        # Suas configurações (PRIVADO)
├── docker-compose.yml          # Configuração Docker
│
├── data/                       # Dados do n8n
│   ├── database.sqlite         # Banco de dados
│   └── .n8n/                   # Workflows e configs
│
├── backups/                    # Seus backups
│   ├── manual-backup-*.tar.gz
│   └── auto-backup-*.tar.gz
│
├── logs/                       # Logs do sistema
│   └── restart.log
│
└── scripts/                    # Scripts úteis
    ├── setup.*
    ├── backup.*
    └── health-check.sh
```

---

## 🎓 Próximos Passos

Após instalar o n8n:

### 1. Configurar Integrações
Siga `../docs/02-instalacao-configuracao.md` para configurar:
- Google Sheets
- Google Forms
- Telegram/WhatsApp
- Mercado Pago

### 2. Importar Workflows
Veja workflows prontos em `../workflows/`:
- Automação de Vendas
- Gestão de Estoque
- Monitoramento de Preços
- Marketing e CRM

### 3. Personalizar
Adapte os workflows para seu negócio específico.

### 4. Monitorar
Use `./scripts/health-check.sh` para verificar saúde do sistema.

---

## 💰 Custo

- **Software**: R$ 0,00 (100% gratuito)
- **Execuções**: Ilimitadas
- **Energia**: ~R$ 10/mês (notebook 24/7)

**Total**: R$ 10/mês

**Economia vs Cloud**: R$ 1.680/ano! 💰

---

## 📞 Suporte

- **Documentação**: `../docs/`
- **Referência Rápida**: `../REFERENCIA-RAPIDA.md`
- **Troubleshooting**: `../docs/04-solucao-problemas.md`
- **Comunidade n8n**: https://community.n8n.io

---

## ✅ Pronto para Começar?

1. Preencha: **[FORMULARIO-CONFIGURACAO.md](FORMULARIO-CONFIGURACAO.md)**
2. Execute: `./scripts/setup.sh` (ou `.ps1` no Windows)
3. Acesse: **http://localhost:5678**

**Tempo total**: 10-15 minutos

**Boa sorte com suas automações! 🍮🚀**

---

**Desenvolvido com ❤️ para pequenos empreendedores**
